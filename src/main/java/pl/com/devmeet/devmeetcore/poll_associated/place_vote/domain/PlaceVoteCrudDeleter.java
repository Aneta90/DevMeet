package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;

public class PlaceVoteCrudDeleter implements CrudEntityDeleter<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    PlaceVoteCrudDeleter(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    public PlaceVoteDto delete(PlaceVoteDto placeVoteDto) throws CrudException {
        if (!placeVoteCrudFinder.findEntityByMemberNick(placeVoteDto.getMember().getNick()).isEmpty()) {
            placeVoteDto.setActive(false);
            placeVoteDto.setCreationTime(DateTime.now());
            placeVoteCrudSaver.saveEntity(PlaceVoteMapper.map(placeVoteDto));
            return placeVoteDto;
        }
        throw new PlaceVoteNotFoundException("PlaceVote is not found in our database");
    }

    @Override
    public PlaceVoteEntity deleteEntity(PlaceVoteDto dto) {
        return null;
    }
}