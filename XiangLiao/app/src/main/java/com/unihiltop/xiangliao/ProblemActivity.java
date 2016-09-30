package com.unihiltop.xiangliao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.unihiltop.xiangliao.adapter.ProblemAdapter;
import com.unihiltop.xiangliao.util.UISkip;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yangyang on 2015/11/28.
 */
public class ProblemActivity extends Activity{
    private ImageView ivReturn;
    private ListView lvProblem;
    private ArrayList<String> list;
    private String[] problem;
    private ProblemAdapter problemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        initData();
        initView();
        initListenter();
    }

    private void initData() {
        list = new ArrayList<String>();
        problem = getResources().getStringArray(R.array.problem_common);
        list.addAll(Arrays.asList(problem));
         problemAdapter = new ProblemAdapter(ProblemActivity.this,list);
    }

    private void initView() {
        ivReturn = (ImageView) findViewById(R.id.imageView_return);
        lvProblem = (ListView)findViewById(R.id.listView_problem);
        lvProblem.setAdapter(problemAdapter);
    }
    private void initListenter() {
        MyListenter myListenter = new MyListenter();
        ivReturn.setOnClickListener(myListenter);
        lvProblem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               switch (position){
//                   case 0:
//                       UISkip.skip(false, ProblemActivity.this, ProblemDetailActivity.class);
//                       break;
//                   case 1:
//                       UISkip.skip(false, ProblemActivity.this, TwoproblemActivity.class);
//                       break;
//                   case 2:
//                       UISkip.skip(false, ProblemActivity.this, ThreeproblemActivity.class);
//                       break;
//                   case 3:
//                       UISkip.skip(false, ProblemActivity.this, FourproblemActivity.class);
//                       break;
//               }
               UISkip.skipToProblemDetailActivity(ProblemActivity.this,position);

            }
        });
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
