package fr.traqueur.hypnos.command.commands;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import fr.traqueur.hypnos.manager.AccountManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class SaveCommand extends VCommand {

	private AccountManager accountManager;
	private Settings settings;
	
	public SaveCommand(VCommand parent, boolean consoleCanExecute, Main bot) {
		super(parent, consoleCanExecute, false);
		this.addCommand("save");
		this.accountManager = bot.getAccountManager();
		this.settings = bot.getSettings();
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		accountManager.test();
		accountManager.saveAccounts();
		sendMessage(getUser().getAsMention() + " vient d'effectuer une sauvegarde.");
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_SERVER;
	}

	@Override
	public String getSyntaxe() {
		return settings.getPrefix() + "save";
	}

	@Override
	public String getDescription() {
		return "Permet d'effectuer une sauvegarde.";
	}

}
