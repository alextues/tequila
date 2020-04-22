// Таблица каналов
package progforce.com.model.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "channels", indexes = @Index(columnList = "userId"))
public class ChannelsTable implements Serializable {
    private static final long serialVersionUID = 3830035126652292501L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    private Long userId;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(nullable = false, length = 64, unique = true)
    private String channelName = "";
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Column(nullable = false)
    private Integer pageDisabled = 0;
    public Integer getPageDisabled() {
        return pageDisabled;
    }
    public void setPageDisabled(Integer pageDisabled) {
        this.pageDisabled = pageDisabled;
    }

    @Column(nullable = false)
    private Integer pageFavorite = 0;
    public Integer getPageFavorite() {
        return pageFavorite;
    }
    public void setPageFavorite(Integer pageFavorite) {
        this.pageFavorite = pageFavorite;
    }

    @Column(nullable = false)
    private Integer active = 0;
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }

    @Column(nullable = false)
    private Integer reviewStatus = 0;
    public Integer getReviewStatus() {
        return reviewStatus;
    }
    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Column(length = 32)
    private String template;
    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "channelId")
    private ChannelProfilesTable channelProfilesTable;
    public ChannelProfilesTable getChannelProfilesTable() {
        return channelProfilesTable;
    }
    public void setChannelProfilesTable(ChannelProfilesTable channelProfilesTable) {
        this.channelProfilesTable = channelProfilesTable;
    }

    @OneToMany(mappedBy = "channelId", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<ChannelUrlsTable> channelUrlsTable;
    public List<ChannelUrlsTable> getChannelUrlsTable() {
        return channelUrlsTable;
    }
    public void setChannelUrlsTable(List<ChannelUrlsTable> channelUrlsTable) {
        this.channelUrlsTable = channelUrlsTable;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Entity [%s]:%n", this.getClass().getName()))
                .append(String.format("channelId        = %d%n", getChannelId()))
                .append(String.format("userId           = %d%n", getUserId()))
                .append(String.format("channelName      = %s%n", getChannelName()))
                .append(String.format("pageDisabled     = %d%n", getPageDisabled()))
                .append(String.format("pageFavorite     = %d%n", getPageFavorite()))
                .append(String.format("active           = %d%n", getActive()))
                .append(String.format("reviewStatus     = %d%n", getReviewStatus()))
                .toString();
    }
}
