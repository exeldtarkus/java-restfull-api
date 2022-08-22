package co.id.adira.moservice.contentservice.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import co.id.adira.moservice.contentservice.model.content.Voucher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatusPaymentUtil {

	public String voucherStatusPayment(Voucher voucher) {
    String statusPayment = "";

		Date currentDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DATE, 7);
    Date todayPlus7 = calendar.getTime();

    // Voucher BERBAYAR STATUS mapping
    if (voucher.getTransactionStatusId() == 2 && voucher.getPaymentStatus().equals("PENDING") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 && voucher.getPaymentExpiredAt().compareTo(currentDate) > 0) {
      statusPayment = "Menunggu Pembayaran";
    }
    if (voucher.getTransactionStatusId() == 2 && voucher.getPaymentStatus().equals("PAID") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      statusPayment = "Belum Digunakan";
    }
    if (voucher.getTransactionStatusId() == 3 && voucher.getPaymentStatus().equals("PAID") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      if (voucher.getUpdated() != null && voucher.getUpdated().compareTo(todayPlus7) > 0) {
        System.out.printf("Voucher [%d] Sudah digunakan dan Sudah Lebih dari 7 Hari Setelah Proses Pembelian\n", voucher.getId());
        statusPayment = "VoucherDateCompare>7day";
      } else {
        statusPayment = "Sudah Digunakan";
      }
    }
    if (voucher.getPromo().getAvailableUntil().compareTo(currentDate) < 0) {
      if (voucher.getPromo().getAvailableUntil().compareTo(todayPlus7) > 0) {
        System.out.printf("Voucher [%d] Sudah Kedaluwarsa Lebih dari 7 Hari\n", voucher.getId());
      } else {
        statusPayment = "Kedaluwarsa";
      }
    }
    if (voucher.getPaymentStatus().equals("FAILED")) {
      if (voucher.getRedeemDate() != null && voucher.getRedeemDate().compareTo(todayPlus7) > 0) {
        System.out.printf("Voucher [%d] Gagal Pembayaran dan Sudah Lewat dari 7 Hari\n", voucher.getId());
      } else {
        statusPayment = "Dibatalkan";
      }
    }

    // Voucher GRATIS STATUS mapping
    if (voucher.getPaymentStatus().equals("FREE") && voucher.getTransactionStatusId() == 2 && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      statusPayment = "Belum Digunakan";
    }
    if (voucher.getPaymentStatus().equals("FREE") && voucher.getTransactionStatusId() == 3 && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      statusPayment = "Sudah Digunakan";
    }

		return statusPayment;
	}

}