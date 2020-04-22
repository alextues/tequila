package progforce.com.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progforce.com.model.domain.ChannelProfilesTable;

public interface ChannelProfilesRepository extends JpaRepository<ChannelProfilesTable, Long> {
}
