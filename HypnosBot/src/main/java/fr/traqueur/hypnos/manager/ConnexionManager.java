package fr.traqueur.hypnos.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.security.auth.login.LoginException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.listener.BotListener;
import fr.traqueur.hypnos.listener.CommandListener;
import fr.traqueur.hypnos.listener.MemberConnexion;
import fr.traqueur.hypnos.listener.MessageListener;
import fr.traqueur.hypnos.utils.Logger;
import fr.traqueur.hypnos.utils.Logger.LogType;
import fr.traqueur.hypnos.utils.Settings;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;

public class ConnexionManager {
	
	private Main bot;
	
	private AccountManager accountManager;
	
	private static Gson gson;
	
	public ConnexionManager(Main bot) {
		this.bot = bot;
		this.accountManager = bot.getAccountManager();
		bot.setExecutor(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));
	}
	
	public void connect() throws LoginException, IOException {
		bot.setSettings(readSettings());
		bot.setJDA(new JDABuilder().setToken(bot.getSettings().getToken()).build());
		bot.getJDA().getPresence().setGame(Game.of(GameType.WATCHING, "le dieu Hypnos"));
		bot.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
		registerListener();
		tasks();
		Logger.log(LogType.SUCCESS, "Bot successful login!");
	}
	
	private void registerListener() {
		bot.getJDA().addEventListener(new CommandListener(bot));
		bot.getJDA().addEventListener(new MemberConnexion(bot));
		bot.getJDA().addEventListener(new MessageListener(bot));
		bot.getJDA().addEventListener(new BotListener(bot));
	}
	
	@SuppressWarnings("unused")
	private void tasks() {
		ScheduledFuture<?> save = bot.getExecutor().scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				accountManager.saveAccounts();
			}
		}, 10, 10, TimeUnit.MINUTES);
	}
	
	public Settings readSettings() throws IOException {
		gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File("settings.json");
		Reader in;
		in = new FileReader(file);
		JsonReader reader = new JsonReader(in);
		Settings settings = gson.fromJson(reader, Settings.class);
		reader.close();
		in.close();
		return settings;
	}
	
	public static void saveSettings(Settings settings) {
		gson = new GsonBuilder().setPrettyPrinting().create();
		File jFile = new File("settings.json");
		try {
			if (!jFile.exists()) {
				jFile.createNewFile();
			}
			Writer writer = new FileWriter(jFile);
			writer.write(gson.toJson(settings));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
