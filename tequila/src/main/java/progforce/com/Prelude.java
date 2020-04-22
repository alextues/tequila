package progforce.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import progforce.com.model.domain.ChannelsTable;
import progforce.com.model.domain.UsersTable;
import progforce.com.util.TequilaRepository;

import java.util.List;

@Component
public class Prelude {
    @Autowired
    private TequilaRepository tequilaRepository;

    public void testUsersTable() {
        List<UsersTable> usersTables = tequilaRepository.getRepository(UsersTable.class).findAll();
        if(usersTables != null && !usersTables.isEmpty()) {
            for(int i = 0; i < usersTables.size(); i++) {
                UsersTable usersTable = usersTables.get(i);
                System.out.println(usersTable);
            }
        }
        else {
            System.out.println("Table USERS is empty");
        }
    }

    public void testChannelTable() {
        List<ChannelsTable> channelsTables = tequilaRepository.getRepository(ChannelsTable.class).findAll();
        if(channelsTables != null && !channelsTables.isEmpty()) {
            for (int i = 0; i < channelsTables.size(); i++) {
                ChannelsTable channelsTable = channelsTables.get(i);
                System.out.println(channelsTable);
            }
        }
        else {
            System.out.println("Table CHANNELS is empty");
        }
    }
}
