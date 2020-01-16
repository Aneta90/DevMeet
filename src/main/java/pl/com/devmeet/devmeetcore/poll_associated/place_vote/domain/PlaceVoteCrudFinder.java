package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;
import pl.com.devmeet.devmeetcore.poll_associated.poll.domain.PollCrudFacade;

import java.util.List;
import java.util.Optional;

public class PlaceVoteCrudFinder implements CrudEntityFinder<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteRepository placeVoteRepository;

    public PlaceVoteCrudFinder(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public PlaceVoteEntity findEntity(PlaceVoteDto dto) throws CrudException {
        return null; //?? Jak wyciągnąć PlaceVote skoro nie ma ID w dto?
    }

    public List<PlaceVoteEntity> findEntityByPlace(PlaceVoteDto dto) throws PlaceVoteNotFoundException {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByPlace(dto.getPlace().getPlaceName());
        if (placeVoteEntity.isPresent()) {
            return placeVoteEntity.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote is not in database");
        }
    }

    public List<PlaceVoteEntity> findEntityByPoll(PlaceVoteDto dto) throws PlaceVoteNotFoundException {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByPoll(PollCrudFacade.map(dto.getPoll()));
        if (placeVoteEntity.isPresent()) {
            return placeVoteEntity.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote not found in our database");
        }  ///DO SPRAWDZENIA
    }

    public List<PlaceVoteEntity> findEntityByMemberNick(String memberNick) throws PlaceVoteNotFoundException {
        Optional<List<PlaceVoteEntity>> placeVoteEntity = placeVoteRepository.findByMember(memberNick); // chyba jednak pojedynczy PlaceVoteEntity, bo jak member chce zagłosować na kilka miejsc to musi stworzyć kilka placeVotów
        if (placeVoteEntity.isPresent()) {
            return placeVoteEntity.get();
        } else {
            throw new PlaceVoteNotFoundException("PlaceVote is not found in our database");
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