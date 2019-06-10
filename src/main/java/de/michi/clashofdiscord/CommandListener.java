package de.michi.clashofdiscord;

import de.michi.clashofdiscord.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Created by Michi on 10.06.2019.
 */
public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().startsWith(ClashOfDiscord.instance.getPrefix()) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId() && !event.getAuthor().isBot()) {
            CommandHandler.handleCommand(CommandHandler.parse.parser(event.getMessage().getContentRaw(), event));
        }

    }


}
