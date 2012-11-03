package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SPSkyDive extends StrikePackages{

	public SPSkyDive(Player player, SignInfo signInfo, CautionSigns cs) {
		setEnum(CSEnum.SKYDIVE);
		setID("SkyDive");
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
			String message = getCautionSigns().getConfig().getString("Cautions.skydive.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.skydive.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}

	@Override
	public void strikePlayer() {
		getPlayer().teleport(new Location(getPlayer().getLocation().getWorld(), getPlayer().getLocation().getX(), 300, getPlayer().getLocation().getZ()));
	}

}
