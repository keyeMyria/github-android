package com.sxq.github.ui.modules.profile.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sxq.github.R;
import com.sxq.github.ui.adapter.ProfilePinnedReposAdapter;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import github.profile.GetPinnedReposQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewFragment extends Fragment {

    private static String TAG_LOGIN = "tag_login";

    @BindView(R.id.pinnedReposTextView)
    FontTextView mPinnedTextView;

    @BindView(R.id.pinnedReposCard)
    CardView mPinnedCardView;

    @BindView(R.id.pinnedList)
    DynamicRecyclerView mPinnedRecyclerView;

    @NonNull
    private ProfileOverViewViewModel mViewModel;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    @Nullable
    private String mLogin;

    private Unbinder mUnbinder;

    private static final String TAG = ProfileOverViewFragment.class.getSimpleName();

    public static ProfileOverViewFragment newInstance(String login) {
        Bundle args = new Bundle();
        ProfileOverViewFragment fragment = new ProfileOverViewFragment();
        args.putString(TAG_LOGIN, login);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_overview, container, false);
        mUnbinder = ButterKnife.bind(this, root);
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }

        restoreData(savedInstanceState);

        mCompositeDisposable = new CompositeDisposable();
        mViewModel = ProfileOverViewModule.createViewModel(mLogin);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TAG_LOGIN, mLogin);
        super.onSaveInstanceState(outState);
    }

    private void restoreData(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return;
        mLogin = savedInstanceState.getString(TAG_LOGIN);
    }

    private void bindViewModel() {
        mCompositeDisposable.clear();
        Disposable disposable = mViewModel.getUiModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateUi,
                        throwable -> Timber.e(throwable));

        mCompositeDisposable.add(disposable);
    }

    private void unBindViewModel() {
        mCompositeDisposable.clear();
    }

    private void updateUi(ProfileOverViewUiModel profileOverViewUiModel) {
        // todo update other view
        updatePinnedRepos(profileOverViewUiModel.getPinnedRepositories());
    }

    private void updatePinnedRepos(List<GetPinnedReposQuery.Node> pinnedRepos) {
        if (mPinnedTextView == null)
            return;
        if (!pinnedRepos.isEmpty()) {
            mPinnedTextView.setVisibility(View.VISIBLE);
            mPinnedCardView.setVisibility(View.VISIBLE);
            ProfilePinnedReposAdapter profilePinnedReposAdapter = new ProfilePinnedReposAdapter(pinnedRepos);
            profilePinnedReposAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetPinnedReposQuery.Node>() {
                @Override
                public void onItemClick(int position, View v, GetPinnedReposQuery.Node item) {
                    /**
                     * TODO to launch the url
                     *
                     * {@link GetPinnedReposQuery.Node#url()}
                     */
                }

                @Override
                public void onItemLongClick(int position, View v, GetPinnedReposQuery.Node item) {
                }
            });
            mPinnedRecyclerView.addDivider();
            mPinnedRecyclerView.setAdapter(profilePinnedReposAdapter);
        } else {
            mPinnedTextView.setVisibility(View.GONE);
            mPinnedCardView.setVisibility(View.GONE);
        }

    }
}