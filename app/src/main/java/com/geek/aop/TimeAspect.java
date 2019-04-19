package com.geek.aop;

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
public class TimeAspect {
    private String TAG = "jack";

    @Pointcut("execution(@com.geek.aop.TimeTrace * *(..))")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object dealPoint(ProceedingJoinPoint point) throws Throwable{
        long start=System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) point.getSignature();

        TimeTrace trace = signature.getMethod().getAnnotation(TimeTrace.class);

        String value = trace.value();

        // 执行原方法体
        Object proceed = point.proceed();

        // 方法执行完成后，记录时间，打印日志
        long end = System.currentTimeMillis();

        StringBuffer stringBuffer = new StringBuffer();
        if (proceed instanceof Boolean){
            // 返回的是boolean
            if ((Boolean)proceed){
                stringBuffer.append(value)
                        .append("成功，耗时：")
                        .append(end - start);
            }else{
                stringBuffer.append(value)
                        .append("失败，耗时：")
                        .append(end - start);
            }
        }
        Log.e(TAG,stringBuffer.toString());
        return proceed;
    }

}
