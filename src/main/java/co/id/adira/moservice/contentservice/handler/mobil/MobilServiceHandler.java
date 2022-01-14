package co.id.adira.moservice.contentservice.handler.mobil;

import co.id.adira.moservice.contentservice.json.mobil.get_brand.GetBrandDataResponseJson;
import co.id.adira.moservice.contentservice.json.mobil.get_brand.GetBrandResponseJson;
import co.id.adira.moservice.contentservice.json.mobil.get_model.GetModelDataResponseJson;
import co.id.adira.moservice.contentservice.json.mobil.get_model.GetModelResponseJson;
import co.id.adira.moservice.contentservice.service.MoserviceMobilService;
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
import java.util.List;

@Service
@Slf4j
@RefreshScope
public class MobilServiceHandler {

    @Value("${moservice.api.ms-mobil.baseurl}")
    private String baseUrl;

    public Long getBrandId(String token, String name) {
        Long result = null;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = httpClient
                .addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        MoserviceMobilService moserviceMobilService = retrofit.create(MoserviceMobilService.class);
        Call<GetBrandResponseJson> call = moserviceMobilService.getCarBrands("Bearer " + token, name);

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
                GetBrandResponseJson responseJson = (GetBrandResponseJson) response.body();
                List<GetBrandDataResponseJson> data = responseJson.getData();
                if (data.size() > 0) {
                    return data.get(0).getBrandId();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Long getModelId(String token, Long brandId, String name) {
        Long result = null;

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

        MoserviceMobilService moserviceMobilService = retrofit.create(MoserviceMobilService.class);
        Call<GetModelResponseJson> call = moserviceMobilService.getCarTypes("Bearer " + token, brandId, name);

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
                GetModelResponseJson responseJson = (GetModelResponseJson) response.body();
                List<GetModelDataResponseJson> data = responseJson.getData();
                if (data.size() > 0) {
                    return data.get(0).getModelId();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
