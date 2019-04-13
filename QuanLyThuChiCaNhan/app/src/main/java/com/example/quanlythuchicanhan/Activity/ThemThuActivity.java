package com.example.quanlythuchicanhan.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThemThuActivity extends AppCompatActivity {

 //   private static final String TAG = "MainActivity";
    Button btnThemThu, btnBack;
    EditText edSoTien;
    RadioButton rdBoMeGui, rdHocTap, rdCongViec, rdKhac;
    EditText edNgayThu, edCuThe;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    String url ="http://192.168.43.14:8888/android/themthu.php";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thu);

        edSoTien = findViewById(R.id.edSoTien);
        edNgayThu = findViewById(R.id.edNgayThu);
        edCuThe = findViewById(R.id.edCuThe);
        rdBoMeGui = findViewById(R.id.rdBoMeGui);
        rdCongViec = findViewById(R.id.rdCongViec);
        rdHocTap = findViewById(R.id.rdHocTap);
        rdKhac = findViewById(R.id.rdKhac);
        btnThemThu = findViewById(R.id.btnThemThu);
        btnBack = findViewById(R.id.btnBack);

        edNgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ThemThuActivity.this,
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
                edNgayThu.setText("" + year + "-" + month + "-" + day);
            }
        };

        btnThemThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FragmentThu.this, "ok", Toast.LENGTH_SHORT).show();
                String sotien = edSoTien.getText().toString().trim();
                String cuthe = edCuThe.getText().toString().trim();
                String ngaythu = edNgayThu.getText().toString().trim();
                String loaithu = "";
                if (rdBoMeGui.isChecked()) {
                    loaithu = rdBoMeGui.getText().toString().trim();
                }
                if (rdCongViec.isChecked()) {
                    loaithu = rdCongViec.getText().toString().trim();
                }
                if (rdHocTap.isChecked()) {
                    loaithu = rdHocTap.getText().toString().trim();
                }
                if (rdKhac.isChecked()) {
                    loaithu = rdKhac.getText().toString().trim();
                }

                if (sotien.isEmpty() || loaithu.isEmpty() || ngaythu.isEmpty()) {
                    Toast.makeText(ThemThuActivity.this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                } else {
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

                    String ngaytk = edNgayThu.getText().toString().trim();
                    try {
                        ngay= new SimpleDateFormat("yyyy-MM-dd").parse(edNgayThu.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (ngay.after(thangnam)){
                        Toast.makeText(ThemThuActivity.this, "Không thể thêm trước ngày hôm nay. Nhập lại ngày!", Toast.LENGTH_SHORT).show();
                    }
                    else {
//                  Toast.makeText(FragmentThu.this,sotien + ngaythu + loaithu, Toast.LENGTH_SHORT).show();
                        themThu(url);
                    }
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent ii = getIntent();
//                String username = ii.getStringExtra("Accname");
//                Intent i = new Intent(ThemThuActivity.this, MainActivity.class);
//                i.putExtra("Accname",username );
//                startActivity(i);
                onBackPressed();
            }
        });
    }

    public void themThu(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("ok")){
                    Toast.makeText(ThemThuActivity.this, "Thêm thành công khoản thu ! ", Toast.LENGTH_SHORT).show();
                    edSoTien.setText("");
                    rdBoMeGui.setChecked(true);
                    edNgayThu.setText("");
                    edCuThe.setText("");
                    //onBackPressed();
                }
                else {
                    // Log.d("axxx", "thong tin sai");
                    Toast.makeText(ThemThuActivity.this, "Các thông tin chưa hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("axxx", "khong them duoc");
                Toast.makeText(ThemThuActivity.this, "Thêm không thành công ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // throws AuthFailureError
                String loaithu = "";
                if(rdBoMeGui.isChecked()){
                    loaithu = rdBoMeGui.getText().toString().trim();
                }
                if(rdCongViec.isChecked()){
                    loaithu = rdCongViec.getText().toString().trim();
                }
                if(rdHocTap.isChecked()){
                    loaithu = rdHocTap.getText().toString().trim();
                }
                if(rdKhac.isChecked()){
                    loaithu = rdKhac.getText().toString().trim();
                }

                Intent i = getIntent();
                String username = i.getStringExtra("Accname");
//                Toast.makeText(FragmentThu.this, loaithu,Toast.LENGTH_SHORT).show();
                Map<String, String> data = new HashMap<>();

                data.put("username", username.trim());
                data.put("sotien", edSoTien.getText().toString().trim());
                data.put("loaithu", loaithu.trim());
                data.put("ngaythu", edNgayThu.getText().toString().trim());
                data.put("cuthe", edCuThe.getText().toString().trim());
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
