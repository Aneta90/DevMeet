package pl.com.devmeet.devmeet.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneAndPassword(String phone, String password);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByPhone(String phone);

}
