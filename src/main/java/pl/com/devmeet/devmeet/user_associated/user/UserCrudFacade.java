package pl.com.devmeet.devmeet.user_associated.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCrudFacade implements UserCrudInterface {

    private UserRepository repository;

    private UserCrudFinder userFinder;
    private UserCrudCreator userCreator;
    private UserCrudUpdater userUpdater;
    private UserCrudDeleter userDeleter;

//    private String userNotFoundMessage = "User not found";
//    private String defaultLoginTypeErrMessage = "User default login type not defined";

    @Autowired
    public UserCrudFacade(UserRepository repository) {
        this.repository = repository;

        finderInit();
        creatorInit();
        updaterInit();
        deleterInit();
    }

    private void finderInit() {
        this.userFinder = new UserCrudFinder(repository);
    }

    private void creatorInit() {
        this.userCreator = new UserCrudCreator(repository);
    }

    private void updaterInit() {
        this.userUpdater = new UserCrudUpdater(repository);
    }

    private void deleterInit() {
        this.userDeleter = new UserCrudDeleter(repository);
    }

    @Override
    public UserDto create(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType) {
        return userCreator.create(dto, defaultLoginType);
    }

    @Override
    public UserEntity findEntity(UserDto dto) {
        return userFinder.findEntity(dto);
    }

    @Override
    public UserDto read(UserDto dto) {
        return userFinder.read(dto);
    }

    @Override
    public boolean isExist(UserDto dto) {
        return userFinder.isExist(dto);
    }

    @Override
    public UserDto activation(UserDto dto) {
        return userCreator.activation(dto);
    }

    @Override
    public UserDto update(UserDto newDto, UserDto oldDto) {
        return userUpdater.update(newDto, oldDto);
    }

    @Override
    public boolean delete(UserDto dto) {
        return userDeleter.delete(dto);
    }
}
