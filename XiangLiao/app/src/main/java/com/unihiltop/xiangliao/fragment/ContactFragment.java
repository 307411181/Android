package com.unihiltop.xiangliao.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unihiltop.xiangliao.R;
import com.unihiltop.xiangliao.bean.ContactInfo;
import com.unihiltop.xiangliao.call.utils.CallPhone;
import com.unihiltop.xiangliao.call.utils.StringUtils;
import com.unihiltop.xiangliao.data.ContactData;
import com.unihiltop.xiangliao.dialog.AlertDialog;
import com.unihiltop.xiangliao.manager.ContactManager;
import com.unihiltop.xiangliao.util.PhoneUtils;
import com.unihiltop.xiangliao.view.AlphaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @创建者 张京
 * @创建时间 2016/9/19 9:19
 * @描述 ${通讯录}
 */
public class ContactFragment extends Fragment implements OnClickListener {
    private View      view;
    private ImageView imageview_refresh;

    private ImageView iv_search;


    private CallPhone callPhone = null;
    private ContactAdapter contactAdapter;
    private ArrayList<ContactInfo> contacts = null;
    private ListView                 contactListView;
    private EditText                 searchEditText;
    private AlphaView                alphaView;
    private TextView                 overlay;
    private WindowManager            windowManager;
    private OverlayThread            overlayThread;
    private HashMap<String, Integer> alphaIndexer; // 存放存在的汉语拼音首字母和与之对应的列表位置
    private              Handler               handler             = new Handler();
    private              SparseArray<TextView> alphaArray          = new SparseArray<TextView>();
    private final static String                TAG_SAVE_STATE_LIST = "contact_list";

    private ProgressDialog progressDialog = null;

