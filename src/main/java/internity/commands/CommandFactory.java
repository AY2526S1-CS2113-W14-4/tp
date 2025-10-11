package internity.commands;

import internity.core.InternityException;

public class CommandFactory {
    public Command createCommand(String commandWord, String args) throws InternityException {
        return switch (commandWord) {
        case "add" -> new AddCommand(args);
        case "update" -> new UpdateCommand(args);
        case "exit" -> new ExitCommand();
        default -> throw InternityException.unknownCommand(commandWord);
        };
    }
}
