package com.example.base.base.viper;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.base.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class CommonActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (((ViewGroup) findViewById(android.R.id.content)).getChildCount() != 0) {
            ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                    .findViewById(android.R.id.content)).getChildAt(0);
            ViewUtil.setupUI(viewGroup, CommonActivity.this);
        }
        try {
            EventBus.getDefault().register(this);
        } catch (RuntimeException e) {
            Log.e(TAG, "No event bus method subscribed");
        }
        initLayout();
    }

    protected abstract void initLayout();

    protected abstract int getLayoutId();

    public void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view == null) {
            view = findViewById(android.R.id.content);
        }
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        }
    }

    public void showProgress() {

    }

    public void hideProgress() {

    }

    public boolean needRequestPermissions(String[] permissions, int requestCode) {
        if (permissions == null || permissions.length == 0) return false;
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                ActivityCompat.requestPermissions(CommonActivity.this, permissions, requestCode);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 322) {

        }
    }

    public void addDispose(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        disposables.dispose();
    }
}
