package pl.com.devmeet.devmeetcore.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneAndPassword(String phone, String password);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByPhone(String phone);

    @Query("select u from UserEntity u where lower(u.email) like lower(concat('%', :search, '%') )" +
            "or u.phone like concat('%', :search, '%') ")
    List<UserEntity> findAllByEmailAndPhone(String search);

    @Query(value = "SELECT * FROM users WHERE users.is_active = :isActive", nativeQuery = true)
    List<UserEntity> findAllByIsActive(@Param("isActive") Boolean isActive);

}
