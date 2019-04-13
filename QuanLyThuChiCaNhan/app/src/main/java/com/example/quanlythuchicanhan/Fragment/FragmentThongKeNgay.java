package com.example.quanlythuchicanhan.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentThongKeNgay extends Fragment {

    ListView rvThu;
    TextView tvThu;
    EditText edngaytk;
    ImageView btnTk;
    String url = "http://192.168.43.14:8888/android/thongkethang.php";
    ArrayList<KhoanThu> arrayKhoanThu;
    ThongKeActivity tk;
    KhoanThuAdapter adapter;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke_ngay, container, false);

        rvThu = view.findViewById(R.id.rvThu);
        tvThu = view.findViewById(R.id.tvThu);
        edngaytk = view.findViewById(R.id.edngaytk);
        btnTk = view.findViewById(R.id.btnTk);

        edngaytk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        mDateSetListener,
                        year,
                        month,
                        day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //  Log.d(TAG, "onDateSet: date" + year + "-" +month +"-"+day);
                month = month + 1;
                edngaytk.setText("" + year + "-" + month + "-" + day);
            }
        };

        btnTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date thangnam = null;
                Date ngay = null;

                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM-dd";
                sdf = new SimpleDateFormat(strDateFormat);

                try {
                    thangnam = new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(now.getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String ngaytk = edngaytk.getText().toString().trim();
                try {
                    ngay= new SimpleDateFormat("yyyy-MM-dd").parse(edngaytk.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (ngaytk.isEmpty()){
                    Toast.makeText(getActivity(), "Nhập ngày-tháng-năm cần thống kê !", Toast.LENGTH_SHORT).show();
                } else{
                    if (ngay.after(thangnam)){
                        Toast.makeText(getActivity(), "Không thể thống kê trước ngày hôm nay. Nhập lại ngày!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // ngaytk = edngaytk.getText().toString().trim();
                        arrayKhoanThu = new ArrayList<>();
                        adapter = new KhoanThuAdapter(getActivity(), R.layout.khoanthu_item, arrayKhoanThu);
                        rvThu.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        layKhoanThu(url);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Date day = null;
                try {
                    day = new SimpleDateFormat("yyyy-MM-dd").parse(edngaytk.getText().toString().trim());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String thangnam;
                Date now = day;//Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM-dd";
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