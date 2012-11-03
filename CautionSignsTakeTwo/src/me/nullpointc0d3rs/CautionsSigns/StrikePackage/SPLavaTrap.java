package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SPLavaTrap extends StrikePackages{
	
	private Location[] loc;
	private boolean oneTimeAttack;
	private int[] block;
	private byte[] data;
	private World world;
	
	public SPLavaTrap(Player player, SignInfo signInfo, CautionSigns cs){
		setEnum(CSEnum.LAVATRAP);
		setID("LavaTrap");
		setPlayer(player);
		setSignInfo(signInfo);
		setAttacking(false);
		setWarned(false);
		setInRange(false);
		setCautionSigns(cs);
		setTimerTick(100);
		loc = new Location[7];
		block = new int[7];
		data = new byte[7];
		world = player.getWorld();
		oneTimeAttack = false;
	}
	
	@Override
	public void warnUser() {
		if(!getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.lavatrap.enter").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(true);
		}	
	}

	@Override
	public void userLeftWarnZone() {
		if(getWarned()){
			String message = getCautionSigns().getConfig().getString("Cautions.lavatrap.leave").replace("%P", getSignInfo().getOwner());
			getPlayer().sendMessage(message);
			setWarned(false);
		}		
	}


	
	@Override
	public void strikePlayer() {

		if(!oneTimeAttack){ //changed this
			Location startLoc = getPlayer().getLocation();
			setArrayInfo();
			for(int i = 0; i < loc.length; i++){
				int temp = block[i];
				block[i] = world.getBlockTypeIdAt(loc[i]);
				data[i] = world.getBlockAt(loc[i]).getData();
				world.getBlockAt(loc[i]).setTypeId(temp);
			}
			getPlayer().teleport(startLoc);
			oneTimeAttack = true;
		}
	}
	
	@Override
	public boolean stopAttack(){//1
		boolean temp = super.stopAttack();//2
		
		if(temp){//6 since it true
			oneTimeAttack = false;//7
			for(int i = 0; i < loc.length; i++){// and so on
				world.getBlockAt(loc[i]).setTypeIdAndData(block[i], data[i], false);
			}
		}
		
		return true;
	}
	
	private void setArrayInfo(){		
		loc[0] = getPlayer().getLocation().add(1,0,0);
		loc[1] = getPlayer().getLocation().add(-1,0,0);
		loc[2] = getPlayer().getLocation().add(0,0,1);
		loc[3] = getPlayer().getLocation().add(0,0,-1);
		loc[4] = getPlayer().getLocation().add(0,2,0);
		loc[5] = getPlayer().getLocation().add(0,1,0);
		loc[6] = getPlayer().getLocation().add(0,-1,0);	
		
		block[0] = 7;
		block[1] = 7;
		block[2] = 7;
		block[3] = 7;
		block[4] = 7;
		block[5] = 11;
		block[6] = 7;
	}

}
