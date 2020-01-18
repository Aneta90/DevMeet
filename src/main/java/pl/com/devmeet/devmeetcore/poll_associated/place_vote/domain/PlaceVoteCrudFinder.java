package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;

import java.util.List;
import java.util.Optional;

public class PlaceVoteCrudFinder implements CrudEntityFinder<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteRepository placeVoteRepository;

    PlaceVoteCrudFinder(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public PlaceVoteEntity findEntity(PlaceVoteDto dto) {
        return null;
    }

    List<PlaceVoteEntity> findEntityByPlace(String placeName) throws PlaceVoteNotFoundException {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByPlace(placeName);
        if (placeVoteEntity.isPresent()) {
            return placeVoteEntity.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote is not in database");
        }
    }

    List<PlaceVoteEntity> findEntityByMemberNick(String memberNick) throws PlaceVoteNotFoundException {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByMember(memberNick);
        if (placeVoteEntity.isPresent()) {
            return placeVoteEntity.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote is not found in our database FINDER");
        }
    }

    @Override
    public List<PlaceVoteEntity> findEntities(PlaceVoteDto dto) throws CrudException {
        Optional<List<PlaceVoteEntity>> placeVoteEntityList = placeVoteRepository.findAllActive(dto.isActive());
        if (placeVoteEntityList.isPresent()) {
            return placeVoteEntityList.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote not found in our database");
        }
    }

    @Override
    public boolean isExist(PlaceVoteDto dto) {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByMember(dto.getMember().getNick());
        return placeVoteEntity.isPresent();
    }
}