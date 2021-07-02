package co.id.adira.moservice.contentservice.request;

import com.google.gson.annotations.SerializedName;

public class ShortenUrlRequest {

    private String url;

    @SerializedName("url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