    //    private static ArrayList<ContactInfo> nContacts = new ArrayList<ContactInfo>();
    //    private static ArrayList<Calllog> nList = new ArrayList<Calllog>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container,
                false);
        initData(savedInstanceState);
        initView();
        initListenter();
        return view;
    }


    private void initData(Bundle savedInstanceState) {
        callPhone = new CallPhone();
        contacts = new ArrayList<ContactInfo>();
        ContactInfo contact = new ContactInfo();
        contact.setSortKey(" ");
        contact.setName("祥聊客服");
        contact.setPhone("400-6977-553");
        contacts.add(0, contact);
        if (savedInstanceState != null) {
            ArrayList<ContactInfo> newContacts = savedInstanceState.getParcelableArrayList(TAG_SAVE_STATE_LIST);
            if (newContacts != null) {
                Log.i("Address", "原来数据 ContactInfo");
                contacts.addAll(newContacts);
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setSortKey(" ");
                contactInfo.setName("祥聊客服");
                contactInfo.setPhone("400-6977-553");
                contacts.add(0, contactInfo);
            } else if (ContactData.get(getActivity()).size() != 0) {
                contacts.addAll(ContactData.get(getActivity()).getContacts());
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setSortKey(" ");
                contactInfo.setName("祥聊客服");
                contactInfo.setPhone("400-6977-553");
                contacts.add(0, contactInfo);

            } else {
                Log.i("Address", "原来数据 ContactInfo 为空");
                new ContactInfoTask().execute();
            }
        } else {
            Log.i("Address", "重新加载");
            new ContactInfoTask().execute();
        }
        alphaIndexer = new HashMap<String, Integer>();
        overlayThread = new OverlayThread();
        contactAdapter = new ContactAdapter(getActivity(), contacts);
        Log.i("Address", "contactAdapter size=4" + contacts.size());


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void initView() {
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        imageview_refresh = (ImageView) view.findViewById(R.id.imageview_refresh);


        contactListView = (ListView) view.findViewById(R.id.listView_main_contact);
        //        setContactInfos(contacts);
        contactListView.setAdapter(contactAdapter);
        //        Log.i("Address", "contactListView size=" + contactListView.getCount());


        searchEditText = (EditText) view.findViewById(R.id.editText_contact_search);
        alphaView = (AlphaView) view.findViewById(R.id.alphaView_main_contact);
        initOverlay();

    }

    public void initListenter() {

        imageview_refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContacts();

            }
        });
        //点击listview的item拨打电话

        AlphaChangedListener listener = new AlphaChangedListener();

        alphaView.setOnAlphaChangedListener(listener);
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged");
                iv_search.setVisibility(View.INVISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                System.out.println("beforeTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("afterTextChanged");
                String key = s.toString();
                contacts = new ArrayList<ContactInfo>();
                for (ContactInfo contact : ContactData.get(getActivity()).getContacts()) {
                    if (contact.phone.contains(key) || contact.sortKey.toUpperCase().contains(key.toUpperCase()) || contact.name.contains(key)) {
                        contacts.add(contact);
                    }
                }
                if (TextUtils.isEmpty(key)) {
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setSortKey(" ");
                    contactInfo.setName("快话客服");
                    contactInfo.setPhone("400-6977-553");
                    contacts.add(0, contactInfo);
                }
                setAlphaIndexer(contacts);
                contactAdapter.setItemList(contacts);
                contactListView.setAdapter(contactAdapter);
                AlphaChangedListener listener = new AlphaChangedListener();
                alphaView.setOnAlphaChangedListener(listener);
            }

        });

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {
                TextView textphone = (TextView) view.findViewById(R.id.textView_contact_phone);
                String phone = textphone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getActivity(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                phone = phone.replace("+86", "");
                phone = phone.replace("-", "");
                if (phone.equals("4006977553")) {
                    PhoneUtils.call(getActivity(), "4006977553");
                    return;
                }
                TextView textname = (TextView) view.findViewById(R.id.textView_contact_name);
                String name = textname.getText().toString();
                Log.i("TT", "点击的名字为" + name);
                CallOrNot(phone, name);
            }
        });
    }

    public void CallOrNot(final String number, final String name) {
        AlertDialog alertDialog = new AlertDialog(getActivity());
        alertDialog.setAlertClickListener(new AlertDialog.AlertClickListener() {
            @Override
            public void sure() {
                callPhone.call(getActivity(), name, number.trim(), null);
            }
        });
        alertDialog.setAlert("是否给" + name + ":" + number.trim() + "拨打电话");
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

    }

    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TAG_SAVE_STATE_LIST, contacts);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(overlay);
        super.onDestroy();
    }


    public void setContactInfos(List<ContactInfo> contactInfos) {
        contacts.clear();
        contacts.addAll(contactInfos);
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setSortKey(" ");
        contactInfo.setName("祥聊客服");
        contactInfo.setPhone("400-6977-553");
        contacts.add(0, contactInfo);
        setAlphaIndexer(contacts);
        if (getActivity() != null) {
            contactAdapter = new ContactAdapter(getActivity(), contacts);
            Log.i("Address", "setContactInfos size" + contacts.size());
            contactListView.setAdapter(contactAdapter);
            AlphaChangedListener listener = new AlphaChangedListener();
            alphaView.setOnAlphaChangedListener(listener);
        }
    }


    // 设置overlay不可见
    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    private class AlphaChangedListener implements AlphaView.OnAlphaChangedListener {

        @Override
        public void OnAlphaChanged(String s, int index) {
            if (s != null && s.trim().length() > 0) {
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                handler.postDelayed(overlayThread, 700);
                if (alphaIndexer.get(s) != null) {
                    int position = alphaIndexer.get(s);
                    contactListView.setSelection(position);
                }
            }
        }

    }


    private void setAlphaIndexer(List<ContactInfo> contacts) {
        alphaIndexer = new HashMap<String, Integer>();
        if (contacts != null && !contacts.isEmpty()) {
            String alpha = StringUtils.formatAlpha(contacts.get(0).sortKey);
            alphaIndexer.put(alpha, 0);
            for (int i = 1; i < contacts.size(); i++) {
                alpha = StringUtils.formatAlpha(contacts.get(i).sortKey);
                String prvContactsAlpha = StringUtils.formatAlpha(contacts.get(i - 1).sortKey);
                if (!alpha.equals(prvContactsAlpha)) {
                    alphaIndexer.put(alpha, i);
                }

            }
        }

    }


    private class ContactAdapter extends BaseAdapter {


        private LayoutInflater    myLayoutInflater = null;
        private List<ContactInfo> contactInfos     = null;

        public ContactAdapter(Context context, List<ContactInfo> contactInfos) {
            myLayoutInflater = LayoutInflater.from(getActivity());
            this.contactInfos = contactInfos;
            setAlphaIndexer(contactInfos);
        }


        public void setItemList(List<ContactInfo> contacts) {
            this.contactInfos = contacts;
            notifyDataSetInvalidated();
        }

        @Override
        public int getCount() {
            return contactInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return contactInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = myLayoutInflater.inflate(R.layout.listview_item_contact, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.textView_contact_name);
                viewHolder.phone = (TextView) convertView.findViewById(R.id.textView_contact_phone);
                viewHolder.alpha = (TextView) convertView.findViewById(R.id.textView_contact_alpha);
                viewHolder.linearLayout_contact_alpha = (LinearLayout) convertView.findViewById(R.id.linearLayout_contact_alpha);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.linearLayout_contact_alpha.setVisibility(View.GONE);
            String a = StringUtils.formatAlpha(contactInfos.get(0).sortKey);
            Log.i("alpha", a);
            if (position == 0) {
                String alpha = StringUtils.formatAlpha(contactInfos.get(0).sortKey);
                viewHolder.linearLayout_contact_alpha.setVisibility(View.VISIBLE);
                viewHolder.alpha.setText(alpha);
                alphaArray.append(0, viewHolder.alpha);
            } else if (position > 0) {
                String alpha = StringUtils.formatAlpha(contactInfos.get(position).sortKey);
                String prvContactsAlpha = StringUtils.formatAlpha(contactInfos.get(position - 1).sortKey);
                if (!alpha.equals(prvContactsAlpha)) {
                    viewHolder.linearLayout_contact_alpha.setVisibility(View.VISIBLE);
                    viewHolder.alpha.setText(alpha);
                    alphaArray.append(position, viewHolder.alpha);
                }
            }

            viewHolder.name.setText(contactInfos.get(position).name);
            viewHolder.phone.setText(contactInfos.get(position).phone);

            return convertView;
        }


        private final class ViewHolder {
            TextView     name;
            TextView     phone;
            TextView     alpha;
            LinearLayout linearLayout_contact_alpha;
        }

    }


    public void refreshContacts() {
        //        List<ContactInfo> contactInfos = ContactData.get(getActivity()).getContacts();
        //        if (contactInfos != null && !contactInfos.isEmpty() && !contactInfos.equals(contacts)) {
        //            contacts = new ArrayList<ContactInfo>();
        //            contacts.addAll(contactInfos);
        //            contactAdapter.setItemList(contacts);
        //        }
        //
        //        /*contacts = new ArrayList<ContactInfo>();
        //        contacts.addAll(contactInfos);
        //        contactAdapter.setItemList(contacts);
        //        Log.i("TAG","联系人已刷新");*/
        progressDialog = ProgressDialog.show(
                getActivity(), "刷新中请稍等", "");
        new ContactInfoUpdateTask().execute(0);
    }


    class ContactInfoTask extends AsyncTask<Integer, Integer, List<ContactInfo>> {

        @Override
        protected List<ContactInfo> doInBackground(Integer... params) {
            if (ContactData.get(getActivity()).size() == 0)
                return ContactManager.getInstance(getActivity()).getAllcontactInfos();
            return new ArrayList<ContactInfo>(ContactData.get(getActivity()).getContacts());
        }

        @Override
        protected void onPostExecute(List<ContactInfo> result) {
            setContactInfos(result);
            //            Log.i("Address", "contacts result size=" + result.size());
            //            nContacts.clear();
            //            nContacts.addAll(result);
            ContactData.get(getActivity()).setContacts(result);
        }
    }

    class ContactInfoUpdateTask extends AsyncTask<Integer, Integer, List<ContactInfo>> {

        @Override
        protected List<ContactInfo> doInBackground(Integer... params) {
            progressDialog.dismiss();
            return ContactManager.getInstance(getActivity()).getAllcontactInfos();
        }

        @Override
        protected void onPostExecute(List<ContactInfo> result) {
            progressDialog.dismiss();
            setContactInfos(result);
            ContactData.get(getActivity()).setContacts(result);
        }
    }
}
