package com.example.yuta.mysite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yuta.mysite.MyResult;
import com.example.yuta.mysite.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class MainActivity2 extends Activity {

    public  final static String EXTRA_MYSCORE = "com.example.yuta.mysite.MainActivity.MYSCORE";
    //AndroidManifestからpackage名を取ってきてそれに好きな名前を付けるやり方が多い

    private ArrayList<String[]> quizSet = new ArrayList<String[]>();
    //ArrayListでquiz.txtで作ったものを一行ずつ一つの配列とし管理

    private TextView scoreText;
    private TextView qText;
    private Button a0Button;
    private Button a1Button;
    private Button a2Button;
    private Button a3Button;
    private Button nextButton;
    //すぐとってこれるようメンバにする

    private  int currentQuiz = 0;
    //対象になっているクイズを変数で管理

    private int score = 0;
    //scoreの正当数を変数で管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadQuizSet();
        //quiz.txtを読み込む

        getViews();
        //viewを取得する

        setQuiz();
        //画面に質問を表示する
    }

    private  void getViews() {
        scoreText = (TextView) findViewById(R.id.scoreText);
        qText = (TextView) findViewById(R.id.qText);
        a0Button = (Button) findViewById(R.id.a0Button);
        a1Button = (Button) findViewById(R.id.a1Button);
        a2Button = (Button) findViewById(R.id.a2Button);
        a3Button = (Button) findViewById(R.id.a3Button);
        nextButton = (Button) findViewById(R.id.nextButton);
        //変数を取得する

    }

    private  void showScore(){
        scoreText.setText("Score: " + score + " / " +quizSet.size());
    }
    // scoreText に表示するためのメソッド

    public  void goNext(View view) {
        if (currentQuiz == quizSet.size()) {
            //最後の問題
            Intent intent = new Intent(this, MyResult.class);
            intent.putExtra(EXTRA_MYSCORE, score + " / " + quizSet.size());
            //resultActivityに正当数を送る
            startActivity(intent);
            //次のActivityにいく
        }
        else{
            setQuiz();
            //次の問題
        }
        //次の問題があるか(最後の問題じゃないか)

    }
    //次の問題へ行く

    @Override
    public  void onNewIntent(Intent intent){
        currentQuiz = 0;
        score = 0;
        //帰ってきたとき正当数を0にする
        nextButton.setText("Next");
        setQuiz();
    }
    //replayされたときに最初の問題に戻るようにしボタンも直す

    public  void CheckAnswers(View view){
        Button clickedButton = (Button) view;
        //clickedButtonでクリックされた答えを取得
        String clickedAnswer = clickedButton.getText().toString();
        //答えをチェック
        if (clickedAnswer.equals(quizSet.get(currentQuiz)[1])){
            //0 番目が質問文で、その次に来る最初の選択肢が正解という仕様

            clickedButton.setText("○" + clickedAnswer);
            score++;
            showScore();
        }
        else {
            clickedButton.setText("×" + clickedAnswer);

        }
        //正解とclickedAnswerが同じかチェック

        a0Button.setEnabled(false);
        a1Button.setEnabled(false);
        a2Button.setEnabled(false);
        a3Button.setEnabled(false);
        //選択肢Buttonを使えないようにしておく

        nextButton.setEnabled(true);
        //nextButtonを押せるようにする

        currentQuiz++;
        //次のクイズにいく

        if (currentQuiz == quizSet.size()) {
            nextButton.setText("Check Result");
        }
        //最後の問題の時nextボタンをCheck Resultという文字にする
    }


    private void setQuiz(){
        qText.setText(quizSet.get(currentQuiz)[0]);
        //質問文をセット(currentQuizはいちばん最初の問題[0]は質問文は配列の一個目)

        ArrayList<String> answers = new ArrayList<String>();
        //答えの選択肢をセット
        for (int i = 1; i < 5 ; i++){
            answers.add(quizSet.get(currentQuiz)[i]);
        }
        //そのあとに現在の問題の配列の 1 から 4 までの値をセット

        Collections.shuffle(answers);
        //答えをシャッフルする

        a0Button.setText(answers.get(0));
        a1Button.setText(answers.get(1));
        a2Button.setText(answers.get(2));
        a3Button.setText(answers.get(3));
        //ボタンに答えを入れていく

        a0Button.setEnabled(true);
        a1Button.setEnabled(true);
        a2Button.setEnabled(true);
        a3Button.setEnabled(true);

        nextButton.setEnabled(false);
        //nextButtonを使えないようにしておく

        showScore();
        //スコアを表示

        //取得したViewに対して値を取得していく
    }

    private void loadQuizSet() {
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        //ファイルを読み込むのに必要なもの(上二つ)
        try {
            inputStream = getAssets().open("quiz.txt");
            //inputStreamでgetAssetsのメソッドを使うと、assets フォルダにあるファイルを読みこむ。
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // bufferedReaderをインスタンス化
            String s;
            //1 行ずつ読み込んでいくのですが、読み込んだものを入れる s という文字列を定義しておきます。
            while ((s = bufferedReader.readLine()) != null) {
                //ファイルから 1 行読んで s に入れる(nullでないとき)
                quizSet.add(s.split("\t"));
                // s の中にはタブ区切りの 1 行が入っているので、それをタブで分割して quizSet に突っ込んでいけば OK です。
            }
        } catch (IOException e) {
            //ファイルを読み込むときには例外処理をしなくてはいけない
            e.printStackTrace();
            //StackTraceでエラーの箇所を把握

        }
        finally {
            try {
                if (inputStream != null) inputStream.close();
                if (bufferedReader != null) bufferedReader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                //ストリームを閉じる処理
            }
        }

//一行ずつ読み込んでStringの配列にしてQuizSetに入れる
    }
}


