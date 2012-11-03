package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import org.bukkit.entity.Player;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

public class SPLightning extends StrikePackages {

	public SPLightning(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.LIGHTNING);
		setID("Lightning");
		setPlayer(player);
		setSignInfo(signInfo);
		setAttacking(false);
		setWarned(false);
		setInRange(false);
		setCautionSigns(cs);
		setTimerTick(500);
	}

	@Override
	public void strikePlayer(){
		getPlayer().getLocation().getWorld().strikeLightning(getPlayer().getLocation());
	}
		
	
	@Override
	public void warnUser() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.lightning.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}		
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.lightning.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}
	
	//and yet it works :D
	@Override
	public boolean stopAttack(){
		super.stopAttack();
		
		getPlayer().setFireTicks(0);		
		
		return true;
	}

	@Override
	public String toString() {
		return "SPLightning [stopAttack()=" + stopAttack() + ", getID()="
				+ getID() + ", getEnum()=" + getEnum() + ", getPlayer()="
				+ getPlayer() + ", getSignInfo()=" + getSignInfo()
				+ ", getTimer()=" + getTimer() + ", getAttacking()="
				+ getAttacking() + ", getTimerTick()=" + getTimerTick()
				+ ", getWarned()=" + getWarned() + ", getInRange()="
				+ getInRange() + ", getCautionSigns()=" + getCautionSigns()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	

}