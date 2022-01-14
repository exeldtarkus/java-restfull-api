/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.mobil.get_brand.GetBrandResponseJson;
import co.id.adira.moservice.contentservice.json.mobil.get_model.GetModelResponseJson;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author fatchurrachman
 *
 */
public interface MoserviceMobilService {

    @GET("/api/mobil/car-brands")
    Call<GetBrandResponseJson> getCarBrands(
            @Header("Authorization") String authorization,
            @Query("name") String name
    );

    @GET("/api/mobil/car-types")
    Call<GetModelResponseJson> getCarTypes(
            @Header("Authorization") String authorization,
            @Query("brand_id") Long brandId,
            @Query("name") String name
    );

}
