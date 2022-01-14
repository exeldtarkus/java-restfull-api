package co.id.adira.moservice.contentservice.json.mobil.get_model;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
public class GetModelResponseJson {

    private List<GetModelDataResponseJson> data;
    private String message;
    private String timestamp;
    private DateTime status;
    private Boolean isSuccess;

}
