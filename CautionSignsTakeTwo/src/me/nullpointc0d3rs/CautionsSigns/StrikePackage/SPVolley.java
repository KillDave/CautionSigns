package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.util.Vector;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

public class SPVolley extends StrikePackages{
	
	private ArrayList<Arrow> usedArrows;

	public SPVolley(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.VOLLEY);
		setID("Volley");
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
			String message = getCautionSigns().getConfig().getString("Cautions.volley.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}		
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.volley.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}

	@Override
	public void strikePlayer() {
		if(usedArrows == null) usedArrows = new ArrayList<Arrow>();
		
		Vector vec = new Vector(0, -1, 0);
		Location loc = getPlayer().getLocation();
				
		Location tempLoc = loc;
		tempLoc = loc.add(0,3,0);
		usedArrows.add(getPlayer().getWorld().spawnArrow(tempLoc, vec, 6f, 12f));
	
	}
	
	@Override
	public boolean stopAttack(){
		boolean temp = super.stopAttack();
		
		if(temp){
			for(Arrow a: usedArrows){
				a.remove();
			}
		}
		
		return true;
	}

}
