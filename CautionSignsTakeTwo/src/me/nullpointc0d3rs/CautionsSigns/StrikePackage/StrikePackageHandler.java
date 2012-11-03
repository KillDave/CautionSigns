package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import java.util.ArrayList;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;

import org.bukkit.entity.Player;

public class StrikePackageHandler {
	
	private ArrayList<StrikePackageInfo> arrStrikes = new ArrayList<StrikePackageInfo>();
	private CautionSigns cs;
	
	public StrikePackageHandler(CautionSigns cs){
		this.cs = cs;
	}
	
	public void addStrike(Player player, SignInfo signInfo, CSEnum eNum){
		StrikePackageInfo temp = new StrikePackageInfo(player, signInfo, eNum, cs);
		for(StrikePackageInfo spi: arrStrikes){
			if(spi.equals(temp)){
				return;
			}
		}
		
		player.sendMessage("new strike for you");
		arrStrikes.add(new StrikePackageInfo(player, signInfo, eNum, cs));
	}
	
	public void removeStrike(Player player){
		for(int i = 0; i < arrStrikes.size(); i++){
			if(arrStrikes.get(i).getPlayer().getName().equalsIgnoreCase(player.getName())){
				arrStrikes.get(i).getStrikePackage().stopAttack();
				arrStrikes.remove(i);
				i--;
			}
		}
		if(cs.getConfig().getBoolean("debug")){
			player.sendMessage("Strike Been Removed (player)");
		}
	}
	
	public ArrayList<StrikePackageInfo> getStrikeInfo(){
		return arrStrikes;
	}

	public void removeStrike(Player player, ArrayList<CSEnum> removeStrikeTo) {
		for(int i = 0; i < arrStrikes.size(); i++){
			for(CSEnum eNum: removeStrikeTo){
				if(i >= 0 && arrStrikes.get(i).getCSEnum() == eNum && player.getName().equalsIgnoreCase(arrStrikes.get(i).getPlayer().getName())){
					arrStrikes.get(i).getStrikePackage().stopAttack();
					arrStrikes.remove(i);
					i--;
				}
			}
		}
		
		if(cs.getConfig().getBoolean("debug")){
			player.sendMessage("Strike Been Removed (player, CSEnum)");
		}
		
	}

	public StrikePackages getStrike(Player player, SignInfo signInfo, CSEnum eNum) {
		
		for(StrikePackageInfo spi: arrStrikes){
			if(spi.getPlayer().getName().equalsIgnoreCase(player.getName()) && spi.getSignInfo().equals(signInfo) && spi.getCSEnum() == eNum){
				return spi.getStrikePackage();				
			}
		}
		
		return null;
	}
}
