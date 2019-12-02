package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.domain_utils.exceptions.EntityNotFoundException;

class MessageCrudDeleter {

    private MessageCrudFinder messageCrudFinder;
    private MessageCrudSaver messageCrudSaver;

    MessageCrudDeleter(MessageRepository messageRepository) {
        this.messageCrudFinder = new MessageCrudFinder(messageRepository);
        this.messageCrudSaver = new MessageCrudSaver(messageRepository);
    }

    boolean delete(String memberNick) throws IllegalArgumentException, EntityNotFoundException {
// kasujemy wiadomości dla wybranego Membera
        return false;
    }
}