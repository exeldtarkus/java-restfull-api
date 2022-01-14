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
    private UserServiceHandler userServiceHandler;

    @Autowired
    private AuthServiceHandler authServiceHandler;

    @Autowired
    private MobilServiceHandler mobilServiceHandler;

    @Autowired
    private ContentServiceHandler contentServiceHandler;

    @Autowired
    private PromoRepository promoRepository;

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

        Optional<Promo> promo = promoRepository.findById(blastPromo.get().getPromoId());

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String availableUntil = formatter.format(promo.get().getAvailableUntil());

        Long defaultBrandId = null;
        for (BlastPromoDetail row : blastPromoDetailList) {

            System.out.println("############################################################");
            System.out.println(row.getId());
            System.out.println(row.getPhoneNumber());
            System.out.println(row.getCustomerName());
            System.out.println(row.getMobilBrand());

            Long brandId = null;
            Long modelId = null;

            Boolean forceRegisterStatus = userServiceHandler.forceRegister(row.getCustomerName(), row.getPhoneNumber());
            if (!forceRegisterStatus) {
                throw new Exception("forceRegister Fail");
            }
            GetTokenByPhoneNumberResponseJson getTokenResponse = authServiceHandler.getTokenByPhoneNumber(row.getPhoneNumber());
            System.out.println("############################################################");
            String token = getTokenResponse.getData().getAccess_token();
            Long userId = getTokenResponse.getData().getUser_id();

            System.out.println(token);
            System.out.println(userId);

            if (defaultBrandId == null) {
                defaultBrandId = mobilServiceHandler.getBrandId(token, null);
                System.out.println(defaultBrandId);
            }
            System.out.println("defaultBrandId");
            System.out.println(defaultBrandId);

            brandId = mobilServiceHandler.getBrandId(token, row.getMobilBrand());

            if (brandId == null) {
                brandId = defaultBrandId;
            }

            System.out.println("brandId");
            System.out.println(brandId);

            modelId = mobilServiceHandler.getModelId(token, brandId, row.getMobilModel());

            if (modelId == null) {
                modelId = mobilServiceHandler.getModelId(token, brandId, null);
            }

            System.out.println("modelId");
            System.out.println(modelId);

            Long mobilId = userServiceHandler.getMobilId(
                    token, userId, row.getMobilPlateNo(), brandId, modelId
            );

            System.out.println("mobilId");
            System.out.println(mobilId);

            if (mobilId == null) {
                // create mobil
                mobilId = userServiceHandler.createUserCar(
                        token, userId, row.getMobilPlateNo(), brandId, modelId, null, null
                );

                System.out.println("createUserCar");
                System.out.println(mobilId);
            }

            System.out.println(promo.get().getAvailableUntil().toString());

            Boolean redeemStatus = contentServiceHandler.redeemPromo(
                    token,
                    userId,
                    mobilId,
                    blastPromo.get().getPromoId(),
                    blastPromo.get().getBengkelId(),
                    availableUntil
            );

            if (!redeemStatus) {
                throw new Exception("redeem Fail");
            }

        }

    }

}
