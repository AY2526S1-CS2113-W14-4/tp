package internity.cli;

import internity.commands.DeleteCommand;
import internity.commands.UpdateCommand;
import internity.core.InternityException;

public final class ArgumentParser {
    private ArgumentParser() {} // prevent instantiation

    public static DeleteCommand parseDeleteCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidDeleteCommand();
        }
        try {
            int oneBasedIndex = Integer.parseInt(args.trim());

            int zeroBasedIndex = oneBasedIndex - 1;

            return new DeleteCommand(zeroBasedIndex);

        } catch (NumberFormatException e) {
            throw InternityException.invalidInternshipIndex();
        }
    }

    public static UpdateCommand parseUpdateCommandArgs(String args) throws InternityException {
        String newStatus;
        int zeroBasedIndex;
        try {
            String[] splitArgs = args.split("\\s+status/");
            zeroBasedIndex = Integer.parseInt(splitArgs[0].trim()) - 1;
            newStatus = splitArgs[1].trim();
        } catch (Exception e) {
            throw new InternityException("Invalid update command format. Use: update INDEX status/NEW_STATUS");
        }
        return new UpdateCommand(zeroBasedIndex, newStatus);
    }
}
