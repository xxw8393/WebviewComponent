package com.fqxyi.webviewcomponent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fqxyi.webview.activity.WebviewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenWebview(View view) {
        Intent intent = new Intent(this, WebviewActivity.class);
//        intent.putExtra("url", "file:///android_asset/test.html");
        intent.putExtra("url", "https://www.fqxyi.com/");
        startActivity(intent);
    }

}
