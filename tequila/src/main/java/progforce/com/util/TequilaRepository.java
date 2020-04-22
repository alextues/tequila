package progforce.com.util;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import progforce.com.model.domain.ChannelUrlsTable;
import progforce.com.model.domain.ChannelsTable;
import progforce.com.model.domain.UsersTable;
import progforce.com.model.repository.ChannelUrlsTableRepository;
import progforce.com.model.repository.ChannelsTableRepository;
import progforce.com.model.repository.UsersTableRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TequilaRepository {
    @Autowired
    private ListableBeanFactory beanFactory;

    private Repositories repositories;

    public ListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void postConstruct() {
        repositories = new Repositories(beanFactory);
    }

    public JpaRepository getRepository(Class entity) {
        Optional<Object> repository = repositories.getRepositoryFor(entity);
        if(repository != null && repository.get() instanceof JpaRepository) {
            return (JpaRepository<Object, Serializable>) repository.get();
        }
        return null;
    }

    public UsersTable findUsersTableByEmailAddress(String emailAddress) {
        UsersTableRepository repo = (UsersTableRepository) getRepository(UsersTable.class);
        return repo.findUsersTableByEmailAddress(emailAddress);
    }

    public ChannelsTable findChannelsTableByChannelName(String channelName) {
        ChannelsTableRepository repo = (ChannelsTableRepository) getRepository(ChannelsTable.class);
        return repo.findChannelsTableByChannelName(channelName);
    }

    public List<ChannelUrlsTable> findChannelUrlsTableByChannelId(Long channelId) {
        ChannelUrlsTableRepository repo = (ChannelUrlsTableRepository) getRepository(ChannelUrlsTable.class);
        return repo.findChannelUrlsTableByChannelId(channelId);
    }
}
