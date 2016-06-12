package com.example.dell.contactsdemo.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.contactsdemo.R;
import com.example.dell.contactsdemo.Utils.ColorUtils;
import com.example.dell.contactsdemo.entity.Contact;

import java.util.List;

/**
 * Created by dell on 2016/4/10.
 */
public class CommonAdapter extends RecyclerView.Adapter<CommonHolder> {
    List<Contact> datas;
    Context context;
    public CommonAdapter( Context context, List<Contact> datas) {
        this.datas = datas;
        this.context = context;
    }

    public void  setDatas(List<Contact> setCommondatas){
        this.datas = setCommondatas;
        notifyDataSetChanged();
    }

    @Override
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_common_item, parent, false);
        CommonHolder holder = new CommonHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CommonHolder holder, final int position) {
        Contact contact = datas.get(position);
        holder.img_item.setBackground(new ColorUtils(context).getColor(datas.get(position).getName(),R.drawable.circle));
        holder.common_name.setText(contact.getName());


        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,datas,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    interface OnItemClickListener{
        void onItemClick(View view , List<Contact> datas,int position);

    }
    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
}

class CommonHolder extends RecyclerView.ViewHolder {
    public TextView common_name;
    public ImageView img_item;

    public CommonHolder(View view) {
        super(view);
        img_item = (ImageView) view.findViewById(R.id.img_item);
        common_name = (TextView) view.findViewById(R.id.common_name);
    }
}