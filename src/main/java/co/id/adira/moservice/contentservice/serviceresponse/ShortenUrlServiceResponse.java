package co.id.adira.moservice.contentservice.serviceresponse;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "link"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrlServiceResponse {

    @JsonProperty("link")
    private String link;

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }
}