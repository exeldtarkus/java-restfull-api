/**
 * 
 */
package co.id.adira.moservice.contentservice.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
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
	
	@Transactional(readOnly = false)
	public QRCode generateQRCodeAndSaveVoucher(Voucher voucher) {
		// OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
		// Date date = Date.from(utc.toInstant());
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		Date redeemDate = new Date(now.toInstant().toEpochMilli());
		
		StringBuilder data = new StringBuilder();
		data.append(moserviceBaseUrlMoserviceApps);
		data.append("promo_id=").append(voucher.getPromo().getId());
		data.append("&car_id=").append(voucher.getCarId());
		data.append("&user_id=").append(voucher.getUserId());
		data.append("&bengkel_id=").append(voucher.getBengkelId());
		// data.append("&redeem_date=").append(sdf.format(date));
		data.append("&redeem_date=").append(now);
		
		QRCode qrcode = new QRCode();
		qrcode.setData(data.toString());
		qrcode.setQrcodePath(pathUploadQrcode);
		String[] response = QRCodeUtil.generateQRCodeWithLogo(qrcode, voucher);
		qrcode.setQrcodePath(baseUploadQrcodeUrl + "/sp/qrcode/" + response[0] + ".png");
		//qrcode.setBase64QRCode(response[1]);
		qrcode.setCreatedAt(new Date());
		qrcode.setPromoId(voucher.getPromo().getId());
		qrcode.setUserId(voucher.getUserId());
		
		qrCodeRepository.save(qrcode);
		voucherRepository.insertVoucher(voucher.getBengkelId(), voucher.getBookingId(), voucher.getCarId(), 
				new Date(), voucher.getPromo(), qrcode, redeemDate, null, null, voucher.getUserId());
		
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
