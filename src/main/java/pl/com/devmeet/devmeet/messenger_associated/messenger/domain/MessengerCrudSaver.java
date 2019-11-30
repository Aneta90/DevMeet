package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import pl.com.devmeet.devmeet.domain_utils.CrudEntitySaver;

class MessengerCrudSaver implements CrudEntitySaver<MessengerEntity,MessengerEntity> {

    private MessengerRepository messengerRepository;

    MessengerCrudSaver(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    @Override
    public MessengerEntity saveEntity(MessengerEntity entity) {
        return messengerRepository.save(entity);
    }
}