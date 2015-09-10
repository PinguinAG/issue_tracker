package ebuero.aatasiei.tracker.model.enums;

/**
 * @author aatasiei
 * @version 1.0
 * @since 26-iul.-2015
 */
public enum IssueType {

    BUG, STORY;

    public final static class Values {
        public static final String BUG = "BUG";
        public static final String STORY = "STORY";

        private Values() {
        }
    }
}
