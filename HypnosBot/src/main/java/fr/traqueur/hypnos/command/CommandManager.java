package fr.traqueur.hypnos.command;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand.CommandType;
import fr.traqueur.hypnos.command.commands.ClearCommand;
import fr.traqueur.hypnos.command.commands.JoinCommand;
import fr.traqueur.hypnos.command.commands.LeaveCommand;
import fr.traqueur.hypnos.command.commands.ZobiCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class CommandManager {

	private List<VCommand> commands = new ArrayList<>();
	
	public CommandManager() {
		this.register();
	}
	
	/*
	 * Register commands
	 * */
	
	private void register(){
		
		/**
		 * @param VCOMMAND -> NO WORK ON THIS VERSION
		 * @param BOOLEAN -> Use by console
		 * @param BOOLEAN -> Use in every channel
		 * */
		
		addCommand(new ZobiCommand(null, false));
		addCommand(new ClearCommand(null, false));
		addCommand(new JoinCommand(null, false));
		addCommand(new LeaveCommand(null, false));
		
		
		//System.out.println("[HypnosBot] Nombre de commande enregistrée(s): " + commands.size());
		
	}
	
	public List<VCommand> getCommands() {
		return commands;
	}
	
	private VCommand addCommand(VCommand command){
		commands.add(command);
		return command;
	}
	
	/*
	 * Execute commande
	 * */
	
	public void onCommand(Message message, String command, String[] args){
		
 		for(VCommand zCommand : commands)
 			if (zCommand.getSubCommands().contains(command.toLowerCase())){
 				processCommand(zCommand, message, command, args);
 				return;
 			}
 		
 		System.out.println("[HypnosBot] Cette commande n'existe pas !");
		
	}
	
	/*
	 * Exécuter la commande
	 * */
	
	private void processCommand(VCommand zCommand, Message message, String command, String[] args){
		
		if (zCommand.consoleCanExecute() && message == null){
			
			/* On exécute la console en mode console */
			
			zCommand.setMessage(null);
			zCommand.perform(null, args);
			
			return;
		}else if (message == null && !zCommand.consoleCanExecute()){
			System.out.println("[HypnosBot] Vous ne pouvez pas exécuter cette commande !");return;
		}
		
		/* On exécute la console en mode joueur */
		
		if (zCommand.onlyUseInCommandChannel()) if (!canExecuteHere(message)) return;
		
		User user = message.getAuthor();
		zCommand.setMessage(message);
		
		if (zCommand.getPermission() == null || message.getGuild().getMember(user).hasPermission(zCommand.getPermission())){
			
			if (zCommand.perform(message, args).equals(CommandType.SYNTAX_ERROR)){
				
				message.delete().complete();
				sendErrorMessage("Vous devez exécuter la commande comme ceci: " + zCommand.getSyntaxe(), message.getTextChannel().getIdLong());
				
			}else logCommand(message, args, command);
			
			return;
		}
		
		message.delete().complete();
		sendErrorMessage(message.getAuthor().getAsMention() + " Vous n'avez pas la permission de faire cette commande !", message.getTextChannel().getIdLong());
		
		
	}
	
	/*
	 * Send message error who remove after 3 seconds
	 * */
	
	public void sendErrorMessage(String message, long id, Message supp){
		supp.delete().complete();
		sendErrorMessage(message, id);
	}
	
	public void sendErrorMessage(String message, long id){
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(Color.RED);			
		builder.setDescription(":x: "+message);		
		Message messages = Main.getJDA().getTextChannelById(id).sendMessage(builder.build()).complete();
		
	    Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
			
			@Override
			public void run() {
				messages.delete().complete();
			}
		}, 3, TimeUnit.SECONDS);
	}
	
	public boolean canExecuteHere(Message message){
		if (!message.getTextChannel().getId().equals(Main.getSettings().getCommandChannel())){
			message.delete().complete();
			sendErrorMessage(message.getAuthor().getAsMention() + " vous ne pouvez pas exécuter de commande ici !", message.getTextChannel().getIdLong());
			return false;
		}			
		return true;
	}
	
	
	private void logCommand(Message message, String[] args, String command){
		TextChannel channel = message.getGuild().getTextChannelById(Main.getSettings().getLogChannel());
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setColor(Color.GREEN);
		builder.setTimestamp(OffsetDateTime.now());
		builder.setTitle(message.getAuthor().getName());
		builder.setFooter("Hypnos - 2019 ", null);
		builder.setDescription("Utilisation de la commande: **!" + command +"** "  + (getStringArgs(args) != null ? getStringArgs(args) : "") + " dans le channel " + message.getTextChannel().getAsMention());
		
		channel.sendMessage(builder.build()).complete();
		
	}
	
	private String getStringArgs(String[] args){
		
		if (args.length != 0){
			StringBuilder builder = new StringBuilder();
			
			for(String s : args) builder.append(s +" ");
			
			return "avec les arguments: **"+builder.toString()+"**";
			
		}
		return null;
		
		
	}
}