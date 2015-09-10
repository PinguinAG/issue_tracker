package ebuero.aatasiei.tracker.api.model;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public class NewDeveloperRequest {

    private String name;

    protected NewDeveloperRequest() {
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }
}
