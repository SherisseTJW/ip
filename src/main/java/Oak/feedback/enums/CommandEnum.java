package Oak.feedback.enums;

public enum CommandEnum {
    Bye("bye"),
    List("list"),
    Mark("mark"),
    Unmark("unmark"),
    Delete("delete"),
    Todo("todo"),
    Deadline("deadline"),
    Event("event");
    public final String commandValue;

    private CommandEnum(String commandValue) {
        this.commandValue = commandValue;
    }

    public String getCommandValue() {
        return this.commandValue;
    }

    // @@author SherisseTJW-reused
    // Reused from https://www.baeldung.com/java-search-enum-values, Section 3. Searching an Enum by Value
    // with minor modifications
    public static CommandEnum getCommandEnum(String value) {
        for (CommandEnum commandEnum : values()) {
            if (commandEnum.getCommandValue().equalsIgnoreCase(value)) {
                return commandEnum;
            }
        }

        return null;
    }
}
