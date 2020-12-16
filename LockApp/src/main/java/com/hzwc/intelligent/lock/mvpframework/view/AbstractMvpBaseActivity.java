package com.hzwc.intelligent.lock.mvpframework.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hzwc.intelligent.lock.model.base.BaseActivity;
import com.hzwc.intelligent.lock.mvpframework.factory.PresenterMvpFactory;
import com.hzwc.intelligent.lock.mvpframework.factory.PresenterMvpFactoryImpl;
import com.hzwc.intelligent.lock.mvpframework.presenter.BaseMvpPresenter;
import com.hzwc.intelligent.lock.mvpframework.proxy.BaseMvpProxy;
import com.hzwc.intelligent.lock.mvpframework.proxy.PresenterProxyInterface;


/**
 * 封装 AppCompatActivity
 * @author Tim on 2018/1/19.
 */
@SuppressLint("Registered")
public class AbstractMvpBaseActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends BaseActivity implements PresenterProxyInterface<V,P> {

    private static final String TAG = AbstractMvpBaseActivity.class.getSimpleName(); // perfect
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,P>createFactory(getClass()));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }

    @Override
    protected void initIntentData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
