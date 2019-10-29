package pl.com.devmeet.devmeet.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCrudFacade implements UserCrudInterface {

    @Autowired
    private UserRepository repository;

//    private String userNotFoundMessage = "User not found";
//    private String defaultLoginTypeErrMessage = "User default login type not defined";

    public UserCrudFacade(UserRepository repository) {
        this.repository = repository;
    }

    private UserCrudFinder finderInit() {
        return new UserCrudFinder(repository);
    }

    private UserCrudCreator creatorInit() {
        return new UserCrudCreator(repository);
    }

    private UserCrudUpdater updaterInit() {
        return new UserCrudUpdater(repository);
    }

    private UserCrudDeleter deleterInit() {
        return new UserCrudDeleter(repository);
    }

    private UserCrudActivator activatorInit() {
        return new UserCrudActivator(repository);
    }

    @Override
    public UserDto create(UserDto dto, DefaultUserLoginTypeEnum defaultLoginType) {
        return creatorInit().create(dto, defaultLoginType);
    }

    @Override
    public UserEntity findEntity(UserDto dto) {
        return finderInit().findEntity(dto);
    }

    @Override
    public UserDto read(UserDto dto) {
        return finderInit().read(dto);
    }

    @Override
    public boolean isExist(UserDto dto) {
        return finderInit().isExist(dto);
    }

    @Override
    public UserDto activation(UserDto dto) {
        return activatorInit().activate(dto);
    }

    @Override
    public UserDto update(UserDto newDto, UserDto oldDto) {
        return updaterInit().update(newDto, oldDto);
    }

    @Override
    public boolean delete(UserDto dto) {
        return deleterInit().delete(dto);
    }
}
