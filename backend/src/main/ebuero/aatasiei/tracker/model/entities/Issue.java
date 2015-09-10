package ebuero.aatasiei.tracker.model.entities;

import com.google.common.base.MoreObjects;
import ebuero.aatasiei.tracker.model.enums.IssueType;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract entity that represents an issue tracker's base entity.
 *
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "issueType", discriminatorType = DiscriminatorType.STRING, length = 10)
public abstract class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    protected String status;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @ManyToOne(targetEntity = Developer.class)
    private Developer assignedDeveloper;

    protected Issue() {
    }

    public Issue(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Developer getAssignedDeveloper() {
        return assignedDeveloper;
    }

    public void setAssignedDeveloper(Developer assignedDeveloper) {
        this.assignedDeveloper = assignedDeveloper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Issue)) return false;

        final Issue issue = (Issue) o;

        return id == issue.id &&
                Objects.equals(title, issue.title) &&
                Objects.equals(description, issue.description) &&
                Objects.equals(creationDate, issue.creationDate) &&
                Objects.equals(assignedDeveloper, issue.assignedDeveloper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate, assignedDeveloper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("issueType", getIssueType())
                .add("title", title)
                .add("description", description)
                .add("creationDate", creationDate)
                .add("assignedDeveloper", assignedDeveloper)
                .toString();
    }

    /**
     * @return the {@link IssueType} of the current issue.
     */
    @Transient
    public final IssueType getIssueType() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : IssueType.valueOf(val.value());
    }

}
