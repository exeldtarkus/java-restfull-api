package co.id.adira.moservice.contentservice.controller;

import co.id.adira.moservice.contentservice.request.ShortenUrlRequest;
import co.id.adira.moservice.contentservice.service.ShortenUrlService;
import co.id.adira.moservice.contentservice.servicerequest.ShortenUrlServiceRequest;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/api")
public class ShortenUrlController {

    @Value("${bitly.token}")
    private String token;

    @PostMapping(path = "/shorten-url")
    public ResponseEntity<Object> getShortenUrl(@RequestBody ShortenUrlRequest shortenUrlRequest) {
        ShortenUrlServiceRequest shortenUrlServiceRequest = new ShortenUrlServiceRequest();
        System.out.println(shortenUrlRequest.getUrl());
        shortenUrlServiceRequest.setLong_url(shortenUrlRequest.getUrl());
        shortenUrlServiceRequest.setDomain("bit.ly");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder().header("Authorization", token);
                requestBuilder.header("Content-Type", "application/json");
                requestBuilder.header("Accept", "application/json");
                requestBuilder.method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api-ssl.bitly.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        ShortenUrlService shortenUrlService = retrofit.create(ShortenUrlService.class);
        Call call = shortenUrlService.shortenUrl(shortenUrlServiceRequest, "/v4/shorten");
        try {
            retrofit2.Response r = call.execute();
            if (r.code() == 200) {
                return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), r.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "ERROR");
        }
        return BaseResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "ERROR");
    }

}
