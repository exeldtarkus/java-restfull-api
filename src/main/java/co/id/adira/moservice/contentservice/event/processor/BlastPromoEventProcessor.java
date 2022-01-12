package co.id.adira.moservice.contentservice.event.processor;

import co.id.adira.moservice.contentservice.handler.user.ForceRegisterHandler;
import co.id.adira.moservice.contentservice.handler.user.GetTokenHandler;
import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberResponseJson;
import co.id.adira.moservice.contentservice.model.content.BlastPromo;
import co.id.adira.moservice.contentservice.model.content.BlastPromoDetail;
import co.id.adira.moservice.contentservice.repository.content.BlastPromoDetailRepository;
import co.id.adira.moservice.contentservice.repository.content.BlastPromoRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
@RefreshScope
public class BlastPromoEventProcessor {

    @Autowired
    private BlastPromoRepository blastPromoRepository;

    @Autowired
    private BlastPromoDetailRepository blastPromoDetailRepository;

    @Autowired
    private ForceRegisterHandler forceRegisterHandler;

    @Autowired
    private GetTokenHandler getTokenHandler;

    @KafkaListener(topics = {"moservice-blast-promo"})
    public void processs(@Payload String payload) throws Exception {
        System.out.println("############################################################");
        System.out.println("Receive Payload Blast Promo :: " + payload);
        System.out.println("############################################################");

        JSONObject obj = new JSONObject(payload);
        Long trBlastPromoId = obj.getLong("tr_blast_promo_id");

        Optional<BlastPromo> blastPromo = blastPromoRepository.findById(trBlastPromoId);
        System.out.println("############################################################");
        System.out.println(blastPromo.get().getPromoId());
        System.out.println(blastPromo.get().getId());
        List<BlastPromoDetail> blastPromoDetailList = blastPromoDetailRepository.findAllByTrBlastPromoIdOrderByIdAsc(blastPromo.get().getId());

        System.out.println("############################################################");

        for (BlastPromoDetail row : blastPromoDetailList) {
            System.out.println("############################################################");
            System.out.println(row.getId());
            System.out.println(row.getPhoneNumber());
            System.out.println(row.getCustomerName());
            System.out.println(row.getMobilBrand());
            Boolean forceRegisterStatus = forceRegisterHandler.exec(row.getCustomerName(), row.getPhoneNumber());
            if (!forceRegisterStatus) {
                throw new Exception("forceRegister Fail");
            }
            GetTokenByPhoneNumberResponseJson getTokenResponse = getTokenHandler.exec(row.getPhoneNumber());
            System.out.println("############################################################");
            String token = getTokenResponse.getData().getAccess_token();
            Long userId = getTokenResponse.getData().getUser_id();
            System.out.println(token);
            System.out.println(userId);
        }

    }

}
