package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;

public class SPGolem extends StrikePackages{

	private IronGolem golem;
	private boolean didGolemSpawn;
	
	public SPGolem(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.GOLEM);
		setID("Golem");
		setPlayer(player);
		setSignInfo(signInfo);
		setAttacking(false);
		setWarned(false);
		setInRange(false);
		setCautionSigns(cs);
		setTimerTick(500);
		didGolemSpawn = false;
	}
	
	@Override
	public void warnUser() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.golem.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.golem.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
	}

	@Override
	public boolean stopAttack(){
		boolean temp = super.stopAttack();
		
		if(temp && didGolemSpawn){
			golem.remove();
			didGolemSpawn = false;
		}
		
		return true;
	}
	
	@Override
	public void strikePlayer() {
		if(!didGolemSpawn || golem.isDead()){
			golem = (IronGolem) getPlayer().getWorld().spawnCreature(getPlayer().getLocation().add(1,1,1), EntityType.IRON_GOLEM);
			golem.setTarget(getPlayer());
			golem.damage(0, getPlayer());
			didGolemSpawn = true;
		}
	}

}
