package com.hzwc.intelligent.lock.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.SearchAddressInfo;

import java.util.ArrayList;

/**
 * Created by Fussen on 2016/11/1.
 */

public class AddressAdapter extends BaseAdapter {

    private ArrayList<SearchAddressInfo> mData;

    private Context context;

    public AddressAdapter(Context context, ArrayList<SearchAddressInfo> data) {

        this.context = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = View.inflate(context, R.layout.item_adapter, null);

        TextView address = (TextView) view.findViewById(R.id.address);
        TextView des = (TextView) view.findViewById(R.id.des);
        ImageView check = (ImageView) view.findViewById(R.id.check);

        SearchAddressInfo addressInfo = mData.get(position);

        if (position == 0) {
            des.setVisibility(View.GONE);
        } else {
            des.setVisibility(View.VISIBLE);
        }

        if (addressInfo.isChoose) {
            check.setVisibility(View.VISIBLE);
        } else {
            check.setVisibility(View.INVISIBLE);
        }

        address.setText(addressInfo.title);
        des.setText(addressInfo.addressName);

        return view;
    }

    public void setData(ArrayList<SearchAddressInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}
