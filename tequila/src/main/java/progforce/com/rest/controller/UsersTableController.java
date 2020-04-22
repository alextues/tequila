// REST-запросы к сущности UsersTable
package progforce.com.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progforce.com.exception.ResourceNotFoundException;
import progforce.com.model.domain.UsersTable;
import progforce.com.rest.service.UsersTableService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(UsersTableController.URL_BASE)
public class UsersTableController {
    public static final String URL_BASE = "/admin/users";

    @Autowired
    private UsersTableService usersService;

    // Список всех пользователей
    @GetMapping
    public ResponseEntity<Collection<UsersTable>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    // Пользователь с заданным идентификатором
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(usersService.getUserById(userId));
    }

    // Новый пользователь (signup = true) или регистрация существующего пользователя
    @PostMapping(value = {"/create/{emailAddress}/{password}",
                          "/create/{emailAddress}/{password}/{signup}"})
    public ResponseEntity<?> createOrLoginUser(
            @PathVariable(value = "emailAddress") String emailAddress,
            @PathVariable(value = "password")String password,
            @PathVariable Optional<Boolean> signup) throws ResourceNotFoundException {
        return ResponseEntity.ok(usersService.createOrLoginUser(emailAddress.trim(), password.trim(), signup.orElse(false)));
    }

    // Обновить данные пользователя с заданным идентификатором
    @PostMapping
    public ResponseEntity<?> updateUser(@RequestBody UsersTable ut) throws ResourceNotFoundException {
        return ResponseEntity.ok(usersService.updateUser(ut));
    }

    // Удалить пользователя и связанные с ним данные
    @PostMapping("/remove/{userId}")
    public void removeUser(@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
        usersService.removeUser(userId);
    }

}
