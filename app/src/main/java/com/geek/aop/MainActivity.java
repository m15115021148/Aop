package com.geek.aop;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.geek.aop.function.DebugTrace;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "jack-m";
    public static boolean isLogin;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.onclickListener);
        mTv.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @BehaviorTrace(value = "语音:",type = 1)
    public void onFind(View view) {
        Log.i(TAG," 摇到一个红包");
//        isLogin = true;
    }

//    @CheckLogin
    public void onLogin(View view) {
//        Log.i(TAG," 登陆成功");
        login("aaa","123456");
    }


    @TimeTrace(value = "登录")
    @DebugTrace
    private boolean login(String userName, String passWord){

        if ("aaa".equals(userName) && "123456".equals(passWord)){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mTv){
            login("aaa","123456");
        }
    }

    public void onSecondActivity(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }
}
