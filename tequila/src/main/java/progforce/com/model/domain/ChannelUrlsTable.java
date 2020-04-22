package progforce.com.model.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "channel_urls", indexes = @Index(columnList = "channelId"))
public class ChannelUrlsTable implements Serializable {
    private static final long serialVersionUID = -4137277526783294348L;

    @Id
    @Column(length = 128)
    private String urlPath;
    public String getUrlPath() {
        return urlPath;
    }
    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    @Column(nullable = false)
    private Long channelId;
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Column(nullable = false)
    private Integer isPrimary = 0;
    public Integer getIsPrimary() {
        return isPrimary;
    }
    public void setIsPrimary(Integer isPrimary) {
        this.isPrimary = isPrimary;
    }

    @Column(nullable = false)
    private Integer redirectToPrimary = 0;
    public Integer getRedirectToPrimary() {
        return redirectToPrimary;
    }
    public void setRedirectToPrimary(Integer redirectToPrimary) {
        this.redirectToPrimary = redirectToPrimary;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Entity [%s]:%n", this.getClass().getName()))
                .append(String.format("urlPath           = %s%n", getUrlPath()))
                .append(String.format("channelId         = %d%n", getChannelId()))
                .append(String.format("isPrimary         = %d%n", getIsPrimary()))
                .append(String.format("redirectToPrimary = %d%n", getRedirectToPrimary()))
                .toString();
    }
}
