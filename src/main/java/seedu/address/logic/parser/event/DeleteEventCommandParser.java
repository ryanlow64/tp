package seedu.address.logic.parser.event;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new DeleteEventCommand object.
 */
public class DeleteEventCommandParser extends DeleteCommandParser<Event> {
    private static final Logger logger = LogsCenter.getLogger(DeleteEventCommandParser.class);

    @Override
    public DeleteEventCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for DeleteEventCommand: " + args);

        try {
            Index index = ParserUtil.parseIndex(args);
            logger.info("DeleteEventCommand object created");
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            logger.warning("Invalid arguments");
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
