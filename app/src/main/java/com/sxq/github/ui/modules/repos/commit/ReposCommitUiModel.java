package com.sxq.github.ui.modules.repos.commit;


import github.repos.GetCommitsQuery;

public class ReposCommitUiModel {

    private GetCommitsQuery.Data mCommitData;

    public ReposCommitUiModel(GetCommitsQuery.Data commitData) {
        mCommitData = commitData;
    }

    public GetCommitsQuery.Data getCommitData() {
        return mCommitData;
    }
}
