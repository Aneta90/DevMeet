package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

public interface MessengerCrudInterface {

    MessengerDto create(MessengerDto messengerDto);

    MessengerDto read(MessengerDto messengerDto);

    MessengerEntity findEntity(MessengerDto messengerDto);

    MessengerDto update(MessengerDto messengerDto);

    MessengerDto delete(MessengerDto messengerDto);

    static MessengerDto map(MessengerEntity messengerEntity) {
        return MessengerMapper.map(messengerEntity);
    }

    static MessengerEntity map(MessengerDto messengerDto) {
        return MessengerMapper.map(messengerDto);
    }

}
