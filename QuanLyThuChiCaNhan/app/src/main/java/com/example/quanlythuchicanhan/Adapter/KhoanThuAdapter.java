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
import com.example.quanlythuchicanhan.Activity.UpdateActivity;
import com.example.quanlythuchicanhan.Fragment.FragmentKhoanThu;
import com.example.quanlythuchicanhan.Model.KhoanThu;
import com.example.quanlythuchicanhan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KhoanThuAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<KhoanThu> data;

    public KhoanThuAdapter(Context context, int layout, ArrayList<KhoanThu> data) {
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
        TextView tvidthu, tvsotienthu, tvloaithu, tvngaythu, tvcuthethu;
        ImageView imgthu, imgedit, imgdelete;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        KhoanThuAdapter.ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvidthu = convertView.findViewById(R.id.tvidthu);
            viewHolder.tvsotienthu = convertView.findViewById(R.id.tvsotienthu);
            viewHolder.tvloaithu = convertView.findViewById(R.id.tvloaithu);
            viewHolder.tvngaythu = convertView.findViewById(R.id.tvngaythu);
            viewHolder.tvcuthethu = convertView.findViewById(R.id.tvcuthethu);
            viewHolder.imgthu = convertView.findViewById(R.id.imgthu);
            viewHolder.imgedit = convertView.findViewById(R.id.imgedit);
            viewHolder.imgdelete = convertView.findViewById(R.id.imgdelete);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final KhoanThu khoanThu = data.get(position);
        viewHolder.tvidthu.setText(Integer.toString(khoanThu.getIdthu()));
        viewHolder.tvsotienthu.setText(Integer.toString(khoanThu.getSotien()));
        viewHolder.tvloaithu.setText(khoanThu.getLoaithu());
        viewHolder.tvngaythu.setText(khoanThu.getNgaythu());
        viewHolder.tvcuthethu.setText(khoanThu.getCuthe());

        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(context, UpdateActivity.class);
                update.putExtra("dulieugoc", khoanThu);
                context.startActivity(update);
            }
        });

        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThu(khoanThu.getIdthu());
            }
        });

        return convertView;
    }

    private void deleteThu(final  int id){
        AlertDialog.Builder dialogDel = new AlertDialog.Builder(context);
        dialogDel.setMessage("Bạn có muôn xóa khoản thu này không?");
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.14:8888/android/deletethu.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")){
                    Toast.makeText(context, "Xóa khoản thu thành công !", Toast.LENGTH_SHORT).show();
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
                data.put("idthu", String.valueOf(id));

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }
}
