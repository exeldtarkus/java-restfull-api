package co.id.adira.moservice.contentservice.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.adira.moservice.contentservice.model.content.Voucher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusPaymentUtil {

	@Autowired
	private DateUtil dateUtil;

	public String voucherStatusPayment(Voucher voucher) {
    String statusPayment = "";

		Date currentDate = new Date();
    Date availableUntil = voucher.getPromo().getAvailableUntil();
    long availableUntilDiffDays = dateUtil.getDifferenceDays(currentDate, availableUntil);
    Boolean isVoucherExpired = availableUntilDiffDays < 0;
    long check7DaysDiff ;
    
    if (isVoucherExpired) {
      statusPayment = "Kedaluwarsa";
      check7DaysDiff = dateUtil.getDifferenceDays(currentDate, availableUntil);
      if (check7DaysDiff < (-7)) {
        statusPayment = "VoucherDateCompare>7day";
      }
    } else {
      switch(voucher.getPaymentStatus().toString()) {
        case "PAID":
            if (voucher.getUseDate() != null) {
              statusPayment = "Sudah Digunakan";
              check7DaysDiff = dateUtil.getDifferenceDays(currentDate, voucher.getUseDate());
              if (check7DaysDiff < (-7)) {
                statusPayment = "VoucherDateCompare>7day";
              }
            } else {
              statusPayment = "Belum Digunakan";
            }
            break;
        case "PENDING":
            statusPayment = "Menunggu Pembayaran";
            break;
        case "FAILED":
            statusPayment = "Dibatalkan";
            check7DaysDiff = dateUtil.getDifferenceDays(currentDate, voucher.getPaymentExpiredAt());
            if (check7DaysDiff < (-7)) {
              statusPayment = "VoucherDateCompare>7day";
            }
            break;
        case "FREE":
            if (voucher.getUseDate() != null) {
              statusPayment = "Sudah Digunakan";
              check7DaysDiff = dateUtil.getDifferenceDays(currentDate, voucher.getUseDate());
              if (check7DaysDiff < (-7)) {
                statusPayment = "VoucherDateCompare>7day";
              }
            } else {
              statusPayment = "Belum Digunakan";
            }
            break;
      }
    }
		return statusPayment;
	}

}