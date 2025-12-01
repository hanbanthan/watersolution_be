package vn.jabeznguyen.watersolution_be.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResLoginDTO {

    @JsonProperty("access_token")
    private String accessToken;
    private UserLogin user;

    public static class UserLogin {
        private Long id;
        private String email;
        private String username;

        public UserLogin() {
        }

        public UserLogin(Long id, String email, String username) {
            this.id = id;
            this.email = email;
            this.username = username;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class UserGetAccount {
        private UserLogin user;

        public UserGetAccount() {
        }

        public UserGetAccount(UserLogin user) {
            this.user = user;
        }

        public UserLogin getUser() {
            return user;
        }

        public void setUser(UserLogin user) {
            this.user = user;
        }
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
