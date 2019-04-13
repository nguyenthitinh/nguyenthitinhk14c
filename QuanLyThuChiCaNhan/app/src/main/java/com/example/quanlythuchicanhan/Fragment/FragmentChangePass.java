package com.example.quanlythuchicanhan.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.Activity.UserActivity;
import com.example.quanlythuchicanhan.R;

import java.util.HashMap;
import java.util.Map;

public class FragmentChangePass extends Fragment {

    EditText edmkcu, edmkmoi, edcfmkmoi;
    UserActivity user;
    Button btnchange;
    String urlcheck = "http://192.168.43.14:8888/android/laymatkhaucu.php";
    String urlchange = "http://192.168.43.14:8888/android/doimatkhau.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_doimatkhau, container, false);

        edmkcu = view.findViewById(R.id.mkcu);
        edmkmoi = view.findViewById(R.id.mkmoi);
        edcfmkmoi = view.findViewById(R.id.cfmkmoi);
        btnchange = view.findViewById(R.id.change);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkcu = edmkcu.getText().toString().trim();
                String mkmoi = edmkmoi.getText().toString().trim();
                String cfmkmoi= edcfmkmoi.getText().toString().trim();

                if (mkcu.isEmpty() || mkmoi.isEmpty() || cfmkmoi.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhâp đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (mkmoi.length() <6){
                        Toast.makeText(getActivity(), "Nhập mật khẩu tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (mkmoi.equals(cfmkmoi) == false){
                            Toast.makeText(getActivity(), "Mật khẩu mới chưa trùng khớp", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            kiemtramkcu(urlcheck);
                        }
                    }
                }
            }
        });

        return view;
    }

    public void kiemtramkcu(String urlcheck){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcheck, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("dung")){
                    doimk(urlchange);
                    //Toast.makeText(getActivity(), "Mật khẩu cũ đúng !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Mật khẩu cũ chưa đúng ! ", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kiểm tra kết nối ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("Username", user.accname);
                data.put("Password", edmkcu.getText().toString().trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void doimk(String urlchange){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchange, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công ! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Đổi không thành công " +response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kiểm tra kết nối ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("username", user.accname);
                data.put("mkmoi", edmkmoi.getText().toString().trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}