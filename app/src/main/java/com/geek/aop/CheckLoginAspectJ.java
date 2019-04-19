package com.geek.aop;

import android.content.Context;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by ${chenM} on 2019/4/18.
 */
@Aspect
public class CheckLoginAspectJ {
    private String TAG = "jack";

    @Pointcut("execution(@com.geek.aop.CheckLogin * *(..))")
    public void executionCheckLogin(){

    }

    @Around("executionCheckLogin()")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable{
        Log.d(TAG,"checkLogin:");
        MethodSignature signature = (MethodSignature) point.getSignature();
        CheckLogin checkLogin = signature.getMethod().getAnnotation(CheckLogin.class);

        if (checkLogin != null){
            Context context = (Context) point.getThis();
            if (MainActivity.isLogin){
                Log.d(TAG,"checkLogin:success");
                return point.proceed();
            }else {
                Log.d(TAG,"checkLogin:fail");
                return null;
            }
        }
        return point.proceed();

    }

}
