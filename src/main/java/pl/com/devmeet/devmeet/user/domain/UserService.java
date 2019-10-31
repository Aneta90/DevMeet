package pl.com.devmeet.devmeet.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // find

    Optional<UserDto> findById(Long id) {
        return repository.findById(id)
                .map(UserMapper::toDto);
    }

    List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    List<UserDto> findByPhone(String phone) {
        return repository.findByPhone(phone)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    Optional<UserDto> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::toDto);
    }

    // add

    UserDto add(UserDto user) {
        if (user.getId() == null
                && user.getLogin() != null
                && user.getPassword() != null
                && user.getEmail() != null) { // add more ifs statements if required
            checkEmailDuplication(user);
            return mapAndSave(user);
        } else throw new UserExceptionAdd();
    }

    // update
    // TO BE IMPLEMENTED

    // delete
    // TO BE IMPLEMENTED

    // custom methods

    private UserDto mapAndSave(UserDto user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity savedUser = repository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }

    private void checkEmailDuplication(UserDto user) {
        Optional<UserEntity> userByEmail = repository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            if (!userByEmail.get().getId().equals(user.getId()))
                throw new UserExceptionDuplicateEmail();
        }
    }


}
