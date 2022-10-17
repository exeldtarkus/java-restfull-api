package co.id.adira.moservice.contentservice.json.payment.send_invoice;

import lombok.Data;

@Data
public class PaymentSendInvoiceItemJson {

    private String title;
    private Long amount;
    private Long price;
    private Long qty;

}
