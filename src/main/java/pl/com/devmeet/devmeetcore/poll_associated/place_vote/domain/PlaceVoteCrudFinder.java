package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

import java.util.List;

public class PlaceVoteCrudFinder implements CrudEntityFinder {

    private PlaceVoteRepository placeVoteRepository;

    public PlaceVoteCrudFinder(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public Object findEntity(Object dto) throws CrudException {
        return null;
    }

    @Override
    public List findEntities(Object dto) throws CrudException {
        return null;
    }

    @Override
    public boolean isExist(Object dto) {
        return false;
    }
}
