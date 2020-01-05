package pl.com.devmeet.devmeetcore.group_associated.permission.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;
import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberEntity;

import java.util.Optional;
import java.util.UUID;

public interface PermissionCrudRepository extends PagingAndSortingRepository<PermissionEntity, UUID> {

    Optional<PermissionEntity> findByMemberAndGroup(MemberEntity member, GroupEntity group);
}
