package co.id.adira.moservice.contentservice.repository.bengkel;

import org.springframework.data.jpa.repository.JpaRepository;

import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;

public interface BengkelRepository extends JpaRepository <Bengkel, Long> {
  
}
