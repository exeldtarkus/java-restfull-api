package co.id.adira.moservice.contentservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.interceptor.UserIdInterceptor;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.repository.content.VoucherRepository;
import co.id.adira.moservice.contentservice.service.RedeemService;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.event.dto.EmailEventDto;
import co.id.adira.moservice.model.User;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class VoucherController {
	
	private static final String REDEEM_HBS = "redeem.hbs";

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private RedeemService redeemService;
	
	@Autowired
	private UserIdInterceptor userIdInterceptor;

	@GetMapping(path = "/vouchers")
	public ResponseEntity<Object> getVouchers(
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(name = "user_id", required = false) final Long userId) {

		boolean isValidUser = userIdInterceptor.isValidUserId(userId.toString());
		if (!isValidUser) {
			return BaseResponse.jsonResponse(HttpStatus.FORBIDDEN, false, 
					"unknown user", null);
		}
		
		Date currentDate = new Date();
		Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "redeem_date"));
		List<Voucher> vouchers = voucherRepository.findAllUnusedVoucherAndMore(userId, currentDate, pageable);

		Integer start = Math.min(Math.max(size * (page - 1), 0), vouchers.size());
		Integer end = Math.min(Math.max(size * page, start), vouchers.size());
		Page<Voucher> pages = new PageImpl<Voucher>(vouchers.subList(start, end), pageable, vouchers.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/vouchers/{id}")
	public ResponseEntity<Object> getVoucherById(@PathVariable Long id) {
		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), id);
	}
	
	@PostMapping(path = "/vouchers/redeem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> generateQRCodeWithLogo(@RequestBody Voucher voucher) {
		
		log.info("::: GENERATE QRCODE :::");
		QRCode qrcode = redeemService.generateQRCodeAndSaveVoucher(voucher);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EmailEventDto emailEventDto = new EmailEventDto(); 
		emailEventDto.setEventId(UUID.randomUUID().toString());
		emailEventDto.setEventTimestamp(System.currentTimeMillis());
		emailEventDto.setSubject("Moservice - Redeem Promo");
		emailEventDto.setTemplate(REDEEM_HBS);
		emailEventDto.setBengkelName(voucher.getBengkel_name());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		emailEventDto.setAvailableUntil(sdf.format(voucher.getPromo().getAvailableUntil()));
		emailEventDto.setTo(authentication.getPrincipal().toString());
		emailEventDto.setQRCodeBase64(qrcode.getBase64QRCode());
		
		User user = new User();
		user.setId(voucher.getUserId());
		emailEventDto.setUser(user);
		
		redeemService.sendEmailNotifRedeem(emailEventDto);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), qrcode);
	}
	
}
