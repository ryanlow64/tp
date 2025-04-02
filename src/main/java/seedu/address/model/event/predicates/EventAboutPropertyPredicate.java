package seedu.address.model.event.predicates;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.Event;
import seedu.address.model.property.PropertyName;

/**
 * Tests that a {@code Event}'s {@code PropertyName} matches the {@code PropertyName} given.
 */
public class EventAboutPropertyPredicate extends EventPredicate<PropertyName> {

    /**
     * Constructs a {@code EventAboutPropertyPredicate}.
     *
     * @param propertyName The name of the property to be used as a filter.
     */
    public EventAboutPropertyPredicate(PropertyName propertyName) {
        super(propertyName);
        requireNonNull(propertyName);
    }

    @Override
    public boolean test(Event event) {
        String[] keywords = value.fullName.split("\\s+");
        return Stream.of(keywords)
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getPropertyName().fullName, keyword));
    }
}
