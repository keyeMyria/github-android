package com.sxq.github.ui.modules.repos.release;


import github.repos.GetReleasesQuery;

public class ReposReleasesUiModel {

    private GetReleasesQuery.Data mReleasesData;

    public ReposReleasesUiModel(GetReleasesQuery.Data releasesData) {
        mReleasesData = releasesData;
    }

    public GetReleasesQuery.Data getReleasesData() {
        return mReleasesData;
    }
}
