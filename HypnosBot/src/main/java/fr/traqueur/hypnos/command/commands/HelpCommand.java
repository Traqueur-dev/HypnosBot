package fr.traqueur.hypnos.command.commands;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class HelpCommand extends VCommand {

	private Main bot;
	
	public HelpCommand(VCommand parent, boolean consoleCanExecute, Main bot) {
		super(parent, consoleCanExecute, false);
		this.bot = bot;
		this.addCommand("help");
		this.addCommand("aide");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		bot.getCommandManager().sendHelp(getUser());
		
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MESSAGE_WRITE;
	}

	@Override
	public String getSyntaxe() {
		return bot.getSettings().getPrefix() + "help";
	}

	@Override
	public String getDescription() {
		return "Permet de recevoir une liste des commandes.";
	}

}
