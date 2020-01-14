package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.member_associated.member.domain.MemberCrudFacade;
import pl.com.devmeet.devmeetcore.member_associated.place.domain.PlaceCrudFacade;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudFacade;

public class PlaceVoteMapper {

    public static PlaceVoteDto map(PlaceVoteEntity placeVoteEntity) {
        return placeVoteEntity != null ? PlaceVoteDto.builder()
                .member(MemberCrudFacade.map(placeVoteEntity.getMember()))
                .place(PlaceCrudFacade.map(placeVoteEntity.getPlace()))
                .poll(PollCrudFacade.map(placeVoteEntity.getPoll()))
                .creationTime(placeVoteEntity.getCreationTime())
                .isActive(placeVoteEntity.isActive())
                .build() : null;
    }

    public static PlaceVoteEntity map(PlaceVoteDto placeVoteDto) {
        return placeVoteDto != null ? PlaceVoteEntity.builder()
                .member(MemberCrudFacade.map(placeVoteDto.getMember()))
                .place(PlaceCrudFacade.map(placeVoteDto.getPlace()))
                .poll(PollCrudFacade.map(placeVoteDto.getPoll()))
                .creationTime(placeVoteDto.getCreationTime())
                .isActive(placeVoteDto.isActive())
                .build() : null;
    }
}