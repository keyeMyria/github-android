package com.sxq.github.ui.modules.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sxq.github.BuildConfig;
import com.sxq.github.R;
import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.modules.user.UserActivity;
import com.sxq.github.ui.widgets.FontButton;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.PrefHelper;
import com.sxq.github.utils.common.Constants;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.mainCard)
    FontTextView mMainCard;
    @BindView(R.id.usernameEditText)
    TextInputEditText mUsernameEditText;
    @BindView(R.id.username)
    TextInputLayout mUsername;
    @BindView(R.id.accessTokenCheckbox)
    com.sxq.github.ui.widgets.FontCheckbox mAccessTokenCheckbox;
    @BindView(R.id.passwordEditText)
    TextInputEditText mPasswordEditText;
    @BindView(R.id.password)
    TextInputLayout mPassword;
    @BindView(R.id.twoFactorEditText)
    TextInputEditText mTwoFactorEditText;
    @BindView(R.id.twoFactor)
    TextInputLayout mTwoFactor;
    @BindView(R.id.endpointEditText)
    TextInputEditText mEndpointEditText;
    @BindView(R.id.endpoint)
    TextInputLayout mEndpoint;
    @BindView(R.id.login)
    FloatingActionButton mLogin;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.browserLogin)
    FontButton mBrowserLogin;
    @BindView(R.id.loginForm)
    LinearLayout mLoginForm;

    @OnClick(R.id.login)
    void onClick(View v) {
        if (validateLogin()) {
            UserActivity.startActivity(getActivity(), Login.getCurrentUser().getLogin());
            getActivity().finish();
        } else {
            if (BuildConfig.DEBUG) {
                PrefHelper.set(Constants.LOGIN, "s-xq");
                PrefHelper.set(Constants.TOKEN, BuildConfig.GITHUB_AUTH_TOKEN);
                UserActivity.startActivity(getActivity(), Login.getCurrentUser().getLogin());
                getActivity().finish();
            }
        }
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    private boolean validateLogin() {
        if (!InputHelper.isEmpty(mUsernameEditText) && !InputHelper.isEmpty(mPasswordEditText)) {
            String username = mUsernameEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();
            /**
             * TODO encrypt
             */
            PrefHelper.set(Constants.LOGIN, username);
            PrefHelper.set(Constants.TOKEN, password);
            Toast.makeText(getActivity(), "Welcome " + username, Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(getActivity(), "UserName or Password error", Toast.LENGTH_SHORT).show();
        return false;
    }

}