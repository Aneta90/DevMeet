package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteCrudSaver implements CrudEntitySaver {

    private PlaceVoteRepository placeVoteRepository;

    public PlaceVoteCrudSaver(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public Object saveEntity(Object entity) throws CrudException {
        return null;
    }
}