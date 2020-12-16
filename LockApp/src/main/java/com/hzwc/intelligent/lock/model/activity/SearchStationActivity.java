package com.hzwc.intelligent.lock.model.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzwc.intelligent.lock.R;
import com.hzwc.intelligent.lock.model.bean.BeanList;
import com.hzwc.intelligent.lock.model.view.persenter.SearchStationPresenter;
import com.hzwc.intelligent.lock.model.view.view.SearchStationView;
import com.hzwc.intelligent.lock.mvpframework.factory.CreatePresenter;
import com.hzwc.intelligent.lock.mvpframework.view.AbstractMvpBaseActivity;
import com.hzwc.intelligent.lock.mvpframework.view.bezier.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/15.
 */
@CreatePresenter(SearchStationPresenter.class)
public class SearchStationActivity extends AbstractMvpBaseActivity<SearchStationView, SearchStationPresenter> implements SearchStationView {

    @BindView(R.id.tv_search)
    TextView mCancelSearch;
    @BindView(R.id.iv_search_return)
    ImageView mBack;
    @BindView(R.id.ev_title_input)
    AutoCompleteTextView mEvTitleInput;
    @BindView(R.id.fl_card)
    FlowLayout mFlCard;
    private int mSign;
    private List<BeanList.DataBean> mStationList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
//        initView();
        mEvTitleInput.setThreshold(0);
        initClick();
        initRequest();
    }

    private void initRequest() {
//        getMvpPresenter().clickRequest(SpUtils.getString(this, "token", ""), 0L);
    }

    private void initClick() {
        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEvTitleInput.setText("");
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void initView() {

    }

    @Override
    public void dataLoading() {

    }

    @Override
    public void dataSuccess(BeanList beanList) {
        initSearch(beanList);
    }

    @Override
    public void dataFailure(String result) {

    }

    @Override
    public void onItemClick(String context) {
        mEvTitleInput.setText(context);
        mEvTitleInput.setSelection(context.length());
//        getMvpPresenter().searchStationById(SearchStationActivity.this, context, mStationList, mSign);
    }

    private void initSearch(BeanList beanList) {
//        String[] str = getMvpPresenter().getStr(beanList);
//        mStationList = beanList.getData();
//        List<String> mCacheList = getMvpPresenter().getCacheList(this);
//        mFlCard.setFlowLayout(mCacheList, getMvpPresenter());
//        SearchAdapter<String> searchAdapter = new SearchAdapter<String>(this,
//                android.R.layout.simple_list_item_1, str, SearchAdapter.ALL);
//        initSearchListener(searchAdapter, mCacheList);
    }

//    private void initSearchListener(final SearchAdapter<String> searchAdapter, final List<String> mCacheList) {
//        mEvTitleInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                mEvTitleInput.setAdapter(searchAdapter);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() > 0) {
//                    mCancelSearch.setText(getString(R.string.cancel));
//                } else {
//                    mCancelSearch.setText("");
//                }
//            }
//        });

       /* mEvTitleInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = mEvTitleInput.getText().toString().trim();
                    List<String> cacheSaveList = getMvpPresenter().searchSave(SearchStationActivity.this, content, mStationList, mCacheList);
                    mFlCard.setFlowLayout(cacheSaveList, getMvpPresenter());
                    getMvpPresenter().searchStationById(SearchStationActivity.this, content, mStationList, mSign);
                }
                return false;
            }
        });
    }*/
}
