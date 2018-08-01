package com.liyi.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_base:
                startActivity(new Intent(this, BaseAutoGridActivity.class));
                break;
            case R.id.btn_quick:
                startActivity(new Intent(this, QuickAutoGridActivity.class));
                break;
            case R.id.btn_simple:
                startActivity(new Intent(this, SimpleAutoGridActivity.class));
                break;
        }
    }
}
