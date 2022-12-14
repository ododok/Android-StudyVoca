package com.example.apstudyvoca;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    CardView tableItem;
    TextView textViewTableName, textViewNumberRaw;
    EditText editTextNewTabName;
    ImageView imageViewEditTable, imageViewDeleteTable;
    LinearLayout linearLayoutTable, layoutEditTable;
    View dialogView;
    boolean editTableMode = false; //false ==invisible layoutEditTable, true==visible layoutEditTable



    public TableViewHolder(Context context, @NonNull View itemView) {
      super(itemView);
      tableItem = itemView.findViewById(R.id.tableItem);
      textViewTableName = itemView.findViewById(R.id.textViewTableName);
      textViewNumberRaw = itemView.findViewById(R.id.textViewNumberRaw);
      imageViewEditTable = itemView.findViewById(R.id.imageViewEditTable);
      imageViewDeleteTable = itemView.findViewById(R.id.imageViewDeleteTable);
      linearLayoutTable = itemView.findViewById(R.id.linearLayoutTable);
      layoutEditTable = itemView.findViewById(R.id.layoutEditTable);
      DBHelper helper = new DBHelper(context);

      //click event (normal click)
      tableItem.setOnClickListener(v->{
        if(editTableMode==true) {
          layoutEditTable.setVisibility(View.INVISIBLE);
          linearLayoutTable.setBackgroundColor(Color.WHITE);
          editTableMode = false;
        } else {
          //fragment query table - ????????? ?????? ???????????? ?????? activity ?????????
          //(QueryTable.java & activity_query_table.xml)
          Intent intent = new Intent(context, QueryTable.class);
          intent.putExtra("tableName", textViewTableName.getText().toString());
          context.startActivity(intent);
        }
      });

      //Long click event
      tableItem.setOnLongClickListener(view0->{
        //edit ou delete ?????? ?????? ????????? ??????.
        layoutEditTable.setVisibility(View.VISIBLE);
        linearLayoutTable.setBackgroundColor(Color.LTGRAY);
        editTableMode = true;
        return true;
      });

      //Edit Button listener
      imageViewEditTable.setOnClickListener(view->{
        if(editTableMode == true){
          //Toast.makeText(context, "edit button pushed (Toast:TableAdapter.java)",Toast.LENGTH_SHORT).show();

          //????????? ??? ???????????? ?????? ??????????????? refresh??? ???.
          dialogView = View.inflate(context, R.layout.dialog_modif_table, null);
          AlertDialog.Builder adb = new AlertDialog.Builder(context);
          adb.setView(dialogView);
          adb.setTitle("\uD83D\uDCC2 "+ textViewTableName.getText().toString());


          // Modify button listener
          adb.setPositiveButton(R.string.Modify, (dialog, which) -> {
            editTextNewTabName = dialogView.findViewById(R.id.editTextNewTabName);
            //(???) dialog??? ?????? ????????? ??????reference??? ?????? dialog ????????? ????????? ??????.
            //dialogMakeTable.findViewById ?????? ??? ?????? ????????????. rootView.findViewById?????? ?????????.
            String oldName = textViewTableName.getText().toString();
            String newName = editTextNewTabName.getText().toString();

           // call editTableName method
            helper.editTableName(oldName, newName);

            //???????????? ????????? ?????? ??? ?????????.
            layoutEditTable.setVisibility(View.INVISIBLE);
            linearLayoutTable.setBackgroundColor(Color.WHITE);
            editTableMode = false;

          });



          //CANCEL BUTTON LISTENER
          adb.setNegativeButton(R.string.Cancel, (dialog, which)->{
            //????????? ???????????? ???????????? ????????????, ???????????? ????????? ?????? ??? ????????? ??????.
            layoutEditTable.setVisibility(View.INVISIBLE);
            linearLayoutTable.setBackgroundColor(Color.WHITE);
            editTableMode = false;
          });

          adb.show();
        }
      });


      //Delete Button Listener
      imageViewDeleteTable.setOnClickListener(v->{
        if(editTableMode == true){

          //????????? table??? ??????????????? ????????? ??? ??? ?????? dialog??? ???????????? ?????? ???.

          //????????? ??? ???????????? ?????? ??????????????? refresh??? ???.
          AlertDialog.Builder adb = new AlertDialog.Builder(context);
          adb.setTitle("\uD83D\uDCC2 "+textViewTableName.getText().toString());
          adb.setMessage(R.string.WannaDelete);

          //DELETE BUTTON EVENT
          adb.setPositiveButton(R.string.Delete, (dialog, which)->{
            //call delete table methode

            helper.deleteTable(textViewTableName.getText().toString());

            //delete on the ArrayList also
            items.removeIf(t -> t.getTableName().equals(textViewTableName.getText().toString()));

            //???????????? ????????? ?????? ??? ?????????.
            layoutEditTable.setVisibility(View.INVISIBLE);
            linearLayoutTable.setBackgroundColor(Color.WHITE);
            editTableMode = false;
            notifyDataSetChanged(); //refresh RecyclerView
          });

          //CANCEL BUTTON EVENT
          adb.setNegativeButton(R.string.Cancel, (dialog, which)->{
            //????????? ???????????? ???????????? ????????????, ???????????? ????????? ?????? ??? ????????? ??????.
            layoutEditTable.setVisibility(View.INVISIBLE);
            linearLayoutTable.setBackgroundColor(Color.WHITE);
            editTableMode = false;
            notifyDataSetChanged(); //refresh RecyclerView
          });

          adb.show();
        }
      });

    }//method TableViewHolder

  }//class ViewHolder



}//class TableAdapter
