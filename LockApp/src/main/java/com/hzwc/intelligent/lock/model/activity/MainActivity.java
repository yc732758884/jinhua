package com.hzwc.intelligent.lock.model.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.hzwc.intelligent.lock.model.fragment.AlarmFragment;
import com.hzwc.intelligent.lock.model.fragment.OpenLockFragment;
import com.hzwc.intelligent.lock.model.fragment.InstallFragment;
import com.hzwc.intelligent.lock.model.fragment.LocationFragment;
import com.hzwc.intelligent.lock.model.utils.SpUtils;
import com.hzwc.intelligent.lock.model.view.BottomNavigationViewHelper;
import com.hzwc.intelligent.lock.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static int currIndex = 0;
    private FragmentManager mFragmentManager;
    private ArrayList<String> mFragmentTags;
    private static final String CURR_INDEX = "currIndex";

    private MainBroadcastReceiver receiver;
    private IntentFilter intentFilter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            changeFragment(item.getItemId());

            return true;
        }
    };

    private void changeFragment(int itemId) {
        switch (itemId) {
            case R.id.one:
//                    mTextMessage.setText(R.string.title_home);
                currIndex = 0;

                break;
            case R.id.two:
                currIndex = 1;
                break;

            case R.id.three:
                currIndex = 2;

                break;
            case R.id.fore:
                currIndex = 3;

                break;
        }

        showFragment();
    }

    private void showFragment() {
        System.out.println(SpUtils.getString(this, "token", ""));
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(mFragmentTags.get(currIndex));

        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < mFragmentTags.size(); i++) {

            Fragment f = mFragmentManager.findFragmentByTag(mFragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fl_context, fragment, mFragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 3:
                return new InstallFragment();
            case 2:
                return new AlarmFragment();
            case 1:
                return new OpenLockFragment();
            case 0:
                return new LocationFragment();
            default:
                return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(SpUtils.getString(MainActivity.this,"token", ""));

        receiver = new MainBroadcastReceiver();
        intentFilter = new IntentFilter("com.hzwc.person.lock.logout");
        registerReceiver(receiver, intentFilter);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        BottomNavigationViewHelper.disableShiftMode(navigation);
        int indexId = getIntent().getIntExtra("indexId", 1);
        initData(savedInstanceState);

        if (indexId == 0) {
            changeFragment(R.id.one);
            navigation.setSelectedItemId(R.id.one);
        } else if (indexId == 1) {
            changeFragment(R.id.two);
            navigation.setSelectedItemId(R.id.two);
        } else if (indexId == 2) {
            changeFragment(R.id.three);
            navigation.setSelectedItemId(R.id.three);
        } else if (indexId == 3) {
            changeFragment(R.id.fore);
            navigation.setSelectedItemId(R.id.fore);
        }
    }

    private void initData(Bundle savedInstanceState) {
        mFragmentTags = new ArrayList<>(Arrays.asList("OneFragment", "TwoFragment", "ThreeFragment", "ForeFragment", "FiveFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = mFragmentManager.findFragmentByTag(mFragmentTags.get(currIndex));
        if (fragment != null) {
            mFragmentManager.beginTransaction().hide(fragment).commit();
        }
    }



    class MainBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }
}
