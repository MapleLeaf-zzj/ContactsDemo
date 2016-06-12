package com.example.dell.contactsdemo.moudle.all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.contactsdemo.Utils.ContactUtil;
import com.example.dell.contactsdemo.DetailActivity;
import com.example.dell.contactsdemo.MainActivity;
import com.example.dell.contactsdemo.R;
import com.example.dell.contactsdemo.Utils.Cn2Spell;
import com.example.dell.contactsdemo.Utils.ColorUtils;
import com.example.dell.contactsdemo.entity.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

/**
 * Created by dell on 2016/4/2.
 */
public class AllFragment extends Fragment {


    RecyclerView allRecyclerView;
    MyAdapter adapter;


    //所有联系人 type
    public  final int NORMAL_TYPE = 11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);

        ArrayList<Contact> list=ContactUtil.getContacts(getContext(), NORMAL_TYPE);
        for (Contact c:
            list ) {
            Log.i("ws",""+c.getName());
        }
        Log.i("asd",list.toString());
        adapter = new MyAdapter(getContext(), list);
        allRecyclerView= (RecyclerView) view.findViewById(R.id.all_recyclerView);
        allRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, List<Contact> datas,int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("name",datas.get(position).getName());
                intent.putExtra("phone",datas.get(position).getPhone());
                startActivity(intent);
            }
        });

        /**刷新adapter*/
        allRecyclerView.getAdapter().notifyDataSetChanged();

        /**获取到搜索框输入的文本  过滤*/
        ((MainActivity)getActivity()).setOnQueryChange(new MainActivity.onQueryChange() {
            @Override
            public void onQueryTextChange(String newText) {
                ((MyAdapter)allRecyclerView.getAdapter()).filter(newText);
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.setDate(ContactUtil.getContacts(getContext(),NORMAL_TYPE));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    interface OnItemClickListener{
        void onItemClick(View view , List<Contact> datas , int position);
    }



    class MyAdapter extends  RecyclerView.Adapter<MyAdapter.MyHolder> {

        private List<Contact> CacheContacts = new ArrayList<>();
        private  List<Contact> displayContacts = new ArrayList<>();
        Context context;
        private Cn2Spell cn2Spell=new Cn2Spell();


        public void setDate(List<Contact> list){
            this.displayContacts=list;
            notifyDataSetChanged();

        }

        public MyAdapter(Context context, List<Contact> contacts) {
            this.context = context;
            this.CacheContacts = contacts;
            displayContacts.addAll(CacheContacts);
            for (Contact c : displayContacts) {
                Log.i("lhd",c.getName()+"");
            }
        }

        public OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener listener){
            this.mOnItemClickListener = listener;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_all_item, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {
            /**排序*/
            Collections.sort(displayContacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact lhs, Contact rhs) {
                    String pinyinName1=cn2Spell.getPinYin(lhs.getName());
                    String pinyinName2=cn2Spell.getPinYin(rhs.getName());
                    char c1 = pinyinName1.toString().charAt(0);
                    char c2 = pinyinName2.toString().charAt(0);
                    if (c1>='a'&&c1<='z'){
                        c1-=32;
                    }
                    if (c2>='a'&&c2<='z'){
                        c2-=32;
                    }
                    int result=c1>c2?1:(c1==c2?0:-1);
                    if (result==0){
                        /**再进行一次比较*/
                        result=lhs.toString().compareTo(rhs.toString());
                    }
                    return result;
                }
            });


//           Contact contact = contacts.get(position);
//        char c = contact.getTag().charAt(0);
//        String pinyinString= HanZiZhuanPinYin.toPinYin(c);
//        String s = pinyinString.substring(0, 1).toUpperCase();
            /**获取名字拼音的首字母*/
            char c1=cn2Spell.getPinYin(displayContacts.get(position).getName()).charAt(0);
            if (c1>='a'&&c1<='z'){
                c1-=32;
            }

            /**在布局上加数据*/
            if ( position == 0 ){
                holder.tv_tag.setText(String.valueOf(c1));
                holder.tv_tag.setVisibility(View.VISIBLE);
            }else{
                Contact contact1 = displayContacts.get(position - 1);
                char c2 = cn2Spell.getPinYin(contact1.getName()).charAt(0);
                if (c2>='a'&&c2<='z'){
                    c2-=32;
                }
                if (c1 == c2){
                    holder.tv_tag.setVisibility(View.INVISIBLE);
                }else{
                    holder.tv_tag.setVisibility(View.VISIBLE);
                    holder.tv_tag.setText(String.valueOf(c1));
                }
            }
            holder.tv_name.setText(displayContacts.get(position).getName());
            holder.img.setBackground(new ColorUtils(context).getColor(displayContacts.get(position).getName(),R.drawable.circle));


            if (mOnItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(holder.itemView,displayContacts,position);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return displayContacts.size();
        }

            /**查找联系人  过滤*/
        public void filter(String newText) {
            displayContacts.clear();
            if (TextUtils.isEmpty(newText)){
                displayContacts.addAll(CacheContacts);
            } else if (ischinese(newText.charAt(0))){
                for (Contact contact:CacheContacts){
                    if (contact.getName().contains(newText)){
                        displayContacts.add(contact);
                    }
                }
            }
            else {
                for (Contact contact:CacheContacts){
                    char[] chars = newText.toCharArray();
                    String p = "";
                    for (char c:chars){
                        p+=c+"+.*";
                    }
                    Pattern pattern = Pattern.compile(p);
                    Matcher matcher = pattern.matcher(cn2Spell.getPinYin(contact.getName()));
                    if (matcher.find()){
                        displayContacts.add(contact);
                    }
                }
            }
            notifyDataSetChanged();
        }
        private  boolean ischinese(char a){
            int v = (int) a;
            return (v>=19968&&v<=171941);
        }


        class MyHolder extends RecyclerView.ViewHolder {
            public TextView tv_tag,tv_name;
            public ImageView img;

            public MyHolder(View view) {
                super(view);
                tv_tag = (TextView) view.findViewById(R.id.tv_tag);
                img = (ImageView) view.findViewById(R.id.img_item);
                tv_name = (TextView) view.findViewById(R.id.tv_name);
            }
        }
    }



}


