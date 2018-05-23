package com.sxq.github.ui.modules.repos.branch;


import java.util.List;

import github.repos.GetBranchesQuery;

public class ReposBranchUiModel {

    private List<GetBranchesQuery.Node> mBranches;

    public ReposBranchUiModel(List<GetBranchesQuery.Node> branches) {
        mBranches = branches;
    }

    public List<GetBranchesQuery.Node> getBranches() {
        return mBranches;
    }
}
