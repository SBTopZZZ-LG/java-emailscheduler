package API;

import API.Models.Send;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EndPoints {
    @POST("/send")
    Call<String> send(@Body Send body);
}