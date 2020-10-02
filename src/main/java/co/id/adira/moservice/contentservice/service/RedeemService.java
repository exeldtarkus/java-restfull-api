/**
 * 
 */
package co.id.adira.moservice.contentservice.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.repository.content.QRCodeRepository;
import co.id.adira.moservice.contentservice.repository.content.VoucherRepository;
import co.id.adira.moservice.contentservice.util.QRCodeUtil;

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
	
	@Transactional(readOnly = false)
	public QRCode generateQRCodeAndSaveVoucher(Voucher voucher) {
		
		StringBuilder data = new StringBuilder();
		data.append(moserviceBaseUrlMoserviceApps);
		data.append("promo_id=").append(voucher.getPromo().getId());
		data.append("&car_id=").append(voucher.getCarId());
		data.append("&user_id=").append(voucher.getUserId());
		data.append("&bengkel_id=").append(voucher.getBengkelId());
		data.append("&redeem_date=").append(new Date());
		
		QRCode qrcode = new QRCode();
		qrcode.setData(data.toString());
		qrcode.setQrcodePath(pathUploadQrcode);
		String[] response = QRCodeUtil.generateQRCodeWithLogo(qrcode);
		qrcode.setQrcodePath(baseUploadQrcodeUrl + "/sp/qrcode/" + response[0] + ".png");
		//qrcode.setBase64QRCode(response[1]);
		qrcode.setCreatedAt(new Date());
		qrcode.setPromoId(voucher.getPromo().getId());
		qrcode.setUserId(voucher.getUserId());
		
		qrCodeRepository.save(qrcode);
		voucherRepository.insertVoucher(voucher.getBengkelId(), voucher.getBookingId(), voucher.getCarId(), 
				new Date(), voucher.getPromo(), qrcode, new Date(), null, null, voucher.getUserId());
		
		return qrcode;
	}

}
