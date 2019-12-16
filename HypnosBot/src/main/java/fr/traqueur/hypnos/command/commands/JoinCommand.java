package fr.traqueur.hypnos.command.commands;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import fr.traqueur.hypnos.manager.ConnexionManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class JoinCommand extends VCommand {

	public JoinCommand(VCommand parent, boolean consoleCanExecute) {
		super(parent, consoleCanExecute, false);
		this.addCommand("messagebvn");
		this.addCommand("msgbvn");
		this.addCommand("messagebienvenue");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		if (args.length == 0) {
			sendMessage("Veuillez entrer un message de bienvenue. Utiliser <%s> pour désigner le pseudo du joueur qui rejoint.");;
			return CommandType.SYNTAX_ERROR;
		}
		
		Main.getSettings().setJoinMsg(args.toString());
		ConnexionManager.saveSettings(Main.getSettings());
		sendMessage(getUser().getAsMention() + " vient de modifier le message de bienvenue.");
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public String getSyntaxe() {
		return Main.getSettings().getPrefix() + "msgbvn <message>";
	}

	@Override
	public String getDescription() {
		return "Permet de modifier le message de bienvenue.";
	}

}
