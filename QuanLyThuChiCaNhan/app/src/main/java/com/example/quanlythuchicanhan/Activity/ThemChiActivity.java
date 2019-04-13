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

public class ThemChiActivity extends AppCompatActivity {
    Button btnThemChi, btnBackChi;
    EditText edSoTien;
    RadioButton rdAnUong,rdMuaSam, rdGiaiTri, rdHocTap, rdCongViec, rdKhac;
    EditText edNgayChi, edCuThe;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    String url ="http://192.168.43.14:8888/android/themchi.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chi);

        edSoTien = findViewById(R.id.edSoTien);
        edNgayChi = findViewById(R.id.edNgayChi);
        edCuThe = findViewById(R.id.edCuThe);
        rdMuaSam = findViewById(R.id.rdMuaSam);
        rdGiaiTri = findViewById(R.id.rdGiaiTri);
        rdAnUong = findViewById(R.id.rdAnUong);
        rdCongViec = findViewById(R.id.rdCongViec);
        rdHocTap = findViewById(R.id.rdHocTap);
        rdKhac = findViewById(R.id.rdKhac);
        btnThemChi = findViewById(R.id.btnThemChi);
        btnBackChi = findViewById(R.id.btnBackChi);

        edNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ThemChiActivity.this,
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
                edNgayChi.setText("" + year + "-" + month + "-" + day);
            }
        };

        btnThemChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sotien = edSoTien.getText().toString().trim();
                String cuthe = edCuThe.getText().toString().trim();
                String ngaychi = edNgayChi.getText().toString().trim();
                String loaichi = "";
                if (rdAnUong.isChecked()) {
                    loaichi = rdAnUong.getText().toString().trim();
                }
                if (rdGiaiTri.isChecked()) {
                    loaichi = rdGiaiTri.getText().toString().trim();
                }
                if (rdHocTap.isChecked()) {
                    loaichi = rdHocTap.getText().toString().trim();
                }
                if (rdCongViec.isChecked()) {
                    loaichi = rdCongViec.getText().toString().trim();
                }
                if (rdMuaSam.isChecked()) {
                    loaichi = rdMuaSam.getText().toString().trim();
                }
                if (rdKhac.isChecked()) {
                    loaichi = rdKhac.getText().toString().trim();
                }
                // Log.d("axxx", "onClick: "+sotien+" "+loaithu+" "+ngaythu+" "+cuthe);
                if (sotien.isEmpty() || loaichi.isEmpty() || ngaychi.isEmpty()) {
                    Toast.makeText(ThemChiActivity.this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }
                else {
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

                    String ngaytk = edNgayChi.getText().toString().trim();
                    try {
                        ngay= new SimpleDateFormat("yyyy-MM-dd").parse(edNgayChi.getText().toString().trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (ngay.after(thangnam)){
                        Toast.makeText(ThemChiActivity.this, "Không thể thêm trước ngày hôm nay. Nhập lại ngày!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        themChi(url);
                    }
                }
            }
        });

        btnBackChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void themChi(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("themchiok")){
                    Toast.makeText(ThemChiActivity.this, "Thêm thành công khoản chi ! ", Toast.LENGTH_SHORT).show();
                    edSoTien.setText("");
                    edNgayChi.setText("");
                    edCuThe.setText("");
                    rdAnUong.setChecked(true);
                }
                else {
                    Toast.makeText(ThemChiActivity.this, "Các thông tin chưa hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("axxx", "khong them duoc");
                Toast.makeText(ThemChiActivity.this, "Thêm không thành công ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // throws AuthFailureError
                String loaichi = "";
                if (rdAnUong.isChecked()) {
                    loaichi = rdAnUong.getText().toString().trim();
                }
                if (rdGiaiTri.isChecked()) {
                    loaichi = rdGiaiTri.getText().toString().trim();
                }
                if (rdHocTap.isChecked()) {
                    loaichi = rdHocTap.getText().toString().trim();
                }
                if (rdCongViec.isChecked()) {
                    loaichi = rdCongViec.getText().toString().trim();
                }
                if (rdMuaSam.isChecked()) {
                    loaichi = rdMuaSam.getText().toString().trim();
                }
                if (rdKhac.isChecked()) {
                    loaichi = rdKhac.getText().toString().trim();
                }

                Intent i = getIntent();
                String username = i.getStringExtra("Accname");
//                Toast.makeText(FragmentThu.this, loaithu,Toast.LENGTH_SHORT).show();
                Map<String, String> data = new HashMap<>();

                data.put("username", username.trim());
                data.put("sotien", edSoTien.getText().toString().trim());
                data.put("loaichi", loaichi.trim());
                data.put("ngaychi", edNgayChi.getText().toString().trim());
                data.put("cuthe", edCuThe.getText().toString().trim());
                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
