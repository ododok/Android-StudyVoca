package com.example.apstudyvoca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
  private ArrayList<Word> items;
  private String tableName;

  public WordAdapter(String tableName) {
    items = new ArrayList<>();
    this.tableName =tableName;
  }

  public WordAdapter(ArrayList<Word> items, String tableName) {
    this.items = items;
    this.tableName = tableName;
  }

  @NonNull
  @Override
  public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.word_item, parent, false);
    WordViewHolder viewHolder = new WordViewHolder(context, view);
    return viewHolder;
  }

  //"recycler" function
  @Override //position에 해당하는 데이터를 뷰홀더 아이템에 표시
  public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
    Word word = items.get(position);
    holder.textViewWordId.setText(String.valueOf(word.get_id()));
    holder.textViewWord.setText(word.getWord());
    holder.textViewMeaning.setText(word.getMeaning());
  }

  @Override //number of 'words' items.
  public int getItemCount() {
    return items.size();
  }

  public void setArrayData(Word word){
    items.add(word);
  }




  public class WordViewHolder extends RecyclerView.ViewHolder{
    public TextView textViewWord, textViewMeaning, textViewWordId;
    public LinearLayout linearLayoutWord;

    public WordViewHolder(Context context, @NonNull View itemView) {
      super(itemView);
        textViewWord = itemView.findViewById(R.id.textViewWord);
        textViewMeaning = itemView.findViewById(R.id.textViewMeaning);
        textViewWordId = itemView.findViewById(R.id.textViewWordId);
        linearLayoutWord = itemView.findViewById(R.id.linearLayoutWord);



        //click event
      linearLayoutWord.setOnClickListener(v->{
        //Toast.makeText(context, textViewWord.getText().toString(), Toast.LENGTH_SHORT).show();
        // 단어 조회기능 들어가야 함. 조회시 수정 삭제 가능하도록
        Intent intent = new Intent(context, ModifyWord.class);
        intent.putExtra("_id", Integer.parseInt(textViewWordId.getText().toString()));
        intent.putExtra("tableName", tableName);
        context.startActivity(intent);
      });

      //Long click Listener event
      linearLayoutWord.setOnLongClickListener(v->{

        return false;
      });
    }

  }//class WordViewHolder


}//class WordAdapter
