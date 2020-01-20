package fr.traqueur.hypnos.command.commands;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import fr.traqueur.hypnos.manager.ConnexionManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class BadWordsCommand extends VCommand {

	private Settings settings;

	public BadWordsCommand(VCommand parent, boolean consoleCanExecute, Main bot) {
		super(parent, consoleCanExecute, false);
		this.settings = bot.getSettings();
		this.addCommand("badwords");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		if (args.length < 1) return CommandType.SYNTAX_ERROR;
		
		String subCommand = args[0];
		List<String> badwords = settings.getBadwords();
		
		switch (subCommand) {
		case "add":
			if (args.length < 2) return CommandType.SYNTAX_ERROR;
			
			String newBadWord = args[1].toLowerCase();
			
			if (badwords.contains(newBadWord)) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setColor(Color.RED);
				builder.setDescription(":x: " + "La liste des mots interdits contient déjà ce mot.");
				getTextChannel().sendMessage(builder.build()).queue(m -> {
					m.delete().queueAfter(2, TimeUnit.SECONDS);
				});
				break;
			}
			
			badwords.add(newBadWord);
			sendMessage(getUser().getAsMention() + " vient d'ajouter un mot dans la liste des mots interdits.");
			break;
		case "remove":
if (args.length < 2) return CommandType.SYNTAX_ERROR;
			
			String oldBadWord = args[1].toLowerCase();
			
			if (!badwords.contains(oldBadWord)) {
				EmbedBuilder builder = new EmbedBuilder();
				builder.setColor(Color.RED);
				builder.setDescription(":x: " + "La liste des mots interdits ne contient pas ce mot.");
				getTextChannel().sendMessage(builder.build()).queue(m -> {
					m.delete().queueAfter(2, TimeUnit.SECONDS);
				});
				break;
			}
			
			badwords.removeIf(b -> b == oldBadWord);
			sendMessage(getUser().getAsMention() + " vient d'enlever un mot dans la liste des mots interdits.");
			break;
		case "list":
			StringBuilder builder = new StringBuilder();
			builder.append("__Liste de mots interdits:__\n");
			for (String badword : badwords) {
				builder.append(" - `" + badword + "`\n");
			}
			sendMessage(builder.toString());
			break;
		default:
			return CommandType.SYNTAX_ERROR;
		}
		
		settings.setBadwords(badwords);
		ConnexionManager.saveSettings(settings);
		
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public String getSyntaxe() {
		return settings.getPrefix() + "badwords [add/remove/list] <word>";
	}

	@Override
	public String getDescription() {
		return "Permet de gérer la liste des mots interdits.";
	}

}
