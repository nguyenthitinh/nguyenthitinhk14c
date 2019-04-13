package com.example.quanlythuchicanhan.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.quanlythuchicanhan.Activity.ThemThuActivity;
import com.example.quanlythuchicanhan.Adapter.KhoanThuAdapter;
import com.example.quanlythuchicanhan.Model.KhoanThu;
import com.example.quanlythuchicanhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentKhoanThu extends Fragment {
    ListView rvThu;
    TextView tvThu;
    ImageView imgreload;
    FloatingActionButton fbaAddThu;
    String url = "http://192.168.43.14:8888/android/laykhoanthu.php";
    String url2 = "http://192.168.43.14:8888/android/xoakhoanthu.php";
    String username ="";
    ArrayList<KhoanThu> arrayKhoanThu;
    KhoanThuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dskhoanthu, container, false);

        rvThu = view.findViewById(R.id.rvThu);
        tvThu = view.findViewById(R.id.tvThu);
        imgreload = view.findViewById(R.id.imgreload);
        fbaAddThu = view.findViewById(R.id.fabAddThu);
        arrayKhoanThu = new ArrayList<>();
        adapter = new KhoanThuAdapter(getActivity(), R.layout.khoanthu_item, arrayKhoanThu);
        rvThu.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        layKhoanThu(url);
        adapter.notifyDataSetChanged();

        Bundle b = getArguments();
        if (b != null){
            username = b.getString("Accname");
        }

        fbaAddThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Them Thu" + username, Toast.LENGTH_SHORT).show();
                Intent addthu = new Intent(getActivity(), ThemThuActivity.class);
                addthu.putExtra("Accname", username);
                startActivity(addthu);
            }
        });

        imgreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayKhoanThu.clear();
                layKhoanThu(url);
            }
        });

        return view;
    }

    public void layKhoanThu(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idthu;
                int sotienthu;
                String loaithu = "";
                String ngaythu = "";
                String cuthethu = "";
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        idthu = Integer.parseInt(object.getString("idthu"));
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Bundle b = getArguments();
                if (b != null){
                    username = b.getString("Accname");
                }

                Map<String, String> data= new HashMap<>();
                data.put("username", username.trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

}

