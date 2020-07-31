package pyjavapros;

import main.generalLogger.LOGGER;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pyjavapros.commands.BumpCommand;
import pyjavapros.utilities.Command;
import pyjavapros.utilities.GuildMessageReference;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class Launcher extends ListenerAdapter {

    public static final String refer_token = "/";

    ArrayList<Command> command_list = new ArrayList<>();

    public void initializeListener() {
        command_list.add(new BumpCommand());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        initializeListener();
        LOGGER.info("Bot ready and online!! Live on ", event.getGuildTotalCount(), " guilds.");
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(refer_token)) return;

        var ref = GuildMessageReference.build(event);

        command_list.stream().filter(cmd -> cmd.call.equals(ref.getCmd())).findFirst()
                .ifPresent(command -> command.onGuildMessageReceived(ref));

        LOGGER.general(ref);
    }

    public static void main(String[] args) {

        // TODO Set token be accessible from env
        var builder = JDABuilder.createDefault("NExNTMy.XrlT8Q.BzAEbgYTY7_f5t7fJFVQs3wJbVw");

        try {
            JDA jda = builder.build();
            jda.addEventListener(new Launcher());
        } catch (LoginException e) {
            LOGGER.error("JDA: busy.", e);
        }

    }

}
