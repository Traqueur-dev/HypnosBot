package fr.traqueur.hypnos.account;

import net.dv8tion.jda.core.entities.User;

public class Account {
	
	private String idInviter;
	private String id;
	private String name;
	private boolean isMuted;
	private int leaveInvitations;
	private int joinInvitations;
	private int bonusInvitations;
	
	public Account(User user, User inviter) {
		this.setIdInviter(inviter.getId());
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
		return joinInvitations + bonusInvitations - leaveInvitations < 0 ? 0 : joinInvitations + bonusInvitations - leaveInvitations;
	}

	public int getLeaveInvitations() {
		return leaveInvitations;
	}
	
	public void addLeaveInvitations(int number) {
		setLeaveInvitations(getLeaveInvitations() + number);
	}
	
	public void addOneLeaveInvitation() {
		addLeaveInvitations(1);
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
	
	public void addJoinInvitations(int number) {
		setJoinInvitations(getJoinInvitations() + number);
	}
	
	public void addOneJoinInvitation() {
		addJoinInvitations(1);
	}

	public int getBonusInvitations() {
		return bonusInvitations;
	}

	public void setBonusInvitations(int bonusInvitations) {
		this.bonusInvitations = bonusInvitations;
	}
	
	public void addBonusInvitations(int number) {
		setBonusInvitations(getBonusInvitations() + number);
	}
	
	public void addOneBonusInvitation() {
		addBonusInvitations(1);
	}

	public String getIdInviter() {
		return idInviter;
	}

	public void setIdInviter(String idInviter) {
		this.idInviter = idInviter;
	}
	
}
