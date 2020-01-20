package fr.traqueur.hypnos.listener;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.account.Account;
import fr.traqueur.hypnos.manager.AccountManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

	private Main bot;
	
	private AccountManager accountManager;
	private Settings settings;
	
	public BotListener(Main bot) {
		this.bot = bot;
		this.accountManager = bot.getAccountManager();
		this.settings = bot.getSettings();
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		Guild guild = event.getJDA().getGuildById(settings.getguildID());
		Account acc = accountManager.getAccountByUser(event.getJDA().getSelfUser());
		
		for (Member member : guild.getMembers()) {
			User user = member.getUser();
			if (!accountManager.contains(user)) {
				accountManager.setupAccount(user, event.getJDA().getSelfUser());
				acc.addOneJoinInvitation();
			}
		}
		accountManager.saveAccounts();
		bot.setInvites(guild.getInvites().complete());
		
		super.onReady(event);
	}
}
