package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

public class MessengerCrudSaver implements CrudEntitySaver<MessengerDto,MessengerEntity> {

    private MessengerRepository messengerRepository;

    MessengerCrudSaver(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    @Override
    public MessengerDto saveEntity(MessengerEntity entity) {
        return MessengerCrudInterface.map(messengerRepository.save(entity));
    }
}