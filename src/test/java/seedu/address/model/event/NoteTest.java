package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void equals() {
        Note note1 = new Note("thequickbrownfoxjumpsoverthelazydog");
        Note note2 = new Note("thequickbrownfoxjumpsoverthelazydog");
        Note note3 = new Note("thequickbrowndogjumpsoverthelazyfox");
        assertTrue(note1.equals(note2));
        assertFalse(note1.equals(note3));
    }

    @Test
    public void toStringMethod() {
        String text = "thequickbrownfoxjumpsoverthelazydog";
        Note note = new Note(text);
        assertEquals(text, note.toString());
    }
}
