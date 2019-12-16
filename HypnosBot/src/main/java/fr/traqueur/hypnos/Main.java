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

	private static JDA jda;
	private static Settings settings;
	
	private static ConnexionManager connexionManager;
	private static CommandManager commandManager;
	
	public static void main(String[] args) {
		commandManager = new CommandManager();
		connexionManager = new ConnexionManager();
		try {
			connexionManager.connect();
			ConnexionManager.saveSettings(settings);
		} catch (LoginException e) {
			Logger.log(LogType.ERROR, "Error, when bot login.");
		} catch (IOException e) {
			Logger.log(LogType.ERROR, "Error with settings file, maybe it doesn't exist.");
		}
	}
	
	public static void setJDA(JDA jda) {
		Main.jda = jda;
	}
	
	public static JDA getJDA() {
		return Main.jda;
	}	
	public static ConnexionManager getConnexionManager() {
		return Main.connexionManager;
	}
	
	public static void setSettings(Settings settings) {
		Main.settings = settings;
	}
	
	public static Settings getSettings() {
		return Main.settings;
	}

	public static CommandManager getCommandManager() {
		return commandManager;
	}

	public static void setCommandManager(CommandManager commandManager) {
		Main.commandManager = commandManager;
	}
}
