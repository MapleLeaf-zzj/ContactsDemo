package com.example.dell.contactsdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.contactsdemo.Utils.ContactUtil;
import com.example.dell.contactsdemo.entity.Contact;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2016/4/10.
 */
public class FabActivity extends AppCompatActivity {

    @InjectView(R.id.back)
    ImageButton back;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.success)
    ImageButton success;
    @InjectView(R.id.fab_image)
    ImageView fabImage;
    @InjectView(R.id.imageview)
    ImageView imageview;
    @InjectView(R.id.fab_name)
    EditText fabName;
    @InjectView(R.id.imageview2)
    ImageView imageview2;
    @InjectView(R.id.fab_phone)
    EditText fabPhone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab_activity);
        ButterKnife.inject(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fabName.getText().toString();
                String phone = fabPhone.getText().toString();

                Contact contact=new Contact();
                contact.setName(name);
                contact.setPhone(phone);
                ContactUtil.insertContacts(FabActivity.this,contact);
                Toast.makeText(FabActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }

}
