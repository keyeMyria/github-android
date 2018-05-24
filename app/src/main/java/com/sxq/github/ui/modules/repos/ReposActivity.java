package com.sxq.github.ui.modules.repos;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.utils.ActivityUtil;

import static com.sxq.github.ui.modules.repos.ReposPagerFragment.TAG_LOGIN;
import static com.sxq.github.ui.modules.repos.ReposPagerFragment.TAG_REPOS_NAME;


public class ReposActivity extends BaseActivity {

    @NonNull
    private String mLogin;

    @NonNull
    private String mReposName;

    @Override
    protected int activityLayout() {
        return R.layout.activity_repos;
    }

    public static void startActivity(@NonNull Context context, @NonNull String login, @NonNull String reposName) {
        Intent intent = new Intent(context, ReposActivity.class);
        intent.putExtra(TAG_LOGIN, login);
        intent.putExtra(TAG_REPOS_NAME, reposName);
        if (context instanceof Service || context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLogin = savedInstanceState.getString(TAG_LOGIN);
            mReposName = savedInstanceState.getString(TAG_REPOS_NAME);
        }

        mLogin = getIntent().getStringExtra(TAG_LOGIN);
        mReposName = getIntent().getStringExtra(TAG_REPOS_NAME);

        ReposPagerFragment reposPagerFragment = (ReposPagerFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (reposPagerFragment == null) {
            reposPagerFragment = ReposPagerFragment.newInstance(mLogin, mReposName);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), reposPagerFragment, R.id.container);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
