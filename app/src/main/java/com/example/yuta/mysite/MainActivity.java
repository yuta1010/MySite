package com.example.yuta.mysite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    private WebView mySite;
    private static final String INITIAL_WEBSITE = "http://riead-fashion.secret.jp/";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySite = (WebView) findViewById(R.id.mySite);

        mySite.getSettings().setJavaScriptEnabled(true);
        //javascriptの無効化を解除

       mySite.setWebViewClient(new WebViewClient());
        //これでほかのページに移動したときにブラウザに飛ばない

        mySite.loadUrl(INITIAL_WEBSITE);

       button = (Button) findViewById(R.id.button);

        button.setOnClickListener( new View.OnClickListener() {

            //ボタンが押されたら何かする
            @Override
            public void onClick(View v) {

                //インテントに、この画面と、遷移する別の画面を指定する
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                //インテントで指定した別の画面に遷移する
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mySite.canGoBack()){
            mySite.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        mySite.stopLoading();
        mySite.destroy();
    }


    //以下optionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
