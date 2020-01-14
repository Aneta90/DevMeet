package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityFinder;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

import java.util.List;
import java.util.Optional;

public class PlaceVoteCrudFinder implements CrudEntityFinder <PlaceVoteDto,PlaceVoteEntity> {

    private PlaceVoteRepository placeVoteRepository;


    public PlaceVoteCrudFinder(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    @Override
    public PlaceVoteEntity findEntity(PlaceVoteDto dto) throws CrudException {
        return null; //??
    }

    public Optional<PlaceVoteEntity> findEntityByPlace(PlaceVoteDto dto){
        return placeVoteRepository.findByPlace(dto.getPlace().getPlaceName());
    }

    public Optional<PlaceVoteEntity> findEntityByPoll(PlaceVoteDto dto){
         return placeVoteRepository.findByPoll(dto.getPoll());
    }

    public Optional<PlaceVoteEntity> findEntityByMemberId(PlaceVoteDto dto){
        return placeVoteRepository.findByMember(dto.getMember().getNick());
    }

    @Override
    public List <PlaceVoteEntity> findEntities(PlaceVoteDto dto) throws CrudException {
        return null;
    }

    @Override
    public boolean isExist(PlaceVoteDto dto) {
        return false;
    }
}
