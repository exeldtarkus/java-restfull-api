package co.id.adira.moservice.contentservice.json.payment.check_payment_status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCheckStatusPaymentResponseJson {
    private PaymentCheckStatusDataResponseJson data;
}
