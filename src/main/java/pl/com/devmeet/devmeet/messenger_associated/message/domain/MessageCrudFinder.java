package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import java.util.ArrayList;
import java.util.List;

class MessageCrudFinder {

    private MessageRepository repository;

    public MessageCrudFinder(MessageRepository repository) {
        this.repository = repository;
    }


}