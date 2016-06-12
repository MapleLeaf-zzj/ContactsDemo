package com.example.dell.contactsdemo.entity;

import android.widget.EditText;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by dell on 2016/4/9.
 */
public class Contact {
    private String name;
    private String tag;
    private  String lastTime;
    private String phone;
    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Contact(String name, String tag, String lastTime, String phone, String index) {
        this.name = name;
        this.tag = tag;
        this.lastTime = lastTime;
        this.phone = phone;
        this.index = index;
    }

    public Contact() {
        super();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", phone='" + phone + '\'' +
                ", index='" + index + '\'' +
                '}';
    }



}
