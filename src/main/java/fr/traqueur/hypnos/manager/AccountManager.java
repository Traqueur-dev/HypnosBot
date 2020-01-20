package fr.traqueur.hypnos.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import fr.traqueur.hypnos.Main;
import fr.traqueur.hypnos.account.Account;
import fr.traqueur.hypnos.utils.Logger;
import fr.traqueur.hypnos.utils.Logger.LogType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.User;

public class AccountManager {
	
	private Main bot;
	
	private Gson gson;
	private HashMap<String, Account> accounts;
	
	public AccountManager(Main bot) {
		this.bot = bot;
		accounts = new HashMap<String, Account>();
		try {
			accounts = readAccounts();
		} catch (IOException e) {
			Logger.log(LogType.ERROR, "Error when bot read accounts.");
			e.printStackTrace();
		}
	}
	
	public void setupAccount(User user, User inviter) {
		Account account = getAccountByUser(user);
		if (account == null) {
			account = new Account(user, inviter);
		}
		addAccount(account);
	}
	
	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
		Logger.log(LogType.INFO, account.getName() + "'s account added with success.");
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account.getId());
		Logger.log(LogType.INFO, account.getName() + "'s account removed with success.");
	}
	
	public Account getAccountByUser(User user) {
		return accounts.get(user.getId());
	}
	
	public Account getAccountById(String id) {
		return accounts.get(id);
	}
	
	public boolean contains(Account account) {
		if (accounts.containsKey(account.getId())) return true;
		return false;
	}
	
	public boolean contains(User user) {
		if (accounts.containsKey(user.getId())) return true;
		return false;
	}
	
	public Invite getInvite(Guild guild) {
		List<Invite> newInvites = guild.getInvites().complete();
		for (Invite i : newInvites) {
			for (Invite i2 : bot.getInvites()) {
				if (i.getURL().equals(i2.getURL())) {
					if (i.getUses() > i2.getUses()) {
						return i;
					}
				}
			}
		}
		return null;
	}
	
	public HashMap<String, Account> readAccounts() throws IOException {
		gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File("accounts.json");
		Reader in;
		in = new FileReader(file);
		JsonReader reader = new JsonReader(in);
		HashMap<String, Account> acounts = gson.fromJson(reader, new TypeToken<HashMap<String, Account>>(){
			@SuppressWarnings("unused")
			private static final long serialVersionUID = 1L;
		}.getType());
		reader.close();
		in.close();
		return acounts;
	}
	
	public void saveAccounts() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		File jFile = new File("accounts.json");
		try {
			if (!jFile.exists()) {
				jFile.createNewFile();
			}
			Writer writer = new FileWriter(jFile);
			writer.write(gson.toJson(accounts));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.log(LogType.ERROR, "Accounts don't save.");
		}
		Logger.log(LogType.INFO, "Accounts saved with success.");
	}

	public void test() {
		System.out.println("test");
		
	}
}
