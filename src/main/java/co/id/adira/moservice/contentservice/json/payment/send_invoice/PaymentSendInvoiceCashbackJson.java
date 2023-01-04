package co.id.adira.moservice.contentservice.json.payment.send_invoice;

import lombok.Data;

@Data
public class PaymentSendInvoiceCashbackJson {

    private String adiraku_cashback_ref_id;
    private String adiraku_cashback_code_program;
    private Long adiraku_cashback_amount;

}
