package com.sxq.github.ui.modules.profile.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sxq.github.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewFragment extends Fragment {

    private static String TAG_LOGIN = "tag_login";

    @NonNull
    private ProfileOverViewViewModel mViewModel;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @NonNull
    private String mLogin;

    private static final String TAG = ProfileOverViewFragment.class.getSimpleName();

    public static ProfileOverViewFragment newInstance(String login) {
        Bundle args = new Bundle();
        ProfileOverViewFragment fragment = new ProfileOverViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_LOGIN, login);
        fragment.setArguments(bundle);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_overview, container, false);
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }
        mCompositeDisposable = new CompositeDisposable();
        mViewModel = ProfileOverViewModule.createViewModel();
        return root;
    }

    @Override
    public void onResume() {
        bindViewModel();
        super.onResume();
    }

    @Override
    public void onPause() {
        unBindViewModel();
        super.onPause();
    }


    private void bindViewModel() {
        mCompositeDisposable.clear();
        Disposable disposable = mViewModel.getUiModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ProfileOverViewUiModel>() {
                    @Override
                    public void onNext(ProfileOverViewUiModel profileOverViewUiModel) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mCompositeDisposable.add(disposable);
    }

    private void unBindViewModel() {
        mCompositeDisposable.clear();
    }
}