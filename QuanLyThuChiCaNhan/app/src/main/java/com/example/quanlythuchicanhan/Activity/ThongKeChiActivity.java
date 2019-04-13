package com.example.quanlythuchicanhan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.quanlythuchicanhan.Adapter.PagerChiAdapter;
import com.example.quanlythuchicanhan.R;

public class ThongKeChiActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    ImageView imgback;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_chi);

        Intent i = getIntent();
        username  = i.getStringExtra("Accname");
        //  Toast.makeText(ThongKeActivity.this, username, Toast.LENGTH_SHORT).show();


        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(ThongKeChiActivity.this, MainActivity.class);
                back.putExtra("Accname", username);
                startActivity(back);
            }
        });

        pager = findViewById(R.id.view_pager);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        PagerAdapter adapterchi = new PagerChiAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterchi);
    }
}
