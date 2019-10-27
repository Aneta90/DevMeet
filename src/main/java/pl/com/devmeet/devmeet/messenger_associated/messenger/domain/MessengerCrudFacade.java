package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

import java.util.Optional;

public class MessengerCrudFacade implements MessengerCrudInterface {

    @Autowired
    private MessengerRepository messengerRepository;

    public MessengerCrudFacade(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    private MessengerCrudFinder finderInit() {
        return new MessengerCrudFinder(messengerRepository);
    }

    private MessengerCrudCreator creatorInit() {
        return new MessengerCrudCreator(messengerRepository);
    }

    private MessengerCrudDeleter deleterInit() {
        return new MessengerCrudDeleter(messengerRepository);
    }

    private MessengerCrudUpdater updateInit() {
        return new MessengerCrudUpdater(messengerRepository);
    }

    @Override
    public MessengerDto create(MessengerDto messengerDto) throws EntityAlreadyExistsException, EntityNotFoundException {
        return creatorInit().create(messengerDto);
    }

    @Override
    public Optional<MessengerDto> read(MessengerDto messengerDto) throws EntityNotFoundException {
        return Optional.ofNullable(finderInit().read(messengerDto.getGroup()));
    }

    @Override
    public Optional<MessengerEntity> findEntity(MessengerDto messengerDto) throws EntityNotFoundException {
        return finderInit().findEntity(messengerDto.getGroup());
    }

    @Override
    public MessengerDto update(MessengerDto messengerDto) {
        return null;
    }

    @Override
    public boolean delete(MessengerDto messengerDto) throws EntityNotFoundException {
        return deleterInit().delete(messengerDto);
    }

    static MessengerEntity map(MessengerDto messengerDto) {
        return MessengerMapper.map(messengerDto);
    }

    static MessengerDto map(MessengerEntity messengerEntity) {
        return MessengerMapper.map(messengerEntity);
    }

}
