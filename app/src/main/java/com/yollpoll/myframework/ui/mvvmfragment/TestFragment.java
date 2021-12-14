package com.yollpoll.myframework.ui.mvvmfragment;

import com.yollpoll.arch.annotation.ContentView;
import com.yollpoll.arch.annotation.ViewModel;
import com.yollpoll.framework.fast.FastFragment;
import com.yollpoll.myframework.R;
import com.yollpoll.myframework.databinding.FragmentTestBinding;

/**
 * Created by spq on 2021/3/3
 */
@ContentView(R.layout.fragment_test)
@ViewModel(TestViewModel.class)
public class TestFragment extends FastFragment<FragmentTestBinding, TestViewModel> {

}
