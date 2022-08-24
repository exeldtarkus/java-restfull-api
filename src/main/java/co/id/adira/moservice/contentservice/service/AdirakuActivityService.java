/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdirakuActivityService {

    @POST("/activity/prospect")
    Call<AdirakuMsActivityCreateActivityResponseJson> createActivity(@Body AdirakuMsActivityCreateActivityJson payload);

    @POST("/activity")
    Call<AdirakuMsActivityCreateActivityResponseJson> createActivityProspect(@Body AdirakuMsActivityCreateActivityJson payload);

}
