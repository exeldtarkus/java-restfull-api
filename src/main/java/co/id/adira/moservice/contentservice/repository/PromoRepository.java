package co.id.adira.moservice.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.id.adira.moservice.contentservice.model.Promo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromoRepository extends JpaRepository<Promo, Long> {
  Page<Promo> findAllByZoneId(Pageable pageable, Long zoneId);
}