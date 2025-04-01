package seedu.address.model.commons;

/**
 * An interface that represents an object that has a name.
 * This is used to mark classes that have a name property.
 */
public interface Nameable<T> {
    Name<T> getFullName();
}
