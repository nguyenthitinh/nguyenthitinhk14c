package com.example.quanlythuchicanhan.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.quanlythuchicanhan.Activity.ThemChiActivity;
import com.example.quanlythuchicanhan.Adapter.KhoanChiAdapter;
import com.example.quanlythuchicanhan.Model.KhoanChi;
import com.example.quanlythuchicanhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentKhoanChi extends Fragment {
    ListView rvChi;
    FloatingActionButton fbaAddChi;
    ImageView imgreloadchi;
    TextView tvChi;
    String url = "http://192.168.43.14:8888/android/laykhoanchi.php";
    String username ="";
    ArrayList<KhoanChi> arrayKhoanChi;
    KhoanChiAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dskhoanchi, container, false);

        rvChi = view.findViewById(R.id.rvChi);
        fbaAddChi = view.findViewById(R.id.fabAddChi);
        tvChi = view.findViewById(R.id.tvChi);
        imgreloadchi = view.findViewById(R.id.imgreloadchi);

        arrayKhoanChi = new ArrayList<>();
        adapter = new KhoanChiAdapter(getActivity(), R.layout.khoanchi_item, arrayKhoanChi);
        rvChi.setAdapter(adapter);
//
//        String totalChi;
//        totalChi = tvChi.getText().toString();

        layKhoanChi(url);
        adapter.notifyDataSetChanged();

        Bundle bundle = getArguments();
        if (bundle != null){
            username = bundle.getString("Accname");
        }

        fbaAddChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),"hello", Toast.LENGTH_SHORT).show();
                Intent addchi = new Intent(getActivity(), ThemChiActivity.class);
                addchi.putExtra("Accname", username);
                startActivity(addchi);
            }
        });

        rvChi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Bạn có muốn xóa chi thu này?");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        delKhoanChi(url2);
//                        adapter.notifyDataSetChanged();
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
        });

        imgreloadchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayKhoanChi.clear();
                layKhoanChi(url);
            }
        });

        return view;
    }

    private void layKhoanChi(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Integer idchi;
                Integer sotienchi;
                String loaichi = "";
                String ngaychi = "";
                String cuthechi = "";
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        idchi = Integer.valueOf(object.getString("idchi"));
                        sotienchi = Integer.valueOf(object.getString("sotien"));
                        loaichi = object.getString("loaichi");
                        ngaychi = object.getString("ngaychi");
                        cuthechi =object.getString("cuthe");

                        arrayKhoanChi.add(new KhoanChi(idchi, sotienchi, loaichi, ngaychi, cuthechi));

                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }
                    tvChi.setText("- "+((Integer) total).toString());

                    adapter.notifyDataSetChanged();
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Loi "+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError{
                Bundle bundle = getArguments();
                if (bundle != null){
                    username = bundle.getString("Accname");
                }


                Map<String, String> data= new HashMap<>();
                data.put("username", username.trim());

                return data;
            }

        };
        requestQueue.add(stringRequest);
    }
}
