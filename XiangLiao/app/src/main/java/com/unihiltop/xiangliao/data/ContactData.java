package com.unihiltop.xiangliao.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.bean.CalllogComparator;
import com.unihiltop.xiangliao.bean.ContactInfo;
import com.unihiltop.xiangliao.bean.ContactInfoComparator;
import com.unihiltop.xiangliao.manager.ContactManager;
import com.unihiltop.xiangliao.util.PreferencesHelper;
import com.unihiltop.xiangliao.util.RegExpUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class ContactData {

	/**
	 * 所有联系人
	 */
	//通话记录
	private final static  String CALL_LOG = "call_log";
	//联系人
	private final static String PERSON_CONNECTION = "person_connection";
	private List<ContactInfo> contactInfos = null;
	private List<Calllog> calllogs = null;
	private static ContactData contactData = null;
	private PreferencesHelper preferencesHelper;
	private Context context;
	private ContactData(Context context){
		this.context = context;
		contactInfos = new LinkedList<ContactInfo>();
		calllogs = new LinkedList<Calllog>();
		preferencesHelper = PreferencesHelper.get(context);
		initContacts();
		initCalllog();
	}

	public static ContactData get(Context context){
		if (contactData ==null){
			contactData = new ContactData(context);
		}
		return contactData;
	}


	public List<ContactInfo> getContacts(){
		if (contactInfos.size() > 0){
			return contactInfos;
		}
		String json = preferencesHelper.getString(PERSON_CONNECTION,null);
		if (json != null){
			List<ContactInfo> list = new Gson().fromJson(json,new TypeToken<List<ContactInfo>>(){}.getType());
			if (list != null){
				contactInfos = list;
			}
		}
		Collections.sort(contactInfos, new ContactInfoComparator());
		return contactInfos;
	}

	public List<Calllog> getCalllogs() {
		if (calllogs.size() > 0) {
			 List<Calllog>  list = ContactManager.getInstance(context).getRecordscall(calllogs.get(0).getDate());
			if (list!=null&&list.size() > 0){
				calllogs.addAll(0,list);
				setCalllogs(calllogs);
			}

			return calllogs;
		}
		String json = preferencesHelper.getString(CALL_LOG, null);
		if (json != null) {
			List<Calllog> list = new Gson().fromJson(json, new TypeToken<List<Calllog>>() {
			}.getType());
			if (list != null) {
				calllogs = list;
			}
		}
		Collections.sort(calllogs, new CalllogComparator());
		if (calllogs.size() > 0)
			calllogs.addAll(0, ContactManager.getInstance(context).getRecordscall(calllogs.get(0).getDate()));
			setCalllogs(calllogs);
		return calllogs;
	}

	public ArrayList<Calllog> getCalllogArrays() {
		ArrayList<Calllog> arrayList = new ArrayList<Calllog>();
		if (calllogs.size() > 0) {
			arrayList.addAll(calllogs);
			return arrayList;
		}
		String json = preferencesHelper.getString(CALL_LOG, null);
		if (json != null) {
			List<Calllog> list = new Gson().fromJson(json, new TypeToken<List<Calllog>>() {
			}.getType());
			if (list != null) {
				arrayList.addAll(list);
			}
		}
		Collections.sort(arrayList, new CalllogComparator());
		return arrayList;
	}
	public void setCalllogs(List<Calllog> calllog){
		calllogs = calllog;
		String calllogsJson = new Gson().toJson(calllogs);
		Log.i("ContactData", "setCalllogs=" + calllogsJson);
		preferencesHelper.put(CALL_LOG, calllogsJson);

	}

	public void clearCalllog(){
		calllogs.clear();
	}
	public void addCalllog(Calllog calllog){
		calllogs.add(0, calllog);
		String calllogsJson = new Gson().toJson(calllogs);
		Log.e("ContactData", "setCalllogs=" + calllogsJson);
		preferencesHelper.put(CALL_LOG, calllogsJson);
	}

	public int calllogSize() {
		return calllogs.size();
	}

	private void initCalllog(){
		if (calllogs.size() == 0){
			String json = preferencesHelper.getString(CALL_LOG, null);
			if (!TextUtils.isEmpty(json)) {
				List<Calllog> list = new Gson().fromJson(json, new TypeToken<List<Calllog>>() {
				}.getType());
				if (list != null) {
					calllogs = list;
				}
			}
			Collections.sort(calllogs, new CalllogComparator());
		}
		if(calllogs.size() > 0){
			calllogs.addAll(0, ContactManager.getInstance(context).getRecordscall(calllogs.get(0).getDate()));
			setCalllogs(calllogs);
		}

	}

	private void initContacts(){
		String json = preferencesHelper.getString(PERSON_CONNECTION,null);
		if (json != null){
			List<ContactInfo> list = new Gson().fromJson(json, new TypeToken<List<ContactInfo>>() {
			}.getType());
			if (list != null){
				contactInfos = list;

			}
		}
		Collections.sort(contactInfos, new ContactInfoComparator());
	}
	/**
	 *
	 * @param contactInfo
	 */
	public void addContact(ContactInfo contactInfo){
		contactInfos.add(contactInfo);
		Collections.sort(contactInfos, new ContactInfoComparator());
	}

	/**
	 *
	 * @param linkmans
	 */
	public void addContacts(List<ContactInfo> linkmans){
		contactInfos.addAll(linkmans);
		Collections.sort(contactInfos, new ContactInfoComparator());
	}

	/**
	 *
	 * @param linkmans
	 */
	public void setContacts(List<ContactInfo> linkmans){
		contactInfos = linkmans;
		preferencesHelper.put(PERSON_CONNECTION, new Gson().toJson(contactInfos));
	}

	 public void clear(){
		 contactInfos.clear();
	 }

	public int size() {
		return contactInfos.size();
	}

	public String getName(String phone) {
		Log.i("contactInfos1","phone0="+phone);
		if (phone == null)
			return  "";
		for (ContactInfo contactInfo:contactInfos){

			if (!TextUtils.isEmpty(contactInfo.getPhone()) && contactInfo.getPhone().trim().contains(phone)){
				Log.i("contactInfos1","contactInfos1="+contactInfo.getName());
				return contactInfo.getName();

			}

		}
		Log.i("contactInfos1","phone="+phone);
		return phone;
	}
	public List<ContactInfo> getContactNumbers() {
		List<String> numbers = new LinkedList<String>();
		List<ContactInfo> contacts = new LinkedList<ContactInfo>();
		for (ContactInfo contact:contactInfos){
			String phone = null;
			if (contact.getPhone() != null){
				phone = contact.getPhone().trim().replace("+86", "");
			}else {
				continue;
			}

			if (RegExpUtil.match(RegExpUtil.REGEXP_PHONE,phone)
					&&!numbers.contains(phone)){
				numbers.add(contact.getPhone());
				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setName(contact.getName());
				contactInfo.setPhone(phone);
				contacts.add(contactInfo);
			}
		}
		numbers.clear();
		numbers = null;
		return contacts;
	}

	public ArrayList<ContactInfo> getContactInfoArrays() {
		if (contactInfos.size() == 0){
			String json = preferencesHelper.getString(PERSON_CONNECTION,null);
			if (json != null){
				List<ContactInfo> list = new Gson().fromJson(json,new TypeToken<List<ContactInfo>>(){}.getType());
				if ( list != null){
					contactInfos = list;

				}
			}
		}
		ArrayList<ContactInfo> arrayList = new ArrayList<ContactInfo>();
		arrayList.addAll(contactInfos);
		Collections.sort(arrayList, new ContactInfoComparator());
		return arrayList;
	}
}
