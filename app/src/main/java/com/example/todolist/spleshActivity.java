package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class spleshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_splesh );


        getSupportActionBar ().hide ();

        final Intent intent = new Intent (spleshActivity.this, MainActivity.class);

        new Handler ().postDelayed ( new Runnable () {
            @Override
            public void run() {
                startActivity ( intent );
                finish ();
            }
        }, 1000 );
    }
}