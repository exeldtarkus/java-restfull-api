package co.id.adira.moservice.contentservice.handler.user;

import co.id.adira.moservice.contentservice.json.mobil.get_model.GetModelDataResponseJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarModelBrandJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarModelJson;
import co.id.adira.moservice.contentservice.json.user.create_user_car.CreateUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.get_user_car.GetUserCarDataResponseJson;
import co.id.adira.moservice.contentservice.json.user.get_user_car.GetUserCarResponseJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterCredentialJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterCredentialRoleJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import co.id.adira.moservice.contentservice.service.MoserviceUserService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
import java.util.List;

@Service
@Slf4j
@RefreshScope
public class UserServiceHandler {

    @Value("${moservice.api.ms-user.baseurl}")
    private String baseUrl;

    public Boolean forceRegister(String fullname, String phoneNumber) {

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

    public Long getMobilId(
            String token,
            Long userId,
            String plateNo,
            Long brandId,
            Long modelId
    ) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        System.out.println("============== base url ================");
        System.out.println(baseUrl);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        MoserviceUserService moserviceUserService = retrofit.create(MoserviceUserService.class);
        Call<GetUserCarResponseJson> call = moserviceUserService.getUserCars(
                "Bearer " + token,
                userId,
                plateNo,
                brandId,
                modelId
        );

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
                GetUserCarResponseJson responseJson = (GetUserCarResponseJson) response.body();
                List<GetUserCarDataResponseJson> data = responseJson.getData();
                if (data.size() > 0) {
                    return data.get(0).getId();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Long createUserCar(
            String token,
            Long userId,
            String plateNo,
            Long brandId,
            Long modelId,
            String brandName,
            String modelName
    ) {

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

        CreateUserCarModelBrandJson createUserCarModelBrandJson = new CreateUserCarModelBrandJson();
        createUserCarModelBrandJson.setBrandId(brandId);

        CreateUserCarModelJson createUserCarModelJson = new CreateUserCarModelJson();
        createUserCarModelJson.setBrand(createUserCarModelBrandJson);
        createUserCarModelJson.setModelId(modelId);

        CreateUserCarJson createUserCarJson = new CreateUserCarJson();
        createUserCarJson.setModel(createUserCarModelJson);
        createUserCarJson.setMobilKm((long) 0);
        createUserCarJson.setPlatNo(plateNo);
        createUserCarJson.setPrimary(false);
        createUserCarJson.setYear(null);

        MoserviceUserService moserviceUserService = retrofit.create(MoserviceUserService.class);
        Call<CreateUserCarResponseJson> call = moserviceUserService.createUserCar(
                "Bearer " + token,
                userId,
                createUserCarJson
        );

        try {
            Response response = call.execute();
            CreateUserCarResponseJson responseJson = (CreateUserCarResponseJson) response.body();

            if (response.isSuccessful()) {
                return responseJson.getData().getId();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
