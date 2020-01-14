package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.devmeet.devmeetcore.domain_utils.CrudFacadeInterface;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;

@Service
public class PlaceVoteCrudFacade implements CrudFacadeInterface <PlaceVoteDto, PlaceVoteEntity>{

    private PlaceVoteRepository placeVoteRepository;

    @Autowired
    public PlaceVoteCrudFacade(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteRepository = placeVoteRepository;
    }

    private PlaceVoteCrudCreator initCreator () {
        return new PlaceVoteCrudCreator(placeVoteRepository);
    }

    private PlaceVoteCrudFinder initFinder(){
        return new PlaceVoteCrudFinder(placeVoteRepository);
    }

    private PlaceVoteCrudUpdater initUpdater(){
        return new PlaceVoteCrudUpdater(placeVoteRepository);
    }

    private PlaceVoteCrudDeleter initDeleter(){
        return new PlaceVoteCrudDeleter(placeVoteRepository);
    }

    @Override
    public PlaceVoteDto add(PlaceVoteDto dto) throws CrudException {
        return map(initCreator().createEntity(dto));
    }

    @Override
    public PlaceVoteDto update(PlaceVoteDto oldDto, PlaceVoteDto newDto) throws CrudException {
        return map(initUpdater().updateEntity(oldDto,newDto));
    }

    @Override
    public PlaceVoteDto delete(PlaceVoteDto dto) throws CrudException {
        return null;
    }

    public static PlaceVoteDto map(PlaceVoteEntity placeVoteEntity){
        return PlaceVoteMapper.map(placeVoteEntity);
    }

    public static PlaceVoteEntity map(PlaceVoteDto placeVoteDto){
        return PlaceVoteMapper.map(placeVoteDto);
    }
}
