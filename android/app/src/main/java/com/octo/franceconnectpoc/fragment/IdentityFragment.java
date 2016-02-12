package com.octo.franceconnectpoc.fragment;

import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.model.UserInfo;
import com.octo.franceconnectpoc.utils.DateFormatUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lda on 17/11/2015.
 */
public class IdentityFragment extends Fragment {

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
    public static IdentityFragment newInstance(UserInfo userInfo) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER_INFO, userInfo);

        IdentityFragment fragment = new IdentityFragment();
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

        mIconImageView.setImageResource(R.drawable.ic_identity);
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

        // de sexe masculin/féminin
        contentBuilder.append(getString(R.string.content_sex)).append(SPACE).append(OPEN_EMPHASIS);
        if ("male".equals(userInfo.getGender())) {
            contentBuilder.append(getString(R.string.content_gender_male));
        } else if ("female".equals(userInfo.getGender())) {
            contentBuilder.append(getString(R.string.content_gender_female));
        } else {
            contentBuilder.append(UNKNOWN);
        }
        contentBuilder.append(CLOSE_EMPHASIS).append(COMA).append(LINE_BREAK);

        // né le XX/XX/XXXX, à XXXX
        contentBuilder.append(getString(R.string.content_born_on)).append(SPACE).append(OPEN_EMPHASIS);
        try {
            Date birthDate = DateFormatUtils.getUserInfoDateFormat().parse(userInfo.getBirthdate());
            String birthDateString = DateFormatUtils.getContentDateFormat().format(birthDate);
            contentBuilder.append(birthDateString);
        } catch (ParseException e) {
            contentBuilder.append(UNKNOWN);
        }
        contentBuilder.append(CLOSE_EMPHASIS).append(COMA).append(SPACE).append(getString(R.string.content_at)).append(SPACE)
                .append(OPEN_EMPHASIS);
        if (userInfo.getStringBirthplace() != null) {
            contentBuilder.append(userInfo.getStringBirthplace());
        } else {
            contentBuilder.append(UNKNOWN);
        }
        contentBuilder.append(CLOSE_EMPHASIS).append(LINE_BREAK);

        // en XXX
        contentBuilder.append(getString(R.string.content_in)).append(SPACE).append(OPEN_EMPHASIS);
        if (userInfo.getStringBirthcountry() != null) {
            contentBuilder.append(userInfo.getStringBirthcountry());
        } else {
            contentBuilder.append(UNKNOWN);
        }
        contentBuilder.append(CLOSE_EMPHASIS);

        mContentTextView.setText(Html.fromHtml(contentBuilder.toString()));
    }
}
