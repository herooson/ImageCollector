package com.android.hyoonseol.imagecollector;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.hyoonseol.imagecollector.fragment.DirFragment;
import com.android.hyoonseol.imagecollector.fragment.SearchFragment;
import com.android.hyoonseol.imagecollector.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    class PagerAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragmentArray = new Fragment[] {new SearchFragment(), new DirFragment()};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = mFragmentArray[i];

            if (fragment != null) {
                Bundle args = new Bundle();
                fragment.setArguments(args);
            }
            return fragment;
        }

        public Fragment[] getFragmentArray() {
            return mFragmentArray;
        }

        @Override
        public int getCount() {
            return mFragmentArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Fragment fragment = mFragmentArray[position];
            if (fragment instanceof SearchFragment) {
                return "이미지 리스트";
            } else if (fragment instanceof DirFragment) {
                return "내 보관함";
            }
            return "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (mPagerAdapter != null && mPagerAdapter.getFragmentArray() != null) {
            for (int i = 0; i < mPagerAdapter.getFragmentArray().length; i++) {
                mPagerAdapter.getFragmentArray()[i].onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
