/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityProspectJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdirakuActivityService {

    @POST("/ms-activity/activity")
    Call<AdirakuMsActivityCreateActivityResponseJson> createActivity(@Body AdirakuMsActivityCreateActivityJson payload);

    @POST("/ms-activity/activity/prospect")
    Call<AdirakuMsActivityCreateActivityResponseJson> createActivityProspect(@Body AdirakuMsActivityCreateActivityProspectJson payload);

}
