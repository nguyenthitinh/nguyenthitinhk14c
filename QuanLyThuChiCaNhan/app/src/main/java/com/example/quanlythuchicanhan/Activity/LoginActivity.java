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

public class LoginActivity extends AppCompatActivity {
    TextView btnResigter;
    EditText edName, edPass;
    Button btnLogin;
    String url ="http://192.168.43.14:8888/android/login.php";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edName = findViewById(R.id.edName);
        edPass = findViewById(R.id.edPass);
        btnResigter = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        requestQueue = Volley.newRequestQueue(this);
        init();

        btnResigter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, " okkk", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }


        public void init(){
            btnLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    String username = edName.getText().toString();
                    String password = edPass.getText().toString();
                    if(username.isEmpty() || password.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        dangNhap(url);
                    }
                }
            });
        }

    public void dangNhap(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (response.trim().equals("ok")){
                    //Toast.makeText(LoginActivity.this, "Dang Nhap Thanh Cong !", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("Accname", edName.getText().toString().trim());
                    startActivity(i);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu chưa đúng ! ", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Đăng Nhập không thành công ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
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
