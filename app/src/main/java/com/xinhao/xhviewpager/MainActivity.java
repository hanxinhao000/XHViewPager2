package com.xinhao.xhviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xinhao.xhviewpagercool.XHDialog;
import com.xinhao.xhviewpagercool.XHViewPagerCool;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private XHViewPagerCool xhView;

    private Button btn;
    private XHDialog xhDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xhView = findViewById(R.id.xhView);
        btn = findViewById(R.id.btn);
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < 4; i++) {


            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.hxh);
            views.add(imageView);


        }
        xhView.addViewViewPager(views);

        btn.setOnClickListener(this);


        xhDialog1 = new XHDialog(this);

    }

    @Override
    public void onClick(View v) {

        xhDialog1.show();


    }
}
