package co.id.adira.moservice.contentservice.handler.user;

import co.id.adira.moservice.contentservice.json.user.register.RegisterCredentialJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterCredentialRoleJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import co.id.adira.moservice.contentservice.service.MoserviceUserService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
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
public class UserServiceHandler {

    @Value("${moservice.api.ms-user.baseurl}")
    private String baseUrl;

    public Boolean exec(String fullname, String phoneNumber) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        System.out.println("============== base url ================");
        System.out.println(baseUrl);

        RegisterCredentialRoleJson registerCredentialRoleJson = new RegisterCredentialRoleJson();
        registerCredentialRoleJson.setId("user");

        RegisterCredentialJson registerCredentialJson = new RegisterCredentialJson();
        registerCredentialJson.setRole(registerCredentialRoleJson);
        registerCredentialJson.setEnabled(1);
        registerCredentialJson.setEmail("");
        registerCredentialJson.setMobileNo(phoneNumber);
        registerCredentialJson.setPassword("");
        registerCredentialJson.setAccountNonLocked(1);

        RegisterJson registerJson = new RegisterJson();
        registerJson.setCredential(registerCredentialJson);
        registerJson.setFirstName(fullname);
        registerJson.setProvider("adiraku");
        registerJson.setEmailVerified(1);

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        MoserviceUserService moserviceUserService = retrofit.create(MoserviceUserService.class);
        Call<RegisterResponseJson> call = moserviceUserService.register(registerJson);

        try {
            Response response = call.execute();
            RegisterResponseJson responseJson = (RegisterResponseJson) response.body();

            if (response.isSuccessful()) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
