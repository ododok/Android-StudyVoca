package com.example.apstudyvoca;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    TextView textViewWord, textViewMeaning, textViewWordId;
    LinearLayout linearLayoutWord;
    ImageView imageViewDeleteWord;
    boolean deleteWordIconVisible = false; ////false ==invisible, true==visible


    public WordViewHolder(Context context, @NonNull View itemView) {
      super(itemView);

        textViewWord = itemView.findViewById(R.id.textViewWord);
        textViewMeaning = itemView.findViewById(R.id.textViewMeaning);
        textViewWordId = itemView.findViewById(R.id.textViewWordId);
        linearLayoutWord = itemView.findViewById(R.id.linearLayoutWord);
        imageViewDeleteWord = itemView.findViewById(R.id.imageViewDeleteWord);


        //click event
      linearLayoutWord.setOnClickListener(v->{
        if(deleteWordIconVisible==true) {
          imageViewDeleteWord.setVisibility(View.INVISIBLE);
          linearLayoutWord.setBackgroundColor(Color.TRANSPARENT);
          deleteWordIconVisible = false;
        } else {
          //Toast.makeText(context, textViewWord.getText().toString(), Toast.LENGTH_SHORT).show();
          // 단어 조회기능 들어가야 함. 조회시 수정 가능하도록
          Intent intent = new Intent(context, ModifyWord.class);
          intent.putExtra("tableName", tableName);
          intent.putExtra("_id", Integer.parseInt(textViewWordId.getText().toString()));
          intent.putExtra("word", textViewWord.getText().toString());
          intent.putExtra("meaning",textViewMeaning.getText().toString());
          context.startActivity(intent);
        }
      });

      //Long click Listener event
      linearLayoutWord.setOnLongClickListener(v->{
        linearLayoutWord.setBackgroundColor(Color.LTGRAY);
        imageViewDeleteWord.setVisibility(View.VISIBLE);
        deleteWordIconVisible = true;
        return true;
      });

      //red delete button click listener
      imageViewDeleteWord.setOnClickListener(view->{
        if(deleteWordIconVisible) {
          AlertDialog.Builder adb = new AlertDialog.Builder(context);
          adb.setTitle(textViewWord.getText().toString());
          adb.setMessage("Do you really want to delete?");
          adb.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              DBHelper helper = new DBHelper(context);
              helper.deleteRow(tableName, Integer.parseInt(textViewWordId.getText().toString()));

              //아이템을 DB에서 삭제한 후에도 ArrayList에서도 삭제해야 한다.
              items.removeIf(w -> w.get_id() == Integer.parseInt(textViewWordId.getText().toString()));

              //아이템을 ArrayList에서 삭제했으면, 리사이클러뷰의 삭제대상 표시 색이나 아이콘도
              //다시 숨겨줘야 한다.
              imageViewDeleteWord.setVisibility(View.INVISIBLE);
              linearLayoutWord.setBackgroundColor(Color.TRANSPARENT);
              deleteWordIconVisible = false;

              //이 과정을 마쳤으면 뷰를 다시 REFRESH 한다.
              //이 메서드는 dialog에서 액티비티로 변경사항이 있다는 것을 전달한다.
              notifyDataSetChanged();
            }
          });//todo
          adb.setNegativeButton("CANCEL", null);
          adb.show();
        }
      });

    }




  }//class WordViewHolder


}//class WordAdapter
