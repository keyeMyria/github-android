package com.sxq.github.ui.modules.profile.overview;

import java.util.ArrayList;
import java.util.List;

import github.GetPinnedReposQuery;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewUiModel {

    private List<GetPinnedReposQuery.Node> pinnedRepositories = new ArrayList<>();

    public ProfileOverViewUiModel(List<GetPinnedReposQuery.Node> pinnedRepositories) {
        this.pinnedRepositories = pinnedRepositories;
    }
}
