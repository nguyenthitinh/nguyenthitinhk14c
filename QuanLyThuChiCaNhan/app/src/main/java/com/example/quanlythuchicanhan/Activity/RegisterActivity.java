package com.example.quanlythuchicanhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edName, edPass, edCnfPass;
    TextView btnLogin;
    Button btnResigter;
    String url ="http://192.168.43.14:8888/android/register.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edName = findViewById(R.id.edName);
        edPass = findViewById(R.id.edPass);
        edCnfPass = findViewById(R.id.edCnfPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnResigter = findViewById(R.id.btnRegister);
        requestQueue = Volley.newRequestQueue(this);
        click();

    }

    public void click(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
        btnResigter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String username = edName.getText().toString().trim();
                String password = edPass.getText().toString().trim();
                String cnfpass = edCnfPass.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty() || cnfpass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.length()<6 || password.length()>12){
                        Toast.makeText(RegisterActivity.this,"Mật khẩu từ 6 đến 12 ký tự !",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (password.equals(cnfpass)==false){
                            Toast.makeText(RegisterActivity.this,"Mật khẩu chưa trùng khớp !",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            dangKy(url);
                        }
                    }
                }
            }
        });
    }


    public void dangKy(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("ok")){
                    Toast.makeText(RegisterActivity.this, "Bạn đã đăng ký thành công ! ",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Tài khoản đã tồn tại !", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Đăng ký không thành công ! " +error.toString()
                        , Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();

                data.put("Username", edName.getText().toString().trim());
                data.put("Password", edPass.getText().toString().trim());
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
