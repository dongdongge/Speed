package soyouarehere.imwork.speed.app.base.mvp;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import soyouarehere.imwork.speed.util.log.LogUtil;

public class AppManager {
    private static final String TAG = "ScreenManager";
    private static Stack<Activity> activityStack;

    /**
     * <单例方法>
     * <功能详细描述>
     *
     * @return 该对象的实例
     * @see [类、类#方法、类#成员]
     */
    public static AppManager getInstance() {
        return AppManagerHelp.INSTANCE;
    }
    private static class AppManagerHelp{
        private static AppManager INSTANCE = new AppManager();
    }

    /**
     * <获取当前栈顶Activity>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.size() == 0) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        LogUtil.e(TAG+"get current activity:" + activity.getClass().getSimpleName());
        return activity;
    }

    /**
     * <将Activity入栈>
     * <功能详细描述>
     *
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        LogUtil.e(TAG+ "push stack activity:" + activity.getClass().getSimpleName());
        activityStack.add(activity);
    }

    /**
     * <退出栈顶Activity>
     * <功能详细描述>
     *
     * @param activity
     * @see [类、类#方法、类#成员]
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            LogUtil.e(TAG+"remove current activity:" + activity.getClass().getSimpleName());
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * <退出栈中所有Activity,当前的activity除外>
     * <功能详细描述>
     *
     * @param cls
     * @see [类、类#方法、类#成员]
     */
    public void popAllActivityExceptMain(Class cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)){
                continue;
            }
            activityStack.get(i).finish();
        }
    }




    /**
     *
     *
     * 通过反射获取意图Activity然后关闭；
     *
     * */
    public void finishActivity(Class cls){
        for (int i = 0; i < activityStack.size(); i++) {
            LogUtil.e("finishActivity"+"==="+activityStack.get(i).getClass()+"==="+cls);
            if (activityStack.get(i).getClass().equals(cls)){
                if (activityStack.get(i)!=null){
                    activityStack.get(i).finish();
                    activityStack.remove(activityStack.get(i));
                }
            }
        }
    }


    /**
     *
     * 获取当前栈中所有的Activity（存活的界面）；
     *
     *
     *
     * */
    public static synchronized List<String> getAllActivityFromStack(){
        List<String> stringListActivityName = new ArrayList<>();
        if (activityStack.size()==0){
            return stringListActivityName;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            String activityName = activityStack.get(i).getClass().getSimpleName();
            stringListActivityName.add(activityName);
        }
        return stringListActivityName;
    }
}
