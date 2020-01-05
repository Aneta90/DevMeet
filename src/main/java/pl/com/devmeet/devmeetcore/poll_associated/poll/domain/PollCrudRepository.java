package pl.com.devmeet.devmeetcore.poll_associated.poll.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.com.devmeet.devmeetcore.group_associated.group.domain.GroupEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PollCrudRepository extends PagingAndSortingRepository<PollEntity, UUID> {

    Optional<PollEntity> findByGroup(GroupEntity groupEntity);

    Optional<List<PollEntity>> findAllByGroup(GroupEntity groupEntity);
}
