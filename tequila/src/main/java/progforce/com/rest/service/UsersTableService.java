// Обработчик REST-запросов к сущности UsersTable
package progforce.com.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import progforce.com.exception.CreateException;
import progforce.com.exception.ResourceNotFoundException;
import progforce.com.exception.ValidationException;
import progforce.com.model.domain.ChannelsTable;
import progforce.com.model.domain.UsersTable;
import progforce.com.util.Constants;
import progforce.com.util.TequilaRepository;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsersTableService {
    @Resource
    private TequilaRepository repo;

    @Autowired
    private ChannelsTableService channelsTableService;

    // Список всех пользователей
    public List<UsersTable> getAllUsers() {
        return repo.getRepository(UsersTable.class).findAll();
    }

    // Пользователь с заданным идентификатором
    public ResponseEntity<UsersTable> getUserById(Long userId) throws ResourceNotFoundException {
        Optional<UsersTable> usersTable = repo.getRepository(UsersTable.class).findById(userId);
        if (usersTable.isPresent()) {
            return ResponseEntity.ok().body(usersTable.get());
        } else {
            throw new ResourceNotFoundException(String.format("User with id = %d not found ", userId));
        }
    }

    // Новый пользователь (signup = true) или регистрация существующего пользователя
    public ResponseEntity<UsersTable> createOrLoginUser(String emailAddress, String password, boolean signup) throws ResourceNotFoundException {
        // Проверить входные параметры
        if(isEmpty(emailAddress)) {
            throw new ValidationException(String.format("Empty parameter: emailAddress"));
        }
        if(isEmpty(password)) {
            throw new ValidationException(String.format("Empty parameter: password"));
        }
        if(!isValidEmailAddress(emailAddress)) {
            throw new ValidationException(String.format("Not valid email address: '%s'", emailAddress));
        }

        UsersTable ut = repo.findUsersTableByEmailAddress(emailAddress);
        // Новый пользователь
        if (signup) {
            if (ut != null) {
                throw new ValidationException(String.format("User with such email address (%s) already exists (id = %d)", emailAddress, ut.getUserId()));
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordHash = encoder.encode(password);
            UsersTable usersTable = new UsersTable();
            String name = getAutoNameFromEmailaddress(emailAddress);
            usersTable.setName(name);
            usersTable.setFullName(name);
            usersTable.setEmailAddress(emailAddress);
            usersTable.setPasswordHash(passwordHash);
            repo.getRepository(UsersTable.class).save(usersTable);

            // Данные о канале и профиле канала
            ResponseEntity<?> channels = channelsTableService.createChannel(usersTable.getName(), usersTable.getUserId());
            ResponseEntity<?> urls = ResponseEntity.ok(channelsTableService.createUrl(((ChannelsTable) channels.getBody()).getChannelId(), usersTable.getName()));
            if (channels.getStatusCode() == HttpStatus.OK && urls.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok().body(usersTable);
            }
            else {
                throw new CreateException(String.format("For user (id = %d) can't create record into tables: channels, channel_profiles & channel_urls", usersTable.getUserId()));
            }
        }
        // Регистрация существующего пользователя
        else {
            if (ut == null) {
                throw new ValidationException(String.format("User with such email address (%s) not exists", emailAddress));
            }

            if (!BCrypt.checkpw(password, ut.getPasswordHash())) {
                throw new ValidationException(String.format("Bad password for user with email address '%s'", emailAddress));
            }

            if (isUserAllowedToLogin(ut.getActive(), ut.getUserFlags())) {
                return ResponseEntity.ok().body(ut);
            }
            else {
                throw new ValidationException(String.format("User with email address '%s' is blocked", emailAddress));
            }
        }
    }

    // Обновить данные пользователя
    public ResponseEntity<UsersTable> updateUser(UsersTable ut) throws ResourceNotFoundException{
        Optional<UsersTable> usersTable = repo.getRepository(UsersTable.class).findById(ut.getUserId());
        if(usersTable.isPresent()) {
            repo.getRepository(UsersTable.class).save(ut);
            return ResponseEntity.ok().body(ut);
        }
        else {
            throw new ResourceNotFoundException(String.format("User with id = %d not found", ut.getUserId()));
        }
    }

    // Удалить пользователя и связанные с ним данные
    public void removeUser(Long userId) throws ResourceNotFoundException {
        Optional<UsersTable> usersTable = repo.getRepository(UsersTable.class).findById(userId);
        if (usersTable.isPresent()) {
            repo.getRepository(UsersTable.class).deleteById(userId);
        }
        else {
            throw new ResourceNotFoundException(String.format("User with id = %d not found", userId));
        }
    }

    private boolean isEmpty(String value) {
        return value.isEmpty();
    }

    private boolean isValidEmailAddress(String email) {
        final Pattern regex = Pattern.compile(Constants.EMAIL_PATTERN);

        return email.length() <= Constants.EMAIL_MAX_LENGTH && regex.matcher(email).matches();
    }

    private String getAutoNameFromEmailaddress(String emailAddress) {
        return StringUtils.substringBefore(emailAddress, "@");
    }

    private boolean isUserAllowedToLogin(int active, long flags) {
        return (active | flags) != 0;
    }
}
