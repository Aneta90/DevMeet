package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MessengerRepository  extends PagingAndSortingRepository<MessengerEntity, UUID> {

    @Query("SELECT m FROM Messengers m where m.group.groupName = ?1")
    MessengerEntity findMessengerByGroup(String groupName);
}
