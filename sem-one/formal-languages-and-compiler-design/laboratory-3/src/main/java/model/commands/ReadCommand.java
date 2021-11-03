package model.commands;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public sealed abstract class ReadCommand extends Command permits ReadSequenceCommand{
    protected final String resourceName;

    protected ReadCommand(String resourceName) {
        this.resourceName = resourceName;
    }
}
