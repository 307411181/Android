package com.unihiltop.xiangliao.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.adapter.CalllogAdapter;
import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.data.ContactData;
import com.unihiltop.xiangliao.manager.ContactManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯
 */
public class CalllogRecordFragment extends Fragment implements OnClickListener {
    private static final String TAG_SAVE_CALL_LOG_LIST = "";
    private String lOG_TAG = "CALLFRAGMENT";
    private View     view;
    private TextView tv_cancel;//取消按钮
    private TextView tv_all;//全选按钮
    private TextView tv_edit;//编辑按钮
    private Button   btn_delete;//Listview底部删除按钮

    private ListView listview_recordscall;
    private ArrayList<Calllog> list = null;//传递到Adapter里面的集合
    public CalllogAdapter adapter;
    private ProgressDialog progressDialog = null;
    private String         type           = "hide";
    private CallPhone      callPhone      = null;

    List<Integer> del = new ArrayList<>();

    ArrayList<Calllog> newList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recently_record, container,
                false);
        initData(savedInstanceState);

        initView();
        initListenter();
        return view;
    }

    private void initData(Bundle savedInstanceState) {
        Log.e(lOG_TAG, "initData");
        callPhone = new CallPhone();
        list = new ArrayList<Calllog>();
        if (savedInstanceState != null) {
            newList = savedInstanceState.getParcelableArrayList(TAG_SAVE_CALL_LOG_LIST);
            if (newList != null) {
                Log.e(lOG_TAG, "原来数据 Calllog");
                list.addAll(newList);
            } else if (ContactData.get(getActivity()).calllogSize() > 0) {
                list.addAll(ContactData.get(getActivity()).getCalllogs());
                Log.e(lOG_TAG, "Calllog > 0");
            } else {
                Log.e(lOG_TAG, "原来数据 Calllog 为空");
                new CalllogTask().execute();
            }
        } else {
            Log.e(lOG_TAG, "重新加载");
            if (ContactData.get(getActivity()).calllogSize() != 0) {
                Log.e(lOG_TAG,"Address重新加载 != 0");
                list.addAll(ContactData.get(getActivity()).getCalllogs());
            } else {
                Log.e(lOG_TAG,"Address重新加载 == 0");
                new CalllogTask().execute();
            }
        }
        adapter = new CalllogAdapter(getActivity(), list, type);
        Log.e("第一次Adapter", "size====" + list.size());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void initView() {
        tv_cancel = (TextView) view.findViewById(R.id.Text_fragment_cancel);
        tv_all = (TextView) view.findViewById(R.id.Text_fragment_selectall);
        tv_edit = (TextView) view.findViewById(R.id.Text_fragment_clear);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);

        listview_recordscall = (ListView) view.findViewById(R.id.listview_recordscall);
        listview_recordscall.setAdapter(adapter);
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void initListenter() {

        //点击编辑按钮
        tv_edit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消按钮,删除按钮显示,本身隐藏,adapter的选择按钮显示,图标隐藏
                tv_edit.setVisibility(View.GONE);
                tv_cancel.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                tv_all.setVisibility(View.VISIBLE);
                show();
                adapter.isVisible = true;
                adapter.isIconVisible = false;
                adapter.notifyDataSetChanged();
            }
        });
        //点击取消退回以前的界面
        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //把编辑按钮逻辑倒置
                btn_delete.setVisibility(View.GONE);
                tv_edit.setVisibility(View.VISIBLE);
                tv_cancel.setVisibility(View.GONE);
                tv_all.setVisibility(View.GONE);

                hide();
                //把所有的按钮设置成未点击
                for (int i = 0; i < list.size(); i++) {
                    adapter.getItem(i).setChecked(false);
                    adapter.notifyDataSetChanged();
                }
                adapter.isVisible = false;
                adapter.isIconVisible = true;
                adapter.notifyDataSetChanged();
            }
        });


        /**
         * 删除按钮
         */
        btn_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isChecked()) {
                        del.add(i);
                    }
                }

                if (del.size() == 0) {
                    showToast("请勾选要删除的记录");
                    return;
                }

                Log.e("记录需要删除的del集合", del.size() + "-------------------" + del.toString());
                for (int i = del.size(); i > 0; i--) {
                    int j = i - 1;
                    Log.e("删除了", j + "");
                    list.remove(j);

                }

                //                adapter.notifyDataSetChanged();
                Log.e("点击删除按钮后-------------", "size====" + list.size());
                Log.e("点击删除按钮后-------------", "size====" + ContactData.get(getActivity()).getCalllogs().size());
                /**
                 * 清楚记录删除集合的数据,同时让页面回归原始
                 */
                del.clear();
                //把编辑按钮逻辑倒置
                btn_delete.setVisibility(View.GONE);
                tv_edit.setVisibility(View.VISIBLE);
                tv_cancel.setVisibility(View.GONE);
                tv_all.setVisibility(View.GONE);

                hide();
                //把所有的按钮设置成未点击
                for (int i = 0; i < list.size(); i++) {
                    adapter.getItem(i).setChecked(false);
                    adapter.notifyDataSetChanged();
                }

                adapter.isVisible = false;
                adapter.isIconVisible = true;
                adapter.notifyDataSetChanged();
            }
        });

        //点击全选通话记录全部选中
        tv_all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    adapter.getItem(i).setChecked(true);
                }
                Log.e("点击全选按钮后", "size====" + list.size());

                changeAdapter();
            }
        });

        //点击listview的item拨打电话
        listview_recordscall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView textViewName = (TextView) view.findViewById(R.id.textView_name);
                TextView textnumber = (TextView) view.findViewById(R.id.textView_number);
                String phonenumber = textnumber.getText().toString();
                String name = textViewName.getText().toString();
                if (TextUtils.isEmpty(phonenumber)) {
                    Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                phonenumber = phonenumber.replace("+86", "");

                Log.i("contactInfos1", "号码是" + phonenumber);
                Log.i("contactInfos1", "名字是" + ContactData.get(getActivity()).getName(phonenumber));
                callPhone.call(getActivity(), name, phonenumber.trim(), new CallPhone.OnCallListener() {
                    @Override
                    public void onCallSuccess(String name, String tel, long time) {
                        Calllog calllog = new Calllog();
                        calllog.setName(name);
                        calllog.setNumber(tel);
                        calllog.setDate(time);
                        list.add(0, calllog);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }


    public void addCallLog(String name, String tel, long time) {
        Calllog calllog = new Calllog();
        calllog.setName(name);
        calllog.setNumber(tel);
        calllog.setDate(time);
        calllog.setType(CallLog.Calls.OUTGOING_TYPE);
        list.add(0, calllog);
        adapter.notifyDataSetChanged();
    }

    public void addCallLog(Calllog calllog) {
        if (calllog != null) {
            list.add(0, calllog);
            adapter.notifyDataSetChanged();
        }

    }

    public void show() {
        type = "show";
        changeAdapter();
    }

    public void hide() {
        type = "hide";
        changeAdapter();
    }

    public void changeAdapter() {
        adapter = new CalllogAdapter(getActivity(), list, type);
        listview_recordscall.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(lOG_TAG, "onSaveInstanceState!");
        outState.putParcelableArrayList(TAG_SAVE_CALL_LOG_LIST, list);
        super.onSaveInstanceState(outState);
    }


    public void setCalllogs(List<Calllog> calllogs) {
        Log.e(lOG_TAG, "setCalllogs!!");
        list.clear();
        list.addAll(calllogs);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

    }

    class CalllogTask extends AsyncTask<Integer, Integer, ArrayList<Calllog>> {
        @Override
        protected ArrayList<Calllog> doInBackground(Integer... params) {
            if (ContactData.get(getActivity()).calllogSize() == 0) {
                Log.e(lOG_TAG, "if----!!!!!!!!!!!!!!!");
                ArrayList<Calllog> calllogs = ContactManager.getInstance(getActivity()).getRecordscall();
                return calllogs;
            } else {
                Log.e(lOG_TAG, "else-----!!!!!!!!!!!!!!!");
                List<Calllog> calllogs = ContactData.get(getActivity()).getCalllogs();
                return new ArrayList<Calllog>(calllogs);
            }
        }


        @Override
        protected void onPostExecute(ArrayList<Calllog> result) {
            Log.e(lOG_TAG, "onPostExecute-----!!!!!!!!!!!!!!!");
            setCalllogs(result);
            ContactData.get(getActivity()).clearCalllog();
            ContactData.get(getActivity()).setCalllogs(result);
        }
    }

    class CalllogUpdateTask extends AsyncTask<Integer, Integer, ArrayList<Calllog>> {
        @Override
        protected ArrayList<Calllog> doInBackground(Integer... params) {
            Log.e(lOG_TAG, "doInBackground-----!!!!!!!!!!!!!!!");
            return ContactManager.getInstance(getActivity()).getRecordscall();
        }

        @Override
        protected void onPostExecute(ArrayList<Calllog> result) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Log.e(lOG_TAG, "onPostExecute-----!!!!!!!!!!!!!!!");
            setCalllogs(result);
            // nList.clear();
            ContactData.get(getActivity()).clearCalllog();
            //  nList.addAll(result);
            ContactData.get(getActivity()).setCalllogs(result);
        }
    }

    @Override
    public void onResume() {
        Log.e(lOG_TAG, "onResume");
//        setCalllogs(ContactData.get(getActivity()).getCalllogs());
        super.onResume();
    }
}
