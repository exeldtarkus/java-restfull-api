package co.id.adira.moservice.contentservice.repository.content;

import co.id.adira.moservice.contentservice.model.content.BlastPromoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlastPromoDetailRepository extends JpaRepository<BlastPromoDetail, Long> {
    List<BlastPromoDetail> findAllByTrBlastPromoIdOrderByIdAsc(Long trBlastPromoId);
}