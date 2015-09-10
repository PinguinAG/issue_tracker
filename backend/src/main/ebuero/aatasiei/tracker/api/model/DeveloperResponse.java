package ebuero.aatasiei.tracker.api.model;

import ebuero.aatasiei.tracker.model.entities.Developer;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public class DeveloperResponse extends NewDeveloperRequest {

    private final Long id;

    public DeveloperResponse(Developer developer) {
        this.id = developer.getId();
        this.setName(developer.getName());
    }

    public Long getId() {
        return id;
    }
}
