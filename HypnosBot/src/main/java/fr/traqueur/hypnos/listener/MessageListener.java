package fr.traqueur.hypnos.listener;

import java.util.concurrent.TimeUnit;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

	private Settings settings;

	public MessageListener(Main bot) {
		this.settings = bot.getSettings();
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		TextChannel channel = event.getTextChannel();
		User user = event.getAuthor();
		Message message = event.getMessage();
		
		String[] args = message.getContentRaw().split(" ");
		
		if (message.getMentionedMembers().size() >= 4) {
			channel.sendMessage(user.getAsMention() + " il est interdit de mentionner plus de 4 personnes en même temps.").queue(m -> {
				m.delete().queueAfter(2, TimeUnit.SECONDS);
			});
			message.delete().complete();
			return;
		}
		
		for (String arg : args) {
			for (String badword : settings.getBadwords()) {
				if (arg.equalsIgnoreCase(badword) && !args[0].contains("bad")) {
				event.getMessage().delete().complete();
				event.getChannel().sendMessage("Veuillez surveiller votre langage.").queue(m -> {
					m.delete().queueAfter(2, TimeUnit.SECONDS);
				});
				return;
				}
			}
		}
		
		super.onMessageReceived(event);
	}
	
}
