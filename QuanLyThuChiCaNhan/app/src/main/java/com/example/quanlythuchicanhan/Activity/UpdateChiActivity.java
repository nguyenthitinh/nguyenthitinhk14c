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
import com.example.quanlythuchicanhan.Model.KhoanChi;
import com.example.quanlythuchicanhan.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateChiActivity extends AppCompatActivity {

    EditText SoTien,  NgayChi, CuThe;
    Button btnUpdate, btnHuy;
    RadioButton rdAnUong,rdMuaSam, rdGiaiTri, rdHocTap, rdCongViec, rdKhac;
    KhoanChi khoanChi;
    String url = "http://192.168.43.14:8888/android/updatechi.php";
    int idchi = 0;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chi);

        SoTien = findViewById(R.id.edSoTien);
        NgayChi = findViewById(R.id.edNgayChi);
        CuThe = findViewById(R.id.edCuThe);
        rdMuaSam = findViewById(R.id.rdMuaSam);
        rdGiaiTri = findViewById(R.id.rdGiaiTri);
        rdAnUong = findViewById(R.id.rdAnUong);
        rdCongViec = findViewById(R.id.rdCongViec);
        rdHocTap = findViewById(R.id.rdHocTap);
        rdKhac = findViewById(R.id.rdKhac);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnHuy= findViewById(R.id.btnHuy);

        Intent intent= getIntent();
        if (intent.hasExtra("Accname")){
            username = intent.getStringExtra("Accname");
            Log.e("USERNAME", username);
        }

        khoanChi = (KhoanChi) intent.getSerializableExtra("dulieugoc");
        idchi = khoanChi.getIdchi();
        SoTien.setText(String.valueOf(khoanChi.getSotien()));
        NgayChi.setText(khoanChi.getNgaychi());
        CuThe.setText(khoanChi.getCuthe());

        NgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar cal=Calendar.getInstance();
                int Year =cal.get(Calendar.YEAR);
                int Month=cal.get(Calendar.MONTH);
                int Day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date=new DatePickerDialog(UpdateChiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i1, int i2, int i3) {
                        cal.set(i1,i2,i2);
                        NgayChi.setText(i1+"-"+(i2+1)+"-"+i3);
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
                String sotienchi = SoTien.getText().toString().trim();
                String ngaychi = NgayChi.getText().toString().trim();
                String mota = CuThe.getText().toString().trim();
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

                if (sotienchi.isEmpty() || loaichi.isEmpty() || ngaychi.isEmpty()) {
                    Toast.makeText(UpdateChiActivity.this, "Vui lòng nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                } else {
                    capNhat(url);
                }
            }
        });

    }

    public void capNhat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(UpdateChiActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(UpdateChiActivity.this, "Cập nhật khoản chi thành công !", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(UpdateChiActivity.this, "Lỗi  "+ response.toString(), Toast.LENGTH_SHORT) .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateChiActivity.this, "Kiểm tra kết nối của bạn !", Toast.LENGTH_SHORT).show();
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
                params.put("username", username.toString());
                params.put("sotien", SoTien.getText().toString().trim());
                params.put("ngaychi", NgayChi.getText().toString().trim());
                params.put("cuthe", CuThe.getText().toString().trim());
                params.put("loaichi", loaichi.toString());
                params.put("idchi", String.valueOf(khoanChi.getIdchi()));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
