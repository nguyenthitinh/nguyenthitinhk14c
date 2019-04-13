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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentUpdateAccount extends Fragment {
    EditText edsdt, edemail, eddiachi;
    Button btnupdate;
    UserActivity user;
    String urlthongtin ="http://192.168.43.14:8888/android/laythongtin.php";
    String urlupdate ="http://192.168.43.14:8888/android/update_account.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_updateaccount, container, false);

        edsdt = view.findViewById(R.id.sdt);
        edemail = view.findViewById(R.id.email);
        eddiachi = view.findViewById(R.id.diachi);
        btnupdate = view.findViewById(R.id.update);

        laythongtin(urlthongtin);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capnhat(urlupdate);
            }
        });

        return view;
    }

    public void laythongtin(String urlthongtin){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                Toast.makeText(getActivity(), "Loi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data= new HashMap<>();
                data.put("username", user.accname.trim());

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void capnhat(String urlupdate){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("thanhcong")){
                    Toast.makeText(getActivity(), "Cập nhật tài khoản thành công !", Toast.LENGTH_SHORT).show();
                    laythongtin(urlthongtin);
                }
                else {
                    Toast.makeText(getActivity(), "Lỗi  "+ response.toString(), Toast.LENGTH_SHORT) .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Kiểm tra kết nối của bạn !", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user.accname);
                params.put("email", edemail.getText().toString().trim());
                params.put("sodienthoai", edsdt.getText().toString().trim());
                params.put("diachi", eddiachi.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
