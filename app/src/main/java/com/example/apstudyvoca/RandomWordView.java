package com.example.apstudyvoca;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RandomWordView extends Fragment {

  TextView textViewRandomWord, textViewRandomMeaning;
  Word word;
  ViewPager2 viewPager;

  public RandomWordView(Word word) {
    this.word = word;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_random_word_view,
        container, false);

    textViewRandomWord = view.findViewById(R.id.textViewRandomWord);
    textViewRandomWord.setText(word.getWord());
    textViewRandomMeaning = view.findViewById(R.id.textViewRandomMeaning);
    textViewRandomMeaning.setText(word.getMeaning());




    return view;
  }//onCreateView




}//class RandomWordView