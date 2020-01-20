package fr.traqueur.hypnos.command.commands;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

public class ZobiCommand extends VCommand {

	private Main bot;

	public ZobiCommand(VCommand parent, boolean consoleCanExecute, Main bot) {
		super(parent, consoleCanExecute, false);
		this.bot = bot;
		this.addCommand("zobi");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setImage("https://image.noelshack.com/fichiers/2019/49/5/1575596783-index.jpeg");
		builder.addField("C'est Zobi la mouche!!!", "ZOBI ZOBI ZOBI", true);
		getTextChannel().sendMessage(builder.build()).queue();
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.ADMINISTRATOR;
	}

	@Override
	public String getSyntaxe() {
		return bot.getSettings().getPrefix() + "zobi";
	}

	@Override
	public String getDescription() {
		return "Gnééé, zobi la mouche!!!";
	}
	
}
