package co.id.adira.moservice.contentservice.handler.content;

import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoPromoJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoResponseJson;
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
import co.id.adira.moservice.contentservice.service.MoserviceContentService;
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
public class ContentServiceHandler {

    @Value("${moservice.api.ms-content.baseurl}")
    private String baseUrl;

    public Long redeemPromo(
            String token,
            Long userId,
            Long carId,
            Long promoId,
            Long bengkelId,
            String availableUntil
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

        RedeemPromoPromoJson redeemPromoPromoJson = new RedeemPromoPromoJson();
        redeemPromoPromoJson.setId(promoId);
        redeemPromoPromoJson.setAvailableUntil(availableUntil);

        RedeemPromoJson redeemPromoJson = new RedeemPromoJson();
        redeemPromoJson.setPromo(redeemPromoPromoJson);
        redeemPromoJson.setBengkelId(bengkelId);
        redeemPromoJson.setCarId(carId);
        redeemPromoJson.setUserId(userId);
        redeemPromoJson.setUtm("adiraku");
        redeemPromoJson.setBengkel_name("");

        MoserviceContentService moserviceContentService = retrofit.create(MoserviceContentService.class);
        Call<RedeemPromoResponseJson> call = moserviceContentService.redeemPromo(
                "Bearer " + token,
                redeemPromoJson
        );

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
                RedeemPromoResponseJson redeemPromoResponseJson = (RedeemPromoResponseJson) response.body();
                return redeemPromoResponseJson.getData().getId();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
