package fr.traqueur.hypnos.listener;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.manager.AccountManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

	private AccountManager accountManager;
	private Settings settings;
	
	public BotListener(Main bot) {
		this.accountManager = bot.getAccountManager();
		this.settings = bot.getSettings();
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		Guild guild = event.getJDA().getGuildById(settings.getguildID());
		for (Member member : guild.getMembers()) {
			User user = member.getUser();
			if (!accountManager.contains(user)) {
				accountManager.setupAccount(user);
			}
		}
		accountManager.saveAccounts();
		super.onReady(event);
	}
}
