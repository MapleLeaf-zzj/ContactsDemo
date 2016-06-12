package com.example.dell.contactsdemo.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;

import com.example.dell.contactsdemo.R;

/**
 * Created by dell on 2016/4/9.
 */
public class ColorUtils {
    int color;
    Context context;
    static int[] colors = {R.color.google_blue,
            R.color.google_blue_highlight,
            R.color.google_green,
            R.color.google_red,
            R.color.google_gray,
            R.color.google_blue_dark,
            R.color.google_purple,
            R.color.google_orange,
            R.color.google_yellow};

    public ColorUtils(Context context) {
        this.context = context;
    }

     public  Drawable getColor(String  name,int imgPath){
         Drawable drawable =context.getResources().getDrawable(imgPath);
         drawable.setTint(context.getResources().getColor(colors[Math.abs(name.hashCode()) % colors.length]));
         return  drawable;

    }

//    public  Drawable getColors(String  name){
//        Drawable drawable =context.getResources().getDrawable(R.drawable.rectangle);
//        drawable.setTint(context.getResources().getColor(colors[Math.abs(name.hashCode()) % colors.length]));
//        return  drawable;
//
//    }
    public static int getColors(Resources mResources, String str) {
        return mResources.getColor(colors[Math.abs(str.hashCode()) % colors.length]);
    }

    public static int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv); // convert to hsv
        // make darker
        hsv[1] = hsv[1] + 0.1f; // more saturation
        hsv[2] = hsv[2] - 0.1f; // less brightness
        return Color.HSVToColor(hsv);
    }
/**不分版本的头像颜色*/
//    Drawable myIcon = getResources().getDrawable( R.drawable.ic_person_add_white_36dp );
//    ColorFilter filter = new LightingColorFilter( Color.RED, Color.RED );
//    myIcon.setColorFilter(filter);

}
