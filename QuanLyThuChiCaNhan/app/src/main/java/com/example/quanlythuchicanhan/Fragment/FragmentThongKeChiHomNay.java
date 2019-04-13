package com.example.quanlythuchicanhan.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.quanlythuchicanhan.Activity.ThongKeChiActivity;
import com.example.quanlythuchicanhan.Adapter.KhoanChiAdapter;
import com.example.quanlythuchicanhan.Model.KhoanChi;
import com.example.quanlythuchicanhan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentThongKeChiHomNay extends Fragment {

    ListView rvChi;
    TextView tvChi;
    String url = "http://192.168.43.14:8888/android/thongkethangchi.php";
    ArrayList<KhoanChi> arrayKhoanChi;
    ThongKeChiActivity tk;
    KhoanChiAdapter adapterchi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke_chi_homnay, container, false);

        rvChi = view.findViewById(R.id.rvChi);
        tvChi = view.findViewById(R.id.tvChi);
        arrayKhoanChi = new ArrayList<>();

        adapterchi = new KhoanChiAdapter(getActivity(), R.layout.khoanchi_item, arrayKhoanChi);
        rvChi.setAdapter(adapterchi);
        adapterchi.notifyDataSetChanged();

        layKhoanChi(url);
        adapterchi.notifyDataSetChanged();

        return view;
    }

        public void layKhoanChi(String url){
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
                    for (int i = 0; i< array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        idchi = Integer.valueOf(object.getString("idchi"));
                        sotienchi = Integer.valueOf(object.getString("sotien"));
                        loaichi = object.getString("loaichi");
                        ngaychi = object.getString("ngaychi");
                        cuthechi = object.getString("cuthe");

                        arrayKhoanChi.add(new KhoanChi(idchi, sotienchi, loaichi, ngaychi, cuthechi));
                    }
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }

                    tvChi.setText("- "+((Integer) total).toString());
                    adapterchi.notifyDataSetChanged();

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

                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM-dd";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", tk.username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
