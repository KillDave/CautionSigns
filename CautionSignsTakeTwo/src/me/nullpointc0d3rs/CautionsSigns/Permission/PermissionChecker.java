package me.nullpointc0d3rs.CautionsSigns.Permission;

import org.bukkit.entity.Player;

import me.nullpointc0d3rs.CautionsSigns.CautionSigns;

public class PermissionChecker {

	private CautionSigns cautionSign;
	
	public PermissionChecker(CautionSigns cautionSign){
		this.cautionSign = cautionSign;
	}
	
	public boolean onPlayerMove(Player player){
		
		if(player.isDead()){
			return true;
		}
		
		if(cautionSign.getConfig().getBoolean("debug")){
			return false;
		}
		
		if (player.isOp() && !cautionSign.getConfig().getBoolean("OP.signsAttackOP")){
			return true;
		}
		
		return false;
	}
	
	public boolean onPlayerMoveSign(Player player, SignInfo signInfo){
		
		if(cautionSign.getConfig().getBoolean("debug")){
			return false;
		}
		
		if (signInfo.getOwner().equals(player.getName())){
			return true;
		}
		
		if(signInfo.getFriends().contains(player.getName())){
			return true;
		}
		
		return false;
	}

	public boolean onSignBreak(Player player, SignInfo signInfo) {		
		
		if(player.isOp()){
			if(cautionSign.getConfig().getBoolean("OP.breakOthersSigns")){
				return true;
			}
		}

		if(signInfo.getOwner().equalsIgnoreCase(player.getName())){
			if(player.hasPermission("cautionsigns.*") || player.hasPermission("cautionsigns.destroy")){
				return true;
			}
		}
		
		return false;
	}

	public boolean onSignChange(Player player) {

		if(player.isOp()){
			return true;
		}
		
		if(player.hasPermission("cautionsigns.*") || player.hasPermission("cautionsigns.create")){
			return true;
		}
		
		return false;
	}

	public boolean onSignChangeCheckTwo(Player player, SignOwnership signOwner) {
		
		if(player.isOp()){
			return true;
		}
		
		if(cautionSign.getConfig().getBoolean("NotOP.useTheseRules")){
			if(signOwner.numOfSignForOwner(player.getName()) >= cautionSign.getConfig().getInt("NotOP.maxNumOfSigns")){
				return false;
			}
		}
		
		return true;
	}

	
}
