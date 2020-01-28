package kr.co.core.tools.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.core.tools.R;

public class AddressSubAct extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactsAdapter adapter;
    Activity act;

    private List<PhoneBook> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_sub);
        act = this;

        recyclerView = (RecyclerView) findViewById(R.id.rcv_address);
        adapter = new ContactsAdapter(act, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        int type = getIntent().getIntExtra("test", 1);

        if (type == 1) {
            getContacts01(act);
        } else if (type == 2) {
            getContacts02();
        }
    }

    private void getContacts01(Context context) {
        try {
            JSONObject total = new JSONObject();
            JSONArray ja = new JSONArray();

            // 데이터베이스 혹은 content resolver 를 통해 가져온 데이터를 적재할 저장소를 먼저 정의
//        List<PhoneBook> list = new ArrayList<>();

            // 1. Resolver 가져오기(데이터베이스 열어주기)
            // 전화번호부에 이미 만들어져 있는 ContentProvider 를 통해 데이터를 가져올 수 있음
            // 다른 앱에 데이터를 제공할 수 있도록 하고 싶으면 ContentProvider 를 설정
            // 핸드폰 기본 앱 들 중 데이터가 존재하는 앱들은 Content Provider 를 갖는다
            // ContentResolver 는 ContentProvider 를 가져오는 통신 수단
            ContentResolver resolver = context.getContentResolver();

            // 2. 전화번호가 저장되어 있는 테이블 주소값(Uri)을 가져오기
            Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

            // 3. 테이블에 정의된 칼럼 가져오기
            // ContactsContract.CommonDataKinds.Phone 이 경로에 상수로 칼럼이 정의
            String[] projection = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID // 인덱스 값, 중복될 수 있음 -- 한 사람 번호가 여러개인 경우
                    , ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    , ContactsContract.CommonDataKinds.Phone.NUMBER};

            // 4. ContentResolver로 쿼리를 날림 -> resolver 가 provider 에게 쿼리하겠다고 요청
            Cursor cursor = resolver.query(phoneUri, projection, null, null, ContactsContract.Data.RAW_CONTACT_ID + " ASC");

            // 4. 커서로 리턴된다. 반복문을 돌면서 cursor 에 담긴 데이터를 하나씩 추출
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    JSONObject jo = new JSONObject();

                    // 4.1 이름으로 인덱스를 찾아준다
                    int idIndex = cursor.getColumnIndex(projection[0]); // 이름을 넣어주면 그 칼럼을 가져와준다.
                    int nameIndex = cursor.getColumnIndex(projection[1]);
                    int numberIndex = cursor.getColumnIndex(projection[2]);
                    // 4.2 해당 index 를 사용해서 실제 값을 가져온다.
                    String id = cursor.getString(idIndex);
                    String name = cursor.getString(nameIndex);
                    String number = cursor.getString(numberIndex);

                    Log.e("TEST_HOME", "id: " + id);
                    Log.e("TEST_HOME", "name: " + name);
                    Log.e("TEST_HOME", "number: " + number);
                    Log.e("TEST_HOME", "--------------------------------------------");

                    PhoneBook phoneBook = new PhoneBook();
                    phoneBook.setId(id);
                    phoneBook.setName(name);

                    number = number.replace(" ", "");
                    number = number.replace("-", "");
                    number = number.replace("//", "");
                    phoneBook.setTel(number);
                    jo.put("address", number);
                    ja.put(jo);

                    list.add(phoneBook);
                }
            }
            // 데이터 계열은 반드시 닫아줘야 한다.
            cursor.close();

            adapter.setItems(list);
            adapter.notifyDataSetChanged();

            total.put("addr_list", ja);

            Log.e("TEST_HOME", "total(JSONArray): " + total);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getContacts02() {

        Uri uri = ContactsContract.Data.CONTENT_URI;
        String selection = ContactsContract.Data.RAW_CONTACT_ID + "=" + ContactsContract.Data.RAW_CONTACT_ID;
        Cursor cursor = getContentResolver().query(uri, new String[]{ContactsContract.Data.RAW_CONTACT_ID, ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.DATA1, ContactsContract.Data.DATA2, ContactsContract.Data.DATA3,
                ContactsContract.Data.DATA4, ContactsContract.Data.DATA5, ContactsContract.Data.DATA6,
                ContactsContract.Data.DATA7, ContactsContract.Data.DATA8, ContactsContract.Data.DATA9,
                ContactsContract.Data.DATA10, ContactsContract.Data.DATA11, ContactsContract.Data.DATA12,
                ContactsContract.Data.DATA13, ContactsContract.Data.DATA14, ContactsContract.Data.DATA15
        }, selection, null, ContactsContract.Data.RAW_CONTACT_ID + " ASC");

        cursor.moveToFirst();

//        Log.i("TEST","count: "+cursor.getCount());
        ArrayList<HashMap> list = new ArrayList<>();
        HashMap<String, String> datas = new HashMap<>();

        if (cursor.getCount() > 0) {
            datas.put("raw contact id", cursor.getString(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID)));
            while (cursor.isAfterLast() == false) {
                String raw_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));

                String mime = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE));

                if (datas.get("raw contact id").equalsIgnoreCase(raw_id)) {
                    List<String> dataValueList = getColumnValueByMimetype(cursor, mime);
                    // 데이터 추가
                    for (String dataValue : dataValueList) {

                        String[] temp = dataValue.split(" : ");
                        if (temp.length > 1) {

                            if (!datas.containsKey(temp[0])) {
                                datas.put(temp[0], temp[1]);
                            } else {
                                datas.put("Next " + temp[0], temp[1]);
                            }
                        }
                    }

                    // 마지막 일때
                    if (cursor.isLast()) {
                        list.add(datas);
                    }
                } else {
                    // 리스트에 해시맵 추가
                    list.add(datas);
                    datas = new HashMap<>();
                    datas.put("raw contact id", raw_id);
                    List<String> dataValueList = getColumnValueByMimetype(cursor, mime);
                    // 데이터 추가
                    for (String dataValue : dataValueList) {

                        String[] temp = dataValue.split(" : ");
                        if (temp.length > 1) {
                            if (!datas.containsKey(temp[0])) {
                                datas.put(temp[0], temp[1]);
                            } else {
                                datas.put("Next " + temp[0], temp[1]);
                            }
                        }
                    }
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        cursor = null;

        if (list.size() > 0) {
            try {
                JSONObject total = new JSONObject();
                JSONArray ja = new JSONArray();

                for (HashMap item : list) {
                    if (item.containsKey("raw contact id")) {
                        if (item.containsKey("Phone Number")) {
                            JSONObject jo = new JSONObject();
                            jo.put("a_phone_idx", item.get("raw contact id"));
                            jo.put("a_phone_up_is", "Y");
                            jo.put("a_name", item.get("Display Name"));
                            jo.put("a_hp", item.get("Phone Number"));
                            jo.put("a_group", item.get("Group NAME"));
                            jo.put("a_email", item.get("Email Address"));
                            jo.put("a_tel", item.get("Home Number"));
                            jo.put("a_tel", item.get("Work Number"));
                            jo.put("a_tel", item.get("etc Number"));
                            jo.put("a_fax", item.get("Faxw Number"));
                            jo.put("a_fax", item.get("Faxh Number"));
                            jo.put("a_fax", item.get("Faxw Number"));

                            ja.put(jo);

                        } else if (item.containsKey("etc Number")) {
                            String number = item.get("etc Number").toString();

                            if (number.substring(0, 3).equals("010") || number.substring(0, 3).equals("011") || number.substring(0, 3).equals("016") ||
                                    number.substring(0, 3).equals("017") || number.substring(0, 3).equals("018") || number.substring(0, 3).equals("019") ||
                                    number.substring(0, 3).equals("+82")) {
                                JSONObject jo = new JSONObject();
                                jo.put("a_phone_idx", item.get("raw contact id"));
                                jo.put("a_phone_up_is", "Y");
                                jo.put("a_name", item.get("Display Name"));
                                jo.put("a_hp", item.get("etc Number"));
                                jo.put("a_group", item.get("Group NAME"));
                                jo.put("a_email", item.get("Email Address"));
                                jo.put("a_tel", item.get("Home Number"));
                                jo.put("a_tel", item.get("Work Number"));
                                jo.put("a_fax", item.get("Faxw Number"));
                                jo.put("a_fax", item.get("Faxh Number"));
                                jo.put("a_fax", item.get("Faxw Number"));

                                ja.put(jo);
                            }
                        }
                    }
                }

                total.put("LIST", ja);

//                regAddressBook(total.toString());
//                Log.i("TEST", "jsonArr: " + total.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> getColumnValueByMimetype(Cursor cursor, String mimeType) {
        List<String> ret = new ArrayList<String>();

        switch (mimeType) {
            // Get email data.
            case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                // Email.ADDRESS == data1
                String emailAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                // Email.TYPE == data2
                int emailType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String emailTypeStr = getEmailTypeString(emailType);

                ret.add("Email Address : " + emailAddress);
                ret.add("Email Int Type : " + emailType);
                ret.add("Email String Type : " + emailTypeStr);
                break;

            // Get im data.
            case ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE:
                // Im.PROTOCOL == data5
                String imProtocol = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                // Im.DATA == data1
                String imId = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                ret.add("IM Protocol : " + imProtocol);
                ret.add("IM ID : " + imId);
                break;

            // Get nickname
            case ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE:
                // Nickname.NAME == data1
                String nickName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                ret.add("Nick name : " + nickName);
                break;

            // Get phone number.
            case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                // Phone.NUMBER == data1
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // Phone.TYPE == data2
                int phoneTypeInt = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneTypeStr = getPhoneTypeString(phoneTypeInt);

//                ret.add("Phone Number : " + phoneNumber);
//                ret.add("Phone Type Integer : " + phoneTypeInt);
//                ret.add("Phone Type String : " + phoneTypeStr);
                if (phoneTypeStr.equals("Mobile")) {
                    ret.add("Phone Number : " + phoneNumber);
                    ret.add("Phone Type Integer : " + phoneTypeInt);
                    ret.add("Phone Type String : " + phoneTypeStr);
                } else if (phoneTypeStr.equals("Home")) {
                    ret.add("Home Number : " + phoneNumber);
                    ret.add("Home Type Integer : " + phoneTypeInt);
                    ret.add("Home Type String : " + phoneTypeStr);
                } else if (phoneTypeStr.equals("Work")) {
                    ret.add("Work Number : " + phoneNumber);
                    ret.add("Work Type Integer : " + phoneTypeInt);
                    ret.add("Work Type String : " + phoneTypeStr);
                } else if (phoneTypeStr.equals("FaxW")) {
                    ret.add("Faxw Number : " + phoneNumber);
                    ret.add("Faxw Type Integer : " + phoneTypeInt);
                    ret.add("Faxw Type String : " + phoneTypeStr);
                } else if (phoneTypeStr.equals("FaxH")) {
                    ret.add("Faxh Number : " + phoneNumber);
                    ret.add("Faxh Type Integer : " + phoneTypeInt);
                    ret.add("Faxh Type String : " + phoneTypeStr);
                } else {
                    ret.add("etc Number : " + phoneNumber);
                    ret.add("etc Type Integer : " + phoneTypeInt);
                    ret.add("etc Type String : " + phoneTypeStr);
                }
                break;

            // Get display name.
            case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                // StructuredName.DISPLAY_NAME == data1
                String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                // StructuredName.GIVEN_NAME == data2
                String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                // StructuredName.FAMILY_NAME == data3
                String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));

                ret.add("Display Name : " + displayName);
                ret.add("Given Name : " + givenName);
                ret.add("Family Name : " + familyName);
                break;

            // Get identity.
            case ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE:
                // Identity.IDENTITY == data1
                String identity = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.IDENTITY));
                // Identity.NAMESPACE == data2
                String namespace = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.NAMESPACE));

                ret.add("Identity : " + identity);
                ret.add("Identity Namespace : " + namespace);
                break;

            // Get group membership.
            case ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE:
                // GroupMembership.GROUP_ROW_ID == data1
                int groupId = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID));
