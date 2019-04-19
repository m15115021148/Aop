package com.geek.aop.function;

import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by ${chenM} on 2019/4/18.
 */
@Aspect
public class LogPrintAspect {
    private String TAG = "jack";

    private static final String ANDROID_VIEW_ON_CLICK = "execution(* android.view.View.OnClickListener.onClick(..))";
    private static final String ANDROID_ACTIVITY_ON_CREATED = "execution(* android.app.Activity+.onCreate(..))";
    private static final String ANDROID_ACTIVITY_ON_PAUSE = "execution(* android.app.Activity+.onPause(..))";
    private static final String ANDROID_ACTIVITY_ON_DESTROY = "execution(* android.app.Activity+.onDestroy(..))";
    private static final String ANDROID_VIEW_PAGER_CHANGE_LISTENER = "execution(* android.support.v4.view.ViewPager.OnPageChangeListener.onPageSelected(..))";

    private static final String ANDROID_FRAGMENT_ON_CREATED = "execution(* android.app.Fragment+.onCreate(..))";

    @Around(ANDROID_ACTIVITY_ON_CREATED)
    public void onActivityCreated(ProceedingJoinPoint point) throws Throwable{
        Object target = point.getTarget();
        String className = target.getClass().getName();
        Log.d(TAG,"onActivityCreated->"+className);
        point.proceed();
    }

    @Around(ANDROID_ACTIVITY_ON_PAUSE)
    public void onActivityPause(ProceedingJoinPoint point) throws Throwable{
        Object target = point.getTarget();
        String className = target.getClass().getName();
        Log.d(TAG,"onActivityPause->"+className);
        point.proceed();
    }

    @Around(ANDROID_ACTIVITY_ON_DESTROY)
    public void onActivityDestroy(ProceedingJoinPoint point) throws Throwable{
        Object target = point.getTarget();
        String className = target.getClass().getName();
        Log.d(TAG,"onActivityDestroy->"+className);
        point.proceed();
    }

    /**
     * android.view.View.OnClickListener.onClick(android.view.View) 此类事件的点击监听
     *
     * @param joinPoint JoinPoint
     */
    @Around(ANDROID_VIEW_ON_CLICK)
    public void onViewClickAOP(final ProceedingJoinPoint joinPoint) throws Throwable{
        joinPoint.proceed();
        View view = (View) joinPoint.getArgs()[0];
        String xmlId = "";
        if (view.getId() != View.NO_ID) {
            xmlId = view.getResources().getResourceEntryName(view.getId());
        }
        Log.e(TAG,"onViewClickAOP:"+" xmlId = " + xmlId );
    }

    /**
     * 统计用户滑动行为的数据
     *
     * @param joinPoint
     */
    @Around(ANDROID_VIEW_PAGER_CHANGE_LISTENER)
    public void onPageScrolled(final ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
            int position = (int) joinPoint.getArgs()[0];
            Log.d(TAG, "onPageScrolled: "+" position = " + position);
            String str  = joinPoint.getTarget().getClass().getName();
            Log.d(TAG, "onPageScrolled: "+ "activity = " +str);
        } catch (Throwable throwable) {
            Log.d(TAG, "onPageScrolled: "+throwable.getMessage());
            throwable.printStackTrace();
        }
    }

}
