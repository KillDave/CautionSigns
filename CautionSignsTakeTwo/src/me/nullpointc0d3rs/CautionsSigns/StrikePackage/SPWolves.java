package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class SPWolves extends StrikePackages{

	private Wolf[] wolves;
	
	public SPWolves(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.WOLVES);
		setID("Wolves");
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
			String message = getCautionSigns().getConfig().getString("Cautions.wolves.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.wolves.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}

	@Override
	public boolean stopAttack(){
		boolean temp = super.stopAttack();
		
		if(temp && wolves != null){
			for(Wolf w: wolves){
				w.remove();
			}
			wolves = null;
		}
		
		return true;
	}
	
	@Override
	public void strikePlayer() {
		if(wolves == null){
			wolves = new Wolf[4];
			for(int i = 0; i < wolves.length; i++){
				wolves[i] = (Wolf) getPlayer().getWorld().spawnCreature(getPlayer().getLocation(), EntityType.WOLF);
				wolves[i].setTarget(getPlayer());
				wolves[i].setAngry(true);
				wolves[i].damage(0, getPlayer());
			}
			
		}
		
		for(Wolf w: wolves){
			if(w.isDead()){
				w = (Wolf) getPlayer().getWorld().spawnCreature(getPlayer().getLocation(), EntityType.WOLF);
				w.setTarget(getPlayer());
				w.setAngry(true);
				w.damage(0, getPlayer());
			}
		}
	}

	
}
