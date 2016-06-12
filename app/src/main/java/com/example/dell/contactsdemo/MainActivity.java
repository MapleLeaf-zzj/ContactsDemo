package com.example.dell.contactsdemo;

import android.animation.ValueAnimator;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.contactsdemo.moudle.all.AllFragment;
import com.example.dell.contactsdemo.common.CommonFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.fab)
    FloatingActionButton fab;

    int tablayoutHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        toolbar.setTitle("通讯录");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(toolbar);


        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        tablayout.setupWithViewPager(viewPager);

        /**调用onCreate 布局有可能没绘制完  就用这个方法*/
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                tablayoutHeight = tablayout.getHeight();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FabActivity.class);
                startActivity(intent);
            }
        });

    }



    /**
     * TabLayout改变的动画效果
     */
    private void animationChangeTabLayout(int oldValue, int newValue) {
        /**缩放*/
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(oldValue, newValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animationValue = (int) animation.getAnimatedValue();
                /**拿到布局参数*/
                ViewGroup.LayoutParams layoutParams = tablayout.getLayoutParams();
                /**动态改变高度*/
                layoutParams.height = animationValue;
                tablayout.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(100L);
        valueAnimator.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**通过菜单的打气筒拿到menu的布局加到界面上*/
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.searchView);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setSearchableInfo(searchableInfo);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    if (onQueryChange!=null){
                        onQueryChange.onQueryTextChange(newText);
                    }
                return false;
            }
        });

        /**设置点击搜索  添加上Tablayout动画效果*/
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                animationChangeTabLayout(tablayoutHeight, 0);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                animationChangeTabLayout(0, tablayoutHeight);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


   public interface onQueryChange{
        void onQueryTextChange(String newText);
    }
    private onQueryChange onQueryChange;

    public void setOnQueryChange(MainActivity.onQueryChange onQueryChange){
        this.onQueryChange = onQueryChange;
    }


    class MyAdapter extends FragmentPagerAdapter {

        String[] titles;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            /**拿到title*/
            titles = getResources().getStringArray(R.array.tab_title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CommonFragment();
                case 1:
                    return new AllFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
