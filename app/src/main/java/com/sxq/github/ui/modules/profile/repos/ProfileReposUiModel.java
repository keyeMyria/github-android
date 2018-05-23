package com.sxq.github.ui.modules.profile.repos;

import github.profile.GetOwnedReposQuery;

public class ProfileReposUiModel {

    private GetOwnedReposQuery.Data mOwnedRepos;

    public ProfileReposUiModel(GetOwnedReposQuery.Data data) {
        mOwnedRepos = data;
    }

    public GetOwnedReposQuery.Data getOwnedRepos() {
        return mOwnedRepos;
    }
}
