package com.hzwc.intelligent.lock.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.Scan;

import java.util.ArrayList;

/**
 * Created by apple on 2018/7/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<Scan> mArrayList;
    private MyInterface mMyInterface;

    public MyAdapter(Context context, ArrayList<Scan> arrayList) {
        mContext = context;
        mArrayList =arrayList ;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_add_install, parent, false));
    }

    public void closeInputMethod(EditText et_install_mac) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(et_install_mac.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        if (!mArrayList.get(position).equals("")) {
            holder.et_install_mac.setText(mArrayList.get(position).getMac());

        }

        if (!TextUtils.isEmpty((mArrayList.get(position).getCode()))){
            holder.code.setText(mArrayList.get(position).getCode());

        }




//        if (position==mArrayList.size()-1){
//            holder.refresh.setVisibility(View.VISIBLE);
//        }else {
//            holder.refresh.setVisibility(View.INVISIBLE);
//        }

        holder.refresh.setOnClickListener(v -> {
            if (mMyInterface!=null){
                mMyInterface.refresh();
            }

        });





        closeInputMethod(holder.et_install_mac);

        if (mMyInterface != null) {
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMyInterface.onScanClick(v, position);
                }
            });
        }
        holder.et_install_mac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mArrayList.get(position).setMac(holder.et_install_mac.getText().toString().trim());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList == null ? 0 : mArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnScanClickListener(MyInterface myInterface) {
        mMyInterface = myInterface;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private EditText et_install_mac;
        private  ImageView  refresh;
        private TextView  code;

        public MyHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_install_zxing);
            et_install_mac = itemView.findViewById(R.id.et_install_mac);
            code=itemView.findViewById(R.id.tv_code);
            refresh=itemView.findViewById(R.id.ima_install_refresh);
        }
    }
}
