package com.example.base.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.base.R;
import com.example.base.base.viper.CommonActivity;

public abstract class ContainerActivity extends CommonActivity {


    @Override
    protected void initLayout() {
        addFragment(getFirstFragment(), false);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.setCustomAnimations(R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left);
        transaction.add(R.id.container_frame, fragment);
        transaction.commitAllowingStateLoss();
    }

    public abstract Fragment getFirstFragment();

    @Override
    protected int getLayoutId() {
        return R.layout.container;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }
}
