package pl.com.devmeet.devmeet.domain_utils;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 01.12.2019
 * Time: 13:51
 *
 *
 * Class that implements that interface is useful when You want to save object e.g. to database.
 * E.g. If You want to save the new Messenger to DB, You must connect Member to it before You save Messenger object.
 *
 * @param <O> the output object parameter e.g. Member
 * @param <I> the input object parameter e.g. Messenger
 *
 */
public interface ObjectsConnector<O, I> {

    /**
     * Connect o.
     *
     * @param output the output object e.g. Member
     * @param input  the input object e.g. Messenger
     * @return the o is the output object that contains the connected input object
     * e.g. Member contains a connected Messenger
     */
    O connect(O output, I input);
}
