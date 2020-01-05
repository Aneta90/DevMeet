package pl.com.devmeet.devmeetcore.messenger_associated.messenger.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessengerRepository extends PagingAndSortingRepository<MessengerEntity, UUID> {

    Optional<MessengerEntity> findByGroup(GroupEntity groupEntity);

    Optional<MessengerEntity> findByMember(MemberEntity memberEntity);
}

