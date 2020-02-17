package com.example.base;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.base.base.ContainerActivity;

public class MainActivity extends ContainerActivity {
    @Override
    public Fragment getFirstFragment() {
        return new AFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: " + String.valueOf(grantResults.length));
    }

    public void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.replace(R.id.container_frame, fragment);
        transaction.commitAllowingStateLoss();
    }
}
