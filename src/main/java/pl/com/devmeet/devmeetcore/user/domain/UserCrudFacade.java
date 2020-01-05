package pl.com.devmeet.devmeetcore.user.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeetcore.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyActiveException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserFoundButNotActive;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCrudFacade implements CrudFacadeInterface<UserDto, UserEntity> {

    private UserRepository repository;

    @Autowired
    public UserCrudFacade(UserRepository repository) {
        this.repository = repository;
    }


    private UserCrudSaver initSaver() {
        return UserCrudSaver.builder()
                .repository(repository)
                .build();
    }

    private UserCrudFinder initFinder() {
        return UserCrudFinder.builder()
                .repository(repository)
                .build();
    }

    private UserCrudCreator initCreator() {
        return UserCrudCreator.builder()
                .userFinder(initFinder())
                .userSaver(initSaver())
                .build();
    }

    private UserCrudUpdater initUpdater() {
        return UserCrudUpdater.builder()
                .userFinder(initFinder())
                .userSaver(initSaver())
                .build();
    }

    private UserCrudDeleter initDeleter() {
        return UserCrudDeleter.builder()
                .userFinder(initFinder())
                .userSaver(initSaver())
                .build();
    }

    private UserCrudActivator initActivator() {
        return UserCrudActivator.builder()
                .userFinder(initFinder())
                .userSaver(initSaver())
                .build();
    }

    @Override
    public UserDto add(UserDto dto) throws UserAlreadyExistsException {
        return map(initCreator().create(dto));
    }

    public UserEntity findEntityByEmail(UserDto dto) throws UserNotFoundException {
        return initFinder().findEntityByEmail(dto);
    }

    public UserDto findByEmail(UserDto dto) throws UserNotFoundException {
        return map(initFinder().findEntityByEmail(dto));
    }

    public List<UserDto> findAll() {
        return map(initFinder().findAllEntities());
    }

    @Deprecated
    public boolean isExist(UserDto dto) {
        return initFinder().isExist(dto);
    }


    public UserDto activation(UserDto dto) throws UserAlreadyActiveException, UserNotFoundException {
        return map(initActivator().activate(dto));
    }

    @Override
    public UserDto update(UserDto newDto, UserDto oldDto) throws UserFoundButNotActive, UserNotFoundException {
        return map(initUpdater().update(newDto, oldDto));
    }

    @Override
    public UserDto delete(UserDto dto) throws UserFoundButNotActive, UserNotFoundException {
        return map(initDeleter().delete(dto));
    }

    public static UserDto map(UserEntity entity) {
        return UserMapper.toDto(entity);
    }

    public static UserEntity map(UserDto dto) {
        return UserMapper.toEntity(dto);
    }

    private List<UserDto> map(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserCrudFacade::map)
                .collect(Collectors.toList());
    }
}
