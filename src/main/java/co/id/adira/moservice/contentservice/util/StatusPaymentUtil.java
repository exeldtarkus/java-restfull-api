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
    Date plus7Date ;
    
    if (isVoucherExpired) {
      statusPayment = "Kedaluwarsa";
      plus7Date = dateUtil.datePlus(voucher.getPromo().getAvailableUntil(), 7);
      if (currentDate.compareTo(plus7Date) > 0) {
        System.out.printf("Voucher [%d] Sudah Kedaluwarsa | [voucher AvailableUntil (%s) | (%s) hari ini] Lebih dari 7 Hari\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
        statusPayment = "VoucherDateCompare>7day";
      }
    } else {
      switch(voucher.getPaymentStatus().toString()) {
        case "PAID":
            if (voucher.getUseDate() != null) {
              statusPayment = "Sudah Digunakan";
              plus7Date = dateUtil.datePlus(voucher.getUseDate(), 7);
              if (currentDate.compareTo(plus7Date) > 0) {
                System.out.printf("Voucher [%d] Sudah digunakan | [voucher UseDate (%s) | (%s) hari ini] lebih dari 7 Hari Setelah Proses Pembelian\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
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
            plus7Date = dateUtil.datePlus(voucher.getPaymentExpiredAt(), 7);
            if (currentDate.compareTo(plus7Date) > 0) {
              System.out.printf("Voucher [%d] Gagal Pembayaran | [voucher RedeemDate (%s) | (%s) hari ini] Sudah Lewat dari 7 Hari\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
              statusPayment = "VoucherDateCompare>7day";
            }
            break;
        case "FREE":
            if (voucher.getUseDate() != null) {
              statusPayment = "Sudah Digunakan";
              plus7Date = dateUtil.datePlus(voucher.getUseDate(), 7);
              if (currentDate.compareTo(plus7Date) > 0) {
                System.out.printf("Voucher [%d] Sudah digunakan | [voucher UseDate (%s) | (%s) hari ini] lebih dari 7 Hari Setelah Proses Pembelian\n", voucher.getId(), plus7Date.toString(), currentDate.toString());
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