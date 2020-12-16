package com.hzwc.intelligent.lock.model.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.LocationWarnsBean;
import com.hzwc.intelligent.lock.model.bean.NoCloseInfo;
import com.hzwc.intelligent.lock.model.bean.PowerWarnsBean;
import com.hzwc.intelligent.lock.model.bean.RestsWarnBean;
import com.hzwc.intelligent.lock.model.bean.UnlockApplyBean;
import com.hzwc.intelligent.lock.model.bean.UnlocksBean;
import com.hzwc.intelligent.lock.model.bean.UserLockBean;
import com.hzwc.intelligent.lock.model.bean.UserRamBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ANN_TIM
 *         Created by 80002796 on 2017/12/12.
 *         <p>
 *         <p>
 *         用于列表适配器
 *         加载各种布局
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> implements View.OnClickListener {

    private static final String TAG = ListAdapter.class.getSimpleName();

    /* 布局类型 */

    /**
     * 下拉框条目
     */
    public static final int EQUIPMENT_ONE = 0;
    public static final int DOWN_BOX = 1;
    public static final int OPEN_LOCK_HISTORY = 4;
    //UNLOCKING UNDERVOLTAGE LOCATION WARNING_ELSE 共用一个item
    public static final int UNLOCKING = 5;
    public static final int UNDERVOLTAGE = 6;
    public static final int LOCATION = 7;
    public static final int WARNING_ELSE = 8;
    public  static  final  int NOCLOSE=22;
    public static final int INSTALL = 9;
    public static final int USER_LOCK = 10;
    public static final int USER_RAM = 11;
    public static final int INSTALL_LOCK = 12;


    /**
     * 当前布局的类型
     */
    private int mCurrentType = EQUIPMENT_ONE;
    private final LayoutInflater mLayoutInflater;
    private Context mContext;


    /**
     * 哪个数据请加备注 ,set对应数据
     */

    //下拉框对应的数据
    private List<String> mDownBox = new ArrayList<>();
    private List<String> mInstallList = new ArrayList<>();
    private List<String> mUnlockingList = new ArrayList<>();
    private List<String> mInstallLock = new ArrayList<>();
    private List<UserLockBean.InstallUserLocksBean> mUserLockList = new ArrayList<>();
    private List<UserRamBean.InstallUserCabinetsBean> mUserRamList = new ArrayList<>();

    public List<UnlockApplyBean.UnlockRecordEntityBean.ListBean> getUnlockRecordAllBeanList() {
        return mUnlockRecordAllBeanList;
    }

    private List<UnlockApplyBean.UnlockRecordEntityBean.ListBean> mUnlockRecordAllBeanList = new ArrayList<>();

    public List<PowerWarnsBean.WarnsBean.ListBean> getPowerWarnsBeanList() {
        return mPowerWarnsBeanList;
    }

    private List<PowerWarnsBean.WarnsBean.ListBean> mPowerWarnsBeanList = new ArrayList<>();

    public List<LocationWarnsBean.WarnsBean.ListBean> getLocationWarnsBeanList() {
        return mLocationWarnsBeanList;
    }

    private List<LocationWarnsBean.WarnsBean.ListBean> mLocationWarnsBeanList = new ArrayList<>();

    public List<RestsWarnBean.WarnsBean.ListBean> getRestsWarnBeanBeanList() {
        return mRestsWarnBeanBeanList;
    }

    private List<RestsWarnBean.WarnsBean.ListBean> mRestsWarnBeanBeanList = new ArrayList<>();
    private List<UnlocksBean.UnlockBean> mUnlockList = new ArrayList<>();

    public void setRestsWarnBeanBeanList(List<RestsWarnBean.WarnsBean.ListBean> mRestsWarnBeanBeanList) {
        this.mRestsWarnBeanBeanList.addAll(mRestsWarnBeanBeanList);
    }
    private  List<NoCloseInfo> noCloseInfoList=new ArrayList<>();

    public void setNoCloseInfoList(List<NoCloseInfo> noCloseInfoList) {
        this.noCloseInfoList = noCloseInfoList;
    }

    public void clearRestsWarnBeanBeanList() {
        mRestsWarnBeanBeanList.clear();
    }
    public void clearNoclose() {
        noCloseInfoList.clear();
    }
    public void setLocationWarnsBeanList(List<LocationWarnsBean.WarnsBean.ListBean> mLocationWarnsBeanList) {
        this.mLocationWarnsBeanList.addAll(mLocationWarnsBeanList);
    }

    public void clearLocationWarnsBeanList() {
        mLocationWarnsBeanList.clear();
    }

    public void setPowerWarnsBeanList(List<PowerWarnsBean.WarnsBean.ListBean> mPowerWarnsBeanList) {
        this.mPowerWarnsBeanList.addAll(mPowerWarnsBeanList);
    }

    public void clearPowerWarnsBeanList() {
        mPowerWarnsBeanList.clear();
    }

    public void setUnlockList(List<UnlocksBean.UnlockBean> mUnlockList) {
        this.mUnlockList = mUnlockList;
    }


    public void setInstallList(List<String> mInstallList) {
        this.mInstallList = mInstallList;
    }

    public void setInstallLockList(List<String> mInstallList) {
        this.mInstallLock = mInstallList;
    }

    public void setUnlockApplyBeanList(List<UnlockApplyBean.UnlockRecordEntityBean.ListBean> mUnlockApplyBeanList) {
        this.mUnlockRecordAllBeanList.addAll(mUnlockApplyBeanList);
    }
    public List<NoCloseInfo> getNoCloseList() {
        return noCloseInfoList;
    }

    public void clearUnlockApplyBeanList() {
        mUnlockRecordAllBeanList.clear();
    }

    public void setUnlockingList(List<String> mUnlockingList) {
        this.mUnlockingList = mUnlockingList;
    }

    public void setDownBox(List<String> downBox) {
        this.mDownBox = downBox;
    }


    public void setUserLockList(List<UserLockBean.InstallUserLocksBean> mUserLockBean) {
        this.mUserLockList = mUserLockBean;
    }

    public void setUserRamList(List<UserRamBean.InstallUserCabinetsBean> mUserRamBean) {
        this.mUserRamList = mUserRamBean;
    }

    public ListAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置布局类型
     *
     * @param type
     */
    public void setViewType(int type) {
        this.mCurrentType = type;
        Log.e("awj", "type === " + type);

    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View container = null;
        switch (mCurrentType) {

            case DOWN_BOX:
                container = mLayoutInflater.inflate(R.layout.item_listview_popwin, parent, false);
                break;
            case UNLOCKING:
            case UNDERVOLTAGE:
            case LOCATION:
            case NOCLOSE:
            case WARNING_ELSE:
                container = mLayoutInflater.inflate(R.layout.item_all_lock, parent, false);
                break;
            case OPEN_LOCK_HISTORY:
                container = mLayoutInflater.inflate(R.layout.item_station, parent, false);
                break;
            case INSTALL:
                container = mLayoutInflater.inflate(R.layout.item_install, parent, false);
                break;
            case USER_LOCK:
                container = mLayoutInflater.inflate(R.layout.item_lock, parent, false);
                break;
            case USER_RAM:
                container = mLayoutInflater.inflate(R.layout.item_ram, parent, false);
                break;
            case INSTALL_LOCK:
                container = mLayoutInflater.inflate(R.layout.item_add_install, parent, false);
                break;




            default:

                break;
        }
        container.setOnClickListener(this);
        return new ListViewHolder(container);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {

        switch (mCurrentType) {

            case DOWN_BOX:
                holder.mPopitemTv.setText(mDownBox.get(position));
                holder.itemView.setTag(position);
                break;
            case UNLOCKING:
                holder.mTvLockCord.setVisibility(View.VISIBLE);
                holder.mTvLockMac.setText(String.format(mContext.getString(R.string.lock_mac), mUnlockList.get(position).getLockNo()));
                holder.mTvLockCabinet.setText(TextUtils.isEmpty(mUnlockList.get(position).getCabinetName()) ? String.format(mContext.getString(R.string.lock_cabinet), mContext.getString(R.string.no_data)) : String.format(mContext.getString(R.string.lock_cabinet), mUnlockList.get(position).getCabinetName()));
                holder.mTvLockCord.setText(TextUtils.isEmpty(mUnlockList.get(position).getLockCode()) ? String.format(mContext.getString(R.string.lock_cord), mContext.getString(R.string.no_data)) : String.format(mContext.getString(R.string.lock_cord), mUnlockList.get(position).getLockCode()));
                if (mUnlockList.get(position).getLockState() == 2) {
                    holder.mTvStationNub.setVisibility(View.GONE);
                } else {
                    if (mUnlockList.get(position).getLockState() == 1) {
                        holder.mTvStationNub.setText(mContext.getString(R.string.past));
                    } else if (mUnlockList.get(position).getLockState() == 3) {
                        holder.mTvStationNub.setText(mContext.getString(R.string.no_jurisdiction));
                    } else if (mUnlockList.get(position).getLockState() == 4) {
                        holder.mTvStationNub.setText(mContext.getString(R.string.jurisdiction_audit));
                    }else {
                        holder.mTvStationNub.setText(mContext.getString(R.string.jurisdiction_abnormal));
                    }
                    holder.mTvStationNub.setVisibility(View.VISIBLE);
                }
                holder.itemView.setTag(position);
                break;
            case OPEN_LOCK_HISTORY:
                holder.mIvUnlockRecord.setImageDrawable(mContext.getDrawable(R.mipmap.record_lock));
                holder.mTvRecordMac.setText(String.format(mContext.getString(R.string.lock_mac), mUnlockRecordAllBeanList.get(position).getLockNo()));
                holder.mIvRecordNub.setText(String.format(mContext.getString(R.string.lock_ram), mUnlockRecordAllBeanList.get(position).getCabinetName()));
                holder.mTvRecordTime.setText(String.format(mContext.getString(R.string.open_lock_time), mUnlockRecordAllBeanList.get(position).getUnlockTime()));
                holder.itemView.setTag(position);
                break;
            case UNDERVOLTAGE:
                holder.mIvRecordLock.setImageDrawable(mContext.getDrawable(R.mipmap.undervoltage));
                holder.mTvLocationLockMac.setVisibility(View.GONE);
                holder.mTvLockMac.setVisibility(View.VISIBLE);
                holder.mTvLockCabinet.setVisibility(View.VISIBLE);
                holder.mTvLockMac.setText(String.format(mContext.getString(R.string.lock_mac), mPowerWarnsBeanList.get(position).getLockNo()));
                holder.mTvLockCabinet.setText(String.format(mContext.getString(R.string.warn_quantity), mPowerWarnsBeanList.get(position).getPower()));
                holder.itemView.setTag(position);
                break;
            case LOCATION:
                holder.mIvRecordLock.setImageDrawable(mContext.getDrawable(R.mipmap.ic_ram_blue));
                holder.mTvLocationLockMac.setVisibility(View.VISIBLE);
                holder.mTvLockMac.setVisibility(View.GONE);
                holder.mTvLockCabinet.setVisibility(View.GONE);
                holder.mTvLocationLockMac.setText(String.format(mContext.getString(R.string.lock_mac), mLocationWarnsBeanList.get(position).getLockNo()));
                holder.itemView.setTag(position);
                break;
            case WARNING_ELSE:
                holder.mIvRecordLock.setImageDrawable(mContext.getDrawable(R.mipmap.warning_else));
                holder.mTvLocationLockMac.setVisibility(View.GONE);
                holder.mTvLockMac.setVisibility(View.VISIBLE);
                holder.mTvLockCabinet.setVisibility(View.VISIBLE);
                holder.mTvLockMac.setText(String.format(mContext.getString(R.string.lock_mac), mRestsWarnBeanBeanList.get(position).getLockNo()));
                holder.mTvLockCabinet.setText(String.format(mContext.getString(R.string.summarize), mRestsWarnBeanBeanList.get(position).getInfos()));
                holder.itemView.setTag(position);
                break;

            case NOCLOSE:
                holder.mTvLockMac.setVisibility(View.VISIBLE);
                holder.mTvLockMac.setText(String.format(mContext.getString(R.string.lock_mac), noCloseInfoList.get(position).getLockNo()));
                holder.mTvLockCabinet.setText(String.format(mContext.getString(R.string.lock_cabinet), noCloseInfoList.get(position).getCabinetName()));

                holder.itemView.setTag(position);
                break;
            case INSTALL:
                holder.mTvName.setText(mInstallList.get(position));
                if (position == 0)
                    holder.mBgView.setVisibility(View.VISIBLE);
                break;
            case USER_LOCK:
                Log.e("awj", "name ==" + mUserLockList.get(position).getCabinetName());

                holder.tvRamCabinet.setText(String.format(mContext.getString(R.string.lock_mac),mUserLockList.get(position).getLockNo()+ "" ));
//                holder.tvRamCabinet.setText(mUserLockList.get(position).getCabinetName());
                holder.tvLockMac.setText(String.format(mContext.getString(R.string.lock_cabinet),  mUserLockList.get(position).getCabinetName()));
                holder.tvRamLocation.setText(String.format(mContext.getString(R.string.tv_ram_location), mUserLockList.get(position).getAddr() + ""));
                holder.itemView.setTag(position);
                break;
            case USER_RAM:


                Log.e("awj", "name 1==" + mUserRamList.get(position).getCabinetName());
                Log.e("awj", "name 2==" + mUserRamList.get(position).getAddr());
                holder.tvRamName.setText(String.format(mContext.getString(R.string.lock_ram), mUserRamList.get(position).getCabinetName() + ""));
                holder.tvRamLocation.setText(String.format(mContext.getString(R.string.tv_ram_location), mUserRamList.get(position).getAddr() + ""));
                holder.itemView.setTag(position);
                break;
            case INSTALL_LOCK:

                holder.itemView.setTag(position);
                break;

            default:

                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (mCurrentType) {

            case DOWN_BOX:
                return mDownBox.size();
            case UNLOCKING:
                return mUnlockList.size();
            case OPEN_LOCK_HISTORY:
                return mUnlockRecordAllBeanList.size();
            case UNDERVOLTAGE:
                return mPowerWarnsBeanList.size();
            case LOCATION:
                return mLocationWarnsBeanList.size();
            case WARNING_ELSE:
                return mRestsWarnBeanBeanList.size();
            case INSTALL:
                return mInstallList.size();
            case USER_LOCK:
                Log.e("awj", "size mUserLockList =" + mUserLockList.size());
                return mUserLockList.size();
            case USER_RAM:
                Log.e("awj", "size mUserRamList =" + mUserRamList.size());
                return mUserRamList.size();
            case INSTALL_LOCK:

                return mInstallLock.size();

            case 22:
                return  noCloseInfoList.size();

            default:
                return 0;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView mPopitemTv;
        private ImageView mIvRecordLock;
        private TextView mTvLocationLockMac;
        private TextView mTvLockMac;
        private TextView mTvLockCabinet;
        private TextView mTvStationNub;
        private TextView mTvName;
        private View mBgView;
        private TextView tvLockMac;
        private TextView tvRamCabinet;
        private TextView tvRamName;
        private TextView tvRamLocation;
        private EditText etMac;
        private ImageView mIvUnlockRecord;
        private TextView mTvRecordMac;
        private TextView mIvRecordNub;
        private TextView mTvRecordTime;
        private ImageView ivInstallZxing;
        private EditText etInstallMac;
        private TextView mTvLockCord;


        ListViewHolder(View itemView) {
            super(itemView);

            switch (mCurrentType) {

                case DOWN_BOX:
                    mPopitemTv = itemView.findViewById(R.id.listview_popwind_tv);
                    break;
                case OPEN_LOCK_HISTORY:
                    mIvUnlockRecord = itemView.findViewById(R.id.iv_unlock_record);
                    mTvRecordMac = itemView.findViewById(R.id.tv_station_list_name);
                    mIvRecordNub = itemView.findViewById(R.id.tv_station_nub);
                    mTvRecordTime = itemView.findViewById(R.id.tv_record_time);
                    break;
                case UNLOCKING:
                case UNDERVOLTAGE:
                case LOCATION:
                case NOCLOSE:
                case WARNING_ELSE:
                    mIvRecordLock = itemView.findViewById(R.id.iv_record_lock);
                    mTvLocationLockMac = itemView.findViewById(R.id.tv_location_lock_mac);
                    mTvLockMac = itemView.findViewById(R.id.tv_lock_mac);
                    mTvLockCabinet = itemView.findViewById(R.id.tv_lock_cabinet);
                    mTvStationNub = itemView.findViewById(R.id.tv_station_nub);
                    mTvLockCord = itemView.findViewById(R.id.tv_lock_cord);
                    break;
                case INSTALL:
                    mTvName = itemView.findViewById(R.id.tv_name);
                    mBgView = itemView.findViewById(R.id.view);
                    break;
                case USER_LOCK:
                    tvLockMac = itemView.findViewById(R.id.tv_lock_mac);
                    tvRamCabinet = itemView.findViewById(R.id.tv_lock_cabinet);
                    tvRamLocation = itemView.findViewById(R.id.tv_lock_location);
                    break;
                case USER_RAM:
                    tvRamName = itemView.findViewById(R.id.tv_ram_name);
                    tvRamLocation = itemView.findViewById(R.id.tv_ram_location);
                    break;
                case INSTALL_LOCK:
                    etInstallMac = itemView.findViewById(R.id.et_install_mac);
                    ivInstallZxing = itemView.findViewById(R.id.iv_install_zxing);
                    break;
                default:
                    break;
            }
        }
    }

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


}


