package ebuero.aatasiei.tracker.model.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    protected Developer() {
    }

    public Developer(String name) {
        Objects.requireNonNull("Name must not be null!", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer)) return false;

        final Developer developer = (Developer) o;
        return id == developer.id &&
                Objects.equals(name, developer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
