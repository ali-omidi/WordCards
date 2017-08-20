package com.testa.wordscard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import helpers.DBHelper;
import utils.Util;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    ImageButton mBtnRec;
    Button mBtnWordList;
    TextView mTvWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRec = (ImageButton) findViewById(R.id.btn_rec_voice);
        mTvWord = (TextView) findViewById(R.id.tv_word);
        mBtnWordList = (Button) findViewById(R.id.btn_words_list);

        mBtnRec.setOnClickListener(this);
        mBtnWordList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rec_voice) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.speech_not_supported),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btn_words_list){
            Intent i = new Intent(this, WordListActivity.class);
            startActivity(i);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    float[] confidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

                    DBHelper dbHelper = new DBHelper(this);
                    StringBuilder sb = new StringBuilder();
                    for(String s:result){
                        sb.append(s).append(" ");
                    }
                    dbHelper.addNewWord(result.get(0));
                    Util.scheduleJob(this);
                    mTvWord.setText(sb.toString());
                }
                break;
            }
        }
    }
}
