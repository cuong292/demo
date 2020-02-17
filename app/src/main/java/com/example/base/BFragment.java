package com.example.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BFragment extends Fragment {
    private static final String TAG = "Fragment B";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container, false);
        TextView tv = v.findViewById(R.id.tv);
        tv.setOnClickListener(view -> ((MainActivity) getActivity()).replace(new AFragment()));
        Log.d(TAG, "onCreateView: ");
        return v;
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }
}
