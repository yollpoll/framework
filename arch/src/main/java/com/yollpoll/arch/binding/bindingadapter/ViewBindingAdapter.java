package com.yollpoll.arch.binding.bindingadapter;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;

import com.yollpoll.arch.binding.bindingwrapper.command.BindingCommand;

import java.lang.ref.WeakReference;

/**
 * Created by spq on 2021/3/3
 */
public class ViewBindingAdapter {
    @Deprecated
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        onClick(view, clickCommand, isThrottleFirst, 0);
    }

    @BindingAdapter(value = {"onClick", "isThrottleFirst", "gapTime"}, requireAll = false)
    public static void onClick(View view, final BindingCommand clickCommand, final boolean isThrottleFirst, final int time) {
        ViewBindingAdapter.bindViewEnable(view, clickCommand);
        if (isThrottleFirst) {//防止快速点击
            view.setOnClickListener(new ClickProxy(v -> {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }, time));

        } else {
            view.setOnClickListener(v -> {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            });
        }
    }


    public static class ClickProxy implements View.OnClickListener {

        private View.OnClickListener origin;
        private long lastclick = 0;
        private long timems = 1000; //ms
        private IAgain mIAgain;

        public ClickProxy(View.OnClickListener origin, long timems, IAgain again) {
            this.origin = origin;
            this.mIAgain = again;
            this.timems = timems;
        }

        public ClickProxy(View.OnClickListener origin) {
            this.origin = origin;
        }
        public ClickProxy(View.OnClickListener origin,int timems) {
            this.origin = origin;
            if(timems>0){
                this.timems = timems;
            }

        }
        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() - lastclick >= timems) {
                origin.onClick(v);
                lastclick = System.currentTimeMillis();
            } else {
                if (mIAgain != null) mIAgain.onAgain();
            }
        }

        public interface IAgain {
            void onAgain();//重复点击
        }
    }

    public static void bindViewEnable(View view, BindingCommand command) {
        if (command != null && command.isViewEnable != null) {
            command.isViewEnable.addOnPropertyChangedCallback(new OnViewEnableChangedCallback(view));
        }
    }

    private static class OnViewEnableChangedCallback extends Observable.OnPropertyChangedCallback {

        private WeakReference<View> nView;

        public OnViewEnableChangedCallback(View view) {
            nView = new WeakReference<>(view);
        }


        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (sender instanceof ObservableBoolean) {
                boolean b = ((ObservableBoolean) sender).get();
                if (nView != null && nView.get() != null) {
                    nView.get().setEnabled(b);
                }
            }
        }
    }
}
