package vn.jabeznguyen.watersolution_be.repository;

import vn.jabeznguyen.watersolution_be.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    boolean existsByEmail(String email);

    User findByRefreshTokenAndUsername(String token, String username);
}
