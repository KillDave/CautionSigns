package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

public abstract class StrikePackages {
		
	private String ID;
	private CSEnum ENUM;
	private Player player;
	private SignInfo signInfo; 
	private Timer timer;
	private int timerTick;
	private boolean warned;
	private boolean inRange;
	private boolean attacking;
	private CautionSigns cs;
		
	public String getID(){
		return ID;
	}
	
	public void setID(String ID){
		this.ID = ID;
	}
	
	public CSEnum getEnum(){
		return ENUM;
	}
	
	public void setEnum(CSEnum ENUM){
		this.ENUM = ENUM;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public SignInfo getSignInfo(){
		return signInfo;
	}
	
	public void setSignInfo(SignInfo signInfo){
		this.signInfo = signInfo;
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}
	
	public boolean getAttacking(){
		return attacking;
	}
	
	public void setAttacking(boolean attacking){
		this.attacking = attacking;
	}
	
	public int getTimerTick(){
		return timerTick;
	}
	
	public void setTimerTick(int timerTick){
		this.timerTick = timerTick;
	}
	
	public boolean getWarned(){
		return warned;
	}
	
	public void setWarned(boolean warned){
		this.warned = warned;
	}
	
	public boolean getInRange(){
		return inRange;
	}
	
	public void setInRange(boolean inRange){
		this.inRange = inRange;
	}
	
	public void attack() {		
		if(!getAttacking() && getInRange()){
			setTimer(new Timer());
			
			getTimer().scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
						strikePlayer();
				}
				
			},0, getTimerTick());
			setAttacking(true);
		}
	}
	
	public void playerInRange() {
		setInRange(true);
		
	}

	public void playerLeftRange() {
		setInRange(false);
	}
	
	public CautionSigns getCautionSigns(){
		return cs;
	}
	
	public void setCautionSigns(CautionSigns cs){
		this.cs = cs;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof StrikePackages){
			StrikePackages tempSP = (StrikePackages) obj;
			if(tempSP.getEnum() == getEnum() && tempSP.getPlayer().getName().equalsIgnoreCase(getPlayer().getName()) && tempSP.getSignInfo().equals(getSignInfo())){
				return true;
			}
		}
		
		return false;
		
	}
	
	
	//boolean determins if the overridden method should continue.
	public boolean stopAttack() {//3    //if no overridden stopAttack() ... this will always work or do and only this
		if(getAttacking()){//4   //i need to fix lighting ... but in ther theres no stopAttack()
			if(getTimer() != null){
				getTimer().cancel();
				getTimer().purge();
				setAttacking(false);
			}
			return true;//5
		}else{
			return false;
		}
	}
		
	
	public abstract void warnUser();
	
	public abstract void userLeftWarnZone();
	
	public abstract void strikePlayer();

	
}
