package api.iam.user.domain;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {

    public Page<User> findAll(Pageable pageable, User user);

    public User findByUserId(UUID userId);

    public User findByUserEmail(String userEnail) throws Exception;

    public User save(User user) throws Exception;

    public void delete(User user) throws Exception;
}
