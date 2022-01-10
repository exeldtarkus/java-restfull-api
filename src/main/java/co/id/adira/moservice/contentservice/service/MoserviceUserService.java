/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.*;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceUserService {

    @POST("/api/users")
    Call<RegisterResponseJson> register(@Body RegisterJson registerJson);

}
