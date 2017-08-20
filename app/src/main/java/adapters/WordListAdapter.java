package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testa.wordscard.R;

import java.util.List;

import helpers.DBHelper;
import model.Word;

/**
 * Created by aomidi on 6/30/2017.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private List<Word> mWordList;
    private AdapterEvents mEvents;

    public WordListAdapter(List<Word> wList, AdapterEvents events){
        this.mWordList = wList;
        this.mEvents = events;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word_list, parent, false);
       ViewHolder viewHolder =new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word currWord = mWordList.get(position);
        holder.tvWord.setText(currWord.getWord());
        holder.tvDescription.setText(currWord.getDescription());
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWord;
        TextView tvDescription;
        public ViewHolder(View itemView) {
            super(itemView);
            tvWord = (TextView)itemView.findViewById(R.id.tv_word);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_word_description);

            tvWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onItemClick(ViewHolder.this);
                }
            });

            tvWord.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mEvents.onItemLongClick(ViewHolder.this);
                    return true;
                }
            });
        }

        public TextView getTvWord() {
            return tvWord;
        }

        public void setTvWord(TextView tvWord) {
            this.tvWord = tvWord;
        }

        public TextView getTvDescription() {
            return tvDescription;
        }

        public void setTvDescription(TextView tvDescription) {
            this.tvDescription = tvDescription;
        }
    }

    public interface AdapterEvents{
        void onItemLongClick(ViewHolder viewHolder);
        void onItemClick(ViewHolder viewHolder);
    }
}
