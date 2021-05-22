package gr.athtech.coolmovies.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import gr.athtech.coolmovies.Activity.AbstractActivity;
import gr.athtech.coolmovies.Adapter.ViewPagerAdapter;
import gr.athtech.coolmovies.Fragment.FragmentFav;
import gr.athtech.coolmovies.Fragment.FragmentPopular;
import gr.athtech.coolmovies.Fragment.FragmentSearch;
import gr.athtech.coolmovies.R;

public class MainActivity extends AbstractActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Fragment contentFragment;
    FragmentPopular fragmentPopular;
    FragmentFav fragmentFav;

    @Override
    int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    void initLayout() {
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentPopular(), "");
        adapter.AddFragment(new FragmentFav(), "");
        adapter.AddFragment(new FragmentSearch(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.getAdapter().notifyDataSetChanged();

        tabLayout.getTabAt(0).setIcon(R.drawable.fire);
        tabLayout.getTabAt(1).setIcon(R.drawable.heart);
        tabLayout.getTabAt(2).setIcon(R.drawable.search);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //This is called when orientation is changed.

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(FragmentFav.ARG_ITEM_ID)) {
                        contentFragment = fragmentManager
                                .findFragmentByTag(FragmentFav.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(FragmentPopular.ARG_ITEM_ID) != null) {
                fragmentPopular = (FragmentPopular) fragmentManager
                        .findFragmentByTag(FragmentPopular.ARG_ITEM_ID);
                contentFragment = fragmentPopular;
            }
        else {
            fragmentPopular = new FragmentPopular();
            switchContent(fragmentPopular, FragmentPopular.ARG_ITEM_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof FragmentFav) {
            outState.putString("content", FragmentFav.ARG_ITEM_ID);
        } else {
            outState.putString("content", FragmentPopular.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.viewpager_id, fragment, tag);
            //Only FavoriteListFragment is added to the back stack.
            if (!(fragment instanceof FragmentPopular)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof FragmentPopular
                || fm.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    void runOperations() {

    }

    @Override
    void stopOperations() {

    }

}