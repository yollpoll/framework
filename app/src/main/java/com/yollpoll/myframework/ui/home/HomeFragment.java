package com.yollpoll.myframework.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.yollpoll.myframework.R;
import com.yollpoll.myframework.ui.aidldemo.AIDLTestActivity;
import com.yollpoll.myframework.ui.databinding.DataBindingTestActivity;
import com.yollpoll.myframework.ui.lifecycledemo.LifecycleActivity;
import com.yollpoll.myframework.ui.livedatademo.LiveDataActivity;
import com.yollpoll.myframework.ui.pagingdemo.PagingDemoActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static final String EVENT_KEY = "key";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LiveEventBus.use(EVENT_KEY, String.class).post("121312");

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        root.findViewById(R.id.btn_aidl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AIDLTestActivity.gotoAIDLTestActivity(getActivity());
            }
        });

        root.findViewById(R.id.btn_datebinding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBindingTestActivity.gotoActivity(getActivity());
            }
        });

        root.findViewById(R.id.btn_lifecycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LifecycleActivity.gotoLiftcycleActivity(getActivity());
            }
        });
        root.findViewById(R.id.btn_livedata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDataActivity.gotoLiveDataActivity(getActivity());
            }
        });
        root.findViewById(R.id.btn_paging).setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PagingDemoActivity.class);
            intent.putExtra("key","dsadhasjdas");
            getActivity().startActivity(intent);
//            PagingDemoActivity.Companion.gotoPagingDemo(getActivity());
        });
        root.findViewById(R.id.btn_liveEventBus).setOnClickListener(v -> {
//            LiveEventBus.use(EVENT_KEY, String.class).observe(this, new ObserverWrapper<String>() {
//                @Override
//                public boolean isSticky() {
//                    return false;
//                }
//
//                @Override
//                public void onChanged(String value) {
//                    ToastUtil.showLongToast(value);
//                }
//
//                @Override
//                public boolean mainThread() {
//                    return true;
//                }
//            });
//            LiveEventBus.use(EVENT_KEY, String.class).post("after");
        });
        return root;
    }
}