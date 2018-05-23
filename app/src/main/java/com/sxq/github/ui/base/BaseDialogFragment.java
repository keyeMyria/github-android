package com.sxq.github.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends DialogFragment {

    Unbinder mUnBinder;


    @LayoutRes
    protected abstract int fragmentLayout();

    protected abstract void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState);

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentLayout() != 0) {
            final Context contextThemeWrapper = new ContextThemeWrapper(getContext(), getContext().getTheme());// RestrictedApi
            LayoutInflater themeAwareInflater = inflater.cloneInContext(contextThemeWrapper);
            View view = themeAwareInflater.inflate(fragmentLayout(), container, false);
            mUnBinder = ButterKnife.bind(this, view);
            return view;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }
}

