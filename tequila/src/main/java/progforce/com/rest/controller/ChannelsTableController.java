// REST-запросы к сущности ChannelsTable
package progforce.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progforce.com.exception.ResourceNotFoundException;
import progforce.com.model.domain.ChannelUrlsTable;
import progforce.com.model.domain.ChannelsTable;
import progforce.com.rest.service.ChannelsTableService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(ChannelsTableController.URL_BASE)
public class ChannelsTableController {
    public static final String URL_BASE = "/admin/channels";

    @Autowired
    private ChannelsTableService channelsService;

    // Список всех каналов
    @GetMapping
    public ResponseEntity<Collection<ChannelsTable>> getAllChannels() {
        return ResponseEntity.ok(channelsService.getAllChannels());
    }

    // Канал с заданным идентификатором
    @GetMapping("/{channelId}")
    public ResponseEntity<?> getChannelById(@PathVariable(value = "channelId") Long channelId) throws ResourceNotFoundException {
        return ResponseEntity.ok(channelsService.getChannelById(channelId));
    }

    // Новый канал (без привязки и с привязкой к пользователю)
    @PostMapping(value = {"/create/{channelName}",
                          "/create/{channelName}/{userId}"})
    public ResponseEntity<?> createChannel(
            @PathVariable(value = "channelName") String channelName,
            @PathVariable Optional<Long> userId) {
        return ResponseEntity.ok(channelsService.createChannel(channelName.trim(), userId.orElse(0L)));
    }

    // Удалить канал
    @PostMapping("/remove/{channelId}")
    public void removeChannel(@PathVariable(value = "channelId") Long channelId) throws ResourceNotFoundException {
        channelsService.removeChannel(channelId);
    }

    // Список всех URL
    @GetMapping("/urls")
    public ResponseEntity<Collection<ChannelUrlsTable>> getAllUrls() {
        return ResponseEntity.ok(channelsService.getAllUrls());
    }

    // Информация по заданному URL
    @GetMapping("/urls/{urlPath}")
    public ResponseEntity<?> getUrlById(@PathVariable(value = "urlPath") String urlPath) throws ResourceNotFoundException {
        return ResponseEntity.ok(channelsService.getUrlById(urlPath.trim()));
    }

    // Список URLs для канала
    @GetMapping("/urls/channel/{channelId}")
    public ResponseEntity<Collection<ChannelUrlsTable>> getUrlsByChannelId(@PathVariable(value = "channelId") Long channelId) throws ResourceNotFoundException {
        return ResponseEntity.ok(channelsService.getUrlsByChannelId(channelId));
    }

    // Добавить URL для канала
    @PostMapping("/urls/create/{channelId}/{urlPath}")
    public ResponseEntity<?> createUrl(
            @PathVariable(value = "channelId") Long channelId,
            @PathVariable(value = "urlPath") String urlPath) throws ResourceNotFoundException {
        return ResponseEntity.ok(channelsService.createUrl(channelId, urlPath.trim()));
    }

    // Удалить URL
    @PostMapping("/urls/remove/{urlPath}")
    public void removeUrl(@PathVariable(value = "urlPath") String urlPath) throws ResourceNotFoundException {
        channelsService.removeUrl(urlPath.trim());
    }

    // Удалить URL по заданному каналу
    @PostMapping("/urls/remove/channel/{channelId}")
    public void removeUrlByChannelId(@PathVariable(value = "channelId") Long channelId) throws ResourceNotFoundException {
        channelsService.removeUrlByChannelId(channelId);
    }
}
