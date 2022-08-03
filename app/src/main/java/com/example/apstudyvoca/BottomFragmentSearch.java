package com.example.apstudyvoca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class BottomFragmentSearch extends Fragment {
  SearchView searchView;
  ViewGroup rootView;
  RecyclerView recyclerViewSearch;
  SearchAdapter adapter;
  DBHelper helper;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bottom_search, container, false);
    helper = new DBHelper(getContext());

    //todo search view
    searchView = rootView.findViewById(R.id.searchView);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override //검색 버튼 누를 때 //When user search ( event)
      public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(getContext(), "keyword : "+query, Toast.LENGTH_SHORT).show();
        initSearchResults(query);
        return false;
      }

      @Override //검색창에 글자 변경이 일어날 때 //case of change on searching bar
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });






    return rootView;
  }//onCreateView

  public void initSearchResults(String keyword) {
    //recycler view
    recyclerViewSearch = rootView.findViewById(R.id.recyclerViewSearch);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
    recyclerViewSearch.setLayoutManager(layoutManager);




    ArrayList<SearchItem> items;
    items= helper.search(keyword);

    //test
//    items.add(new SearchItem("table", 10, "word1", "meaning1"));
//   items.add(new SearchItem("table AB", 3, keyword, "meaning2"));

    if(items.size()==0){
      Toast.makeText(getContext(), R.string.noSearchResult, Toast.LENGTH_SHORT).show();
    }

    adapter = new SearchAdapter(items);
    recyclerViewSearch.setAdapter(adapter);

  }


}//class BottomFragmentSearch