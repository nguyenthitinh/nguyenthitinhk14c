package com.example.quanlythuchicanhan.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.Fragment.FragmentKhoanChi;
import com.example.quanlythuchicanhan.Fragment.FragmentKhoanThu;
import com.example.quanlythuchicanhan.Fragment.FragmentLoaiThuThangNay;
import com.example.quanlythuchicanhan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String noidung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        String username = i.getStringExtra("Accname").trim();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentLoaiThuThangNay fragment1 = new FragmentLoaiThuThangNay();
        Bundle b = new Bundle();
        b.putString("Accname", username);
        fragment1.setArguments(b);

        transaction.add(R.id.frLayout,fragment1);
        transaction.commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView textView;
        ImageView imgUser;
        textView = headerView.findViewById(R.id.txtUser);
        imgUser = headerView.findViewById(R.id.imgUser);
        //Intent i = getIntent();
        final String name = i.getStringExtra("Accname");
        textView.setText(i.getStringExtra("Accname") + " !");
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "hello " + name, Toast.LENGTH_SHORT).show();
                Intent user = new Intent(MainActivity.this, UserActivity.class);
                user.putExtra("Accname", name);
                startActivity(user);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main){
            Intent i = getIntent();
            String username = i.getStringExtra("Accname").trim();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            FragmentLoaiThuThangNay fragment1 = new FragmentLoaiThuThangNay();

            Bundle b = new Bundle();
            b.putString("Accname", username);
            fragment1.setArguments(b);

            transaction.add(R.id.frLayout,fragment1);
            transaction.commit();
        }

        if (id == R.id.dsThu) {
            Intent i = getIntent();
            String username = i.getStringExtra("Accname").trim();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            FragmentKhoanThu fragment1 = new FragmentKhoanThu();

            Bundle b = new Bundle();
            b.putString("Accname", username);
            fragment1.setArguments(b);

            transaction.add(R.id.frLayout,fragment1);
            transaction.commit();
        }

        else if (id == R.id.tkThu) {
            Intent i = getIntent();
            String username = i.getStringExtra("Accname");
            Intent tk = new Intent(MainActivity.this, ThongKeActivity.class);
            tk.putExtra("Accname", username);
            startActivity(tk);
        }

        else if (id == R.id.dsChi) {
            Intent i = getIntent();
            String username = i.getStringExtra("Accname").trim();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            FragmentKhoanChi fragment = new FragmentKhoanChi();

            Bundle bundle = new Bundle();
            bundle.putString("Accname", username);
            fragment.setArguments(bundle);

            transaction.add(R.id.frLayout, fragment);
            transaction.commit();
        }

        else if (id == R.id.tkChi) {
            Intent i = getIntent();
            String username = i.getStringExtra("Accname");
            Intent tkchi = new Intent(MainActivity.this, ThongKeChiActivity.class);
            tkchi.putExtra("Accname", username);
            startActivity(tkchi);
        }


        else if (id == R.id.feedback) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle("Phản hồi");
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.layout_phanhoi);
            final EditText phanhoi = dialog.findViewById(R.id.edfeedback);
            Button gui = dialog.findViewById(R.id.send);
            Button huy = dialog.findViewById(R.id.cancel);
            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();


                }
            });
            gui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String url = "http://192.168.43.14:8888/android/phanhoi.php";
                    noidung = phanhoi.getText().toString().trim();
                    if (noidung.length() ==0 ){
                        Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung phản hồi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (noidung.length() >255){
                            Toast.makeText(MainActivity.this, "Nội dung vượt quá 255 ký tự", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            guiphanhoi(url);
                            dialog.cancel();
                        }
                    }
                }
            });
            dialog.show();


        } else if (id == R.id.logout) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("QuanLyThuChiCaNhan");
            alertDialogBuilder.setMessage("Bạn có muốn đăng xuất?");
            alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(logout);
                }
            });
            alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialogBuilder.show();
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void guiphanhoi(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(MainActivity.this, "Cảm ơn bạn đã gửi phản hồi ! ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Chưa thành công !" +response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi ! " +error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent i = getIntent();
                String username = i.getStringExtra("Accname").trim();
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM-dd";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();
                Map<String, String> data = new HashMap<>();

                data.put("username", username);
                data.put("noidung", noidung);
                data.put("ngayphanhoi", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}

