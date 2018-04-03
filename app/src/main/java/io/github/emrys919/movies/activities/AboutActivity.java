package io.github.emrys919.movies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.emrys919.movies.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.fl_about)
    FrameLayout flAbout;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AboutTheme);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this, this);

        AboutView view = AboutBuilder.with(this)

                .setPhoto(R.drawable.developer_profile)
                .setCover(R.drawable.developer_cover)
                .setName("Myo Lwin Oo")
                .setSubTitle("Android Application Developer\n\n" +
                        "Movie list provided by themoviedb.org\n\n" +
                        "App logo from iconfinder.com")
                .setBrief("About screen UI from jrvansuita/MaterialAbout")

                .setAppIcon(R.drawable.app_logo_large)
                .setAppName(R.string.app_name)

                .addGitHubLink("myolwin00")
                .addFacebookLink("myolwin00")
                .addBitbucketLink("myolwin00")
                .addInstagramLink("myolwin00")
                .addGooglePlusLink("115955567114104978956")
                .addLinkedInLink("myolwin00")
                .addEmailLink("emryspotter1234@gmail.com")

                .setVersionNameAsAppSubTitle()
                .setWrapScrollView(true)
                .addFeedbackAction("emryspotter1234@gmail.com")
                .setLinksAnimated(true)
                .setShowAsCard(true)
                //.setBackgroundColor(R.color.primary_light)
                .build();

        flAbout.addView(view);
    }

}
