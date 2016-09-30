package com.unihiltop.xiangliao.manager;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Data;
import android.text.TextUtils;
import android.util.Log;

import com.unihiltop.xiangliao.bean.Calllog;
import com.unihiltop.xiangliao.bean.ContactInfo;
import com.unihiltop.xiangliao.util.PinYinUtil;
import com.unihiltop.xiangliao.util.PreferencesHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ContactManager {
    /**
     *
     */
    private static final String URI_CONTACT_PHONES_FILTER = "content://com.android.contacts/data/phones/filter/";
    private static ContactManager instance = null;
    private final Context context;
    private ContentResolver contentResolver = null;
    private String Key = "key";

    private ContactManager(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public static ContactManager getInstance(Context context) {
        if (instance == null) {
            instance = new ContactManager(context);
        }
        return instance;
    }

    /**
     * @param phone
     * @return
     */
    public Bitmap getPersonPhoto(String phone) {
        Bitmap bitmap = null;
        Uri uriNumber2Contacts = Uri.parse(URI_CONTACT_PHONES_FILTER + phone);
        Cursor cursorCantacts = contentResolver.
                query(uriNumber2Contacts, null, null, null, null);
        if (cursorCantacts.getCount() > 0) { //若游标不为0则说明有头像,游标指向第一条记录
            cursorCantacts.moveToFirst();
            Long contactID = cursorCantacts.getLong(cursorCantacts
                    .getColumnIndex("contact_id"));
            Uri uri = ContentUris
                    .withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
            InputStream input = ContactsContract.Contacts
                    .openContactPhotoInputStream(contentResolver, uri);
            bitmap = BitmapFactory.decodeStream(input);
        }
        cursorCantacts.close();
        return bitmap;
    }

    /**
     * 得到所有联系人
     *
     * @return
     */
    public ArrayList<ContactInfo> getAllcontactInfos() {
        ArrayList<ContactInfo> linkmanInfos = new ArrayList<ContactInfo>();
        String[] projection = {"_id", "display_name", "sort_key"};
        Cursor cursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
                projection, null, null, " sort_key COLLATE LOCALIZED ASC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String rowId = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (!TextUtils.isEmpty(name) && !name.equals("祥聊电话")) {
                    for (String phone : getContactPhones(rowId)) {
                        ContactInfo linkmanInfo = new ContactInfo();

                        linkmanInfo.name = name;
                        linkmanInfo.phone = phone;
//					linkmanInfo.email = email;
                        if (TextUtils.equals(linkmanInfo.name, linkmanInfo.phone)){
                           continue;
                        }
                        linkmanInfo.sortKey = PinYinUtil.GetFirstPinyin(name);
                        Log.i("linkmanInfo.sortKey", "sort_key" + linkmanInfo.sortKey);
                        linkmanInfo.rowId = rowId;
                        linkmanInfos.add(linkmanInfo);
                    }
                }

            }
            cursor.close();
        }

        return linkmanInfos;

    }


    private String getContactName(String rowId) {
        String name = null;
        String[] projection = {"display_name"};
        String selection = BaseColumns._ID + "=?";
        String[] selectionArgs = {rowId};
        Cursor cursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex("display_name"));
            }
            cursor.close();
        }
        return name;
    }


    /**
     * 根据mimeType、data类型和对应的值得到row_contact_id
     *
     * @param dataType
     * @param mimeType
     * @param data
     * @return
     */
    public ArrayList<String> getRowContactID(String mimeType, String dataType, String data) {
        ArrayList<String> rowids = null;
        String selection = dataType
                + "=? and "
                + Data.MIMETYPE
                + "=?";
        String[] projection = {Data.RAW_CONTACT_ID};
        String[] selectionArgs = {data, mimeType};
        Cursor contactIdCursor = contentResolver.query(Data.CONTENT_URI, projection,
                selection,
                selectionArgs,
                null);
        if (contactIdCursor != null) {
            rowids = new ArrayList<String>();
            int rowIdColumn = contactIdCursor.getColumnIndex(Data.RAW_CONTACT_ID);
            while (contactIdCursor.moveToNext()) {
                String rowid = contactIdCursor.getString(rowIdColumn);
                if (rowid != null) {
                    rowids.add(rowid);
                }

            }
            contactIdCursor.close();
        }
        return rowids;

    }

    public long getRowContactId(String data) {
        long rowid = -1;
        String selection = CommonDataKinds.Phone.NUMBER
                + "=? and "
                + Data.MIMETYPE
                + "=?";
        String[] projection = {Data.RAW_CONTACT_ID};
        String[] selectionArgs = {data, CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
        Cursor contactIdCursor = contentResolver.query(Data.CONTENT_URI, projection,
                selection,
                selectionArgs,
                null);
        if (contactIdCursor != null) {

            int rowIdColumn = contactIdCursor.getColumnIndex(Data.RAW_CONTACT_ID);
            while (contactIdCursor.moveToNext()) {
                rowid = contactIdCursor.getLong(rowIdColumn);
            }
            contactIdCursor.close();
        }
        return rowid;

    }

    /**
     * @param uri
     * @param dataType
     * @param rowId
     * @return
     */
    private String getContactDataByRow(Uri uri, String dataType, String rowId) {
        String phone = null;
        String[] projection = {dataType};
        String selection = Data.RAW_CONTACT_ID + "=?";
        String[] selectionArgs = {rowId};
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(dataType));
            }
            cursor.close();
        }
        return phone;

    }

    private ArrayList<String> getContactPhones(String rowId) {
        ArrayList<String> phones = new ArrayList<String>();
        String phone = null;
        String[] projection = {CommonDataKinds.Phone.NUMBER};
        String selection = CommonDataKinds.Phone.RAW_CONTACT_ID + "=?";
        String[] selectionArgs = {rowId};
        Cursor cursor = contentResolver.query(CommonDataKinds.Phone.CONTENT_URI, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                if (!TextUtils.isEmpty(phone)) {
                    phones.add(phone.replace(" ", ""));
                }
            }
            cursor.close();
        }
        return phones;
    }

    public void insertKuaiHua(Context context, String phone) {
        if (phone == null)
            return;
        long rowId = PreferencesHelper.get(context).getLong(Key, -1);
        Log.i("rowId", "rowId0=" + rowId);
        if (rowId < 0) {
            rowId = getRowContactId("076938940014");
            Log.i("rowId", "rowId1=" + rowId);
            if (rowId < 0) {
                rowId = insertKuaiHuas();
                Log.i("rowId", "rowId2=" + rowId);
            }
            PreferencesHelper.get(context).put(Key, rowId);
        }
        insertContact(rowId, phone);
    }


    /**
     * 查询所有的通话记录
     */
    public ArrayList<Calllog> getRecordscall() {
        ArrayList<Calllog> logs = new ArrayList<Calllog>();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] columns = {
                CallLog.Calls._ID,                //0
                CallLog.Calls.CACHED_NAME,                    //2
                CallLog.Calls.NUMBER,        //3
                CallLog.Calls.TYPE,            //4
                CallLog.Calls.DATE            //5
        };
        if (context.checkCallingOrSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return logs;
        }
        Cursor c = contentResolver.query(uri, columns, null, null, CallLog.Calls.DEFAULT_SORT_ORDER + " limit 90");

        if (c != null) {
            int idIndex = c.getColumnIndex(CallLog.Calls._ID);
            int nameIndex = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = c.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = c.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = c.getColumnIndex(CallLog.Calls.DATE);
            while (c.moveToNext()) {
                String number = c.getString(numberIndex);
                if (number == null || number.length() < 3 || number.equals("-1") || number.equals("-2")
                        || number.equals("") || number.startsWith("076")) {
                    continue;
                }
                Calllog log = new Calllog();
                log.setNumber(c.getString(numberIndex));
                if (logs.size() > 0) {
                    String prvNumber = logs.get(logs.size() - 1).getNumber();
                    if (!TextUtils.isEmpty(prvNumber) && prvNumber.equals(log.getNumber())) {
                        continue;
                    }
                }
                log.setId(c.getInt(idIndex));

                log.setName(c.getString(nameIndex));

                if (TextUtils.isEmpty(log.getName()) || log.getNumber().equals(log.getName())) {
                    log.setName(getName(log.getNumber()));
                }

                log.setType(c.getInt(typeIndex));
                log.setDate(c.getLong(dateIndex));

                logs.add(log);

            }
            c.close();
        }

        return logs;
    }


    /**
     * 查询所有的通话记录
     */
    public ArrayList<Calllog> getRecordscall(long date) {
        Log.i("getRecordscalldate", "date=" + date);
        ArrayList<Calllog> logs = new ArrayList<Calllog>();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] columns = {
                CallLog.Calls._ID,                //0
                CallLog.Calls.CACHED_NAME,                    //2
                CallLog.Calls.NUMBER,        //3
                CallLog.Calls.TYPE,            //4
                CallLog.Calls.DATE            //5
        };
        String where = CallLog.Calls.DATE + ">" + date;
        if (context.checkCallingOrSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return logs;
        }
        Cursor c = contentResolver.query(uri, columns, where, null, CallLog.Calls.DEFAULT_SORT_ORDER);

        if (c != null) {
            int idIndex = c.getColumnIndex(CallLog.Calls._ID);
            int nameIndex = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = c.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = c.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = c.getColumnIndex(CallLog.Calls.DATE);
            while (c.moveToNext()) {
                String number = c.getString(numberIndex);
                if (number == null || number.length() < 3 || number.equals("-1") || number.equals("-2")
                        || number.equals("") || number.startsWith("076")) {
                    continue;
                }
                Calllog log = new Calllog();
                log.setNumber(c.getString(numberIndex));
                if (logs.size() > 0) {
                    String prvNumber = logs.get(logs.size() - 1).getNumber();
                    if (!TextUtils.isEmpty(prvNumber) && prvNumber.equals(log.getNumber())) {
                        continue;
                    }
                }
                log.setId(c.getInt(idIndex));

                log.setName(c.getString(nameIndex));

                if (TextUtils.isEmpty(log.getName()) || log.getNumber().equals(log.getName())) {
                    log.setName(getName(log.getNumber()));
                }
                log.setType(c.getInt(typeIndex));
                log.setDate(c.getLong(dateIndex));
                logs.add(log);

            }
            c.close();
        }

        return logs;
    }

    /**
     * 删除通话记录
     *
     */
    public static void DeleteCall(Context context, List<Calllog> callogs) {
        String[] number = null;
        for (int i = 0; i < callogs.size(); i++) {
            number = new String[1];
            number[0] = callogs.get(i).getNumber();
            delete(context, number);
        }
    }

    public static void delete(Context context, String[] number) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.getContentResolver().delete(CallLog.Calls.CONTENT_URI,
                CallLog.Calls.NUMBER + "=?", number);
    }

    /**
     * 根据电话号码得到联系人信息
     *
     */
    public ContactInfo getContactByPhone(String phone) {
        ContactInfo contactInfo = null;
        ArrayList<String> rowIds = getRowContactID(
                CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                CommonDataKinds.Phone.NUMBER, phone);
        if (rowIds != null && rowIds.size() > 0) {
            contactInfo = new ContactInfo();
            contactInfo.rowId = rowIds.get(0);
            contactInfo.phone = phone;
            contactInfo.name = getContactName(rowIds.get(0));
        }

        return contactInfo;
    }

    /**
     * 插入联系人
     *
     */

    public long insertKuaiHuas() {
        if ("祥聊电话".equals(getName("076938940014"))) {
            return -1;
        }
        //首先插入空值，再得到rawContactsId ，用于下面插值
        ContentValues values = new ContentValues();
        //insert a null value
        Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        if (rawContactUri == null) {
            return -1;
        }
        long rawContactsId = ContentUris.parseId(rawContactUri);

        values.clear();
        values.put(CommonDataKinds.StructuredName.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.StructuredName.DISPLAY_NAME, "祥聊电话");
        contentResolver.insert(Data.CONTENT_URI, values);

        //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076938940014");
        contentResolver.insert(Data.CONTENT_URI, values);
        //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076938943889");
        contentResolver.insert(Data.CONTENT_URI, values);

        //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340312");
        contentResolver.insert(Data.CONTENT_URI, values);
        //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340261");
        contentResolver.insert(Data.CONTENT_URI, values); //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340280");
        contentResolver.insert(Data.CONTENT_URI, values); //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340316");
        contentResolver.insert(Data.CONTENT_URI, values); //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340276");
        contentResolver.insert(Data.CONTENT_URI, values); //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340319");
        contentResolver.insert(Data.CONTENT_URI, values);
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340251");
        contentResolver.insert(Data.CONTENT_URI, values);
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076087340265");
        contentResolver.insert(Data.CONTENT_URI, values);
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076938940017");
        contentResolver.insert(Data.CONTENT_URI, values);
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, "076938943890");
        contentResolver.insert(Data.CONTENT_URI, values);

        return rawContactsId;

    }

    /**
     * 插入联系人
     *
     */
    public long insertContact(long rawContactsId, String phone) {
        if ("祥聊电话".equals(getName(phone))) {
            return -1L;
        }
        Log.i("rowId", "rowId2=" + rawContactsId);
        //首先插入空值，再得到rawContactsId ，用于下面插值
        ContentValues values = new ContentValues();

        //插入电话
        values.clear();
        values.put(CommonDataKinds.Phone.RAW_CONTACT_ID, rawContactsId);
        values.put(Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(CommonDataKinds.Phone.NUMBER, phone);
        contentResolver.insert(Data.CONTENT_URI, values);

        return rawContactsId;
    }

    /**
     * 根据联系人电话号码得到姓名
     * @param phone
     * @return
     */
    public String getName(String phone) {
        String name = null;
        if (phone == null) {
            return "";
        }
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                CommonDataKinds.Phone.NUMBER};

        Cursor cursor = contentResolver.query(
                CommonDataKinds.Phone.CONTENT_URI,
                projection, // Which columns to return.
                CommonDataKinds.Phone.NUMBER
                        + " =?", // WHERE clause.
                new String[]{phone}, // WHERE clause value substitution
                null); // Sort order.

        if (cursor == null) {
            return phone;
        }
        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
        while (cursor.moveToNext()) {
            name = cursor.getString(nameFieldColumnIndex);
            if (name != null && !name.equals("")) {
                break;
            }
        }
        cursor.close();
        if (name == null || name.equals("")) {
            name = phone;
        }
        return name;

    }

    public void deleteKuaihuaCallog(Context context) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            contentResolver.delete(
                    CallLog.Calls.CONTENT_URI,
                    CallLog.Calls.CACHED_NAME + "=?",
                    new String[]{"祥聊电话"});
            return;
        }

    }

    private void checkSelfPermission() {
    }
}