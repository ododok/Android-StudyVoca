package com.example.apstudyvoca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public CardView bookItem;
    public TextView textViewTableName, textViewNumberRaw;


    public TableViewHolder(Context context, @NonNull View itemView) {
      super(itemView);
      bookItem = itemView.findViewById(R.id.bookItem);
      textViewTableName = itemView.findViewById(R.id.textViewTableName);
      textViewNumberRaw = itemView.findViewById(R.id.textViewNumberRaw);

      //click event (normal click)
      bookItem.setOnClickListener(v->{
        Toast.makeText(context, textViewTableName.getText().toString(),
            Toast.LENGTH_SHORT).show();
      });

      //Long click event
      bookItem.setOnLongClickListener(v->{
        //edit ou delete 창이 뜨게 만들고 싶다.

        return true;
      });

    }

  }//class ViewHolder

}//class TableAdapter
