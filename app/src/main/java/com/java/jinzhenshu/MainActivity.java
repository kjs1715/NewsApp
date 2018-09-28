package com.java.jinzhenshu;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.java.jinzhenshu.pages.FragmentFive;
import com.java.jinzhenshu.pages.FragmentFour;
import com.java.jinzhenshu.pages.FragmentOne;
import com.java.jinzhenshu.pages.FragmentSeven;
import com.java.jinzhenshu.pages.FragmentSix;
import com.java.jinzhenshu.pages.FragmentThree;
import com.java.jinzhenshu.pages.FragmentTwo;
import com.java.jinzhenshu.untils.SPUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    SmartTabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private FragmentPagerItemAdapter mTabAdapter;

    private FragmentPagerItems.Creator creator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        creator=FragmentPagerItems.with(this);
        if (getShow("checkbox_one")){
            creator.add("国内新闻", FragmentOne.class);
        }
        if (getShow("checkbox_two")){
            creator.add("滚动新闻", FragmentTwo.class);
        }
//        if (getShow("checkbox_three")){
//            creator.add("汽车行情", FragmentThree.class);
//        }
        if (getShow("checkbox_four")){
            creator.add("经济新闻", FragmentFour.class);
        }
        if (getShow("checkbox_five")){
            creator.add("台湾新闻", FragmentFive.class);
        }
        if (getShow("checkbox_six")){
            creator.add("游戏新闻", FragmentSix.class);
        }
        if (getShow("checkbox_feven")){
            creator.add("国际新闻", FragmentSeven.class);
        }
        mTabAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());

        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setViewPager(mViewPager);
    }

    private Boolean getShow(String check){
        return (Boolean) SPUtils.getInstance().get(check,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sc:
                Intent intent=new Intent(this,CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_category:
                Intent intent1=new Intent(this,CategoryActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
