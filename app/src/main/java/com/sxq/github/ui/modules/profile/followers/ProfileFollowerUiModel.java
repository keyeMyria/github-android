package com.sxq.github.ui.modules.profile.followers;

import github.profile.GetFollowerQuery;

public class ProfileFollowerUiModel {

    private GetFollowerQuery.Data mFollowerData ;

    public ProfileFollowerUiModel(GetFollowerQuery.Data FollowerData) {
        mFollowerData = FollowerData;
    }

    public GetFollowerQuery.Data getFollowerData() {
        return mFollowerData;
    }
}
