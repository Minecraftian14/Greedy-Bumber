package pyjavapros.utilities;

import main.generalLogger.LOGGER;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import pyjavapros.Launcher;

public class GuildMessageReference {

    String command = null;

    private GuildMessageReference() {
    }

    public GuildMessageReceivedEvent event;

    public static GuildMessageReference build(GuildMessageReceivedEvent _event) {

        var ref = new GuildMessageReference();
        ref.event = _event;

        return ref;

    }

    public String getCmd() {
        if (command == null) {
            command = event.getMessage().getContentRaw().substring(Launcher.refer_token.length());
            if (command.contains(" "))
                command = command.substring(0, command.indexOf(" "));
            LOGGER.general(command);
        }
        return command;
    }
}
