package com.example.dell.contactsdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.contactsdemo.Utils.ContactUtil;
import com.example.dell.contactsdemo.entity.Contact;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2016/4/12.
 */
public class UpdateActivity extends AppCompatActivity {
    @InjectView(R.id.ud_toolbar)
    Toolbar udToolbar;
    @InjectView(R.id.fab_image)
    ImageView fabImage;
    @InjectView(R.id.update_imageview)
    ImageView updateImageview;
    @InjectView(R.id.update_name)
    EditText updateName;
    @InjectView(R.id.update_imageview2)
    ImageView updateImageview2;
    @InjectView(R.id.update_phone)
    EditText updatePhone;

    Context context;
    private String name;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        ButterKnife.inject(this);
        context = this;
        udToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(udToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        updateName.setText(name);
        updatePhone.setText(phone);

        udToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_uptade, menu);
        MenuItem done = menu.findItem(R.id.done);
        done.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                String bonanzaName = updateName.getText().toString();
                String bonanzaPhone = updatePhone.getText().toString();
                Contact contact=new Contact();
                contact.setName(bonanzaName);
                contact.setPhone(bonanzaPhone);
                ContactUtil.updateContact(context,name,contact);

                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
