package com.sxq.github.ui.modules.repos.contributors;


import github.repos.GetContributorsQuery;

public class ReposContributorsUiModel {

    private GetContributorsQuery.Data mContributorsData;

    public ReposContributorsUiModel(GetContributorsQuery.Data contributorsData) {
        mContributorsData = contributorsData;
    }

    public GetContributorsQuery.Data getContributorsData() {
        return mContributorsData;
    }
}
