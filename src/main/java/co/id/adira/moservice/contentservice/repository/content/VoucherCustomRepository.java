package co.id.adira.moservice.contentservice.repository.content;

import co.id.adira.moservice.contentservice.model.content.VoucherPlain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherCustomRepository extends JpaRepository<VoucherPlain, Long> {}