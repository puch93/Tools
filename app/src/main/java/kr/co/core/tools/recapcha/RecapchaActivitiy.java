//package kr.co.core.tools.recapcha;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.lib.recaptcha.ReCaptcha;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import kr.co.core.tools.R;
//
//public class RecapchaActivitiy extends AppCompatActivity implements View.OnClickListener, ReCaptcha.OnShowChallengeListener, ReCaptcha.OnVerifyAnswerListener {
//    Activity act;
//    private static final String TAG = "TEST_HOME";
//
//    private static final String PUBLIC_KEY  = "6LdsBr4UAAAAAEJuAUj62jtZO5swqAYEKTDl9YZa";
//    private static final String PRIVATE_KEY = "6LdsBr4UAAAAAJEE7K2zWo1jcB_cSXGneANlw7Hn";
//
//    private ReCaptcha reCaptcha;
//    private ProgressBar progress;
//    private EditText answer;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recapcha_activitiy);
//        act = this;
//
//        this.reCaptcha = (ReCaptcha)this.findViewById(R.id.recaptcha);
//        this.progress  = (ProgressBar)this.findViewById(R.id.progress);
//        this.answer    = (EditText)this.findViewById(R.id.answer);
//
//        this.findViewById(R.id.verify).setOnClickListener(this);
//        this.findViewById(R.id.reload).setOnClickListener(this);
//
//        this.showChallenge();
//    }
//
//
//    @Override
//    public void onClick(final View view) {
//        switch (view.getId()) {
//            case R.id.verify:
//                this.verifyAnswer();
//
//                break;
//
//            case R.id.reload:
//                this.showChallenge();
//
//                break;
//        }
//    }
//
//    @Override
//    public void onChallengeShown(final boolean shown) {
//        this.progress.setVisibility(View.GONE);
//
//        if (shown) {
//            // If a CAPTCHA is shown successfully, displays it for the user to enter the words
//            this.reCaptcha.setVisibility(View.VISIBLE);
//        } else {
//            Toast.makeText(this, R.string.show_error, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onAnswerVerified(final boolean success) {
//        if (success) {
//            Toast.makeText(this, R.string.verification_success, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, R.string.verification_failed, Toast.LENGTH_SHORT).show();
//        }
//
//        // (Optional) Shows the next CAPTCHA
//        this.showChallenge();
//    }
//
//    private void showChallenge() {
//        // Displays a progress bar while downloading CAPTCHA
//        this.progress.setVisibility(View.VISIBLE);
//        this.reCaptcha.setVisibility(View.GONE);
//
//        this.reCaptcha.setLanguageCode("en");
//        this.reCaptcha.showChallengeAsync(RecapchaActivitiy.PUBLIC_KEY, this);
//    }
//
//    private void verifyAnswer() {
//        if (TextUtils.isEmpty(this.answer.getText())) {
//            Toast.makeText(this, R.string.instruction, Toast.LENGTH_SHORT).show();
//        } else {
//            // Displays a progress bar while submitting the answer for verification
//            this.progress.setVisibility(View.VISIBLE);
//            this.reCaptcha.verifyAnswerAsync(RecapchaActivitiy.PRIVATE_KEY, this.answer.getText().toString(), this);
//        }
//    }
//}
