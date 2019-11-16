package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

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
    public MessengerDto create(MessengerDto messengerDto) throws EntityAlreadyExistsException {
        return creatorInit().create(messengerDto);
    }

    @Override
    public MessengerDto read(MessengerDto messengerDto) throws EntityNotFoundException {
        return finderInit().read(messengerDto);
    }

    @Override
    public MessengerEntity findEntity(MessengerDto messengerDto) throws EntityNotFoundException {
        return finderInit().findEntity(messengerDto);
    }

    @Override
    public MessengerDto update(MessengerDto messengerDto) {
        return null;
    }

    @Override
    public boolean delete(MessengerDto messengerDto) throws EntityNotFoundException {
        return deleterInit().delete(messengerDto);
    }

    public boolean isExist(MessengerDto messengerDto) {
        return finderInit().isExist(messengerDto);
    }


    public static MessengerEntity map(MessengerDto messengerDto) {
        return MessengerMapper.map(messengerDto);
    }

    public static MessengerDto map(MessengerEntity messengerEntity) {
        return MessengerMapper.map(messengerEntity);
    }

}
