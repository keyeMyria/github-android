package com.sxq.github.ui.modules.profile.overview;

import java.util.ArrayList;
import java.util.List;

import github.profile.GetOrganizationsQuery;
import github.profile.GetPinnedReposQuery;


/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewUiModel {

    private List<GetPinnedReposQuery.Node> pinnedRepositories = null;
    private GetOrganizationsQuery.Data mUser;

    public ProfileOverViewUiModel(List<GetPinnedReposQuery.Node> pinnedRepositories, GetOrganizationsQuery.Data user) {
        this.pinnedRepositories = pinnedRepositories;
        mUser = user;
    }

    public List<GetPinnedReposQuery.Node> getPinnedRepositories() {
        return pinnedRepositories;
    }

    public GetOrganizationsQuery.Data getUser() {
        return mUser;
    }
}
