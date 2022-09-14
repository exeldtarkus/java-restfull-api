package co.id.adira.moservice.contentservice.json.payment.check_payment_status;

import lombok.Data;

@Data
public class PaymentCheckStatusItemsJson {

  private String id;
  private String title;
  private int qty;
  private String price;
  private int amount;

}
