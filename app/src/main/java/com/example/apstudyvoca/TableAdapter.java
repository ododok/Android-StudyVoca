package com.example.apstudyvoca;

import android.app.Activity;
import android.content.Context;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{

  private ArrayList<Table> items;

  public TableAdapter(){
    items= new ArrayList<>();
  }

  @NonNull
  @Override
  public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.table_item, parent,false);
    TableViewHolder viewHolder = new TableViewHolder(context, view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
    Table table = items.get(position);

    holder.textViewTableName.setText(table.getTableName());
    holder.textViewNumberRaw.setText(String.valueOf(table.getNumberOfRaw()));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  //add "a" table data in  ArrayList<Table> items
  public void setArrayData(Table table){
    items.add(table);
  }

  public class TableViewHolder extends RecyclerView.ViewHolder{
    public CardView tableItem;
    public TextView textViewTableName, textViewNumberRaw;
    public ImageView imageViewEditTable;
    LinearLayout linearLayoutTable;
    boolean editTableIcon = false; //false ==invisible, true==visible

    //QueryTable액티비티 띄우는 코드 필요없나벼
//    public static final int CODE_TABLEVIEWHOLDER_TO_QUERYTABLE = 102;
//    public static final int CODE_QUERYTABLE_TO_TABLEVIEWHOLDER = 201;


    public TableViewHolder(Context context, @NonNull View itemView) {
      super(itemView);
      tableItem = itemView.findViewById(R.id.tableItem);
      textViewTableName = itemView.findViewById(R.id.textViewTableName);
      textViewNumberRaw = itemView.findViewById(R.id.textViewNumberRaw);
      imageViewEditTable = itemView.findViewById(R.id.imageViewEditTable);
      linearLayoutTable = itemView.findViewById(R.id.linearLayoutTable);


      //click event (normal click)
      tableItem.setOnClickListener(v->{
        if(editTableIcon==true) {
          imageViewEditTable.setVisibility(View.INVISIBLE);
          linearLayoutTable.setBackgroundColor(Color.WHITE);
          editTableIcon = false;
        } else {
          Toast.makeText(context, textViewTableName.getText().toString(),
              Toast.LENGTH_SHORT).show();

          //fragment query table - 테이블 내부 단어목록 조회 activity 띄우기
          //(QueryTable.java & activity_query_table.xml)
          Intent intent = new Intent(context, QueryTable.class);
          intent.putExtra("tableName", textViewTableName.getText().toString());
          context.startActivity(intent);

        }
      });

      //Long click event
      tableItem.setOnLongClickListener(v->{
        //edit ou delete 창이 뜨게 만들고 싶다.
        imageViewEditTable.setVisibility(View.VISIBLE);
        linearLayoutTable.setBackgroundColor(Color.LTGRAY);
        editTableIcon = true;
        return true;
      });

      imageViewEditTable.setOnClickListener(v->{
        if(editTableIcon == true){
          Toast.makeText(context, "edit아이콘 눌림",Toast.LENGTH_SHORT).show();
          //여기에 table의 이름수정과 삭제를 할 수 있는 dialog를 띄울 것.

        }
      });

    }

  }//class ViewHolder

}//class TableAdapter
