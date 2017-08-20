package com.testa.wordscard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import adapters.WordListAdapter;
import helpers.DBHelper;
import model.Word;

public class WordListActivity extends AppCompatActivity implements WordListAdapter.AdapterEvents {

    RecyclerView mWordListRecyclerView;
    WordListAdapter mAdapter;
    List<Word> mWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        mWords = new DBHelper(this).getAllWords();
        mAdapter = new WordListAdapter(mWords, this);

        mWordListRecyclerView = (RecyclerView)findViewById(R.id.list);
        mWordListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWordListRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemLongClick(WordListAdapter.ViewHolder viewHolder) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteWord(viewHolder.getTvWord().getText().toString());
        int pos = viewHolder.getAdapterPosition();
        mWords.remove(pos);
//        mWordListRecyclerView.removeViewAt(pos);
        mAdapter.notifyItemRemoved(pos);
        mAdapter.notifyItemRangeChanged(pos, mWords.size());
    }

    @Override
    public void onItemClick(WordListAdapter.ViewHolder viewHolder) {
        if(viewHolder.getTvDescription().getVisibility() == View.GONE)
            viewHolder.getTvDescription().setVisibility(View.VISIBLE);
        else
            viewHolder.getTvDescription().setVisibility(View.GONE);
    }
}
