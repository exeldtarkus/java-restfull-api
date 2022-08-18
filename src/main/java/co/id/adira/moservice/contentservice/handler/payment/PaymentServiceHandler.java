package co.id.adira.moservice.contentservice.handler.payment;

import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceDataResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceResponseJson;
import co.id.adira.moservice.contentservice.service.MoservicePaymentService;
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
public class PaymentServiceHandler {

    @Value("${moservice.api.ms-payment.baseurl}")
    private String baseUrl;

    @Value("${moservice.api.ms-payment.token}")
    private String apiToken;

    public PaymentSendInvoiceDataResponseJson sendInvoice(
            PaymentSendInvoiceJson param
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

        MoservicePaymentService moservicePaymentService = retrofit.create(MoservicePaymentService.class);
        Call<PaymentSendInvoiceResponseJson> call = moservicePaymentService.sendInvoice(
                "Bearer " + apiToken,
                param
        );

        try {
            Response response = call.execute();

            if (response.isSuccessful()) {
                PaymentSendInvoiceResponseJson responseJson = (PaymentSendInvoiceResponseJson) response.body();
                return responseJson.getData();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
