package com.example.quanlythuchicanhan.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.quanlythuchicanhan.Activity.UpdateChiActivity;
import com.example.quanlythuchicanhan.Model.KhoanChi;
import com.example.quanlythuchicanhan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhoanChiAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<KhoanChi> data;

    public KhoanChiAdapter(Context context, int layout, ArrayList<KhoanChi> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvidchi, tvsotienchi, tvloaichi, tvngaychi, tvcuthechi;
        ImageView imgchi, imgedit, imgdelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
             viewHolder.tvidchi = convertView.findViewById(R.id.tvidchi);
             viewHolder.tvsotienchi = convertView.findViewById(R.id.tvsotienchi);
             viewHolder.tvloaichi = convertView.findViewById(R.id.tvloaichi);
             viewHolder.tvngaychi = convertView.findViewById(R.id.tvngaychi);
             viewHolder.tvcuthechi = convertView.findViewById(R.id.tvcuthechi);
             viewHolder.imgchi = convertView.findViewById(R.id.imgchi);
             viewHolder.imgedit = convertView.findViewById(R.id.imgedit);
             viewHolder.imgdelete = convertView.findViewById(R.id.imgdelete);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final KhoanChi khoanChi = data.get(position);
        viewHolder.tvidchi.setText(Integer.toString(khoanChi.getIdchi()));
        viewHolder.tvsotienchi.setText("- "+Integer.toString(khoanChi.getSotien()));
        viewHolder.tvloaichi.setText(khoanChi.getLoaichi());
        viewHolder.tvngaychi.setText(khoanChi.getNgaychi());
        viewHolder.tvcuthechi.setText(khoanChi.getCuthe());

        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updatechi = new Intent(context, UpdateChiActivity.class);
                updatechi.putExtra("dulieugoc", khoanChi);
                context.startActivity(updatechi);
            }
        });

        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChi(khoanChi.getIdchi());
            }
        });

        return convertView;
    }
    private void deleteChi(final  int id){
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(context);
        dialogDel.setMessage("Bạn có muôn xóa khoản chi này không?");
        dialogDel.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoa(id);
            }
        });
        dialogDel.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogDel.show();
    }

    public void xoa(final int id){
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.43.14:8888/android/deletechi.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(context, "Xóa khoản chi thành công !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Lỗi "+response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Xóa không thành công. Vui lòng kiểm tra kết nối !", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idchi", String.valueOf(id));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}