package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

public class MessengerCrudSaver {

    private MessengerRepository messengerRepository;

    MessengerCrudSaver(MessengerRepository messengerRepository) {
        this.messengerRepository = messengerRepository;
    }

    public MessengerDto saveEntity(MessengerEntity entity) {
        return MessengerCrudInterface.map(messengerRepository.save(entity));
    }
}