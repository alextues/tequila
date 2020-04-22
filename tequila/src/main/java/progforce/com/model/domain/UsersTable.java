// Таблица пользователей
package progforce.com.model.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import progforce.com.util.LinkedRecords;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "users", indexes = @Index(columnList = "emailAddress, createdOn"))
public class UsersTable implements Serializable {
    private static final long serialVersionUID = -5399031269268523013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(nullable = false, length = 64)
    private String name = "";
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 64, unique = true)
    private String emailAddress;
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(length = 128)
    private String passwordHash;
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Column(nullable = false)
    private Integer active = 1;
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }

    @Column(nullable = false)
    private Long userFlags = 0L;
    public Long getUserFlags() {
        return userFlags;
    }
    public void setUserFlags(Long userFlags) {
        this.userFlags = userFlags;
    }

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    public Date getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(length = 256)
    private String fullName;
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private Long canderUsage;
    public Long getCanderUsage() {
        return canderUsage;
    }
    public void setCanderUsage(Long canderUsage) {
        this.canderUsage = canderUsage;
    }

    private Long canderUser;
    public Long getCanderUser() {
        return canderUser;
    }
    public void setCanderUser(Long canderUser) {
        this.canderUser = canderUser;
    }

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<ChannelsTable> channelsTables = new ArrayList<>();
    public List<ChannelsTable> getChannelsTables() {
        return this.channelsTables;
    }
    public void setChannelsTables(List<ChannelsTable> channelsTables) {
        this.channelsTables = channelsTables;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Entity [%s]:%n", this.getClass().getName()))
                .append(String.format("userId       = %d%n", getUserId()))
                .append(String.format("name         = %s%n", getName()))
                .append(String.format("passwordHash = %s%n", getPasswordHash()))
                .append(String.format("active       = %d%n", getActive()))
                .append(String.format("userFlags    = %d%n", getUserFlags()))
                .append(String.format("createdOn    = %s%n", getCreatedOn()))
                .append(String.format("fullName     = %s%n", getFullName()))
                .append(String.format("--- channels table ---%n"))
                .append(LinkedRecords.prepareChannelsList(channelsTables))
                .toString();
    }
}
