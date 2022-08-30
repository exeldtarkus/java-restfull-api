package co.id.adira.moservice.contentservice.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

import co.id.adira.moservice.contentservice.handler.adiraku.activity.AdirakuActivityCreateActivityHandler;
import co.id.adira.moservice.contentservice.handler.payment.PaymentServiceHandler;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityPassParamJson;
import co.id.adira.moservice.contentservice.json.adiraku.activity.AdirakuMsActivityCreateActivityProspectJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoDataResponseJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoPromoJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.UpdateVoucherPaymentStatusJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceDataResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceItemJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceJson;
import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.model.content.VoucherPlain;
import co.id.adira.moservice.contentservice.repository.content.PromoRepository;
import co.id.adira.moservice.contentservice.repository.content.VoucherCustomRepository;
import co.id.adira.moservice.contentservice.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import co.id.adira.moservice.contentservice.interceptor.UserIdInterceptor;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.model.content.Voucher;
import co.id.adira.moservice.contentservice.repository.bengkel.CityRepository;
import co.id.adira.moservice.contentservice.repository.content.VoucherRepository;
import co.id.adira.moservice.contentservice.service.RedeemService;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.util.CloudinaryUtil;
import co.id.adira.moservice.contentservice.util.StatusPaymentUtil;
import co.id.adira.moservice.event.dto.EmailEventDto;
import co.id.adira.moservice.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@RestController
@Slf4j
@RequestMapping("/api")
public class VoucherController {

	private static final String REDEEM_HBS = "redeem.hbs";

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private VoucherCustomRepository voucherCustomRepository;

	@Autowired
	private PromoRepository promoRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private RedeemService redeemService;

	@Autowired
	private UserIdInterceptor userIdInterceptor;

	@Autowired
	private KafkaTemplate<String, Long> kafkaTemplateNotif;

	@Autowired
	private CloudinaryUtil cloudinaryUtil;

	@Autowired
	private PaymentServiceHandler paymentServiceHandler;

	@Autowired
	private DateUtil dateUtil;

	@Autowired
	private StatusPaymentUtil statusPaymentUtil;

	@Value("${server.auth.secret_key}")
	private String key;

	@Autowired
	private AdirakuActivityCreateActivityHandler adirakuActivityCreateActivityHandler;

