package com.pplovely.anotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pplovely.anotationrouter.RouterManager;
import com.pplovely.anotationrouter.TestRouter;

@TestRouter(url = "scheme://test")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RouterManager.getInstance().init(); //初始化注解，init中调用的代码是动态生成的

        RouterManager.getInstance().showAllActivity();//show目前注册的
    }
}
