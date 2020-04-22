package progforce.com.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import progforce.com.model.domain.UsersTable;

public interface UsersTableRepository extends JpaRepository<UsersTable, Long> {
    UsersTable findUsersTableByEmailAddress(String emailAddress);
}
