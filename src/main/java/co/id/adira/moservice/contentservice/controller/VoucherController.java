package co.id.adira.moservice.contentservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.repository.content.QRCodeRepository;
import co.id.adira.moservice.contentservice.repository.content.VoucherRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class VoucherController {

	@Autowired
	private VoucherRepository voucherRepository;
	
	//private final static String URL = "https://assets.adira.one/sp/qrcode/";
	
	@Value("${spring.base.upload.qrcode.url}")
	private String baseUploadQrcodeUrl;
	
	@Value("${spring.path.upload.qrcode}")
	private String pathUploadQrcode;
	
	@Value("${moservice.base.url.apps}")
	private String moserviceBaseUrlMoserviceApps;

	@Autowired
	private QRCodeRepository qrCodeRepository;

	@GetMapping(path = "/vouchers")
	public ResponseEntity<Object> getVouchers(
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(name = "user_id", required = false) final Long userId) {

		Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "redeem_date"));
		List<Voucher> vouchers = voucherRepository.findAllUnusedVoucherAndMore(userId, pageable);

		Integer start = Math.min(Math.max(size * (page - 1), 0), vouchers.size());
		Integer end = Math.min(Math.max(size * page, start), vouchers.size());
		Page<Voucher> pages = new PageImpl<Voucher>(vouchers.subList(start, end), pageable, vouchers.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/vouchers/{id}")
	public ResponseEntity<Object> getVoucherById(@PathVariable Long id) {
		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), id);
	}
	
	@PostMapping(path = "/voucher/redeem")
	public ResponseEntity<Object> generateQRCodeWithLogo(@RequestBody Voucher voucher) {
		
		log.info("=== SAVE VOUCHER ===");
		voucher.setCreated(new Date());
		voucher.setRedeemDate(new Date());
		voucherRepository.save(voucher);
		
		StringBuilder data = new StringBuilder();
		data.append(moserviceBaseUrlMoserviceApps);
		data.append("promo_id=").append(voucher.getPromoId());
		data.append("&car_id=").append(voucher.getCarId());
		data.append("&user_id=").append(voucher.getUserId());
		data.append("&bengkel_id=").append(voucher.getBengkelId());
		data.append("&redeem_date=").append(new Date());
		
		QRCode qrcode = new QRCode();
		qrcode.setData(data.toString());
		qrcode.setQrcodePath(pathUploadQrcode);
		String[] response = QRCodeUtil.generateQRCodeWithLogo(qrcode);
		qrcode.setQrcodePath(baseUploadQrcodeUrl + "/sp/qrcode/" + response[0] + ".png");
		qrcode.setBase64QRCode(response[1]);
		qrcode.setCreatedAt(new Date());
		qrcode.setPromoId(voucher.getPromoId());
		qrcode.setUserId(voucher.getUserId());
		
		log.info("=== SAVE QRCODE ===");
		qrCodeRepository.save(qrcode);
		
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), qrcode);
	}
	
}