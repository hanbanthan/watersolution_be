package vn.jabeznguyen.watersolution_be.controller;

import vn.jabeznguyen.watersolution_be.domain.User;
import vn.jabeznguyen.watersolution_be.service.UserService;
import vn.jabeznguyen.watersolution_be.util.error.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createNewUser(
            @RequestBody User postManUser
    ) {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id)
            throws IdInvalidException {
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("newUser");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User fetchUser = this.userService.fetchUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> fetchUsers = this.userService.fetchAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(fetchUsers);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User newUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}
