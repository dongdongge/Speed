package soyouarehere.imwork.speed.app.base.mvp;

/**
 * Fragment 懒加载 防止fragment初始化时加载大量数据
 * Created by Administrator on 2016-04-07.
 */
public abstract class LazyFragment extends BaseFragment {

    protected boolean isVisible = false;//当前Fragment是否可见


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment显示时调用
     */
    protected abstract void lazyLoad();

    /**
     * fragment隐藏时调用
     */
    protected abstract void onInvisible();

}