package vn.jabeznguyen.watersolution_be.controller;

import jakarta.validation.Valid;
import vn.jabeznguyen.watersolution_be.domain.User;
import vn.jabeznguyen.watersolution_be.domain.dto.ResCreateUserDTO;
import vn.jabeznguyen.watersolution_be.domain.dto.ResUpdateUserDTO;
import vn.jabeznguyen.watersolution_be.domain.dto.ResUserDTO;
import vn.jabeznguyen.watersolution_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.jabeznguyen.watersolution_be.util.annotation.ApiMessage;
import vn.jabeznguyen.watersolution_be.util.error.IdInvalidException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(
            @Valid
            @RequestBody User postManUser
    ) throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + "has already existed, please use other email."
            );
        }
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));

    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id)
            throws IdInvalidException {
        User currentUser = this.userService.fetchUserById(id);
        if (currentUser == null) {
            throw new IdInvalidException("User with id = " + id + " not exists.");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") Long id) throws IdInvalidException{
        User fetchUser = this.userService.fetchUserById(id);
        if (fetchUser == null) {
            throw new IdInvalidException("User with id = " + id + "not exists.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<List<ResUserDTO>> getAllUser() {
        List<ResUserDTO> fetchUsers = this.userService.fetchAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(fetchUsers);
    }

    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        User newUser = this.userService.handleUpdateUser(user);
        if (newUser == null) {
            throw new IdInvalidException("User with id = " + user.getId() + " not exists.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUpdateUserDTO(newUser));
    }
}
