package fr.traqueur.hypnos.command.commands;

import java.util.List;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import fr.traqueur.hypnos.manager.ConnexionManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class JoinCommand extends VCommand {

	private Main bot;
	
	public JoinCommand(VCommand parent, boolean consoleCanExecute, Main bot) {
		super(parent, consoleCanExecute, false);
		this.bot = bot;
		this.addCommand("join");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		if (args.length <= 1) {
			return CommandType.SYNTAX_ERROR;
		}
		
		String subcommand = args[0];
		switch (subcommand) {
		case "message":
		case "msg":
			StringBuilder builder = new StringBuilder();
			for (String string : args) {
				if (!string.equalsIgnoreCase("message") && !string.equalsIgnoreCase("msg")) {
					builder.append(string + " ");
				}
			}
			bot.getSettings().setJoinMsg(builder.toString());
			ConnexionManager.saveSettings(bot.getSettings());
			sendMessage(getUser().getAsMention() + " vient de modifier le message de bienvenue.");
			break;
		
		case "salon":
		case "channel":
		case "chan":
			if (args.length > 2) {
				return CommandType.SYNTAX_ERROR;
			}
			List<TextChannel> channels = getMessage().getMentionedChannels();
			if (channels.isEmpty()) {
				return CommandType.SYNTAX_ERROR;
			}
			bot.getSettings().setJoinChannel(channels.get(0).getId());
			sendMessage(getUser().getAsMention() + " vient de modifier le channel des messages de bienvenue.");
			break;
		default:
			return CommandType.SYNTAX_ERROR;
		}
		
		
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public String getSyntaxe() {
		return bot.getSettings().getPrefix() + "join [salon/message] [channel/message], veuillez utiliser %s pour mentionner la personne qui rejoint.";
	}

	@Override
	public String getDescription() {
		return "Permet de gérer le salon et le message de bienvenue";
	}

}
