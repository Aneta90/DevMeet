package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteAlreadyExistsException;

public class PlaceVoteCrudCreator implements CrudEntityCreator<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudCreator(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    @Override
    public PlaceVoteEntity createEntity(PlaceVoteDto dto) throws CrudException {
        PlaceVoteEntity placeVoteEntity;

        if (placeVoteCrudFinder.isExist(dto)) {
            throw new PlaceVoteAlreadyExistsException("PlaceVote already exists");
        } else {
            placeVoteEntity = placeVoteCrudSaver.saveEntity(PlaceVoteCrudFacade.map(dto));
            return placeVoteEntity;
        }
    }
}