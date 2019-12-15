package pl.com.devmeet.devmeet.messenger_associated.message.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeet.messenger_associated.messenger.domain.MessengerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<MessageEntity,UUID> {

    Optional<MessageEntity> findBySenderAndMessage(MessengerEntity sender, String message);

    Optional<List<MessageEntity>> findAllBySender(MessengerEntity senderMessenger);

    Optional<List<MessageEntity>> findAllByReceiver(MessengerEntity receiverMemberOrGroupMessenger);
}