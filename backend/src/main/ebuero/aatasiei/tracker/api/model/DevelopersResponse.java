package ebuero.aatasiei.tracker.api.model;

import ebuero.aatasiei.tracker.model.entities.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public class DevelopersResponse {

    private final List<DeveloperResponse> developers;

    public DevelopersResponse(Iterable<Developer> developers) {
        this.developers = StreamSupport.stream(developers.spliterator(), false)
                .map(DeveloperResponse::new)
                .collect(Collectors.toList());
    }

    public List<DeveloperResponse> getDevelopers() {
        return developers;
    }

}
