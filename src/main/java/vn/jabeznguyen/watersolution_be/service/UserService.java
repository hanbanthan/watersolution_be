package vn.jabeznguyen.watersolution_be.service;

import vn.jabeznguyen.watersolution_be.domain.User;
import vn.jabeznguyen.watersolution_be.domain.dto.ResCreateUserDTO;
import vn.jabeznguyen.watersolution_be.domain.dto.ResUpdateUserDTO;
import vn.jabeznguyen.watersolution_be.domain.dto.ResUserDTO;
import vn.jabeznguyen.watersolution_be.repository.UserRepository;
import org.springframework.stereotype.Service;
import vn.jabeznguyen.watersolution_be.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User fetchUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }

    public List<ResUserDTO> fetchAllUser() {
        List<User> users = this.userRepository.findAll();
        List<ResUserDTO> listUser = users.stream()
                .map(item -> new ResUserDTO(
                        item.getId(),
                        item.getUsername(),
                        item.getEmail(),
                        item.getCreatedAt(),
                        item.getUpdatedAt()
                ))
                .collect(Collectors.toList());
        return listUser;
    }

    public User handleUpdateUser(User reqUser) throws IdInvalidException {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setUsername(reqUser.getUsername());
            currentUser.setPassword(reqUser.getPassword());
            currentUser = this.userRepository.save(currentUser);
        }
        else {
            throw new IdInvalidException("User with id " + reqUser.getId() + " does not exist");
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User handleGetUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setUsername(user.getUsername());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setEmail(user.getEmail());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setCreatedAt(user.getCreatedAt());
        return res;
    }

    public ResUpdateUserDTO convertToResUpdateUserDTO(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setUsername(user.getUsername());
        res.setUpdatedAt(user.getCreatedAt());
        return res;
    }

    public void updateUserToken(String token, String username) {
        User currentUser = this.handleGetUserByUsername(username);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }

    public User getUserByRefreshTokenAndUsername(String token, String username) {
        return this.userRepository.findByRefreshTokenAndUsername(token, username);
    }
}
