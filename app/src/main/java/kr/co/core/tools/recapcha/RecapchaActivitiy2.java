package kr.co.core.tools.recapcha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.core.tools.MainActivity;
import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityRecapchaActivitiy2Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public class RecapchaActivitiy2 extends AppCompatActivity {
    ActivityRecapchaActivitiy2Binding binding;

    private static final String PUBLIC_KEY  = "6LdsBr4UAAAAAEJuAUj62jtZO5swqAYEKTDl9YZa";
    private static final String PRIVATE_KEY = "6LdsBr4UAAAAAJEE7K2zWo1jcB_cSXGneANlw7Hn";
    private static final String TAG = "TEST_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recapcha_activitiy2, null);

        SafetyNet.getClient(RecapchaActivitiy2.this).verifyWithRecaptcha(PUBLIC_KEY)
                .addOnSuccessListener(new SuccessListener())
                .addOnFailureListener(new FailureListener());
    }


    public class FailureListener implements OnFailureListener {

        @Override
        public void onFailure(@NonNull Exception e) {
            // Handle failure scenario
            Log.e(TAG, "onFailure");
            binding.tvResult.setText("실패");
        }
    }

    public class SuccessListener implements OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse> {

        @Override
        public void onSuccess(final SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {

            String userResponseToken = recaptchaTokenResponse.getTokenResult();
            Log.e(TAG, "onSuccess");
            binding.tvResult.setText("성공");

            if (!userResponseToken.isEmpty()) {

            } else {
                // Device side validation resulted in no token. Failure
            }
        }
    }

}
