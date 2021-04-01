package com.yollpoll.myframework.ui.mvvmfragment;

import com.yollpoll.fast.FastFragment;
import com.yollpoll.framework.annotation.ContentView;
import com.yollpoll.framework.annotation.ViewModel;
import com.yollpoll.myframework.R;
import com.yollpoll.myframework.databinding.FragmentTestBinding;

/**
 * Created by spq on 2021/3/3
 */
@ContentView(R.layout.fragment_test)
@ViewModel(TestViewModel.class)
public class TestFragment extends FastFragment<FragmentTestBinding, TestViewModel> {

}
