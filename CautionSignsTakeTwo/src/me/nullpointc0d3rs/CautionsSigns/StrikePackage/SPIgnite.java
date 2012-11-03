package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.entity.Player;

public class SPIgnite extends StrikePackages{

	public SPIgnite(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.IGNITE);
		setID("Ignite");
		setPlayer(player);
		setSignInfo(signInfo);
		setAttacking(false);
		setWarned(false);
		setInRange(false);
		setCautionSigns(cs);
		setTimerTick(500);
	}

	@Override
	public void warnUser() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.ignite.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}	
	}

	@Override
	public void userLeftWarnZone() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.ignite.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}		
	}

	@Override
	public void strikePlayer() {
		getPlayer().setFireTicks(1000);
		
	}
	
	public boolean stopAttack(){
		boolean temp = super.stopAttack();
		
		if(temp){
			getPlayer().setFireTicks(0);
		}
		
		return true;
	}
	
}
