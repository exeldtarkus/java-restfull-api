/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.mobil.get_brand.GetBrandResponseJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterJson;
import co.id.adira.moservice.contentservice.json.user.register.RegisterResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceMobilService {

    @POST("/api/mobil/car-brands")
    Call<GetBrandResponseJson> getCarBrands(
            @Query("name") String name
    );

    @POST("/api/mobil/car-types")
    Call<GetBrandResponseJson> getCarTypes(
            @Query("name") String name,
            @Query("brand_id") String brandId
    );

}
