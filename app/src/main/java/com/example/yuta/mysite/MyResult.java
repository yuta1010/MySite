package com.example.yuta.mysite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MyResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_result);

        TextView resultText = (TextView) findViewById(R.id.resultText);
        Intent intent = getIntent();
        resultText.setText(intent.getStringExtra(MainActivity2.EXTRA_MYSCORE));
        //MainActivityから正当数を取ってきている


    }

    public void replayQuiz(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}