package com.example.quanlythuchicanhan.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    TextView username, updateAcc, changePass, main;
    FrameLayout fr;
    public static String accname;
    String urlcheck = "http://192.168.43.14:8888/android/laymatkhaucu.php";
    String urlchange = "http://192.168.43.14:8888/android/doimatkhau.php";
    String urlthongtin ="http://192.168.43.14:8888/android/laythongtin.php";
    String urlupdate ="http://192.168.43.14:8888/android/update_account.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        main = findViewById(R.id.main);
        updateAcc = findViewById(R.id.updateAcc);
        changePass = findViewById(R.id.changePass);
        username = findViewById(R.id.username);
        fr = findViewById(R.id.frUser);

        Intent name = getIntent();
        accname = name.getStringExtra("Accname");
        username.setText(accname);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(UserActivity.this, MainActivity.class);
                main.putExtra("Accname", accname);
                startActivity(main);
            }
        });

        updateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserActivity.this);
                dialog.setTitle("Cập nhật thông tin");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.fragment_updateaccount);
                final EditText edsdt = dialog.findViewById(R.id.sdt);
                final EditText edemail = dialog.findViewById(R.id.email);
                final EditText eddiachi = dialog.findViewById(R.id.diachi);
                Button btnupdate = dialog.findViewById(R.id.update);
                Button huy = dialog.findViewById(R.id.huy);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                //laythongtin(urlthongtin);
                final RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlthongtin, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String sdt = "";
                        String email = "";
                        String diachi = "";

                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i< array.length(); i++){
                                JSONObject object = array.getJSONObject(i);
                                sdt = object.getString("sodienthoai");
                                email = object.getString("email");
                                diachi= object.getString("diachi");

                                edsdt.setText(sdt);
                                edemail.setText(email);
                                eddiachi.setText(diachi);
                            }


                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserActivity.this, "Loi " +error, Toast.LENGTH_SHORT).show();
                    }
                })

                {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data= new HashMap<>();
                        data.put("username", accname.trim());

                        return data;
                    }
                };
                requestQueue.add(stringRequest);

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //capnhat(urlupdate);
                        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupdate, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.trim().equals("thanhcong")){
                                    Toast.makeText(UserActivity.this, "Cập nhật tài khoản thành công !", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }
                                else {
                                    Toast.makeText(UserActivity.this, "Lỗi  "+ response.toString(), Toast.LENGTH_SHORT) .show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UserActivity.this, "Kiểm tra kết nối của bạn !", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", accname);
                                params.put("email", edemail.getText().toString().trim());
                                params.put("sodienthoai", edsdt.getText().toString().trim());
                                params.put("diachi", eddiachi.getText().toString().trim());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                dialog.show();

            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(UserActivity.this);
                dialog.setTitle("Đổi mật khẩu");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.fragment_doimatkhau);
                final EditText edmkcu = dialog.findViewById(R.id.mkcu);
                final EditText edmkmoi = dialog.findViewById(R.id.mkmoi);
                final EditText edcfmkmoi = dialog.findViewById(R.id.cfmkmoi);
                Button btnchange = dialog.findViewById(R.id.change);
                Button btncancle = dialog.findViewById(R.id.btcancle);
                btncancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                btnchange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mkcu = edmkcu.getText().toString().trim();
                        String mkmoi = edmkmoi.getText().toString().trim();
                        String cfmkmoi= edcfmkmoi.getText().toString().trim();

                        if (mkcu.isEmpty() || mkmoi.isEmpty() || cfmkmoi.isEmpty()){
                            Toast.makeText(UserActivity.this, "Vui lòng nhâp đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (mkmoi.length() <6){
                                Toast.makeText(UserActivity.this, "Nhập mật khẩu mới tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if (mkmoi.equals(cfmkmoi) == false){
                                    Toast.makeText(UserActivity.this, "Mật khẩu mới chưa trùng khớp", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    kiemtramkcu(urlcheck, mkcu, mkmoi);
                                }
                            }
                        }
                    }
                });
                dialog.show();
            }
        });

    }
    public void kiemtramkcu(String urlcheck, final String mkcu, final String mkmoi){
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcheck, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("dung")){
                    doimk(urlchange, mkmoi);
                    //Toast.makeText(UserActivity.this, "Mật khẩu cũ đúng !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UserActivity.this, "Mật khẩu cũ chưa đúng ! ", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserActivity.this, "Kiểm tra kết nối ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("Username", accname);
                data.put("Password", mkcu);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void doimk(String urlchange, final String mkmoi){
        RequestQueue requestQueue = Volley.newRequestQueue(UserActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchange, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(UserActivity.this, "Đổi mật khẩu thành công ! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UserActivity.this, "Đổi không thành công " +response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserActivity.this, "Kiểm tra kết nối ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("username", accname);
                data.put("mkmoi", mkmoi);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
