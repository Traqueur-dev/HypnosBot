package fr.traqueur.hypnos.command.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.command.VCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class ClearCommand extends VCommand {

	public ClearCommand(VCommand parent, boolean consoleCanExecute) {
		super(parent, consoleCanExecute, false);
		this.addCommand("clear");
	}

	@Override
	protected CommandType perform(Message message, String[] args) {
		if (args.length == 0) {
			sendMessage("Vous devez spécifier une quantité de messages à supprimer.");
			return CommandType.SYNTAX_ERROR;
		}
		
		if (args[0].equalsIgnoreCase("all")) {
			TextChannel newChan = (TextChannel) getGuild().getController().createCopyOfChannel((Channel) getMessageChannel()).complete();
			int position = ((Channel) getMessageChannel()).getPosition();
			newChan.getManager().setPosition(position++).complete();
			((Channel) getMessageChannel()).delete().complete();
			newChan.sendMessage("Vous venez de supprimer tout les messages du channel.").queue();
			return CommandType.SUCCESS;
		}
		
		
		int msgs = 0;
		try {
			msgs = Integer.parseInt(args[0]);
		} catch (NumberFormatException nfe) {
			sendMessage("Veuillez entrer un nombre valide.");
			return CommandType.SYNTAX_ERROR;
		}
		if (msgs <= 1 || msgs > 100) {
			sendMessage("Veuillez entrer un nombre entre **2 ~ 100**.");
			return CommandType.SYNTAX_ERROR;
		}
		getMessageChannel().deleteMessageById(getMessage().getId()).complete();
		getMessageChannel().getHistory().retrievePast(msgs).queue((List<Message> mess) -> {
			try {
				((TextChannel) getMessageChannel()).deleteMessages(mess).queue(
						success ->
						getMessageChannel().sendMessage(" `" + args[1] + "` messages supprimés.").queue(m -> {
									m.delete().queueAfter(2, TimeUnit.SECONDS);
								}),
						error -> getMessageChannel().sendMessage("Une erreur est survenue!").queue());
			} catch (IllegalArgumentException iae) {
				sendMessage("Impossible de supprimer un message de plus de 2 semaines.");
			}
		});
		return CommandType.SUCCESS;
	}

	@Override
	public Permission getPermission() {
		return Permission.MANAGE_CHANNEL;
	}

	@Override
	public String getSyntaxe() {
		return Main.getSettings().getPrefix() + "clear <quantité>";
	}

	@Override
	public String getDescription() {
		return "Permet de clear un salon.";
	}

}
