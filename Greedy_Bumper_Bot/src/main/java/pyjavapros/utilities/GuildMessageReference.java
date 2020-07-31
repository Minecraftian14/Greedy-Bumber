package pyjavapros.utilities;

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
            command = event.getMessage().getContentRaw();
            command = command.substring(Launcher.refer_token.length(), command.indexOf(" "));
        }
        return command;
    }
}
