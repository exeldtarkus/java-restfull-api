package co.id.adira.moservice.contentservice.json.mobil.get_brand;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class GetBrandResponseJson {

    private List<GetBrandDataResponseJson> data;
    private String message;
    private String timestamp;
    private DateTime status;
    private Boolean isSuccess;

}
