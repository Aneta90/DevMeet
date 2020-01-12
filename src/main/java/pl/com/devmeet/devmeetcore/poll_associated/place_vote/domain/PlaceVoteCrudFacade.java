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

    private PlaceVoteCrudCreator placeVoteCrudCreator () {
        return new PlaceVoteCrudCreator(placeVoteRepository);
    }

    private PlaceVoteCrudFinder placeVoteCrudFinder(){
        return new PlaceVoteCrudFinder(placeVoteRepository);
    }

    private PlaceVoteCrudUpdater placeVoteCrudUpdater(){
        return new PlaceVoteCrudUpdater(placeVoteRepository);
    }

    private PlaceVoteCrudDeleter placeVoteCrudDeleter(){
        return new PlaceVoteCrudDeleter(placeVoteRepository);
    }

    @Override
    public PlaceVoteDto add(PlaceVoteDto dto) throws CrudException {
        return null;
    }

    @Override
    public PlaceVoteDto update(PlaceVoteDto oldDto, PlaceVoteDto newDto) throws CrudException {
        return null;
    }

    @Override
    public PlaceVoteDto delete(PlaceVoteDto dto) throws CrudException {
        return null;
    }
}
