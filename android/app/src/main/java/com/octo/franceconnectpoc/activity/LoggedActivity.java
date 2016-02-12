package com.octo.franceconnectpoc.activity;

import com.octo.franceconnectpoc.BuildConfig;
import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.fragment.DetailsFragment;
import com.octo.franceconnectpoc.fragment.HistoryFragment;
import com.octo.franceconnectpoc.fragment.IdentityFragment;
import com.octo.franceconnectpoc.model.UserInfo;
import com.octo.franceconnectpoc.network.BackendService;
import com.octo.franceconnectpoc.network.FranceConnectService;
import com.octo.franceconnectpoc.network.InjectCookieInterceptor;
import com.octo.franceconnectpoc.utils.CogCityFetcher;
import com.octo.franceconnectpoc.utils.CogCountryFetcher;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lda on 02/11/15.
 */
public class LoggedActivity extends AppCompatActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    public static final int RESULT_CODE_SHOULD_LOGIN = 2;
    public static final String EXTRA_COOKIE = "EXTRA_COOKIE";
    public static final String EXTRA_CODE = "EXTRA_CODE";
    public static final String EXTRA_NONCE = "EXTRA_NONCE";

    private static final String SAVED_USER_INFO = "SAVED_USER_INFO";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private LoggedFragmentPagerAdapter mPagerAdapter;
    private UserInfo mUserInfo;
    private String mCookie;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.layout_progress_logged)
    View mProgressLayout;
    @Bind(R.id.viewpager_logged)
    ViewPager mViewPager;

    // ----------------------------------
    // LIFECYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        ButterKnife.bind(this);

        mPagerAdapter = new LoggedFragmentPagerAdapter(this, getSupportFragmentManager());
        initializeView();

        mCookie = getIntent().getStringExtra(EXTRA_COOKIE);
        String code = getIntent().getStringExtra(EXTRA_CODE);
        String nonce = getIntent().getStringExtra(EXTRA_NONCE);

        if (savedInstanceState != null) {
            mUserInfo = (UserInfo) savedInstanceState.getSerializable(SAVED_USER_INFO);
        }

        if (mUserInfo == null) {
            fetchUserInfo(code, nonce);
        } else {
            showTabs(mUserInfo);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_USER_INFO, mUserInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logged_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.ic_logged);

        mProgressLayout.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void fetchUserInfo(String code, final String nonce) {
        Retrofit retrofit = getRetrofit(null, BuildConfig.BACKEND_BASE_URL);
        BackendService backendService = retrofit.create(BackendService.class);
        Call<UserInfo> userInfoCall = backendService.userInfo(code, nonce);

        userInfoCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                final UserInfo userInfo = response.body();

                new AsyncTask<Integer, Void, String>() {

                    @Override
                    protected String doInBackground(Integer... params) {
                        try {
                            return CogCountryFetcher.fetch(LoggedActivity.this, params[0]);
                        } catch (IOException e) {
                            // Do nothing
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        userInfo.setStringBirthcountry(result);

                        new AsyncTask<String, Void, String>() {

                            @Override
                            protected String doInBackground(String... params) {
                                try {
                                    return CogCityFetcher.fetch(LoggedActivity.this, params[0]);
                                } catch (IOException e) {
                                    // Do nothing
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String result) {
                                userInfo.setStringBirthplace(result);
                                showTabs(userInfo);
                            }
                        }.execute(userInfo.getBirthplace());

                    }
                }.execute(userInfo.getBirthcountry());
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                mProgressLayout.setVisibility(View.GONE);
                showError(t.getLocalizedMessage());
            }
        });
    }

    private Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();

        if (client != null) {
            retrofitBuilder.client(client);
        }

        Retrofit retrofit = retrofitBuilder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private void showTabs(UserInfo userInfo) {
        mProgressLayout.setVisibility(View.GONE);

        if (userInfo != null) {
            mUserInfo = userInfo;
            mPagerAdapter.setUserInfo(mUserInfo);
            mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
            mViewPager.setVisibility(View.VISIBLE);
        } else {
            showError(getString(R.string.empty_data_error_message));
        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(LoggedActivity.this)
                .setTitle(R.string.error_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        finish();
                    }
                })
                .show();
    }

    private void logout() {
        mProgressLayout.setAlpha(0);
        mProgressLayout.setVisibility(View.VISIBLE);
        mProgressLayout.animate().alpha(1);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new InjectCookieInterceptor(mCookie))
                .build();
        Retrofit retrofit = getRetrofit(client, BuildConfig.FC_BASE_URL);
        FranceConnectService backendService = retrofit.create(FranceConnectService.class);

        Call<Void> logoutCall = backendService.logOut();
        logoutCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                removeCookies();
                setResult(RESULT_CODE_SHOULD_LOGIN);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                removeCookies();
                setResult(RESULT_CODE_SHOULD_LOGIN);
                finish();
            }
        });
    }

    private void removeCookies() {
        mCookie = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            //noinspection deprecation
            CookieManager.getInstance().removeAllCookie();
        }
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private static class LoggedFragmentPagerAdapter extends FragmentPagerAdapter {

        private static final int POSITION_IDENTITY = 0;
        private static final int POSITION_DETAILS = 1;
        private static final int POSITION_HISTORY = 2;

        private final Context mContext;
        private UserInfo mUserInfo;

        public LoggedFragmentPagerAdapter(Context context, FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case POSITION_IDENTITY:
                    return IdentityFragment.newInstance(mUserInfo);

                case POSITION_DETAILS:
                    return DetailsFragment.newInstance(mUserInfo);

                case POSITION_HISTORY:
                    return new HistoryFragment();

                default:
                    return new Fragment();
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            @StringRes int stringId;

            switch (position) {
                case POSITION_IDENTITY:
                    stringId = R.string.identity_title;
                    break;

                case POSITION_DETAILS:
                    stringId = R.string.details_title;
                    break;

                case POSITION_HISTORY:
                    stringId = R.string.history_title;
                    break;

                default:
                    stringId = -1;
            }

            if (stringId != -1) {
                return mContext.getString(stringId);
            }

            return null;
        }

        @Override
        public int getCount() {
            return mUserInfo != null ? 3 : 0;
        }

        public void setUserInfo(UserInfo userInfo) {
            mUserInfo = userInfo;
            notifyDataSetChanged();
        }
    }
}
