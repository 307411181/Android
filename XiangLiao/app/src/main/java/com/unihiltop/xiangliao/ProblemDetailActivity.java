package com.unihiltop.xiangliao;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.unihiltop.xiangliao.adapter.AnswerAdapter;
import com.unihiltop.xiangliao.bean.IconDetailArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yangyang on 2015/11/28.
 */
public class ProblemDetailActivity extends Activity{
    private ImageView ivReturn;
    private ListView lvProblemText;
    private IconDetailArray list;
    public String[] textview;
    public int position;//
    private  List<IconDetailArray> lists;
    private final int[] icons1 = {0 };
    private final int[] icons2 = {R.drawable.img_safe_1,
            R.drawable.img_safe_2,R.drawable.img_safe_3,
            R.drawable.img_safe_4};
    private final int[] icons3 = {0
           };
    private final int[] icons4 = {0
           };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneproblem);
        initData();
        initView();
        initSteView();
        initListenter();
    }
    private void initData() {
        position = getIntent().getIntExtra("position",0);
        Log.i("positionss",position+"");
        Resources resources = getResources();
        lists = new LinkedList<IconDetailArray>();
        list = new IconDetailArray();
        switch (position){
            case 0:
                list.nameArray = resources.getStringArray(R.array.answer_common_one);
                list.iconArray = icons1;
                break;
            case 1:
                list.nameArray = resources.getStringArray(R.array.answer_common_two);
                list.iconArray = icons2;
                break;
            case 2:
                list.nameArray = resources.getStringArray(R.array.answer_common_three);
                list.iconArray = icons3;
                break;
            case 3:
                list.nameArray = resources.getStringArray(R.array.answer_common_four);
                list.iconArray = icons4;
                break;
        }

        list.count = list.nameArray.length;

    }
    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        lvProblemText = (ListView) findViewById(R.id.listView_problem_text);
        AnswerAdapter answerAdapter = new AnswerAdapter(ProblemDetailActivity.this,list);
       lvProblemText.setAdapter(answerAdapter);
    }
    private void initSteView() {

    }
    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);

    }
    private class MyListenter implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v == ivReturn){
                finish();
            }
        }
    }
}
