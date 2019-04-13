package com.example.quanlythuchicanhan.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.quanlythuchicanhan.Model.KhoanThu;
import com.example.quanlythuchicanhan.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    EditText SoTien,  NgayThu, CuThe;
    Button btnUpdate, btnHuy;
    RadioButton BoMeGui, Khac, CongViec, HocTap;
    KhoanThu khoanThu;
    String url = "http://192.168.43.14:8888/android/update.php";
    int idthu = 0;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        anhxa();

        Intent intent= getIntent();
        if (intent.hasExtra("Accname")){
            username = intent.getStringExtra("Accname");
            Log.e("USERNAME", username);
        }
        //Toast.makeText(UpdateActivity.this, username,Toast.LENGTH_SHORT).show();
        khoanThu=(KhoanThu) intent.getSerializableExtra("dulieugoc");
        idthu = khoanThu.getIdthu();
        SoTien.setText(khoanThu.getSotien()+"");
        NgayThu.setText(khoanThu.getNgaythu());
        CuThe.setText(khoanThu.getCuthe());
        //onClick();

        NgayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar cal=Calendar.getInstance();
                int Year =cal.get(Calendar.YEAR);
                int Month=cal.get(Calendar.MONTH);
                int Day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date=new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i1, int i2, int i3) {
                        cal.set(i1,i2,i2);
                        NgayThu.setText(i1+"-"+(i2+1)+"-"+i3);
                    }
                }, Year, Month, Day);
                date.show();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sotienthu = SoTien.getText().toString().trim();
                String ngaythu = NgayThu.getText().toString().trim();
                String mota = CuThe.getText().toString().trim();
                String LoaiThu = "";
                if (BoMeGui.isChecked()){
                    LoaiThu = BoMeGui.getText().toString().trim();
                }
                if (Khac.isChecked()){
                    LoaiThu = Khac.getText().toString().trim();
                }
                if (HocTap.isChecked()){
                    LoaiThu = HocTap.getText().toString().trim();
                }
                if (CongViec.isChecked()){
                    LoaiThu = CongViec.getText().toString().trim();
                }

                if (LoaiThu.isEmpty() || sotienthu.isEmpty() || ngaythu.isEmpty()){
                    Toast.makeText(UpdateActivity.this, "Vui Lòng Nhập Đủ Thông Tin !", Toast.LENGTH_SHORT).show();
                }
                else {
                    capNhat(url);
                }
            }
        });
    }

    public void capNhat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(UpdateActivity.this, "Cập nhật khoản thu thành công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(UpdateActivity.this, "Lỗi  "+ response.toString(), Toast.LENGTH_SHORT) .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateActivity.this, "Kiểm tra kết nối của bạn !", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Intent intent= getIntent();
                if (intent.hasExtra("Accname")){
                    username = intent.getStringExtra("Accname");
                   // Log.e("USERNAME", username);
                }
                String LoaiThu = "";
                if (BoMeGui.isChecked()){
                    LoaiThu = BoMeGui.getText().toString().trim();
                }
                if (Khac.isChecked()){
                    LoaiThu = Khac.getText().toString().trim();
                }
                if (HocTap.isChecked()){
                    LoaiThu = HocTap.getText().toString().trim();
                }
                if (CongViec.isChecked()){
                    LoaiThu = CongViec.getText().toString().trim();
                }
                params.put("username", username.toString());
                params.put("sotien", SoTien.getText().toString().trim());
                params.put("ngaythu", NgayThu.getText().toString().trim());
                params.put("cuthe", CuThe.getText().toString().trim());
                params.put("loaithu", LoaiThu.toString());
                params.put("idthu", String.valueOf(khoanThu.getIdthu()));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void anhxa(){
        SoTien = findViewById(R.id.SoTien);
        NgayThu = findViewById(R.id.NgayThu);
        CuThe = findViewById(R.id.CuThe);
        BoMeGui = findViewById(R.id.BoMeGui);
        CongViec = findViewById(R.id.CongViec);
        Khac = findViewById(R.id.Khac);
        HocTap = findViewById(R.id.HocTap);
        btnHuy = findViewById(R.id.btnHuy);
        btnUpdate =findViewById(R.id.btnUpdate);
    }
}
