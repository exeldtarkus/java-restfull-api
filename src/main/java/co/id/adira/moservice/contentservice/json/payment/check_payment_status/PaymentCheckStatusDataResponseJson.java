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
    private String adiraku_account_id;
    private String adiraku_account_oid;
    private String adiraku_cashback_ref_id;
    private String adiraku_cashback_code_program;
    private String adiraku_cashback_amount;
    private String adiraku_cashback_record_trx_status;
    private String adiraku_cashback_disburse_status;
    private String adiraku_cashback_notif_status;
    private PaymentCheckStatusMethodJson method;
    private List<PaymentCheckStatusItemsJson> items;

}
