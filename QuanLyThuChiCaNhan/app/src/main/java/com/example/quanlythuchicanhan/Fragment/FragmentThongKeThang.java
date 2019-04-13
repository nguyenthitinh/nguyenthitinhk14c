package com.example.quanlythuchicanhan.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.Activity.ThongKeActivity;
import com.example.quanlythuchicanhan.Adapter.KhoanThuAdapter;
import com.example.quanlythuchicanhan.Model.KhoanThu;
import com.example.quanlythuchicanhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentThongKeThang  extends Fragment {

    ListView rvThu;
    TextView tvThu;
    String url = "http://192.168.43.14:8888/android/thongkethang.php";
    ArrayList<KhoanThu> arrayKhoanThu;
    ThongKeActivity tk;
    KhoanThuAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke_thang, container, false);

//        Calendar now = Calendar.getInstance();
//        SimpleDateFormat sdf =null;
//        String strDateFormat = "yyyy-MM-dd";
//        sdf = new SimpleDateFormat(strDateFormat);
//        tvday.setText("Bây giờ là : "+ username +" hj "+sdf.format(now.getTime())+"Ngày" + now.get(Calendar.DATE) +"Tháng "+(now.get(Calendar.MONTH) + 1 ) +"Năm "+now.get(Calendar.YEAR));

        rvThu = view.findViewById(R.id.rvThu);
        tvThu = view.findViewById(R.id.tvThu);
        arrayKhoanThu = new ArrayList<>();

        adapter = new KhoanThuAdapter(getActivity(), R.layout.khoanthu_item, arrayKhoanThu);
        rvThu.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        layKhoanThu(url);
        adapter.notifyDataSetChanged();

        return view;

    }

    private void layKhoanThu(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Integer idthu;
                int sotienthu;
                String loaithu = "";
                String ngaythu = "";
                String cuthethu = "";

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        idthu = Integer.valueOf(object.getString("idthu"));
                        sotienthu = Integer.parseInt(object.getString("sotien"));
                        loaithu = object.getString("loaithu");
                        ngaythu = object.getString("ngaythu");
                        cuthethu = object.getString("cuthe");

                        arrayKhoanThu.add(new KhoanThu(idthu, sotienthu, loaithu, ngaythu, cuthethu));
                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }

                    tvThu.setText("+ "+((Integer) total).toString());
                    adapter.notifyDataSetChanged();

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
                //   tk = new ThongKeActivity();

                Log.d("htht", "getParams: "+tk.username);
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", tk.username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
