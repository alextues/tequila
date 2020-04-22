// Обработчик REST-запросов к сущности ChannelsTable
package progforce.com.rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import progforce.com.exception.ResourceNotFoundException;
import progforce.com.exception.ValidationException;
import progforce.com.model.domain.ChannelProfilesTable;
import progforce.com.model.domain.ChannelUrlsTable;
import progforce.com.model.domain.ChannelsTable;
import progforce.com.model.domain.UsersTable;
import progforce.com.util.TequilaRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class ChannelsTableService {
    @Resource
    private TequilaRepository repo;

    // Список всех каналов
    public List<ChannelsTable> getAllChannels() {
        return repo.getRepository(ChannelsTable.class).findAll();
    }

    // Канал с заданным идентификатором
    public ResponseEntity<ChannelsTable> getChannelById(Long channelId) throws ResourceNotFoundException {
        Optional<ChannelsTable> channelsTable = getChannel(channelId);
        if (channelsTable.isPresent()) {
            return ResponseEntity.ok().body(channelsTable.get());
        }
        else {
            throw new ResourceNotFoundException(String.format("Channel with id = %d not found", channelId));
        }
    }

    // Новый канал (без привязки и с привязкой к пользователю)
    public ResponseEntity<ChannelsTable> createChannel(String channelName, Long userId) {
        // Проверить входные параметры
        if(isEmpty(channelName)) {
            throw new ValidationException(String.format("Empty parameter: channel name"));
        }
        if(userId > 0L && !repo.getRepository(UsersTable.class).findById(userId).isPresent()) {
            throw new ValidationException(String.format("No user with id = %d exists", userId));
        }
        if(isExistsChannel(channelName)) {
            throw new ValidationException(String.format("Channel with such name (%s) already exists", channelName));
        }

        ChannelsTable channelsTable = new ChannelsTable();
        channelsTable.setChannelName(channelName);
        if(userId > 0L) {
            channelsTable.setUserId(userId);
        }
        repo.getRepository(ChannelsTable.class).save(channelsTable);

        // Профиль канала
        ChannelProfilesTable channelProfilesTable = new ChannelProfilesTable();
        channelProfilesTable.setChannelId(channelsTable.getChannelId());
        channelProfilesTable.setShortDescription(channelsTable.getChannelName());
        repo.getRepository(ChannelProfilesTable.class).save(channelProfilesTable);
        return ResponseEntity.ok().body(channelsTable);
    }

    // Удалить канал и связанные с ним данные
    public void removeChannel(Long channelId) throws ResourceNotFoundException {
        Optional<ChannelsTable> channelsTable = getChannel(channelId);
        if (channelsTable.isPresent()) {
            repo.getRepository(ChannelsTable.class).deleteById(channelId);
        }
        else {
            throw new ResourceNotFoundException(String.format("Channel with id = %d not found", channelId));
        }
    }

    // Список всех URL
    public List<ChannelUrlsTable> getAllUrls() {
        return repo.getRepository(ChannelUrlsTable.class).findAll();
    }

    // Информация по заданному URL
    public ResponseEntity<ChannelUrlsTable> getUrlById(String urlPath) throws ResourceNotFoundException {
        if(isEmpty((urlPath))) {
            throw new ValidationException("Empty parameter: urlPath");
        }

        Optional<ChannelUrlsTable> channelUrlsTable = repo.getRepository(ChannelUrlsTable.class).findById(urlPath);
        if (channelUrlsTable.isPresent()) {
            return ResponseEntity.ok().body(channelUrlsTable.get());
        } else {
            throw new ResourceNotFoundException(String.format("Channel url with urlPath = %s not found", urlPath));
        }
    }

    // Список URL-ов для канала
    public List<ChannelUrlsTable> getUrlsByChannelId(Long channelId) throws ResourceNotFoundException {
        Optional<ChannelsTable> channelsTable = getChannel(channelId);
        if (channelsTable.isPresent()) {
            return repo.findChannelUrlsTableByChannelId(channelId);
        }
        else {
            throw new ResourceNotFoundException(String.format("Channel with id = %d not found", channelId));
        }
    }

    // Добавить URL для канала
    public ResponseEntity<ChannelUrlsTable> createUrl(Long channelId, String urlPath) throws ResourceNotFoundException {
        if(isEmpty((urlPath))) {
            throw new ValidationException("Empty parameter: urlPath");
        }

        Optional<ChannelsTable> channelsTable = getChannel(channelId);
        if (channelsTable.isPresent()) {
            ChannelUrlsTable channelUrlsTable = new ChannelUrlsTable();
            channelUrlsTable.setChannelId(channelId);
            channelUrlsTable.setUrlPath(urlPath);
            repo.getRepository(ChannelUrlsTable.class).save(channelUrlsTable);
            return ResponseEntity.ok().body(channelUrlsTable);
        }
        else {
            throw new ResourceNotFoundException(String.format("Channel with id = %d not found", channelId));
        }
    }

    // Удалить URL
    public void removeUrl(String urlPath) throws ResourceNotFoundException {
        if(isEmpty((urlPath))) {
            throw new ValidationException("Empty parameter: urlPath");
        }

        Optional<ChannelUrlsTable> channelUrlsTable = repo.getRepository(ChannelUrlsTable.class).findById(urlPath);
        if (channelUrlsTable.isPresent()) {
            repo.getRepository(ChannelUrlsTable.class).deleteById(urlPath);
        }
        else {
            throw new ResourceNotFoundException(String.format("Channel with urlPath = %s not found", urlPath));
        }
    }

    // Удалить URL по заданному каналу
    public void removeUrlByChannelId(Long channelId) throws ResourceNotFoundException {
        List<ChannelUrlsTable> channelUrlsTables = repo.findChannelUrlsTableByChannelId(channelId);
        if(!channelUrlsTables.isEmpty()) {
            for (int i = 0; i < channelUrlsTables.size(); i++) {
                repo.getRepository(ChannelUrlsTable.class).delete(channelUrlsTables.get(i));
            }
        }
        else {
            throw new ResourceNotFoundException(String.format("No channel urls found for channelId = %d", channelId));
        }
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }

    private boolean isExistsChannel(String channelName) {
        return repo.findChannelsTableByChannelName(channelName) != null;
    }

    // Найти канал по заданному channelId
    private Optional<ChannelsTable> getChannel(Long channelId) {
        return repo.getRepository(ChannelsTable.class).findById(channelId);
    }
}
