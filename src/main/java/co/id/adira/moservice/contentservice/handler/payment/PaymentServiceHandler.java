package co.id.adira.moservice.contentservice.handler.payment;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import co.id.adira.moservice.contentservice.json.payment.check_payment_status.PaymentCheckStatusDataResponseJson;
import co.id.adira.moservice.contentservice.json.payment.check_payment_status.PaymentCheckStatusPaymentResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceDataResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceResponseJson;
import co.id.adira.moservice.contentservice.service.MoservicePaymentService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

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

    public PaymentCheckStatusDataResponseJson checkStatusPayment(String payment_uuid) {
      
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
      Call<PaymentCheckStatusPaymentResponseJson> call = moservicePaymentService.checkStatusPaymentEspay(
              payment_uuid
              ,"Bearer " + apiToken
      );

      try {
          Response response = call.execute();

          if (response.isSuccessful()) {
            PaymentCheckStatusPaymentResponseJson responseJson = (PaymentCheckStatusPaymentResponseJson) response.body();
              return responseJson.getData();
          }

      } catch (IOException e) {
          e.printStackTrace();
      }

      return null;
  }

}
