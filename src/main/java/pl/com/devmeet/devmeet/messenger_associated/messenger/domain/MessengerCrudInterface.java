package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

public interface MessengerCrudInterface {

    MessengerDto create(MessengerDto messengerDto);

    MessengerDto read(MessengerDto messengerDto);

    MessengerDto update(MessengerDto messengerDto);

    MessengerDto delete(MessengerDto messengerDto);

    MessengerDto map(MessengerEntity messengerEntity);

    MessengerEntity map(MessengerDto messengerDto);


}
