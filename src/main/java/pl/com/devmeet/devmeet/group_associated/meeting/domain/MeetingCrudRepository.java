package pl.com.devmeet.devmeet.group_associated.meeting.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MeetingCrudRepository extends PagingAndSortingRepository<MeetingEntity,UUID> {
}
