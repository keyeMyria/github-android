package com.sxq.github.ui.modules.profile.starred;

import github.profile.GetStarredReposQuery;

public class ProfileStarredUiModel {

    private GetStarredReposQuery.Data mStarredReposData;

    public ProfileStarredUiModel(GetStarredReposQuery.Data starredReposData) {
        mStarredReposData = starredReposData;
    }

    public GetStarredReposQuery.Data getStarredReposData() {
        return mStarredReposData;
    }
}
