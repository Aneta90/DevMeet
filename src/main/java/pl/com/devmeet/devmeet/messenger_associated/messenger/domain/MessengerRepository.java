package pl.com.devmeet.devmeet.messenger_associated.messenger.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeet.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessengerRepository extends PagingAndSortingRepository<MessengerEntity, UUID> {

//    @Query("SELECT m FROM MessengerEntity m where m.group.groupName = ?1")
//    Optional<MessengerEntity> findMessengerByGroup(String groupName);
//
//    @Query("SELECT m FROM MessengerEntity m where m.messengerName = ?1")
//    MessengerEntity findByMessengerName(String messengerName);

    Optional<MessengerEntity> findByGroup(GroupEntity groupEntity);

    Optional<MessengerEntity> findByMember(MemberEntity memberEntity);

    Optional<List<MessengerEntity>> findAllByGroup(GroupEntity groupEntity);
}

