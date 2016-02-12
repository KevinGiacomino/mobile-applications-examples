package com.octo.franceconnectpoc.activity;

import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.service.WakeHerokuIntentService;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final int REQUEST_CODE_AUTHORIZE = 10;
    private static final int REQUEST_CODE_LOGGED_ACTIVITY = 11;

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.textview_fc_what_is_it)
    TextView mWhatIsItTextView;

    // ----------------------------------
    // LIFECYCLE
    // ----------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mWhatIsItTextView.setTypeface(Typeface.createFromAsset(getAssets(), "olivier_demo.ttf"));

        // Trick to wake up heroku and shorten the userinfo web-service response time
        startService(new Intent(this, WakeHerokuIntentService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_AUTHORIZE) {
            if (resultCode == RESULT_OK) {

                String cookie = data.getStringExtra(AuthorizeDialogActivity.RESULT_EXTRA_COOKIE);
                String code = data.getStringExtra(AuthorizeDialogActivity.RESULT_EXTRA_CODE);
                String nonce = data.getStringExtra(AuthorizeDialogActivity.RESULT_EXTRA_NONCE);

                Intent intent = new Intent(this, LoggedActivity.class);
                intent.putExtra(LoggedActivity.EXTRA_COOKIE, cookie);
                intent.putExtra(LoggedActivity.EXTRA_CODE, code);
                intent.putExtra(LoggedActivity.EXTRA_NONCE, nonce);
                startActivityForResult(intent, REQUEST_CODE_LOGGED_ACTIVITY);

            }

        } else if (requestCode == REQUEST_CODE_LOGGED_ACTIVITY) {
            if (resultCode != LoggedActivity.RESULT_CODE_SHOULD_LOGIN) {
                finish();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // ----------------------------------
    // CLICK LISTENERS
    // ----------------------------------
    @OnClick(R.id.button_login_with_france_connect)
    void onLaunchWebviewClicked() {
        startActivityForResult(new Intent(this, AuthorizeDialogActivity.class), REQUEST_CODE_AUTHORIZE);
    }
}
