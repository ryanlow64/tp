package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.property.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property MAPLE = new PropertyBuilder().withPropertyName("Maple Villa Condominium")
            .withAddress("123 Maple Street").withPrice("2.4")
            .withSize("1000")
            .withDescription("Spacious 4-bedroom home").build();
    public static final Property ORCHID = new PropertyBuilder().withPropertyName("Orchid Gardens Condominium")
            .withAddress("234 Orchid Street")
            .withPrice("1.2").withSize("500")
            .withDescription("Spacious 2-bedroom home").build();
    public static final Property JURONG = new PropertyBuilder().withPropertyName("Jurong Lake Gardens HDB")
            .withAddress("336 Tah Ching Rd")
            .withPrice("0.6").withSize("600")
            .withDescription("Spacious 3-bedroom home").build();
    public static final Property PUNGGOL = new PropertyBuilder().withPropertyName("Punggol Waterway Ridges HDB")
            .withAddress("670C Edgefield Plains")
            .withPrice("0.9").withSize("1200")
            .withDescription("Spacious 5-bedroom home").build();

    private TypicalProperties() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical properties.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Property property : getTypicalProperties()) {
            ab.addProperty(property);
        }
        return ab;
    }

    public static List<Property> getTypicalProperties() {
        return new ArrayList<>(Arrays.asList(MAPLE, ORCHID, JURONG, PUNGGOL));
    }
}
