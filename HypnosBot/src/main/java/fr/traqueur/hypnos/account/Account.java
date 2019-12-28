package fr.traqueur.hypnos.account;

import net.dv8tion.jda.core.entities.User;

public class Account {
	
	private String id;
	private String name;
	private boolean isMuted;
	private int leaveInvitations;
	private int joinInvitations;
	private int bonusInvitations;
	
	public Account(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.setMuted(false);
		this.setJoinInvitations(0);
		this.setLeaveInvitations(0);
		this.setBonusInvitations(0);
	}
	
	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public int getInvitations() {
		return getJoinInvitations() + getBonusInvitations() - getLeaveInvitations() < 0 ? 0 : getJoinInvitations() + getBonusInvitations() - getLeaveInvitations();
	}

	public int getLeaveInvitations() {
		return leaveInvitations;
	}

	public void setLeaveInvitations(int leaveInvitations) {
		this.leaveInvitations = leaveInvitations;
	}

	public int getJoinInvitations() {
		return joinInvitations;
	}

	public void setJoinInvitations(int joinInvitations) {
		this.joinInvitations = joinInvitations;
	}

	public int getBonusInvitations() {
		return bonusInvitations;
	}

	public void setBonusInvitations(int bonusInvitations) {
		this.bonusInvitations = bonusInvitations;
	}
	
}
