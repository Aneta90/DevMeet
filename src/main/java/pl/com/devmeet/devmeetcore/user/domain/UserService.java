package pl.com.devmeet.devmeetcore.user.domain;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

//    List<UserDto> findByPhone(String phone) {
//        return repository.findByPhone(phone)
//                .stream()
//                .map(UserMapper::toDto)
//                .collect(Collectors.toList());
//    }

    Optional<UserDto> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(UserMapper::toDto);
    }

//    List<UserDto> findAllByEmailAndPhone(String text) {
//        return repository.findAllByEmailAndPhone(text)
//                .stream()
//                .map(UserMapper::toDto)
//                .collect(Collectors.toList());
//    }

    List<UserDto> findAllByIsActive(Boolean isActive) {
        return repository.findAllByIsActive(isActive)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // add

    UserDto add(UserDto user) {
        if (user.getPassword() != null
                && user.getEmail() != null) { // add more ifs statements if required
//            checkEmailDuplication(user);
            user.setActive(false);
            user.setCreationTime(DateTime.now());
            return mapAndSave(user);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                "Adding user error!. Set all required fields without id or use update.");
    }

    // update
//    UserDto update(UserDto user) {
//        Optional<UserEntity> first = repository.findById(user.getId());
//        if (first.isPresent()) {
//            if (user.getEmail() == null) {
//                user.setEmail(first.get().getEmail());
//            } else checkEmailDuplication(user);
//            if (user.getPassword() == null) user.setPassword(first.get().getPassword());
//            if (user.getLogin() == null) user.setLogin(first.get().getLogin());
//            if (user.getPhone() == null) user.setPhone(first.get().getPhone());
//            user.setCreationTime(first.get().getCreationTime());
//            user.setModificationTime(DateTime.now());
//            return mapAndSave(user);
//        } else
//            throw new ResponseStatusException(HttpStatus.CONFLICT,
//                    "Resource with id (" + user.getId() + ") Not found");
//    }

    // delete

    boolean delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // custom methods

    private UserDto mapAndSave(UserDto user) {
        UserEntity userEntity = UserMapper.toEntity(user);
        UserEntity savedUser = repository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }
//
//    private void checkEmailDuplication(UserDto user) {
//        Optional<UserEntity> userByEmail = repository.findByEmail(user.getEmail());
//        if (userByEmail.isPresent()) {
//            if (!userByEmail.get().getId().equals(userByEmail.get().))
//                throw new ResponseStatusException(HttpStatus.CONFLICT,
//                        "Email: " + user.getEmail() + " already assigned for user id = " + userByEmail.get().getId());
//        }
//    }


}
