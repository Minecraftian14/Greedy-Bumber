package pyjavapros.commands;

import pyjavapros.utilities.Command;
import pyjavapros.utilities.GuildMessageReference;

public class BumpCommand extends Command {

    public BumpCommand() {
        super("bump");
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReference ref) {

        ref.event.getChannel().sendMessage("Hello Everyone!").queue();

    }
}
