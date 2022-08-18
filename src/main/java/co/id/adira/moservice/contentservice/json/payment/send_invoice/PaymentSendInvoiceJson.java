package co.id.adira.moservice.contentservice.json.payment.send_invoice;

import lombok.Data;

import java.util.List;

@Data
public class PaymentSendInvoiceJson {
    private String payment_method_id;
    private Long amount;
    private Long promo_id;
    private Long bengkel_id;
    private Long customer_id;
    private List<PaymentSendInvoiceItemJson> items;
}
