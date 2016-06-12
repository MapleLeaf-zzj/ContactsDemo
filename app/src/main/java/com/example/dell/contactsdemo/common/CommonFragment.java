package com.example.dell.contactsdemo.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.contactsdemo.R;
import com.example.dell.contactsdemo.Utils.ContactUtil;
import com.example.dell.contactsdemo.entity.Contact;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2016/4/2.
 */
public class CommonFragment extends Fragment {

    @InjectView(R.id.common_recyclerview)
    RecyclerView commonRecyclerview;

    //常用联系人 type
    public  final int USUALLY_TYPE = 10;

    private CommonAdapter commonAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        ButterKnife.inject(this, view);

        commonAdapter = new CommonAdapter(getActivity(), ContactUtil.getContacts(getContext(), USUALLY_TYPE));
        commonRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonRecyclerview.setAdapter(commonAdapter);

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, List<Contact> datas, int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +datas.get(position).getPhone() ));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        commonAdapter.setDatas(ContactUtil.getContacts(getContext(),USUALLY_TYPE));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
