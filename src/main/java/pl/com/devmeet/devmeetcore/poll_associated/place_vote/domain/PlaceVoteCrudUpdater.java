package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;

import java.util.List;

public class PlaceVoteCrudUpdater implements CrudEntityUpdater<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudUpdater(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    public PlaceVoteEntity findOldEntity(String memberNick, String placeName) throws PlaceVoteNotFoundException {
        List<PlaceVoteEntity> placeVoteEntityList = placeVoteCrudFinder.findEntityByMemberNick(memberNick);

        return placeVoteEntityList.stream().filter(a -> a.getPlace().getPlaceName().equals(placeName)).findFirst().get();
    }

    private PlaceVoteEntity mapToEntity(PlaceVoteDto placeVoteDto) {
        return PlaceVoteCrudFacade.map(placeVoteDto);
    }

    private PlaceVoteEntity updateAllParameters(PlaceVoteEntity oldEntity, PlaceVoteEntity newEntity) {
        oldEntity.setMember(newEntity.getMember());
        oldEntity.setPlace(newEntity.getPlace());
        oldEntity.setPoll(newEntity.getPoll());
        oldEntity.setCreationTime(newEntity.getCreationTime());
        oldEntity.setActive(true);
        return oldEntity;
    }

    @Override
    public PlaceVoteEntity updateEntity(PlaceVoteDto oldDto, PlaceVoteDto newDto) throws CrudException {
        PlaceVoteEntity placeVoteEntity = findOldEntity(oldDto.getMember().getNick(), oldDto.getPlace().getPlaceName());
        if (placeVoteEntity != null) {
            updateAllParameters(placeVoteEntity, mapToEntity(newDto));
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote is not found in our database");
        }
        return placeVoteCrudSaver.saveEntity(updateAllParameters(placeVoteEntity, mapToEntity(newDto)));
    }
}