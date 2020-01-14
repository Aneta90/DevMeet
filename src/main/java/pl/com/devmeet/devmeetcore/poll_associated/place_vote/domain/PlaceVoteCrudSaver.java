package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteCrudSaver implements CrudEntitySaver<PlaceVoteEntity, PlaceVoteEntity> {

    private PlaceVoteRepository placeVoteRepository;

    public PlaceVoteCrudSaver(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public PlaceVoteEntity saveEntity(PlaceVoteEntity entity) throws CrudException {
        return placeVoteRepository.save(entity);
    }
}