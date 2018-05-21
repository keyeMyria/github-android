package com.sxq.github.ui.modules.profile.overview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.sxq.github.R;
import com.sxq.github.provider.emoji.EmojiParser;
import com.sxq.github.ui.adapter.ProfilePinnedReposAdapter;
import com.sxq.github.ui.widgets.AvatarLayout;
import com.sxq.github.ui.widgets.FontButton;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.SpannableBuilder;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.ParseDateFormat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import github.profile.GetOrganizationsQuery;
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

    @BindView(R.id.avatarLayout)
    AvatarLayout mAvatarLayout;

    @BindView(R.id.fullname)
    FontTextView mFullName;

    @BindView(R.id.username)
    FontTextView mUserName;

    @BindView(R.id.description)
    FontTextView mDescription;

    @BindView(R.id.company)
    FontTextView mCompany;

    @BindView(R.id.location)
    FontTextView mLocation;

    @BindView(R.id.email)
    FontTextView mEmail;

    @BindView(R.id.link)
    FontTextView mLink;

    @BindView(R.id.joined)
    FontTextView mCreateAt;

    @BindView(R.id.following)
    FontButton mFollowing;

    @BindView(R.id.followers)
    FontButton mFollowers;

    @BindView(R.id.followBtn)
    FontButton mFollowBtn;

    @BindView(R.id.orgsList)
    DynamicRecyclerView mOrgsRecyclerView;

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

    private Unbinder mUnBinder;

    private static final String TAG = ProfileOverViewFragment.class.getSimpleName();

    public static ProfileOverViewFragment newInstance(String login) {
        Bundle args = new Bundle();
        ProfileOverViewFragment fragment = new ProfileOverViewFragment();
        args.putString(TAG_LOGIN, login);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.following, R.id.followers, R.id.followBtn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.following:
                /**
                 * TODO jump to {@link com.sxq.github.ui.modules.profile.following.ProfileFollowingFragment}
                 */
                break;
            case R.id.followers:
                /**
                 * TODO jump to {@link com.sxq.github.ui.modules.profile.followers.ProfileFollowersFragment}
                 */
            case R.id.followBtn:
                /**
                 * TODO to judge if has followed, if not , POST follow action
                 */
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_overview, container, false);
        mUnBinder = ButterKnife.bind(this, root);
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
        if (mUnBinder != null) {
            mUnBinder.unbind();
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
        updateUserInfo(profileOverViewUiModel.getUser().user());
        updatePinnedRepos(profileOverViewUiModel.getPinnedRepositories());
    }

    private void updateUserInfo(GetOrganizationsQuery.User user) {
        mAvatarLayout.setUrl(user.avatarUrl().toString(), user.login());
        mAvatarLayout.findViewById(R.id.avatar).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /**
                 * TODO jump to
                 */
                return false;
            }
        });
        mFullName.setText(user.name());
        mUserName.setText(user.login());
        if (InputHelper.isEmpty(user.bio())) {
            mDescription.setVisibility(View.GONE);
        } else {
            mDescription.setText(EmojiParser.parseToUnicode(user.bio()));
        }
        if (InputHelper.isEmpty(user.company())) {
            mCompany.setVisibility(View.GONE);
        } else {
            mCompany.setText(user.company());
        }
        if (InputHelper.isEmpty(user.location())) {
            mLocation.setVisibility(View.GONE);
        } else {
            mLocation.setText(user.location());
        }
        if (InputHelper.isEmpty(user.email())) {
            mEmail.setVisibility(View.GONE);
        } else {
            mEmail.setText(user.email());
        }
        if (InputHelper.isEmpty(user.websiteUrl().toString())) {
            mLink.setVisibility(View.GONE);
        } else {
            mLink.setText(user.websiteUrl().toString());
        }
        if (InputHelper.isEmpty(user.createdAt())) {
            mCreateAt.setVisibility(View.GONE);
        } else {
            mCreateAt.setText(ParseDateFormat.parseTimeAgo(ParseDateFormat.getGithubDateFrom(user.createdAt().toString())));
        }

        mFollowing.setText(SpannableBuilder.builder()
                .append(getString(R.string.following))
                .append(" (")
                .bold(String.valueOf(user.following().totalCount()))
                .append(")"));
        mFollowers.setText(SpannableBuilder.builder()
                .append(getString(R.string.followers))
                .append(" (")
                .bold(String.valueOf(user.followers().totalCount()))
                .append(")"));
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