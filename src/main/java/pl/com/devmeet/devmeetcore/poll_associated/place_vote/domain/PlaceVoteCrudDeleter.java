package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteCrudDeleter implements CrudEntityDeleter {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudDeleter(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    @Override
    public Object deleteEntity(Object dto) throws CrudException {
        return null;
    }
}