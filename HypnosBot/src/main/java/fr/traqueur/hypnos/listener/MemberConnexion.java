package fr.traqueur.hypnos.listener;

import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MemberConnexion extends ListenerAdapter {

	private Settings settings;

	public MemberConnexion(Settings settings) {
		this.settings = settings;
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		User user = event.getUser();
		
		if (!settings.getSalonMsg().equalsIgnoreCase("")) {
			event.getGuild().getTextChannelById(settings.getSalonMsg()).sendMessage(String.format(settings.getJoinMsg(), user.getAsMention())).queue();
			return;
		}
	}

	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		User user = event.getUser();
		if (!settings.getSalonMsg().equalsIgnoreCase("")) {
			event.getGuild().getTextChannelById(settings.getSalonMsg()).sendMessage(String.format(settings.getLeaveMsg(), user.getAsMention())).queue();
			return;
		}

	}

}
