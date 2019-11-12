package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.domain_utils.EntityAlreadyExistsException;
import pl.com.devmeet.devmeet.domain_utils.EntityNotFoundException;

public interface MessageCrudInterface {

    MessageDto create(MessageDto messageDto) throws EntityAlreadyExistsException, EntityNotFoundException;
}
