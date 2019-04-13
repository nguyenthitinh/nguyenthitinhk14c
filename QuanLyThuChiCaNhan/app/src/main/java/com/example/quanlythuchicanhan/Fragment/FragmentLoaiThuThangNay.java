package com.example.quanlythuchicanhan.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlythuchicanhan.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentLoaiThuThangNay extends Fragment implements OnChartValueSelectedListener{
    TextView thangnay, tongthu, tongchi, sodu;
    private static  Integer dathu, dachi, conlai;
    String username;
    String urlthu = "http://192.168.43.14:8888/android/thongkethang.php";
    String urlchi = "http://192.168.43.14:8888/android/thongkethangchi.php";
    String loai ="http://192.168.43.14:8888/android/layloaithu/loaithu.php";
    String loaichi ="http://192.168.43.14:8888/android/layloaichi/loaichi.php";
    PieChart mChart, mChart2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loaithu_thangnay, container, false);
        thangnay = view.findViewById(R.id.thangnay);
        tongthu = view.findViewById(R.id.tongthu);
        tongchi = view.findViewById(R.id.tongchi);
        sodu = view.findViewById(R.id.sodu);
        mChart = (PieChart) view.findViewById(R.id.piechart);
        mChart2 = (PieChart) view.findViewById(R.id.piechartchi);

        Bundle b = getArguments();
        if (b != null){
            username = b.getString("Accname");
        }

        laythangThu(urlthu);
        layloaithu(loai);
        layloaichi(loaichi);

        return view;
    }

    public void laythangThu(String urlthu){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlthu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int total = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        total = total + Integer.parseInt(object.getString("sotien"));
                    }
                    tongthu.setText("+ "+((Integer) total).toString() + " VNĐ");
                    dathu = (Integer) total;
                    laythangChi(urlchi, dathu);


                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void laythangChi(String urlchi, final int dathuok){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlchi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int totalchi = 0;
                    for (int j = 0; j < array.length(); j++){
                        JSONObject object = array.getJSONObject(j);
                        totalchi = totalchi + Integer.parseInt(object.getString("sotien"));
                    }

                    dachi = ((Integer) totalchi);

                    tongchi.setText("- "+((Integer) totalchi).toString() +" VNĐ");
                    conlai = dathuok - dachi;
                    if (conlai < 0){
                        sodu.setText(conlai.toString() + " VNĐ");
                    }
                    else {
                        sodu.setText("+ "+conlai.toString() + " VNĐ");
                    }

                    //Toast.makeText(getActivity(), dachi.toString() + conlai.toString(), Toast.LENGTH_SHORT).show();
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
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void layloaithu(String loai){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int bomegui = 0;
                    int hoctap = 0;
                    int congviec = 0;
                    int khac = 0;
                    bomegui = array.getJSONObject(0).getInt("tong1");
                    hoctap = array.getJSONObject(1).getInt("tong1");
                    congviec = array.getJSONObject(2).getInt("tong1");
                    khac = array.getJSONObject(3).getInt("tong1");
                        ve(bomegui, hoctap, congviec, khac);

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
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ve(int tong1, int tong2, int tong3, int tong4) {
        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5,5,10,5);
        mChart.setDrawHoleEnabled(false);
        mChart.setDrawCenterText(false);

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        int[] yData = {tong1, tong2, tong3, tong4};
        String[] xData = { "Bố Mẹ Gửi", "Học Tập", "Công Việc" , "Khác"};

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        mChart.setDrawEntryLabels(false);
        PieDataSet dataSet = new PieDataSet(yEntrys, xEntrys.toString());
        dataSet.setDrawValues(false);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        mChart.setData(pieData);
        mChart.invalidate();
        mChart.setDrawSliceText(false);
        setLegend2();
        mChart.setOnChartValueSelectedListener(this);
    }

        private void setLegend2(){
            Legend l = mChart.getLegend();
            l.setForm(Legend.LegendForm.SQUARE);
            l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);

            List<LegendEntry> entries = new ArrayList<>();
            ArrayList<PieEntry> yValues = new ArrayList<>();
            yValues.add(new PieEntry(0, "Bố mẹ gửi"));
            yValues.add(new PieEntry(0, "Học Tập"));
            yValues.add(new PieEntry(0, "Công Việc"));
            yValues.add(new PieEntry(0, "Khác"));
            ArrayList<Integer> colors=new ArrayList<>();
            colors.add(Color.BLUE);
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.YELLOW);
            for (int i = 0 ; i<4 ; i++){
                LegendEntry entry = new LegendEntry();
                entry.formColor =colors.get(i) ;
                entry.label = yValues.get(i).getLabel();
                entries.add(entry);
            }
            l.setCustom(entries);
    }

    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(getActivity(), "Số Tiền: " + e.getY() + " VNĐ", Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected() {
    }

    public void layloaichi(String loaichi){
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loaichi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    int anuong = 0;
                    int hoctap = 0;
                    int muasam = 0;
                    int congviec = 0;
                    int giaitri = 0;
                    int khac = 0;
                    anuong = array.getJSONObject(0).getInt("tong1");
                    hoctap = array.getJSONObject(1).getInt("tong1");
                    muasam = array.getJSONObject(2).getInt("tong1");
                    congviec = array.getJSONObject(3).getInt("tong1");
                    giaitri = array.getJSONObject(4).getInt("tong1");
                    khac = array.getJSONObject(5).getInt("tong1");
                    vechi(anuong, hoctap,muasam, congviec,giaitri, khac);
                    //Toast.makeText(getActivity(), String.valueOf(anuong), Toast.LENGTH_SHORT).show();

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi mạng " +error, Toast.LENGTH_SHORT).show();
            }
        })

        {
            protected Map<String, String> getParams() throws AuthFailureError {
                String thangnam;
                Calendar now = Calendar.getInstance();
                SimpleDateFormat sdf =null;
                String strDateFormat = "yyyy-MM";
                sdf = new SimpleDateFormat(strDateFormat);
                thangnam = sdf.format(now.getTime()).toString().trim();

                Map<String, String> data= new HashMap<>();
                data.put("username", username);
                data.put("thangnam", thangnam);

                return data;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void vechi(int tong1, int tong2, int tong3, int tong4, int tong5, int tong6) {
        mChart2.setUsePercentValues(false);
        mChart2.getDescription().setEnabled(false);
        mChart2.setExtraOffsets(5,5,10,5);
        mChart2.setDrawHoleEnabled(false);
        mChart2.setDrawCenterText(false);

        //addDataSet(mChart);
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        int[] yData = {tong1, tong2, tong3, tong4, tong5, tong6};
        String[] xData = { "Ăn Uống", "Học Tập", "Mua Sắm" , "Công Việc", "Giải Trí", "Khác"};

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, xEntrys.toString());
        pieDataSet.setDrawValues(false);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);

        PieData pieData=new PieData(pieDataSet);
        mChart2.setData(pieData);
        mChart2.invalidate();
        mChart2.setDrawSliceText(false);
        setLegend();
        mChart2.setOnChartValueSelectedListener(this);
    }

    private void setLegend(){
        Legend l = mChart2.getLegend();
        l.setForm(Legend.LegendForm.SQUARE);
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        List<LegendEntry> entries = new ArrayList<>();
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(0, "Ăn Uống"));
        yValues.add(new PieEntry(0, "Học Tập"));
        yValues.add(new PieEntry(0, "Mua Sắm"));
        yValues.add(new PieEntry(0, "Công Việc"));
        yValues.add(new PieEntry(0, "Giải Trí"));
        yValues.add(new PieEntry(0, "Khác"));
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        for (int j = 0 ; j<6 ; j++){
            LegendEntry entry = new LegendEntry();
            entry.formColor =colors.get(j) ;
            entry.label = yValues.get(j).getLabel();
            entries.add(entry);
        }
        l.setCustom(entries);
    }

//    @Override
//    public void onValueSelected(Entry e, Highlight h) {
//        Toast.makeText(getActivity(), "Số Tiền: " + e.getY(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onNothingSelected() {
//
//    }

}

