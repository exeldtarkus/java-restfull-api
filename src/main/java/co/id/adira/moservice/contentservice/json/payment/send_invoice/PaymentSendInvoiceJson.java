package co.id.adira.moservice.contentservice.json.payment.send_invoice;

import lombok.Data;

import java.util.List;

@Data
public class PaymentSendInvoiceJson {
    private String payment_method_id;
    private Long amount;
    private Long promo_id;
    private Long voucher_id;
    private Long bengkel_id;
    private Long customer_id;
    private String adiraku_account_id;
    private String adiraku_account_oid;
    private List<PaymentSendInvoiceItemJson> items;
    private PaymentSendInvoiceCashbackJson cashback;
}
