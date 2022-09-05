/**
 *
 */
package co.id.adira.moservice.contentservice.service;

import co.id.adira.moservice.contentservice.json.payment.check_payment_status.PaymentCheckStatusPaymentResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceJson;
import antlr.collections.List;
import co.id.adira.moservice.contentservice.json.content.redeem_promo.RedeemPromoResponseJson;
import co.id.adira.moservice.contentservice.json.payment.send_invoice.PaymentSendInvoiceResponseJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @GET("/api/payments/espay/check-status/{voucher_id}")
    Call<PaymentCheckStatusPaymentResponseJson> checkStatusPaymentEspay(
            @Path("voucher_id") Long voucher_id,
            @Header("Authorization") String authorization
    );

}
