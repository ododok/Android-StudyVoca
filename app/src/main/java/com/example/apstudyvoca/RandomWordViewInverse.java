package com.example.apstudyvoca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class RandomWordViewInverse extends Fragment {
  TextView tvInverseRandomMeaning, tvInverseRandomWord;
  boolean wordVisible = false;
  ImageView imageViewTop, imageViewBottom;
  Word word;

  public RandomWordViewInverse(Word word) {
    this.word = word;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_random_word_inverse_view,
        container, false);

    tvInverseRandomMeaning = view.findViewById(R.id.tvInverseRandomMeaning);
    tvInverseRandomMeaning.setText(word.getMeaning());

    tvInverseRandomWord = view.findViewById(R.id.tvInverseRandomWord);
    tvInverseRandomWord.setText(null);


    imageViewTop = view.findViewById(R.id.imageViewTop);
    imageViewBottom = view.findViewById(R.id.imageViewBottom);

    imageViewBottom.setOnClickListener(v -> {
      if (!wordVisible) {
        tvInverseRandomWord.setText(word.getWord());
        wordVisible = true;
      } else {
        tvInverseRandomWord.setText(null);
        wordVisible = false;
      }
    });


    return view;
  }

}//class RandomWordViewInverse
