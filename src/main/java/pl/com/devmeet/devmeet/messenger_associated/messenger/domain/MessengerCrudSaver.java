package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public class MessengerCrudSaver{

    private MessengerRepository messengerRepository;

    public MessengerCrudSaver(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    public MessengerDto saveEntity(MessengerEntity entity) throws EntityNotFoundException {
       return MessengerCrudInterface.map(messengerRepository.save(entity));
    }
}
