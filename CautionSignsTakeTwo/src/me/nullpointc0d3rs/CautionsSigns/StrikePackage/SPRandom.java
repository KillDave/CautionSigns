package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import java.util.Random;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;


import org.bukkit.entity.Player;

public class SPRandom extends StrikePackages{

	private int counterTick;
	private boolean hasStrike;
	private StrikePackages randomStrike;
	
	public SPRandom(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.RANDOM);
		setID("Random");
		setPlayer(player);
		setSignInfo(signInfo);
		setAttacking(false);
		setWarned(false);
		setInRange(false);
		setCautionSigns(cs);
		setTimerTick(1000);
		counterTick = 0;
		hasStrike = false;
	}
	
	@Override
	public void warnUser() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.random.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}	
		
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.random.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}
		
	}

	@Override
	public void strikePlayer() {
		if(!hasStrike){
			randomStrike = getNewStrike(getPlayer(), getSignInfo(), getCautionSigns());
			randomStrike.attack();
		}
		
		if(counterTick >= 5){
			randomStrike.stopAttack();
			randomStrike = getNewStrike(getPlayer(), getSignInfo(), getCautionSigns());
			randomStrike.attack();
		}else{
			counterTick++;
		}		
	}
	
	@Override
	public boolean stopAttack(){
		boolean temp = super.stopAttack();
		
		if(temp){
			randomStrike.stopAttack();
		}
		
		return true;
	}

	private StrikePackages getNewStrike(Player player, SignInfo signInfo, CautionSigns cautionSigns) {
		
		Random rnd = new Random();
		switch(rnd.nextInt(10)){
		case 0: return new SPExplode(player, signInfo, cautionSigns);
		case 1: return new SPGolem(player, signInfo, cautionSigns);
		case 2: return new SPIgnite(player, signInfo, cautionSigns);
		case 3: return new SPLavaTrap(player, signInfo, cautionSigns);
		case 4: return new SPLightning(player, signInfo, cautionSigns);
		case 5: return new SPSilent(player, signInfo, cautionSigns);
		case 6: return new SPSkyDive(player, signInfo, cautionSigns);
		case 7: return new SPVolley(player, signInfo, cautionSigns);
		case 8: return new SPWaterTrap(player, signInfo, cautionSigns);
		case 9: return new SPWolves(player, signInfo, cautionSigns);
		}
		
		return null;
	}

}
