package pl.com.devmeet.devmeet.user_associated.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhoneAndPassword(String phone, String password);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

}
