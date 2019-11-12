package pl.com.devmeet.devmeet.user.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntityFinder;

import java.util.List;
import java.util.Optional;

class UserCrudFinder implements CrudEntityFinder <UserDto, UserEntity> {

    private UserRepository repository;

    private String userNotFoundMessage = "User not found";

    public UserCrudFinder(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserEntity findEntity(UserDto dto) {
        Optional<UserEntity> foundUser = Optional.empty();
        String phone = dto.getPhone();
        String email = dto.getEmail();

        if (phone != null && !phone.equals(""))
            foundUser = repository.findByPhoneAndPassword(phone, dto.getPassword());
        else if (email != null && !phone.equals("")) //tu nie powinien być !email.equals("")
            foundUser = repository.findByEmailAndPassword(email, dto.getPassword());

        if (foundUser.isPresent())
            return foundUser.get();
        else
            throw new IllegalArgumentException(userNotFoundMessage);
    }

    @Override
    public List<UserEntity> findEntities(UserDto dto) {
        return null;
    }

    public UserDto read(UserDto dto) {
        return getDtoFromEntity(findEntity(dto));
    }

    @Override
    public boolean isExist(UserDto dto) {
        try {
            return findEntity(dto) != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private UserDto getDtoFromEntity(UserEntity entity) {
        return UserCrudInterface.map(entity);
    }
}
