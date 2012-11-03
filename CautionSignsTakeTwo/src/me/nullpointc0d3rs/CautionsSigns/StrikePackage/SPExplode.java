package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.entity.Player;

public class SPExplode extends StrikePackages{

	public SPExplode(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.EXPLODE);
		setID("Explode");
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
			String message = getCautionSigns().getConfig().getString("Cautions.explode.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.explode.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}

	@Override
	public void strikePlayer() {
		getPlayer().getLocation().getWorld().createExplosion(getPlayer().getLocation(), 0);	
		getPlayer().damage(7);
	}

}
