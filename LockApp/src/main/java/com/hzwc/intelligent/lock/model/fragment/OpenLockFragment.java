package com.hzwc.intelligent.lock.model.fragment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzwc.intelligent.lock.BuildConfig;
import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.activity.LoginActivity;
import com.hzwc.intelligent.lock.model.activity.OpenLockActivity;
import com.hzwc.intelligent.lock.model.activity.RecordActivity;
import com.hzwc.intelligent.lock.model.adapter.ListAdapter;
import com.hzwc.intelligent.lock.model.bean.BaseBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.Update;
import com.hzwc.intelligent.lock.model.utils.ActivityUtils;
import com.hzwc.intelligent.lock.model.utils.Comutil;
import com.hzwc.intelligent.lock.model.utils.SecurityRSA;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.utils.ToastUtil;
import com.hzwc.intelligent.lock.model.view.persenter.OpenLockPresenter;
import com.hzwc.intelligent.lock.model.view.view.OpenLockView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractBaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hzwc.intelligent.lock.model.adapter.ListAdapter.UNLOCKING;

/**
 * Created by anna on 2018/2/24.
 */
@CreatePresenter(OpenLockPresenter.class)
public class OpenLockFragment extends AbstractBaseFragment<OpenLockView, OpenLockPresenter> implements View.OnClickListener, OpenLockView {


    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.rl_unlocking)
    RecyclerView rlUnlocking;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.iv_title_scan)
    ImageButton ivTitleScan;
    private View mView;
    private Unbinder unbinder;
    private ListAdapter mListAdapter;
    private List<UnlocksBean.UnlockBean> mUnlockList = new ArrayList<>();
    private Map<String, Boolean> mIsLockMap = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_install, container, false);
        unbinder = ButterKnife.bind(this, mView);
        System.out.println(SpUtils.getString(getContext(), "token", " "));
        initView();
        initRl();
        InitBlue();



        getMvpPresenter().update();



        return mView;
    }


    private void DownloadNewApk(String  str,String path) {
        File installFile = new File(path);
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(str);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationUri(Uri.fromFile(installFile));
        long downloadId = downloadManager.enqueue(request);
        registerDownLoadFinishReceiver(downloadId,path);

    }

    private void registerDownLoadFinishReceiver(long downloadId,String  path) {
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == reference) {
                    // 下载完成后取消监听
                    context.unregisterReceiver(this);
                    // 安装应用
                    Log.e("download","xiazai wanc");
                  installApp(path);
                }
            }
        };
        getActivity().registerReceiver(receiver, filter);
    }

    private void installApp(String apkPath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //版本在7.0以上是不能直接通过uri访问的
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File file = (new File(apkPath));
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            startActivity(intent);
            //必须加入，不然不会显示安装成功界面
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getInstallFilePath(String  version) {
        String filePackagePath = getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";
        String fileName = version + ".apk";

        String installFilePath = filePackagePath + fileName;

        return installFilePath;
    }


    private void InitBlue() {
        getMvpPresenter().jurisdiction(getContext());
        getMvpPresenter().bluetoothState();
        getMvpPresenter().connectStomp();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setupListener() {

    }

    private void initView() {
        tvTitleText.setText(getResources().getString(R.string.unlocking));
        tvSearch.setText(getResources().getString(R.string.record));
        tvSearch.setVisibility(View.VISIBLE);
        tvSearch.setOnClickListener(this);
        ivTitleScan.setVisibility(View.VISIBLE);
        ivTitleScan.setOnClickListener(this);
    }

    private void initRl() {
        rlUnlocking.setLayoutManager(new LinearLayoutManager(getContext()));
        mListAdapter = new ListAdapter(getContext());
        mListAdapter.setViewType(UNLOCKING);
        rlUnlocking.setAdapter(mListAdapter);
        onItemClick();
    }

    private void onItemClick() {
        mListAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UnlocksBean.UnlockBean unlockBean = mUnlockList.get(position);


                Log.e("sssssss",new Gson().toJson(unlockBean)+"~~~~~~~~~~");
                if (unlockBean.getLockState() == 2) {//正常开锁
                    Intent intent = new Intent(getActivity(), OpenLockActivity.class);
                    intent.putExtra("isLock", mIsLockMap.get(unlockBean.getLockNo()));
                    intent.putExtra("lockNo", unlockBean.getLockNo());
                    intent.putExtra("password",SecurityRSA.decode(unlockBean.getPassword()) );

                    intent.putExtra("cabinetName", unlockBean.getCabinetName());
                    intent.putExtra("lockId", unlockBean.getLockId());
                    String str=SecurityRSA.decode(unlockBean.getKeyCode());

                    intent.putExtra("key", Comutil. string2Byte(str));
                    intent.putExtra("close",unlockBean.getIsCloseLock());



                    startActivity(intent);
                } else if (unlockBean.getLockState() == 3) {//无权限
                    getMvpPresenter().getDialogView(getContext().getResources().getString(R.string.hint_03), getActivity(), 3, unlockBean.getLockNo());
                } else if (unlockBean.getLockState() == 4) {//权限审核
                    getMvpPresenter().getDialogView(getContext().getResources().getString(R.string.hint_05), getActivity(), 4, null);
                } else if (unlockBean.getLockState() == 1) {//权限过期
                    getMvpPresenter().getDialogView(getContext().getResources().getString(R.string.hint_04), getActivity(), 1, unlockBean.getLockNo());
                } else {
                    getMvpPresenter().getDialogView(getContext().getResources().getString(R.string.hint_08), getActivity(), 4, null);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search://记录
                Intent intent = new Intent(getActivity(), RecordActivity.class);
                intent.putExtra("a", true);
                startActivity(intent);
                break;
            case R.id.iv_title_scan://扫描
                getMvpPresenter().searchDevice();
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void unLockSuccess(UnlocksBean result) {



        if (result.getCode() == 0) {
            for (UnlocksBean.UnlockBean unlockBean : mUnlockList) {
                if (unlockBean.getLockNo().equals(result.getUnlock().getLockNo())) {
                    mUnlockList.remove(unlockBean);
                    mUnlockList.add(result.getUnlock());
                    break;
                }
            }

            mListAdapter.setUnlockList(mUnlockList);
            mListAdapter.notifyDataSetChanged();
        } else if(result.getCode() == 95598) {
            SpUtils.setBoolean(getContext(), "isLogin", false);
            Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
            ActivityUtils.startActivityAndFinish(getActivity(), LoginActivity.class);
        }
    }

    @Override
    public void unlockApplySuccess(BaseBean result) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(result);
        Log.e("awj ", "unlockApplySuccess ="+jsonStr);
        if (result.getCode() == 0) {
            Toast.makeText(getContext(), getContext().getString(R.string.hint_07), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchStarted() {
        mUnlockList.clear();
        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(getContext().getString(R.string.device_founded));
        mListAdapter.setUnlockList(mUnlockList);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceFounded(String lockNo, boolean isLock) {
        tvNoData.setVisibility(View.GONE);
        UnlocksBean.UnlockBean unlockBean = new UnlocksBean.UnlockBean();
        unlockBean.setLockNo(lockNo);
        mIsLockMap.put(lockNo, isLock);
        mUnlockList.add(unlockBean);
        mListAdapter.setUnlockList(mUnlockList);
        mListAdapter.notifyDataSetChanged();
        getMvpPresenter().unlock(SpUtils.getString(mContext, "token", ""), lockNo, SpUtils.getInt(mContext, "userId", -1));
    }

    @Override
    public void onSearchStopped() {
        tvNoData.setText(getContext().getString(R.string.not_data));
    }

    @Override
    public void dataFailure(String result) {

    }

    @Override
    public void onUpdate(Update update) {
        if (getVersionCode()<(update.getData().getAppCode())){


            AlertDialog  dialog=new AlertDialog.Builder(getActivity())
                    .setMessage(update.getData().getAppInfo())
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String path=getInstallFilePath(update.getData().getAppVersion());
                            File f=new File(path) ;
                            dialog.dismiss();

                            if (!f.exists()){
                                DownloadNewApk(update.getData().getAppUrl(),path);
                            }else {
                                installApp(path
                                );
                            }


                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).show();




        }



    }

    private int getVersionCode() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getActivity().getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getActivity().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 999;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        getMvpPresenter().onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpPresenter().searchDevice();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            getMvpPresenter().stopSearchDevice();
        } else {
            getMvpPresenter().searchDevice();
        }
    }

}

