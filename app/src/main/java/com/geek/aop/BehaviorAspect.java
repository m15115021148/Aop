package com.geek.aop;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
public class BehaviorAspect {
    private static final String TAG = "jack";
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 根据切点 切成什么样子
     *
     */
    @Pointcut("execution(@com.geek.aop.BehaviorTrace * *(..))")
    public void annoBehavior() {

    }

    @Before("annoBehavior()")
    public void before(JoinPoint point) {
        Log.i(TAG,"before");
    }


    /**
     * 切成什么样子之后，怎么去处理
     *
     */

    @Around("annoBehavior()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable{
        //方法执行前
        MethodSignature methodSignature = (MethodSignature)point.getSignature();
        BehaviorTrace behaviorTrace = methodSignature.getMethod().getAnnotation(BehaviorTrace.class);
        String contentType = behaviorTrace.value();
        int type = behaviorTrace.type();
        Log.i(TAG,contentType+"使用时间：   "+simpleDateFormat.format(new Date()));
        long beagin=System.currentTimeMillis();
        //方法执行时
        Object object = null;
        try{
            object = point.proceed();
        }catch (Exception e){
            e.printStackTrace();
        }

        //方法执行完成
        Log.i(TAG,"消耗时间："+(System.currentTimeMillis()-beagin)+"ms");
        return object;
    }

    @After("annoBehavior()")
    public void after(JoinPoint point) {
        Log.i(TAG,"@After");
    }

    @AfterReturning("annoBehavior()")
    public void afterReturning(JoinPoint point, Object returnValue) {
        Log.i(TAG,"@AfterReturning");
    }

    @AfterThrowing(value = "annoBehavior()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        Log.i(TAG,"@afterThrowing");
        Log.i(TAG,"ex = " + ex.getMessage());
    }

}
