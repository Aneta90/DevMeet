package pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain;

import org.joda.time.DateTime;
import pl.com.devmeet.devmeetcore.domain_utils.CrudEntityDeleter;
import pl.com.devmeet.devmeetcore.domain_utils.exceptions.CrudException;
import pl.com.devmeet.devmeetcore.poll_associated.place_vote.domain.status_and_exceptions.PlaceVoteNotFoundException;

import java.util.List;
import java.util.Optional;

public class PlaceVoteCrudDeleter implements CrudEntityDeleter<PlaceVoteDto, PlaceVoteEntity> {

    private PlaceVoteCrudFinder placeVoteCrudFinder;
    private PlaceVoteCrudSaver placeVoteCrudSaver;

    public PlaceVoteCrudDeleter(PlaceVoteRepository placeVoteRepository) {
        this.placeVoteCrudFinder = new PlaceVoteCrudFinder(placeVoteRepository);
        this.placeVoteCrudSaver = new PlaceVoteCrudSaver(placeVoteRepository);
    }

    //User głosuje na 3 miejsca, w których chce się spotkać np.Costa, UW, McDonald, ale po jakimś czasie stwierdza, że nie może się jednak spotkać na UW i chce ten głos usunąć. Metoda wyszuka wszystkie głosy danego membera, a następie głos na konkretne miejsce, które chce usunąć. Dlatego wartoby zastanowić się nad ustawieniem @Unique na jakimś polu np. Place, żeby jeden user nie mógł zagłosować na dane miejsce 2 razy - do przemyślenia
    public PlaceVoteEntity deleteEntity(String memberNick, String placeName) throws CrudException {
        List<PlaceVoteEntity> placeVoteEntity = placeVoteCrudFinder.findEntityByMemberNick(memberNick);
        if (!placeVoteEntity.isEmpty()) {
            Optional<PlaceVoteEntity> placeVote = placeVoteEntity.stream().filter(a -> a.getPlace().getPlaceName().equals(placeName)).findFirst();
            placeVote.get().setActive(false);
            placeVote.get().setCreationTime(DateTime.now());
            placeVoteCrudSaver.saveEntity(placeVote.get());
        }
        throw new PlaceVoteNotFoundException("PlaceVote is not found in our database");

    }

    @Override
    public PlaceVoteEntity deleteEntity(PlaceVoteDto dto) throws CrudException {
        return null; //?? co z tym zrobić?
    }
}