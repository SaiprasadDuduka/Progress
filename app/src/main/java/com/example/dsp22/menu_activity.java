package com.example.dsp22.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class menu_activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(mToolbar);
    }
}
