package kr.co.core.tools.retrofit;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kr.co.core.tools.R;
import kr.co.core.tools.databinding.ActivityRetrofitMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitMainAct extends AppCompatActivity {
    ActivityRetrofitMainBinding binding;
    Retrofit retrofit;

    ApiService apiService;
    DefaultRestClient<ApiService> defaultRestClient;

    public static final String TAG = "TEST_HOME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit_main, null);


        //init
        defaultRestClient = new DefaultRestClient<>();
        apiService = defaultRestClient.getClient(ApiService.class);


        Call<Test> call = apiService.getComment("http://high.alrigo.co.kr", "APP", "2", "getTerm");
        call.enqueue(new Callback<Test>() {
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                if(response.isSuccessful()) {
                    //호출성공
                    //response.body() 에 클래스형식으로 담겨집니다.
                    // Class class = response.body(); 식으로 받으면 됩니다.
                    Log.e(TAG, String.valueOf(response.body()));
                    Test test = response.body();
                    binding.tv01.setText(test.getTerms());

                } else {
                    Log.e(TAG, "error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {

            }
        });




//        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
////        apiService = retrofit.create(ApiService.class);
////
////        Call<Test> comment = apiService.getComment("http://high.alrigo.co.kr", "APP", "2", "getTerm");
////        comment.enqueue(new Callback<Test>() {
////            @Override
////            public void onResponse(Call<Test> call, Response<Test> response) {
////                try {
////                    Log.e(TAG, String.valueOf(response.body()));
////                    Test test = response.body();
//////                    JSONObject jo = new JSONObject(response.body());
////                    binding.tv01.setText(test.getTerms());
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<Test> call, Throwable t) {
////                Log.e(TAG, "onFailure"+t.getMessage());
////            }
////        });
    }
}
