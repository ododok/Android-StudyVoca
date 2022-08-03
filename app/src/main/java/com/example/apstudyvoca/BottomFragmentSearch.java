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
      @Override //검색 버튼 누를 때
      public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getContext(), "검색어 : "+query, Toast.LENGTH_SHORT).show();
        initSearchResults(query);
        return false;
      }

      @Override //검색창에 글자 변경이 일어날 때
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


    //검색어를 가져와서 (String keyword);
    //DBhelper에서 검색어를 기반으로 ArrayList<SearchItem> list 를 만들어 리턴하는 메서드를 만들고.
    //검색어를 파라미터로 넣어 이 메서드를 호출하고
    //그 리턴값 ArrayList 데이터를 adapter에 setArrayData 메서드를 호출해서 붙이고
    //리사이클러뷰에 어댑터를 붙인다.

    //todo - make the method in DBHelper.java


    ArrayList<SearchItem> items = new ArrayList<>();
    items= helper.search(keyword);

    //test

//
//    items.add(new SearchItem("table", 10, "word1", "meaning1"));
//   items.add(new SearchItem("table AB", 3, keyword, "meaning2"));



    adapter = new SearchAdapter(items);
    recyclerViewSearch.setAdapter(adapter);

  }


}//class BottomFragmentSearch