package fr.traqueur.hypnos;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import fr.traqueur.hypnos.command.CommandManager;
import fr.traqueur.hypnos.manager.ConnexionManager;
import fr.traqueur.hypnos.utils.Logger;
import fr.traqueur.hypnos.utils.Logger.LogType;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.JDA;

public class Main {

	private Main bot;
	
	private JDA jda;
	private Settings settings;
	
	private ConnexionManager connexionManager;
	private CommandManager commandManager;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		bot = this;
		
		commandManager = new CommandManager(this);
		connexionManager = new ConnexionManager(this);
		try {
			connexionManager.connect();
			ConnexionManager.saveSettings(settings);
		} catch (LoginException e) {
			Logger.log(LogType.ERROR, "Error, when bot login.");
		} catch (IOException e) {
			Logger.log(LogType.ERROR, "Error with settings file, maybe it doesn't exist.");
		}
	}
	
	public void setJDA(JDA jda) {
		this.jda = jda;
	}
	
	public JDA getJDA() {
		return this.jda;
	}	
	public ConnexionManager getConnexionManager() {
		return this.connexionManager;
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	public Settings getSettings() {
		return this.settings;
	}

	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	public void setCommandManager(CommandManager commandManager) {
		this.commandManager = commandManager;
	}

	public Main getBot() {
		return bot;
	}

}
