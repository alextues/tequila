// Профиль канала
package progforce.com.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "channel_profiles")
public class ChannelProfilesTable implements Serializable {
    private static final long serialVersionUID = 357753752706845916L;

    @Id
    private Long channelId;
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    private Long profileMediaId;
    public Long getProfileMediaId() {
        return profileMediaId;
    }
    public void setProfileMediaId(Long profileMediaId) {
        this.profileMediaId = profileMediaId;
    }

    @Column(length = 1024)
    private String tagsJson;
    public String getTagsJson() {
        return tagsJson;
    }
    public void setTagsJson(String tagsJson) {
        this.tagsJson = tagsJson;
    }

    @Column(length = 256)
    private String shortDescription;
    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Column(length = 64)
    private String buttonText;
    public String getButtonText() {
        return buttonText;
    }
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    @Column(length = 256)
    private String communicationDescription;
    public String getCommunicationDescription() {
        return communicationDescription;
    }
    public void setCommunicationDescription(String communicationDescription) {
        this.communicationDescription = communicationDescription;
    }

    @Column(length = 256)
    private String howItWorks;
    public String getHowItWorks() {
        return howItWorks;
    }
    public void setHowItWorks(String howItWorks) {
        this.howItWorks = howItWorks;
    }

    private Long explainerVideoMediaId;
    public Long getExplainerVideoMediaId() {
        return explainerVideoMediaId;
    }
    public void setExplainerVideoMediaId(Long explainerVideoMediaId) {
        this.explainerVideoMediaId = explainerVideoMediaId;
    }

    @Column(length = 512)
    private String bio;
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;
    public Date getModifiedOn() {
        return modifiedOn;
    }
    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @Column(length = 256)
    private String websiteUrl;
    public String getWebsiteUrl() {
        return websiteUrl;
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Entity [%s]:%n", this.getClass().getName()))
                .append(String.format("channelId        = %d%n", getChannelId()))
                .append(String.format("tagJson          = %s%n", getTagsJson()))
                .append(String.format("shortDescription = %s%n", getShortDescription()))
                .append(String.format("websiteUrl       = %s%n", getWebsiteUrl()))
                .toString();
    }
}
