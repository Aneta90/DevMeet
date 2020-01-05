package pl.com.devmeet.devmeetcore.test_utils;


import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyActiveException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserAlreadyExistsException;
import pl.com.devmeet.devmeetcore.user.domain.status_and_exceptions.UserNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: Kamil Ptasinski
 * Date: 30.11.2019
 * Time: 12:47
 *
 * @param <R> the type parameter is the repository e. g. UserRepository
 * @param <F> the type parameter is the facade e. g. UserCrudFacade
 * @param <D> the type parameter is the DTO e. g. UserDto
 */
interface TestObjectInitiator <R, F, D> {

    /**
     * @param private R repository
     * @param private [Object] test[Object]
     */

    /**
     * public CONSTRUCTOR (R repository)
     *
     * Init test object and save it to database
     *
     * @param repository the repository
     */

    /**
     * Init facade f.
     *
     * @return the facade
     */
    F initFacade ();

    /**
     * Init and save test object d.
     *
     * @return the DTO
     */
    D initAndSaveTestObject() throws UserAlreadyExistsException, UserNotFoundException, UserAlreadyActiveException;

}
