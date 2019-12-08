package pl.com.devmeet.devmeet.group_associated.group.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.com.devmeet.devmeet.member_associated.member.domain.MemberEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupCrudRepository extends PagingAndSortingRepository<GroupEntity, UUID> {

    Optional<GroupEntity> findByGroupNameAndWebsiteAndDescription(String groupName, String website, String description);

}
