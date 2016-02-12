package com.octo.franceconnectpoc.fragment;

import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.model.UserInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lda on 17/11/2015.
 */
public class DetailsFragment extends Fragment {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    private static final String EXTRA_USER_INFO = "EXTRA_USER_INFO";

    private static final String SPACE = " ";
    private static final String UNKNOWN = "?";
    private static final String LINE_BREAK = "<br/>";
    private static final String OPEN_EMPHASIS = "<b><font color=\"#3471A9\">";
    private static final String CLOSE_EMPHASIS = "</font></b>";
    public static final String COMA = ",";

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    @Bind(R.id.textview_name)
    TextView mNameTextView;
    @Bind(R.id.imageview_icon)
    ImageView mIconImageView;
    @Bind(R.id.textview_content)
    TextView mContentTextView;

    // ----------------------------------
    // "CONSTRUCTORS"
    // ----------------------------------
    public static DetailsFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER_INFO, userInfo);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ----------------------------------
    // LIFECYCLE
    // ----------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        UserInfo userInfo = (UserInfo) getArguments().getSerializable(EXTRA_USER_INFO);
        initializeView(userInfo);
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void initializeView(UserInfo userInfo) {

        mIconImageView.setImageResource(R.drawable.ic_contact);
        mNameTextView.setText(String.format("%s %s", userInfo.getGivenName(), userInfo.getFamilyName()));

        StringBuilder contentBuilder = new StringBuilder();

        // Monsieur/Madame XXX
        if ("male".equals(userInfo.getGender())) {
            contentBuilder.append(getString(R.string.content_gender_mister));
        } else if ("female".equals(userInfo.getGender())) {
            contentBuilder.append(getString(R.string.content_gender_madam));
        } else {
            contentBuilder.append(UNKNOWN);
        }

        contentBuilder.append(SPACE).append(OPEN_EMPHASIS);
        contentBuilder.append(userInfo.getGivenName()).append(SPACE).append(userInfo.getFamilyName());
        contentBuilder.append(CLOSE_EMPHASIS).append(LINE_BREAK);

        // demeurant au XXXXXXX à WWWW - ZZZZZ
        contentBuilder.append(getString(R.string.content_living_in)).append(SPACE).append(OPEN_EMPHASIS);
        contentBuilder.append(userInfo.getAddress().getStreetAddress());
        contentBuilder.append(CLOSE_EMPHASIS).append(SPACE).append(getString(R.string.content_at)).append(SPACE).append(
                OPEN_EMPHASIS);
        contentBuilder.append(userInfo.getAddress().getLocality()).append(SPACE).append("-").append(SPACE).append(
                userInfo.getAddress().getPostalCode());
        contentBuilder.append(CLOSE_EMPHASIS).append(LINE_BREAK);

        // joignable par téléphone au : XXX
        contentBuilder.append(getString(R.string.content_reachable_by_phone_at)).append(SPACE).append(OPEN_EMPHASIS);
        contentBuilder.append(UNKNOWN);
        contentBuilder.append(CLOSE_EMPHASIS).append(LINE_BREAK);

        // joignable par mail au : XXX
        contentBuilder.append(getString(R.string.content_reachable_by_email_at)).append(SPACE).append(OPEN_EMPHASIS);
        contentBuilder.append(userInfo.getEmail());
        contentBuilder.append(CLOSE_EMPHASIS).append(LINE_BREAK);

        mContentTextView.setText(Html.fromHtml(contentBuilder.toString()));
    }
}