//                String groupName = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
                String groupName = getGroupName(groupId);

                ret.add("Group ID : " + groupId);

                if (groupName.equalsIgnoreCase("Coworkers")) {
                    ret.add("Group NAME : 동료");
                } else if (groupName.equalsIgnoreCase("Friends")) {
                    ret.add("Group NAME : 친구");
                } else if (groupName.equalsIgnoreCase("Family")) {
                    ret.add("Group NAME : 가족");
                } else if (groupName.equalsIgnoreCase("ICE")) {
                    ret.add("Group NAME : 긴급연락처");
                } else if (groupName.equalsIgnoreCase("My Contacts")) {
                    ret.add("Group NAME : 내 연락처");
                } else {
                    ret.add("Group NAME : " + groupName);
                }

                break;

            // Get note.
            case ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE:
                // Note.NOTE == data1
                String note = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                ret.add("Note : " + note);
                break;

        }

        return ret;
    }

    /*
     *  Get email type related string format value.
     * */
    private String getEmailTypeString(int dataType) {
        String ret = "";

        if (ContactsContract.CommonDataKinds.Email.TYPE_HOME == dataType) {
            ret = "Home";
        } else if (ContactsContract.CommonDataKinds.Email.TYPE_WORK == dataType) {
            ret = "Work";
        }
        return ret;
    }


    /*
     *  Get phone type related string format value.
     * */
    private String getPhoneTypeString(int dataType) {
        String ret = "";

        if (ContactsContract.CommonDataKinds.Phone.TYPE_HOME == dataType) {
            ret = "Home";
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_WORK == dataType) {
            ret = "Work";
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE == dataType) {
            ret = "Mobile";
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK == dataType) {
            ret = "FaxW";
        } else if (ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME == dataType) {
            ret = "FaxH";
        }
        return ret;
    }


    private String getGroupName(long groupId) {
        Cursor cursor = getGroups(ContactsContract.Groups._ID + " = " + groupId);

        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }

        cursor.moveToPosition(0);
        String groupName = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE));
        cursor.close();

        return groupName;
    }

    private Cursor getGroups(String selectArg) {
        String[] columns = new String[]{
                ContactsContract.Groups.ACCOUNT_NAME,
                ContactsContract.Groups.ACCOUNT_TYPE,
                ContactsContract.Groups._ID,
                ContactsContract.Groups.TITLE
        };

        if (selectArg != null) {
            selectArg = "AND " + selectArg;
        } else {
            selectArg = "";
        }

        return getContentResolver().query(ContactsContract.Groups.CONTENT_URI, columns,
                ContactsContract.Groups.ACCOUNT_TYPE + " NOT NULL AND " +
                        ContactsContract.Groups.ACCOUNT_NAME + " NOT NULL " + selectArg, null, null);
    }
}
