package com.example.apstudyvoca;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.CharMatcher;


public class BottomFragmentVocas extends Fragment {
  DBHelper helper;
  TextView editTextMakeTable;
  Button btnVocasMakeTable, btnVocasEdit;
  Button btnTest;
  View dialogMakeTable;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_bottom_vocas, container, false);

    helper = new DBHelper(getContext());

    btnVocasMakeTable = rootView.findViewById(R.id.btnVocasMakeTable);
    btnVocasEdit = rootView.findViewById(R.id.btnVocasEdit);
    btnVocasMakeTable.setOnClickListener(v->{ //'make list' 단어장세트 만들기버튼 이벤트처리
      dialogMakeTable = View.inflate(getContext(), R.layout.dialog_make_table, null);
      AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
      adb.setView(dialogMakeTable);

      //버튼리스너. "ADD" 란 문자열은 차후에 언어별로 다르게 변경
      adb.setPositiveButton("ADD", (dialog, which) -> {
        editTextMakeTable = dialogMakeTable.findViewById(R.id.editTextMakeTable);//인플레이트 주의!
        String listName = editTextMakeTable.getText().toString();
        if(listName.length()>=1){
          //String값 검사한 뒤에 create table호출.
          String charsToRemove = "+ ×÷=/_<>[]!@#₩%^&*()-'\":;,?`~\\|{}€£¥$°•○●□■♤♡◇♧☆▪︎¤《》¡¿.,";
          listName = CharMatcher.anyOf(charsToRemove).removeFrom(listName);
          Log.d("log", "입력된값 : "+listName);
          helper.createTable(listName);
        }
      });
      adb.setNegativeButton("CANCEL", null);
      adb.show();
    });


    btnTest = rootView.findViewById(R.id.btnTest);
    btnTest.setOnClickListener(v->{
      String[] sa = helper.listOfTables();

      String str = String.valueOf( helper.numberOfTables());
      Toast.makeText(getContext(), "n of tables : "+str, Toast.LENGTH_SHORT).show();

      for(String s : sa){
        Log.d("db", ">>> "+s);
      }

    });


    return rootView;
  }


}//class BottomFragmentVoca