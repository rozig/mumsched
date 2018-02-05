package mumsched.service;

import mumsched.entity.User;

public interface UserService {
    public User findByEmail(String email);

    public void save(User user);

    public User findByActivationToken(String token);
}
