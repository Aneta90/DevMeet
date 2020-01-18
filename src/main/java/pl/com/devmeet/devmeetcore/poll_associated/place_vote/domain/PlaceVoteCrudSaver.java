package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntitySaver;

public class PlaceVoteCrudSaver implements CrudEntitySaver<PlaceVoteEntity, PlaceVoteEntity> {

    private PlaceVoteRepository placeVoteRepository;

    PlaceVoteCrudSaver(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public PlaceVoteEntity saveEntity(PlaceVoteEntity entity) {
        return placeVoteRepository.save(entity);
    }
}