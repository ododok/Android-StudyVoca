package com.example.apstudyvoca;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
  ViewGroup rootView;
  RecyclerView recyclerView;
  TableAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = (ViewGroup)inflater.inflate(R.layout.fragment_bottom_vocas, container, false);

    initDb();
    initRecyclerView();

    //////
    btnTest = rootView.findViewById(R.id.btnTest);
    btnTest.setOnClickListener(v->{
      int nt = helper.numberOfTables();
      String[] arr = helper.listOfTables();
      for(int i=0; i<arr.length; i++){
        Log.d("db", arr[i]);
      }
      Toast.makeText(getContext(), "테이블수 : "+nt, Toast.LENGTH_SHORT).show();
    });

    return rootView;

  }//onCreateView

  private void initRecyclerView() {
    recyclerView = rootView.findViewById(R.id.recyclerViewTable);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new TableAdapter();
    int numTable = helper.numberOfTables();

    //테이블 이름 리스트를 String형 배열에 리턴받는다.
    //각각의 테이블별 raw수도 리턴받는다.
    //고로 String[n][2]; 이면 될 것 같다. n은 테이블 개수.

    String[] sa1 = helper.listOfTables();
    String[][] sa2 = new String[numTable][2];
    for(int i=0; i<numTable; i++){
      sa2[i][0] = sa1[i];
      sa2[i][1] = String.valueOf(helper.tableSize(sa1[i]));//작동하나? ca marche vraiment?
      adapter.setArrayData(new Table(sa2[i][0], Integer.parseInt(sa2[i][1])));
    }
    recyclerView.setAdapter(adapter);
    //테이블 수가 8개를 넘어가면 마지막 테이블이 리사이클러뷰에 안 나오는 문제 있음.
  }


  private void initDb() {
    helper = new DBHelper(getContext());

    btnVocasMakeTable = rootView.findViewById(R.id.btnVocasMakeTable);
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
          String charsToRemove = "\n+ ×÷=/_<>[]!@#₩%^&*()-'\":;,?`~\\|{}€£¥$°•○●□■♤♡◇♧☆▪︎¤《》¡¿.,";
          listName = CharMatcher.anyOf(charsToRemove).removeFrom(listName);
          //테이블이름이 숫자로 시작하면 입력 안 되는 문제 해결할 것.
          Log.d("log", "입력된값 : "+listName);
          helper.createTable(listName);
          editTextMakeTable.setText(null);
        }
        initRecyclerView();
      });

      adb.setNegativeButton("CANCEL", null);
      adb.show();
    });


    btnVocasEdit = rootView.findViewById(R.id.btnVocasEdit);
    btnVocasEdit.setOnClickListener(v->{

    });

  }


}//class BottomFragmentVoca