package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

public interface MessengerCrudInterface {

    MessengerDto create(MessengerDto messengerDto) throws EntityAlreadyExistsException, EntityNotFoundException;

    MessengerDto read(MessengerDto messengerDto) throws EntityNotFoundException;

    MessengerEntity findEntity(MessengerDto messengerDto) throws EntityNotFoundException;

    MessengerDto update(MessengerDto messengerDto);

    boolean delete(MessengerDto messengerDto) throws EntityNotFoundException;

    static MessengerDto map(MessengerEntity messengerEntity) {
        return MessengerMapper.map(messengerEntity);
    }

    static MessengerEntity map(MessengerDto messengerDto) {
        return MessengerMapper.map(messengerDto);
    }

}
