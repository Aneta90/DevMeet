package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityUpdater;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

public class PlaceVoteCrudUpdater implements CrudEntityUpdater {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudUpdater(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    @Override
    public Object updateEntity(Object oldDto, Object newDto) throws CrudException {
        return null;
    }
}