package com.sxq.github.ui.modules.profile.following;

import github.profile.GetFollowingQuery;

public class ProfileFollowingUiModel {

    private GetFollowingQuery.Data mFollowingData ;

    public ProfileFollowingUiModel(GetFollowingQuery.Data followingData) {
        mFollowingData = followingData;
    }

    public GetFollowingQuery.Data getFollowingData() {
        return mFollowingData;
    }
}
