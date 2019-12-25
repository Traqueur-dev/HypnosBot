package fr.traqueur.hypnos.listener;

import java.util.ArrayList;
import java.util.List;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.CommandManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	private Settings settings;
	private final CommandManager manager;

	public CommandListener(Main bot) {
		this.settings = bot.getSettings();
		this.manager = bot.getCommandManager();

	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		onMessage(event);
		super.onMessageReceived(event);
	}
	
	
	
	private void onMessage(MessageReceivedEvent event){
		
		if (event.getAuthor().equals(event.getJDA().getSelfUser()))return;
		String message = event.getMessage().getContentDisplay();
		
		/*
		 * Système decommande
		 * */
		
		if (message.startsWith(settings.getPrefix())){
			
			message = message.replaceFirst(settings.getPrefix(), "");
			
			String command = message.split(" ")[0];
			message = message.replaceFirst(command, "");
			String[] tableau = message.length() != 0 ? get(message.split(" ")) : new String[0];

			manager.onCommand(event.getMessage(), command, tableau);
		}
		
		
	}
	
	private String[] get(String[] tableau){
		List<String> test = new ArrayList<>();
		for(int a = 1; a != tableau.length; a++){
			test.add(tableau[a]);
		}
		return test.toArray(new String[0]);
	}
	
}