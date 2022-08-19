/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceJson;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * @author fatchurrachman
 *
 */
public interface MoservicePaymentService {

    @POST("/api/payments/invoice")
    Call<PaymentSendInvoiceResponseJson> sendInvoice(
            @Header("Authorization") String authorization,
            @Body PaymentSendInvoiceJson json
    );

}
