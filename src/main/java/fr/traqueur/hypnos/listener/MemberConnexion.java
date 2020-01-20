package fr.traqueur.hypnos.listener;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.account.Account;
import fr.traqueur.hypnos.manager.AccountManager;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemberConnexion extends ListenerAdapter {

	private Settings settings;
	private AccountManager accountManager;

	public MemberConnexion(Main bot) {
		this.accountManager = bot.getAccountManager();
		this.settings = bot.getSettings();
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Guild guild = event.getGuild();
		User user = event.getUser();
		Invite invite = accountManager.getInvite(guild);
		accountManager.setupAccount(user, invite.getInviter());
		Account accInviter = accountManager.getAccountByUser(invite.getInviter());
		accInviter.addOneJoinInvitation();
		if (!settings.getJoinChannel().equalsIgnoreCase("")) {
			event.getGuild().getTextChannelById(settings.getJoinChannel()).sendMessage(String.format(settings.getJoinMsg(), user.getAsMention())).queue();
			return;
		}
	}

	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		User user = event.getUser();
		Account account = accountManager.getAccountByUser(user);
		Account accInviter = accountManager.getAccountById(account.getIdInviter());
		accInviter.addOneLeaveInvitation();
		accountManager.removeAccount(account);
		
		if (!settings.getLeaveChannel().equalsIgnoreCase("")) {
			event.getGuild().getTextChannelById(settings.getLeaveChannel()).sendMessage(String.format(settings.getLeaveMsg(), user.getAsTag())).queue();
			return;
		}

	}

}
