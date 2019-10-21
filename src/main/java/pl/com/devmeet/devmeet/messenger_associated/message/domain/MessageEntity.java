package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

public class MessageEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) //?? sprawdzić
    private MessengerEntity messenger;
}
