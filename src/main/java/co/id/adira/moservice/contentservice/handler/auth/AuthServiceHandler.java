package co.id.adira.moservice.contentservice.handler.auth;

import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberJson;
import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberResponseJson;
import co.id.adira.moservice.contentservice.service.MoserviceAuthService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
@Slf4j
@RefreshScope
public class AuthServiceHandler {

    @Value("${moservice.api.ms-auth.baseurl}")
    private String baseUrl;

    @Value("${moservice.api.ms-auth.token}")
    private String token;

    public GetTokenByPhoneNumberResponseJson getTokenByPhoneNumber(String phoneNumber) {
        GetTokenByPhoneNumberResponseJson result = null;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        GetTokenByPhoneNumberJson payload = new GetTokenByPhoneNumberJson();
        payload.setMobileNo(phoneNumber);

        MoserviceAuthService moserviceAuthService = retrofit.create(MoserviceAuthService.class);
        Call<GetTokenByPhoneNumberResponseJson> call = moserviceAuthService.getToken("Bearer " + token, payload);

        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                GetTokenByPhoneNumberResponseJson responseJson = (GetTokenByPhoneNumberResponseJson) response.body();
                return responseJson;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
