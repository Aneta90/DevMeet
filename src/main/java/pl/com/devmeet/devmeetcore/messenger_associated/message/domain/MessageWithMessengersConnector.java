package pl.com.devmeet.devmeetcore.messenger_associated.message.domain;

import pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain.MessengerEntity;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 02.12.2019
 * Time: 22:36
 */
class MessageWithMessengersConnector {

    public MessageEntity connectMessengers(MessageEntity messageEntity, MessengerEntity senderEntity, MessengerEntity receiverEntity) {
        messageEntity.setSender(senderEntity);
        messageEntity.setReceiver(receiverEntity);

        return messageEntity;
    }
}
