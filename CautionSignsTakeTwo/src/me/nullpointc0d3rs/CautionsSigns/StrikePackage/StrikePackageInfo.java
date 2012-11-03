package me.nullpointc0d3rs.CautionsSigns.StrikePackage;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;
import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class StrikePackageInfo {

	private Player player;
	private SignInfo signInfo;
	private CSEnum eNum;
	private StrikePackages strikePackage;
	private CautionSigns cs;
	
	public StrikePackageInfo(Player player, SignInfo signInfo, CSEnum eNum, CautionSigns cs){
		this.player = player;
		this.signInfo = signInfo;
		this.eNum = eNum;
		this.cs = cs;
		this.strikePackage = getNewClassInstance();
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public SignInfo getSignInfo(){
		return signInfo;
	}
	
	public void setSignInfo(SignInfo signInfo){
		this.signInfo = signInfo;
	}
	
	public CSEnum getCSEnum(){
		return eNum;
	}
	
	public void setCSEnum(CSEnum eNum){
		this.eNum = eNum;
	}	
	
	public StrikePackages getStrikePackage(){
		return strikePackage;
	}
	
	private StrikePackages getNewClassInstance() {
		
		if(eNum == CSEnum.LIGHTNING){
			return new SPLightning(player, signInfo, cs);
		}else if(eNum == CSEnum.IGNITE){
			return new SPIgnite(player, signInfo, cs);
		}else if(eNum == CSEnum.EXPLODE){
			return new SPExplode(player, signInfo, cs);
		}else if(eNum == CSEnum.LAVATRAP){
			return new SPLavaTrap(player, signInfo, cs);
		}else if(eNum == CSEnum.SILENT){
			return new SPSilent(player, signInfo, cs);
		}else if(eNum == CSEnum.SKYDIVE){
			return new SPSkyDive(player, signInfo, cs);
		}else if(eNum == CSEnum.VOLLEY){
			//return new SPVolley(player, signInfo, cs);
		}else if(eNum == CSEnum.WOLVES){
			//return new SPWolves(player, signInfo, cs);
		}else if(eNum == CSEnum.GOLEM){
			return new SPGolem(player, signInfo, cs);
		}else if(eNum == CSEnum.WATERTRAP){
			return new SPWaterTrap(player, signInfo, cs);
		}else if(eNum == CSEnum.RANDOM){
			//return new SPRandom(player, signInfo, cs);
		}
		
		return null;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof StrikePackageInfo){
			StrikePackageInfo spi = (StrikePackageInfo) obj;
			if(spi.getPlayer().getName().equalsIgnoreCase(getPlayer().getName()) && spi.getCSEnum() == getCSEnum()){
				return true;
			}
		}
		return false;
	}
		
}
