package de.michi.clashofdiscord.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Created by Michi on 10.06.2019.
 */
public interface Command {

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean sucess, MessageReceivedEvent event);
    String help();

}

