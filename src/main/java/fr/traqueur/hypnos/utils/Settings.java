package fr.traqueur.hypnos.utils;

import java.util.List;

public class Settings {
	
	private String token;
	private String prefix;
	private String guildID;
	private String joinChannel;
	private String joinMsg;
	private String leaveChannel;
	private String leaveMsg;
	private String staffChannel;
	private List<String> badwords;
	private String commandChannel;
	private String logChannel;
	
	public Settings() {}
	
	public String getToken() {
		return token;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String newPrefix) {
		this.prefix = newPrefix;
	}
	
	public String getguildID() {
		return guildID;
	}

	public List<String> getBadwords() {
		return badwords;
	}

	public void setBadwords(List<String> badwords) {
		this.badwords = badwords;
	}

	public String getStaffChannel() {
		return staffChannel;
	}

	public void setStaffChannel(String staffChannel) {
		this.staffChannel = staffChannel;
	}

	public String getLogChannel() {
		return logChannel;
	}

	public void setLogChannel(String logChannel) {
		this.logChannel = logChannel;
	}

	public String getCommandChannel() {
		return commandChannel;
	}

	public void setCommandChannel(String commandChannel) {
		this.commandChannel = commandChannel;
	}

	public String getJoinMsg() {
		return joinMsg;
	}

	public void setJoinMsg(String joinMsg) {
		this.joinMsg = joinMsg;
	}
	
	public String getLeaveMsg() {
		return leaveMsg;
	}

	public void setLeaveMsg(String leaveMsg) {
		this.leaveMsg = leaveMsg;
	}

	public String getJoinChannel() {
		return joinChannel;
	}

	public void setJoinChannel(String joinChannel) {
		this.joinChannel = joinChannel;
	}

	public String getLeaveChannel() {
		return leaveChannel;
	}

	public void setLeaveChannel(String leaveChannel) {
		this.leaveChannel = leaveChannel;
	}
	
}
