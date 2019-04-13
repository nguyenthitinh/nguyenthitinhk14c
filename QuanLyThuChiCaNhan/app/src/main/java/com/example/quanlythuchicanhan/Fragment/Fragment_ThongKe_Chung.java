package com.example.quanlythuchicanhan.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Fragment_ThongKe_Chung extends Fragment {
    TextView thangnay, tongthu, tongchi, sodu;
    TextView thangnay_1, tongthu_1, tongchi_1, sodu_1;
    TextView thangnay_2, tongthu_2, tongchi_2, sodu_2;
    public  static  Integer dathu, dachi, conlai;
    public  static  Integer dathu1, dachi1, conlai1;
    public  static  Integer dathu2, dachi2, conlai2;
    String username;
    String urlthu = "http://192.168.43.14:8888/android/thongkethang.php";
    String urlchi = "http://192.168.43.14:8888/android/thongkethangchi.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongkechung, container, false);

        thangnay = view.findViewById(R.id.thangnay);
        thangnay_1 = view.findViewById(R.id.thang_1);
        thangnay_2 = view.findViewById(R.id.thang_2);
        tongthu = view.findViewById(R.id.tongthu);
        tongthu_1 = view.findViewById(R.id.tongthu_1);
        tongthu_2 = view.findViewById(R.id.tongthu_2);
        tongchi = view.findViewById(R.id.tongchi);
        tongchi_1 = view.findViewById(R.id.tongchi_1);
        tongchi_2 = view.findViewById(R.id.tongchi_2);
        sodu = view.findViewById(R.id.sodu);
        sodu_1 = view.findViewById(R.id.sodu_1);
        sodu_2 = view.findViewById(R.id.sodu_2);

        Bundle b = getArguments();
        if (b != null){
            username = b.getString("Accname");
        }


        laythangThu(urlthu);
        laythangThu1(urlthu);
        laythangThu2(urlthu);


        return view;
    }

    public void laythangThu(String urlthu){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlthu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
//                    for (int i = 0; i< array.length(); i++){
//                        JSONObject object = array.getJSONObject(i);
//                        sotienthu = Integer.parseInt(object.getString("sotien"));
//                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }
                    tongthu.setText("+ "+((Integer) total).toString() + " VNĐ");
                    dathu = (Integer) total;
                    laythangChi(urlchi);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void laythangChi(String urlchi){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Integer sotienchi;

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        sotienchi = Integer.valueOf(object.getString("sotien"));
                    }
                    int totalchi = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        totalchi = totalchi + Integer.parseInt(object.getString("sotien"));
                    }

                    dachi = ((Integer) totalchi);

                    tongchi.setText("- "+((Integer) totalchi).toString() +" VNĐ");
                    conlai = dathu - dachi;
                    if (conlai < 0){
                        sodu.setText(conlai.toString() + " VNĐ");
                    }
                    else {
                        sodu.setText("+ "+conlai.toString() + " VNĐ");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError{
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();



                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void laythangThu1(String urlthu){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlthu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
//                    for (int i = 0; i< array.length(); i++){
//                        JSONObject object = array.getJSONObject(i);
//                        sotienthu = Integer.parseInt(object.getString("sotien"));
//                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }
                    tongthu_1.setText("+ "+((Integer) total).toString() + " VNĐ");
                    String thangnam, thang, nam;
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat sdf =null;
                    String strDateFormat = "yyyy-MM";
                    sdf = new SimpleDateFormat(strDateFormat);
                    thang = String.valueOf((now.get(Calendar.MONTH)));
                    nam = String.valueOf(now.get(Calendar.YEAR));
                    thangnam =nam+"-"+thang;
                    Date namthang = null;

                    try {
                        namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date hientai= namthang;
                    String layra =sdf.format(hientai.getTime()).toString().trim();
                    thangnay_1.setText(layra);
                    dathu1 = (Integer) total;
                    laythangChi1(urlchi);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                String thangnam, thang, nam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thang = String.valueOf((now.get(Calendar.MONTH)));
                nam = String.valueOf(now.get(Calendar.YEAR));
                thangnam =nam+"-"+thang;
                Date namthang = null;

                try {
                    namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date hientai= namthang;
                String layra =sdf.format(hientai.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", layra);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void laythangChi1(String urlchi){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Integer sotienchi;

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        sotienchi = Integer.valueOf(object.getString("sotien"));
                    }
                    int totalchi = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        totalchi = totalchi + Integer.parseInt(object.getString("sotien"));
                    }

                    dachi1 = ((Integer) totalchi);

                    tongchi_1.setText("- "+((Integer) totalchi).toString() +" VNĐ");
                    conlai1 = dathu1 - dachi1;
                    if (conlai1 < 0){
                        sodu_1.setText(conlai1.toString() + " VNĐ");
                    }
                    else {
                        sodu_1.setText("+ "+conlai1.toString() + " VNĐ");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError{
                String thangnam, thang, nam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thang = String.valueOf((now.get(Calendar.MONTH)));
                nam = String.valueOf(now.get(Calendar.YEAR));
                thangnam =nam+"-"+thang;
                Date namthang = null;

                try {
                    namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date hientai= namthang;
                String layra =sdf.format(hientai.getTime()).toString().trim();
                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", layra);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void laythangThu2(String urlthu){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlthu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
//                    for (int i = 0; i< array.length(); i++){
//                        JSONObject object = array.getJSONObject(i);
//                        sotienthu = Integer.parseInt(object.getString("sotien"));
//                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }
                    tongthu_2.setText("+ "+((Integer) total).toString() + " VNĐ");
                    String thangnam, thang, nam;
                    Calendar now = Calendar.getInstance();
                    SimpleDateFormat sdf =null;
                    String strDateFormat = "yyyy-MM";
                    sdf = new SimpleDateFormat(strDateFormat);
                    thang = String.valueOf((now.get(Calendar.MONTH)-1));
                    nam = String.valueOf(now.get(Calendar.YEAR));
                    thangnam =nam+"-"+thang;
                    Date namthang = null;

                    try {
                        namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Date hientai= namthang;
                    String layra =sdf.format(hientai.getTime()).toString().trim();
                    thangnay_2.setText(layra);
                    dathu2 = (Integer) total;
                    laythangChi2(urlchi);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                String thangnam, thang, nam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thang = String.valueOf((now.get(Calendar.MONTH)-1));
                nam = String.valueOf(now.get(Calendar.YEAR));
                thangnam =nam+"-"+thang;
                Date namthang = null;

                try {
                    namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date hientai= namthang;
                String layra =sdf.format(hientai.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", layra);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void laythangChi2(String urlchi){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Integer sotienchi;

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        sotienchi = Integer.valueOf(object.getString("sotien"));
                    }
                    int totalchi = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        totalchi = totalchi + Integer.parseInt(object.getString("sotien"));
                    }

                    dachi2 = ((Integer) totalchi);

                    tongchi_2.setText("- "+((Integer) totalchi).toString() +" VNĐ");
                    conlai2 = dathu2 - dachi2;
                    if (conlai2 < 0){
                        sodu_2.setText(conlai2.toString() + " VNĐ");
                    }
                    else {
                        sodu_2.setText("+ "+conlai2.toString() + " VNĐ");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError{
                String thangnam, thang, nam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thang = String.valueOf((now.get(Calendar.MONTH)-1));
                nam = String.valueOf(now.get(Calendar.YEAR));
                thangnam =nam+"-"+thang;
                Date namthang = null;

                try {
                    namthang= new SimpleDateFormat("yyyy-MM").parse(thangnam);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date hientai= namthang;
                String layra =sdf.format(hientai.getTime()).toString().trim();
                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", layra);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
