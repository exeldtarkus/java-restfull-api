package co.id.adira.moservice.contentservice.handler.adiraku.activity;

import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityResponseJson;
import co.id.adira.moservice.contentservice.service.AdirakuActivityService;
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
public class AdirakuActivityCreateActivityHandler {

    @Value("${adiraku.api.baseurl}")
    private String baseUrl;

    public AdirakuMsActivityCreateActivityResponseJson createActivity(
            AdirakuMsActivityCreateActivityJson payload,
            String type
    ) {
        AdirakuMsActivityCreateActivityResponseJson result = null;

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

        AdirakuActivityService adirakuActivityService = retrofit.create(AdirakuActivityService.class);

        Call<AdirakuMsActivityCreateActivityResponseJson> call = null;

        if (type.equals("nasabah")) {
            call = adirakuActivityService.createActivity(payload);
        } else {
            call = adirakuActivityService.createActivityProspect(payload);
        }

        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                AdirakuMsActivityCreateActivityResponseJson responseJson = (AdirakuMsActivityCreateActivityResponseJson) response.body();
                return responseJson;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
