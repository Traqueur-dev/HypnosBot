package fr.traqueur.hypnos.command.commands;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import fr.traqueur.hypnos.manager.ConnexionManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class LeaveCommand extends VCommand {

	public LeaveCommand(VCommand parent, boolean consoleCanExecute) {
		super(parent, consoleCanExecute, false);
		this.addCommand("messageleave");
		this.addCommand("msgleave");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		if (args.length == 0) {
			sendMessage("Veuillez entrer un message de leave. Utiliser <%s> pour désigner le pseudo du joueur qui rejoint.");;
			return CommandType.SYNTAX_ERROR;
		}
		StringBuilder builder = new StringBuilder();
		for (String string : args) {
			builder.append(string + " ");
		}
		Main.getSettings().setLeaveMsg(builder.toString());
		ConnexionManager.saveSettings(Main.getSettings());
		sendMessage(getUser().getAsMention() + " vient de modifier le message de leave.");
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public String getSyntaxe() {
		return Main.getSettings().getPrefix() + "msgleave <message>";
	}

	@Override
	public String getDescription() {
		return "Permet de modifier le message de leave.";
	}

}
