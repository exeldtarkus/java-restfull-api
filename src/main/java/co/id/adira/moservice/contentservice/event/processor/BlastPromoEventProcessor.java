package co.id.adira.moservice.contentservice.event.processor;

import co.id.adira.moservice.contentservice.handler.content.ContentServiceHandler;
import co.id.adira.moservice.contentservice.handler.mobil.MobilServiceHandler;
import co.id.adira.moservice.contentservice.handler.user.UserServiceHandler;
import co.id.adira.moservice.contentservice.handler.auth.AuthServiceHandler;
import co.id.adira.moservice.contentservice.json.auth.get_token.GetTokenByPhoneNumberResponseJson;
import co.id.adira.moservice.contentservice.model.content.BlastPromo;
import co.id.adira.moservice.contentservice.model.content.BlastPromoDetail;
import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.repository.content.BlastPromoDetailRepository;
import co.id.adira.moservice.contentservice.repository.content.BlastPromoRepository;
import co.id.adira.moservice.contentservice.repository.content.PromoRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private UserServiceHandler userServiceHandler;

    @Autowired
    private AuthServiceHandler authServiceHandler;

    @Autowired
    private MobilServiceHandler mobilServiceHandler;

    @Autowired
    private ContentServiceHandler contentServiceHandler;

    @Autowired
    private PromoRepository promoRepository;

    // @KafkaListener(topics = {"moservice-blast-promo"})
    // public void processs(@Payload String payload) throws Exception {
    //     System.out.println("############################################################");
    //     System.out.println("Receive Payload Blast Promo :: " + payload);
    //     System.out.println("############################################################");

    //     JSONObject obj = new JSONObject(payload);
    //     Long trBlastPromoId = obj.getLong("tr_blast_promo_id");

    //     BlastPromo blastPromo = blastPromoRepository.findById(trBlastPromoId).get();
    //     List<BlastPromoDetail> blastPromoDetailList = blastPromoDetailRepository.findAllByTrBlastPromoIdOrderByIdAsc(blastPromo.getId());

    //     Promo promo = promoRepository.findById(blastPromo.getPromoId()).get();

    //     DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //     String availableUntil = formatter.format(promo.getAvailableUntil());

    //     Long defaultBrandId = null;
    //     for (BlastPromoDetail row : blastPromoDetailList) {

    //         Long brandId = null;
    //         Long modelId = null;

    //         Boolean forceRegisterStatus = userServiceHandler.forceRegister(row.getCustomerName(), row.getPhoneNumber());
    //         if (!forceRegisterStatus) {
    //             throw new Exception("Force register fail!");
    //         }
    //         GetTokenByPhoneNumberResponseJson getTokenResponse = authServiceHandler.getTokenByPhoneNumber(row.getPhoneNumber());
    //         String token = getTokenResponse.getData().getAccess_token();
    //         Long userId = getTokenResponse.getData().getUser_id();

    //         if (defaultBrandId == null) {
    //             defaultBrandId = mobilServiceHandler.getBrandId(token, null);
    //         }

    //         brandId = mobilServiceHandler.getBrandId(token, row.getMobilBrand());

    //         if (brandId == null) {
    //             brandId = defaultBrandId;
    //         }

    //         modelId = mobilServiceHandler.getModelId(token, brandId, row.getMobilModel());

    //         if (modelId == null) {
    //             modelId = mobilServiceHandler.getModelId(token, brandId, null);
    //         }

    //         Long mobilId = userServiceHandler.getMobilId(
    //                 token, userId, row.getMobilPlateNo(), brandId, modelId
    //         );

    //         if (mobilId == null) {
    //             mobilId = userServiceHandler.createUserCar(
    //                     token, userId, row.getMobilPlateNo(), brandId, modelId, null, null
    //             );
    //         }

    //         Long trPromoUserId = contentServiceHandler.redeemPromo(
    //                 token,
    //                 userId,
    //                 mobilId,
    //                 blastPromo.getPromoId(),
    //                 blastPromo.getBengkelId(),
    //                 availableUntil
    //         );

    //         if (trPromoUserId == null) {
    //             throw new Exception("Redeem fail!");
    //         }

    //         row.setTrPromoUserId(trPromoUserId);
    //         blastPromoDetailRepository.save(row);

    //         System.out.println("Tr promo user id :: " + trPromoUserId);
    //     }

    // }

}
