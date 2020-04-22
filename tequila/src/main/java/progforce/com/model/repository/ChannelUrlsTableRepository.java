package progforce.com.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progforce.com.model.domain.ChannelUrlsTable;

import java.util.List;

public interface ChannelUrlsTableRepository extends JpaRepository<ChannelUrlsTable, String> {
    List<ChannelUrlsTable> findChannelUrlsTableByChannelId(Long channelId);
}
