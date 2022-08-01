package com.example.apstudyvoca;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class RandomAdapter extends FragmentStateAdapter {

  private ArrayList<Word> items;
  private int itemCount;



  public RandomAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Word> items) {
    super(fragmentActivity);
    this.items = items;
    itemCount = items.size();
  }


  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Collections.shuffle(items); //random 기능.
    return new RandomWordView(items.get(position));
    //유저가 선택한 포지션의 인덱스에 해당하는 Profil객체를 파라미터로 하는
    // RandomWordView 객체를 생성해서 리턴한다.
  }

  @Override
  public long getItemId(int position) {
    return super.getItemId(position);
  }

  @Override
  public int getItemCount() { //뷰페이저에서 보여질 최대 아이템 개수 설정.

    return itemCount;
  }



}//class RandomAdapter
