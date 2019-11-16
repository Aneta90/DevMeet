package pl.com.devmeet.devmeet.messenger_associated.message.domain;

public interface MessageCrudInterface {

    MessageDto create(MessageDto messageDto) throws EntityAlreadyExistsException, EntityNotFoundException;
}
