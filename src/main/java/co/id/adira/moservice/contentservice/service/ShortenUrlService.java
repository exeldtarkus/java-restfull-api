package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.servicerequest.ShortenUrlServiceRequest;
import co.id.adira.moservice.contentservice.serviceresponse.ShortenUrlServiceResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ShortenUrlService {

    @POST
    Call<ShortenUrlServiceResponse> shortenUrl(@Body ShortenUrlServiceRequest req, @Url String pathUrl);

}
