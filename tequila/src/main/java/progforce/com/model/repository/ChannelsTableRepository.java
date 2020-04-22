package progforce.com.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progforce.com.model.domain.ChannelsTable;

public interface ChannelsTableRepository extends JpaRepository<ChannelsTable, Long> {
    ChannelsTable findChannelsTableByChannelName(String channelName);
}
