package vn.jabeznguyen.watersolution_be.domain.dto;

public class ResLoginDTO {

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