	@GetMapping(path = "/vouchers")
	public ResponseEntity<Object> getVouchers(
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(name = "utm", required = false, defaultValue = "") final String utmParam,
			@RequestParam(name = "utm_not_in", required = false) List<String> utmNotInParam,
			@RequestParam(name = "user_id", required = false) final Long userId) {

		String cloudinaryPath = cloudinaryUtil.getCloudinaryUrlPath() + cloudinaryUtil.getCloudinaryMainFolder();
		String utm = null;

    List<Voucher> fecthVoucher = new ArrayList<>();
    List<Voucher> voucherData = new ArrayList<>();

		Date currentDate = new Date();

		if (!utmParam.equals("")) {
			utm = utmParam;
		}

		List<String> utmNotIn = null;
		if ((utmNotInParam != null) && (utmNotInParam.size() > 0)) {
			utmNotIn = utmNotInParam;
		}

		List<String> utmIn = null;
		if (utm != null) {
			utmIn = new ArrayList<>();
			if (utm.equals("adiraku-utm") || utm.equals("adiraku") || utm.equals("adirakupayment")) {
				utmIn.add("adiraku");
				utmIn.add("adirakupayment");
			} else {
				utmIn.add(utm);
			}
		}

		boolean isValidUser = userIdInterceptor.isValidUserId(userId.toString());
		if (!isValidUser) {
			return BaseResponse.jsonResponse(HttpStatus.FORBIDDEN, false,
					"unknown user", null);
		}

		Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.DESC, "redeem_date"));

    System.out.println("utm-data :"+utm);
    fecthVoucher = voucherRepository.findAllUnusedVoucherAndMore(userId, currentDate, utm, utmIn, utmNotIn, pageable);
    for (Voucher voucher : fecthVoucher) {
      try {
        String city = cityRepository.findCityNameByBengkelId(voucher.getBengkelId());
        String voucherStatusPayment = statusPaymentUtil.voucherStatusPayment(voucher);

        if (voucherStatusPayment.equals("VoucherDateCompare>7day")) {
          continue;
        }
        voucher.setCityName(city);
        voucher.getQr().setQrcodePath(cloudinaryPath + voucher.getQr().getQrcodePath2());
        voucher.getPromo().setImagePath(cloudinaryPath + voucher.getPromo().getImagePath2());
        voucher.getPromo().setImagePathMobile(cloudinaryPath + voucher.getPromo().getImagePath2());
        voucher.setStatusVoucherPayment(statusPaymentUtil.voucherStatusPayment(voucher));

      } catch (Exception e) {
        System.out.printf("Error voucher_id : [%d]", voucher.getId());
        System.out.println(e);
        continue;
      }
      System.out.println("total-data :"+voucherData.size());
      voucherData.add(voucher);
    }

		Integer start = Math.min(Math.max(size * (page - 1), 0), voucherData.size());
		Integer end = Math.min(Math.max(size * page, start), voucherData.size());
		Page<Voucher> pages = new PageImpl<Voucher>(voucherData.subList(start, end), pageable, voucherData.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/vouchers/{id}")
	public ResponseEntity<Object> getVoucherById(@PathVariable Long id) {
		Long userId = userIdInterceptor.getUserId();

		Optional<VoucherPlain> voucherOptional = voucherCustomRepository.findByIdAndUserId(id, userId);

		if (!voucherOptional.isPresent()) {
			return BaseResponse.jsonResponse(HttpStatus.NOT_FOUND, true, HttpStatus.NOT_FOUND.toString(), "Voucher not found");
		}

		VoucherPlain voucher = voucherOptional.get();

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), voucher);
	}

	@PostMapping(path = "/vouchers/redeem", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> generateQRCodeWithLogo(@RequestBody RedeemPromoJson redeemPromoJson) throws ParseException {

		Voucher voucher = new Voucher();
		voucher.setBengkelId(redeemPromoJson.getBengkelId());
		voucher.setCarId(redeemPromoJson.getCarId());
		voucher.setUtm(redeemPromoJson.getUtm());
		Long promoId = redeemPromoJson.getPromo().getId();
		Optional<Promo> promoOptional = promoRepository.findById(promoId);
		Long paymentAmount = redeemPromoJson.getPaymentAmount();
		String paymentId = null;

		// Get Temporary ID

		VoucherPlain voucherPlain = new VoucherPlain();
		voucherPlain.setBengkelId(redeemPromoJson.getBengkelId());
		voucherPlain.setCarId(redeemPromoJson.getCarId());
		voucherPlain.setUtm(redeemPromoJson.getUtm());
		voucherPlain.setPaymentStatus("FREE");

		voucherPlain.setAdirakuAccountId(redeemPromoJson.getAdirakuAccountId());
		voucherPlain.setMobileNo(redeemPromoJson.getMobileNo());

		VoucherPlain voucherPlain2 = voucherCustomRepository.saveAndFlush(voucherPlain);

		voucher.setBengkel_name(redeemPromoJson.getBengkel_name());
		voucher.setUserId(redeemPromoJson.getUserId());

		if (!promoOptional.isPresent()) {
			return BaseResponse.jsonResponse(HttpStatus.BAD_REQUEST, true, HttpStatus.BAD_REQUEST.toString(), "Promo not found");
		}

		Promo promo = promoOptional.get();
		voucher.setPromo(promo);

		log.info("::: GENERATE QRCODE :::");
    	String cloudinaryPath = cloudinaryUtil.getCloudinaryUrlPath() + cloudinaryUtil.getCloudinaryMainFolder();

		BigDecimal adminFee = BigDecimal.valueOf(6000);
		BigDecimal price = promo.getPrice() != null ? promo.getPrice() : BigDecimal.valueOf(0);
		voucher.setPaymentStatus("FREE");
		voucher.setPaymentExpiredAt(null);
		voucher.setPaymentId(null);

		SimpleDateFormat mysqlDatetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (price.longValue() > 0) {
			if (paymentAmount == null) {
				return BaseResponse.jsonResponse(HttpStatus.BAD_REQUEST, true, HttpStatus.BAD_REQUEST.toString(), "Payment amount is null");
			}

			Date promoAvailableUntil = promo.getAvailableUntil();
			Date now = new Date();
			long differenceDays = dateUtil.getDifferenceDays(now, promoAvailableUntil);

			if (differenceDays <= 0) {
				return BaseResponse.jsonResponse(
						HttpStatus.BAD_REQUEST,
						true,
						HttpStatus.BAD_REQUEST.toString(),
						"Promo is unavailable to buy");
			}

			Long totalPrice = price.longValue() + adminFee.longValue();

			if (paymentAmount.longValue() != totalPrice.longValue()) {
				log.info("PaymentAmount: " + paymentAmount);
				log.info("TotalPrice: " + totalPrice);
				return BaseResponse.jsonResponse(HttpStatus.BAD_REQUEST, true, HttpStatus.BAD_REQUEST.toString(), "Payment amount mismatch");
			}

			PaymentSendInvoiceJson paymentSendInvoiceJson = new PaymentSendInvoiceJson();

			paymentSendInvoiceJson.setPayment_method_id(redeemPromoJson.getPaymentMethodId());
			paymentSendInvoiceJson.setAmount(totalPrice);
			paymentSendInvoiceJson.setBengkel_id(redeemPromoJson.getBengkelId());
			paymentSendInvoiceJson.setPromo_id(promoId);
			paymentSendInvoiceJson.setVoucher_id(voucherPlain2.getId());
			paymentSendInvoiceJson.setCustomer_id(redeemPromoJson.getUserId());

			PaymentSendInvoiceItemJson paymentSendInvoiceItemJson = new PaymentSendInvoiceItemJson();
			paymentSendInvoiceItemJson.setAmount(price.longValue());
			paymentSendInvoiceItemJson.setQty(1L);
			paymentSendInvoiceItemJson.setTitle(promo.getTitle());
			paymentSendInvoiceItemJson.setPrice(price.longValue());

			List<PaymentSendInvoiceItemJson> items = new ArrayList<>();
			items.add(paymentSendInvoiceItemJson);
			paymentSendInvoiceJson.setItems(items);

			PaymentSendInvoiceDataResponseJson paymentSendInvoiceDataResponseJson = paymentServiceHandler.sendInvoice(paymentSendInvoiceJson);

			if (paymentSendInvoiceDataResponseJson == null) {
				return BaseResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Payment fail");
			}

			voucher.setPaymentStatus("PENDING");
			Date expiredAt = mysqlDatetimeFormat.parse(paymentSendInvoiceDataResponseJson.getVa_expired_at());
			voucher.setPaymentExpiredAt(expiredAt);
			voucher.setPaymentId(paymentSendInvoiceDataResponseJson.getId());
			paymentId = paymentSendInvoiceDataResponseJson.getId();
		}

		QRCode qrcode = redeemService.generateQRCodeAndSaveVoucher(
				voucher,
				voucherPlain2,
				promo
		);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	qrcode.setQrcodePath(cloudinaryPath + qrcode.getQrcodePath2());

		String credential = authentication.getPrincipal().toString();
		if (isEmail(credential)) {
			EmailEventDto emailEventDto = new EmailEventDto();
			emailEventDto.setEventId(UUID.randomUUID().toString());
			emailEventDto.setEventTimestamp(System.currentTimeMillis());
			emailEventDto.setSubject("Moservice - Redeem Promo");
			emailEventDto.setTemplate(REDEEM_HBS);
			emailEventDto.setBengkelName(voucher.getBengkel_name());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			emailEventDto.setAvailableUntil(sdf.format(voucher.getPromo().getAvailableUntil()));
			emailEventDto.setTo(credential);
			emailEventDto.setQrCodePath(qrcode.getQrcodePath());

			User user = new User();
			user.setId(voucher.getUserId());
			emailEventDto.setUser(user);

			redeemService.sendEmailNotifRedeem(emailEventDto);
		} else {
			log.info("Redeem using phone number");
		}

		this.sendNotifRedeem(qrcode.getId());

		// Set response data

		RedeemPromoDataResponseJson redeemPromoDataResponseJson = new RedeemPromoDataResponseJson();
		redeemPromoDataResponseJson.setData(qrcode.getData());
		RedeemPromoPromoJson redeemPromoPromoJson = new RedeemPromoPromoJson();
		redeemPromoPromoJson.setAvailableUntil(mysqlDatetimeFormat.format(promo.getAvailableUntil()));
		redeemPromoPromoJson.setId(promo.getId());
		redeemPromoDataResponseJson.setId(voucherPlain2.getId());
		redeemPromoDataResponseJson.setPromo(redeemPromoPromoJson);
		redeemPromoDataResponseJson.setPromoId(promo.getId());
		redeemPromoDataResponseJson.setBase64QRCode(qrcode.getBase64QRCode());
		redeemPromoDataResponseJson.setBookingId(qrcode.getBookingId());
		redeemPromoDataResponseJson.setBengkelId(qrcode.getBengkelId());
		redeemPromoDataResponseJson.setQrcodePath(qrcode.getQrcodePath());
		redeemPromoDataResponseJson.setQrcodePath2(qrcode.getQrcodePath2());
		redeemPromoDataResponseJson.setCustAcction(qrcode.getCustAcction());
		redeemPromoDataResponseJson.setCreatedAt(mysqlDatetimeFormat.format(qrcode.getCreatedAt()));
		redeemPromoDataResponseJson.setUpdatedAt(null);
		redeemPromoDataResponseJson.setPaymentId(paymentId);
		redeemPromoDataResponseJson.setUserId(qrcode.getUserId());

		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), redeemPromoDataResponseJson);
	}

	public void sendNotifRedeem(Long qrId){
		try {
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::: ");
			System.out.println(":::: SEND REDEEM PROMO APPS NOTIF TO KAFKA BROKER :::: ");
			System.out.println(qrId);
			System.out.println(":::::::::::::::::::::::::::::::::::::::::::: ");
			kafkaTemplateNotif.send("notify-redeem-topic", qrId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

    @PutMapping(path = "/vouchers/{id}")
    public ResponseEntity<Object> updateVoucherStatus(
            @PathVariable Long id,
            @RequestBody UpdateVoucherPaymentStatusJson updateVoucherPaymentStatusJson,
            @RequestHeader(name = "X-TOKEN", required = true) final String authToken
    ) {
        if (!authToken.equals(key)) {
            return BaseResponse.jsonResponse(HttpStatus.UNAUTHORIZED, false, HttpStatus.UNAUTHORIZED.toString(), null);
        }

        Optional<VoucherPlain> voucherOptional = voucherCustomRepository.findById(id);

        if (!voucherOptional.isPresent()) {
            return BaseResponse.jsonResponse(HttpStatus.BAD_REQUEST, true, HttpStatus.BAD_REQUEST.toString(), "Voucher not found");
        }

        VoucherPlain voucher = voucherOptional.get();

        voucher.setPaymentStatus(updateVoucherPaymentStatusJson.getPayment_status());
        voucherCustomRepository.save(voucher);

        if (voucher.getUtm().equals("adirakupayment") || voucher.getUtm().equals("adiraku")) {
            System.out.println("Send notification to adiraku");

			Promo promo = voucher.getPromo();

			String group = "Pembelian Promo Moservice";
			String subGroup = "Pembelian Promo Moservice";
			String title = "Pembayaran Berhasil";
			String content = "Selamat! Pembayaran voucher " + promo.getTitle() + " berhasil. Segera bawa kendaraanmu ke bengkel untuk diservis.";
			String linkTo = "MoserviceDetailVoucher";

			AdirakuMsActivityCreateActivityPassParamJson passParam = new AdirakuMsActivityCreateActivityPassParamJson();
			passParam.setId(voucher.getId());
			passParam.setType("my-voucher");

			// nasabah
            if (!voucher.getAdirakuAccountId().isEmpty()) {
				System.out.println("Send Notif Nasabah");
				AdirakuMsActivityCreateActivityJson nasabahPayload = new AdirakuMsActivityCreateActivityJson();
				nasabahPayload.setGroup(group);
				nasabahPayload.setSubGroup(subGroup);
				nasabahPayload.setTitle(title);
				nasabahPayload.setContent(content);
                nasabahPayload.setAccountId(voucher.getAdirakuAccountId());
				nasabahPayload.setLinkTo(linkTo);
				nasabahPayload.setPassParam(passParam);
				adirakuActivityCreateActivityHandler.createActivity(
						nasabahPayload,
						null
				);
            } else {
				System.out.println("Send Notif Prospect");
				AdirakuMsActivityCreateActivityProspectJson prospectPayload = new AdirakuMsActivityCreateActivityProspectJson();
				prospectPayload.setGroup(group);
				prospectPayload.setSubGroup(subGroup);
				prospectPayload.setTitle(title);
				prospectPayload.setContent(content);
				prospectPayload.setMobile_no(voucher.getMobileNo());
				prospectPayload.setLinkTo(linkTo);
				prospectPayload.setPassParam(passParam);
				adirakuActivityCreateActivityHandler.createActivity(
						null,
						prospectPayload
				);
            }

        }

        return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), voucher);
    }

}
