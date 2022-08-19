package co.id.adira.moservice.contentservice.json.payment.send_invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSendInvoiceResponseJson {
    private PaymentSendInvoiceDataResponseJson data;
}
