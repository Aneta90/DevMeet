package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityCreator;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteCrudCreator implements CrudEntityCreator {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudCreator(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    @Override
    public Object createEntity(Object dto) throws CrudException {
        return null;
    }
}