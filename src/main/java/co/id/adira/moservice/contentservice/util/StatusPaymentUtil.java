package co.id.adira.moservice.contentservice.util;

import java.util.Calendar;
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
    Date plus7Date ;

    // Voucher BERBAYAR STATUS mapping
    if (voucher.getPromo().getAvailableUntil().compareTo(currentDate) < 0) {
      plus7Date = dateUtil.datePlus(voucher.getPromo().getAvailableUntil(), 7);
      if (plus7Date.compareTo(currentDate) < 0) {
        System.out.printf("Voucher [%d] Sudah Kedaluwarsa | [voucher AvailableUntil (%s) | (%s) hari ini] Lebih dari 7 Hari\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
        statusPayment = "VoucherDateCompare>7day";
      } else {
        statusPayment = "Kedaluwarsa";
      }
    }

    if (voucher.getPaymentStatus().equals("FAILED")) {
      plus7Date = dateUtil.datePlus(voucher.getRedeemDate(), 7);
      if (voucher.getRedeemDate() != null && plus7Date.compareTo(currentDate) < 0) {
        System.out.printf("Voucher [%d] Gagal Pembayaran | [voucher RedeemDate (%s) | (%s) hari ini] Sudah Lewat dari 7 Hari\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
        statusPayment = "VoucherDateCompare>7day";
      } else {
        statusPayment = "Dibatalkan";
      }
    }

    if (voucher.getTransactionStatusId() == 2 && voucher.getPaymentStatus().equals("PENDING") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 && voucher.getPaymentExpiredAt().compareTo(currentDate) > 0) {
      statusPayment = "Menunggu Pembayaran";
    }

    if (voucher.getTransactionStatusId() == 2 && voucher.getPaymentStatus().equals("PAID") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      statusPayment = "Belum Digunakan";
    }
    
    if (voucher.getUseDate() != null && voucher.getTransactionStatusId() == 4 && voucher.getPaymentStatus().equals("PAID") && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      plus7Date = dateUtil.datePlus(voucher.getUseDate(), 7);
      if (plus7Date.compareTo(currentDate) < 0) {
        System.out.printf("Voucher [%d] Sudah digunakan | [voucher updated (%s) | (%s) hari ini] lebih dari 7 Hari Setelah Proses Pembelian\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
        statusPayment = "VoucherDateCompare>7day";
      } else {
        statusPayment = "Sudah Digunakan";
      }
    }

    // Voucher GRATIS STATUS mapping
    if (voucher.getPaymentStatus().equals("FREE") && voucher.getTransactionStatusId() == 2 && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      statusPayment = "Belum Digunakan";
    }
    if (voucher.getUseDate() != null && voucher.getPaymentStatus().equals("FREE") && voucher.getTransactionStatusId() == 4 && voucher.getPromo().getAvailableUntil().compareTo(currentDate) > 0 ) {
      plus7Date = dateUtil.datePlus(voucher.getUseDate(), 7);
      if (plus7Date.compareTo(currentDate) < 0) {
        System.out.printf("Voucher [%d] Sudah digunakan | [voucher updated (%s) | (%s) hari ini] lebih dari 7 Hari Setelah Proses Pembelian\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
        statusPayment = "VoucherDateCompare>7day";
      } else {
        statusPayment = "Sudah Digunakan";
      }
    }
		return statusPayment;
	}

}