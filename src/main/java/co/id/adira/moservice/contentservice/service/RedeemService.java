/**
 * 
 */
package co.id.adira.moservice.contentservice.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import co.id.adira.moservice.contentservice.model.content.Promo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.repository.content.QRCodeRepository;
import co.id.adira.moservice.contentservice.repository.content.VoucherRepository;
import co.id.adira.moservice.contentservice.util.QRCodeUtil;
import co.id.adira.moservice.event.dto.EmailEventDto;

/**
 * @author fatchurrachman
 *
 */
@Service
public class RedeemService {
	
	@Value("${spring.base.upload.qrcode.url}")
	private String baseUploadQrcodeUrl;
	
	@Value("${spring.path.upload.qrcode}")
	private String pathUploadQrcode;
	
	@Value("${moservice.base.url.apps}")
	private String moserviceBaseUrlMoserviceApps;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private QRCodeRepository qrCodeRepository;
	
	@Autowired
	private KafkaTemplate<String, EmailEventDto> kafkaTemplate;

	@Autowired
	private QRCodeUtil qrCodeUtil;

	@Transactional(readOnly = false)
	public QRCode generateQRCodeAndSaveVoucher(
			Voucher voucher,
			Promo promo
	) {
		// OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		// Date date = Date.from(utc.toInstant());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		Date redeemDate = new Date(now.toInstant().toEpochMilli());
		Long transactionStatusId = new Long(2);

		StringBuilder data = new StringBuilder();
		data.append(moserviceBaseUrlMoserviceApps);
		data.append("promo_id=").append(promo.getId());
		data.append("&car_id=").append(voucher.getCarId());
		data.append("&user_id=").append(voucher.getUserId());
		data.append("&bengkel_id=").append(voucher.getBengkelId());
		// data.append("&redeem_date=").append(sdf.format(date));
		data.append("&redeem_date=").append(now);

		// [LOCAL TESTING PATH]
		// -----------------------------------------------------------------------
		// Path currentRelativePath = Paths.get("");
		// pathUploadQrcode = currentRelativePath.toAbsolutePath().toString();
		// System.out.printf("path qr = [%s]",pathUploadQrcode);
		// -----------------------------------------------------------------------

		QRCode qrcode = new QRCode();
		qrcode.setData(data.toString());
		qrcode.setQrcodePath(pathUploadQrcode);
		String[] response = qrCodeUtil.generateQRCodeWithLogo(qrcode, voucher);
		qrcode.setQrcodePath(baseUploadQrcodeUrl + "/sp/qrcode/" + response[0] + ".png");
		qrcode.setQrcodePath2("/qr/" + response[0]);
		qrcode.setBase64QRCode(response[1]);
		qrcode.setCreatedAt(new Date());
		qrcode.setPromoId(promo.getId());
		qrcode.setUserId(voucher.getUserId());
		qrcode.setBengkelId(voucher.getBengkelId());

		qrCodeRepository.save(qrcode);
		voucherRepository.insertVoucher(
				voucher.getBengkelId(),
				voucher.getBookingId(),
				voucher.getCarId(),
				new Date(),
				promo,
				qrcode,
				redeemDate,
				null,
				null,
				voucher.getUserId(),
				voucher.getUtm(),
				transactionStatusId,
				voucher.getPaymentStatus(),
				voucher.getPaymentId(),
				voucher.getPaymentExpiredAt()
		);

		return qrcode;
	}
	
	public void sendEmailNotifRedeem(EmailEventDto emailEventDto) {
		try {
			kafkaTemplate.send("email-event-topic", emailEventDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
