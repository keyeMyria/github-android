package com.sxq.github.ui.modules.profile.feeds;

import github.user.GetProfileFeedsQuery;

public class ProfileFeedsUiModel {

    private GetProfileFeedsQuery.Data mFeedsData;

    public ProfileFeedsUiModel(GetProfileFeedsQuery.Data feedsData) {
        mFeedsData = feedsData;
    }

    public GetProfileFeedsQuery.Data getFeedsData() {
        return mFeedsData;
    }
}
