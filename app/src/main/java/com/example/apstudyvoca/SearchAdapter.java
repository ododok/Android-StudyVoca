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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

  private ArrayList<SearchItem> items;

  public SearchAdapter(ArrayList<SearchItem> items) {
    this.items = items;
  }

  @NonNull
  @Override //아이템 뷰를 위한 뷰홀대 객체를 생성&리턴
  public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = (LayoutInflater)
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.search_item, parent, false);
//inflate  search_item.xml
    SearchViewHolder viewHolder = new SearchViewHolder(context, view);
    return viewHolder;
  }

  @Override //recycler function
  public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
    SearchItem searchItem = items.get(position);
    holder.tvSearchTableName.setText(searchItem.getTableName());
    holder.tvSearchId.setText(String.valueOf(searchItem.get_id())); //int --> String [attention]
    holder.tvSearchWord.setText(searchItem.getWord());
    holder.tvSearchMeaning.setText(searchItem.getMeaning());
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void setArrayData(SearchItem searchItem){
    items.add(searchItem);
  }



  public class SearchViewHolder extends RecyclerView.ViewHolder{
    TextView tvSearchTableName, tvSearchId, tvSearchWord, tvSearchMeaning;
    LinearLayout linearLayoutSearch;

    public SearchViewHolder(Context context, @NonNull View itemView) {
      super(itemView);
      linearLayoutSearch = itemView.findViewById(R.id.linearLayoutSearch);
      tvSearchTableName = itemView.findViewById(R.id.tvSearchTableName);
      tvSearchId = itemView.findViewById(R.id.tvSearchId);
      tvSearchWord = itemView.findViewById(R.id.tvSearchWord);
      tvSearchMeaning = itemView.findViewById(R.id.tvSearchMeaning);

      //view (LinearLayout) click event //검색결과 클릭 이벤트.
      linearLayoutSearch.setOnClickListener(v->{
        //Toast.makeText(context, tvSearchWord.getText().toString(), Toast.LENGTH_SHORT).show();
        //if click -> open table query view. (Word list)
        Intent intent = new Intent(context, QueryTable.class);
        intent.putExtra("tableName", tvSearchTableName.getText().toString());
        context.startActivity(intent);
      });

    }
  }//class SearchViewHolder



}//class SearchAdapter
