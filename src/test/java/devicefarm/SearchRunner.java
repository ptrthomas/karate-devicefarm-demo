package devicefarm;

import com.intuit.karate.junit5.Karate;

/**
 *
 * @author pthomas3
 */
class SearchRunner {

    @Karate.Test
    Karate testSearch() {
        return new Karate().feature("search").relativeTo(getClass());
    }

}
