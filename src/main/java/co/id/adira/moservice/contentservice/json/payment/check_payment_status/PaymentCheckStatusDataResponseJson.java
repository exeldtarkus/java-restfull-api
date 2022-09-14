package co.id.adira.moservice.contentservice.json.payment.check_payment_status;

import java.util.List;

import lombok.Data;

@Data
public class PaymentCheckStatusDataResponseJson {

    private String id;
    private String amount;
    private int admin_fee;
    private int bengkel_id;
    private String payment_method_id;
    private String va_no;
    private String va_expired_at;
    private String ref;
    private String ref_id;
    private String status;
    private String created_at;
    private String updated_at;
    private String paid_at;
    private String fail_at;
    private PaymentCheckStatusMethodJson method;
    private List<PaymentCheckStatusItemsJson> items;

}
