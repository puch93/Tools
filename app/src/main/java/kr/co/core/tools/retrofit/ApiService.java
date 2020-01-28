package kr.co.core.tools.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    public static final String API_URL = "https://www.naver.com/api/";
    public static final String API_URL = "http://high.alrigo.co.kr";

    //1번
    @FormUrlEncoded
    @POST("/lib/control.siso")
    Call<Test> getComment(@Field("siteUrl") String url,
                                  @Field("CONNECTCODE") String APP,
                                  @Field("_APP_MEM_IDX") String idx,
                                  @Field("dbControl") String control);

    //2번
    @POST("dogs")
    Call<ResponseBody> postComment();

    //4번
    @GET("dogs/name2")
    Call<ResponseBody> getName2(@Query("testquery") String testquery);

    //5번
    @GET("dogs/{name}")
    Call<ResponseBody> getName(@Path("name") String testpath, @Query("query") String testquery);

    //5번
    @PUT("dogs/{name}")
    Call<ResponseBody> putName(@Path("name") String testpath);
}
