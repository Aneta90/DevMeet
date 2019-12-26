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

    Optional<MessengerEntity> findByGroup(GroupEntity groupEntity);

    Optional<MessengerEntity> findByMember(MemberEntity memberEntity);
}

