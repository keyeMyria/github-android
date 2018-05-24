package com.sxq.github.ui.modules.repos.files;


import java.util.List;

import github.repos.GetCurrentLevelTreeViewQuery;

public class ReposFilesUiModel {

    private List<GetCurrentLevelTreeViewQuery.Entry> mFileEntries;

    public ReposFilesUiModel(List<GetCurrentLevelTreeViewQuery.Entry> fileEntries) {
        mFileEntries = fileEntries;
    }

    public List<GetCurrentLevelTreeViewQuery.Entry> getFileEntries() {
        return mFileEntries;
    }
}
