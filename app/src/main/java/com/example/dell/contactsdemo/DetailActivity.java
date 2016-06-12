package com.example.dell.contactsdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.contactsdemo.Utils.ColorUtils;
import com.example.dell.contactsdemo.Utils.ContactUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2016/4/11.
 */
public class DetailActivity extends AppCompatActivity {


    @InjectView(R.id.cardView)
    CardView cardView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.iv_phone)
    ImageView ivPhone;
    @InjectView(R.id.tv_tele)
    TextView tvTele;
    @InjectView(R.id.iv_sms)
    ImageView ivSms;

    Context context;
    private String name;
    private String phone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.inject(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        context = this;


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        tvName.setText(name);
        tvTele.setText(phone);


        tvName.setBackground(new ColorUtils(context).getColor(name, R.drawable.rectangle));
        toolbar.setBackground(new ColorUtils(context).getColor(name, R.drawable.rectangle));
        ivPhone.setBackground(new ColorUtils(context).getColor(name, R.drawable.ic_phone_black_24dp));
        ivSms.setBackground(new ColorUtils(context).getColor(name, R.drawable.ic_chat_black_24dp));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        ivSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        MenuItem creat = menu.findItem(R.id.creat);

        creat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                startActivity(intent);
                return false;
            }
        });

        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ContactUtil.delContact(context,name);
                finish();
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LHD","恢复D");
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        tvName.setText(name);
        tvTele.setText(phone);

    }
}
