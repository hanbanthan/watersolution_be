package vn.jabeznguyen.watersolution_be.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.jabeznguyen.watersolution_be.domain.User;
import vn.jabeznguyen.watersolution_be.exception.AppException;
import vn.jabeznguyen.watersolution_be.exception.ErrorCode;
import vn.jabeznguyen.watersolution_be.repository.UserRepository;

import java.time.Duration;
import java.util.UUID;

@Service
@Slf4j
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public String forgotPassword(String email, String username) {
        User account = userRepository.findByEmail(email);

        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        if (!account.getUsername().equals(username)) {
            throw new AppException(ErrorCode.USERNAME_OR_EMAIL_INVALID);
        }

        String token = UUID.randomUUID().toString();
        String redisKey = "password_reset:" + email;

        // Luu token vao Redis voi TTL = 30 phut
        redisTemplate.opsForValue().set(redisKey, token, Duration.ofMinutes(30));

        this.sendEmail(email, "WaterSolution Password Reset Request", "Your password reset token is: " + token);

        return "Password reset email sent.";
    }

    public String verifyVerificationCode(String email, String token) {
        String redisKey = "password_reset:" + email;
        System.out.println(">>> CHECKING REDIS KEY: [" + redisKey + "]");
        String savedToken = redisTemplate.opsForValue().get(redisKey);
        System.out.println(">>> REDIS RESULT: " + (savedToken == null ? "NULL" : savedToken));
        System.out.println(">>> INPUT TOKEN:  [" + token + "]");
        if (savedToken == null) {
            throw new AppException(ErrorCode.VERIFICATION_TOKEN_NOT_FOUND);
        }

        if (!savedToken.equals(token)) {
            throw new AppException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        return "Verification code is valid.";
    }

    public String resetPassword(String email, String newPassword) {
        User account = userRepository.findByEmail(email);
        if (account == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        account.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(account);

        // Optionally delete the token from Redis after successful reset
        String redisKey = "password_reset:" + email;
        redisTemplate.delete(redisKey);

        return "password has been reset.";
    }
}
