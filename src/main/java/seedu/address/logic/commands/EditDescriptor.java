package seedu.address.logic.commands;

/**
 * Stores the details to edit the client with. Each non-empty field value will replace the
 * corresponding field value of the client.
 */
public abstract class EditDescriptor<T> {

    public EditDescriptor() {}

    /**
     * Returns true if at least one field is edited.
     */
    public abstract boolean isAnyFieldEdited();
}
