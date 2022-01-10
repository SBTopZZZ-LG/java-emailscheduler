package API;

import API.Models.Send;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Methods {
    public static void send(String recipientEmail, String subject, String body, SendListener listener) {
        EndPoints client = Client.getClient().create(EndPoints.class);
        Send body2 = new Send(recipientEmail, subject, body);

        Call<String> call = client.send(body2);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    // Success
                    listener.onSuccess();
                    return;
                }

                listener.onFailure(null);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
    public interface SendListener {
        void onSuccess();
        void onFailure(@Nullable Throwable t);
    }
}
