package co.id.adira.moservice.contentservice.repository.bengkel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.id.adira.moservice.contentservice.dto.bengkel.ProvinceCityDTO;
import co.id.adira.moservice.contentservice.model.bengkel.City;

import java.util.List;

public interface CityRepository extends JpaRepository <City, Long> {
    @Query(value= "SELECT c.city_id as cityId, "
            + "p.provinsi_name as provinceName, "
            + "c.city_name as cityName, "
            + "p.provinsi_id as provinceId "
            + "FROM ref_city c JOIN ref_provinsi p ON c.provinsi_id = p.provinsi_id "
            + "JOIN content.map_promo_area m ON m.city_id = c.city_id "
            + "WHERE m.promo_id = :promo_id "
            + "GROUP BY c.city_id; "
            , nativeQuery = true)
    List<ProvinceCityDTO>findAllCitiesByPromoId(@Param("promo_id") Long promo_id);


}
