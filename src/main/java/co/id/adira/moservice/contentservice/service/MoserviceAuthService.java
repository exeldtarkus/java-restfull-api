/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberJson;
import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceAuthService {

    @POST("/api/token")
    Call<GetTokenByPhoneNumberResponseJson> getToken(@Header("Authorization") String authorization, @Body GetTokenByPhoneNumberJson payload);

}
