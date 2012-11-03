package me.nullpointc0d3rs.CautionsSigns;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;
import me.nullpointc0d3rs.CautionsSigns.Permission.PermissionChecker;
import me.nullpointc0d3rs.CautionsSigns.Permission.SignOwnership;
import me.nullpointc0d3rs.CautionsSigns.StrikePackage.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CautionSignsListener implements Listener{

	private CautionSigns cautionSign;
	private StrikePackageHandler strikeHandler;
	private SignOwnership signOwner;
	private FileIO fIO;
	private Timer timer;
	private PermissionChecker pc;
	
	public CautionSignsListener(CautionSigns cautionSign){
		this.cautionSign = cautionSign;
		this.fIO = new FileIO(cautionSign);
		strikeHandler = new StrikePackageHandler(cautionSign);
		signOwner = fIO.readFromFile();
		timer = new Timer();
		pc = new PermissionChecker(cautionSign);
		startTimer(strikeHandler);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event){
		Player player = event.getPlayer();
			ArrayList<CSEnum> removeStrikeTo = null;
			if(pc.onPlayerMove(player)){
				strikeHandler.removeStrike(player);
				return;
			}
			removeStrikeTo = EnumFile.getArray();
			for(Location l:signOwner.getSignLocations()){
				Block block = l.getBlock();
				if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
					Sign sign = new CraftSign(block);
					if(sign.getLine(0).equals(ChatColor.RED + "[Caution]")){
						CSEnum eNum = EnumFile.getEnum(sign.getLine(2));
						if(eNum != null){
							int intMargin = cautionSign.getConfig().getInt("Cautions." + eNum.toString().toLowerCase() + ".warningMargin");
							int intSignRange = Integer.parseInt(sign.getLine(1));
							if (!pc.onPlayerMoveSign(player, signOwner.getSignInfo(l))){
								if((intSignRange + intMargin) >= player.getLocation().distance(l)){
									removeStrikeTo.remove(eNum);
									strikeHandler.addStrike(player, signOwner.getSignInfo(l), eNum);
									strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum).warnUser();	
									if(intSignRange >= player.getLocation().distance(l)){
										strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum).playerInRange();	
										strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum).attack();
									}else{
										strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum).playerLeftRange();
										strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum).stopAttack();
									}
								}else{
									StrikePackages tempSP =	strikeHandler.getStrike(player, signOwner.getSignInfo(l), eNum);
									if(!(tempSP == null)){
										tempSP.userLeftWarnZone();
									}
								}				
							}
						}
					}
				}
			}
			strikeHandler.removeStrike(player, removeStrikeTo);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event){
		strikeHandler.removeStrike(event.getEntity().getPlayer());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		
		if (event.getLine(0).equalsIgnoreCase("[Caution]"))
		{
			if (pc.onSignChange(player))
			{
				if(event.getLine(0).equalsIgnoreCase("[Caution]")) {
					if(!pc.onSignChangeCheckTwo(player, signOwner)){
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED + "You can only have a max of " + cautionSign.getConfig().getInt("NotOP.maxNumOfSigns") + " signs");						
						return;
					}
					event.setLine(0, ChatColor.RED + "[Caution]");
					signOwner.addSign(event.getBlock().getLocation(), player);
					event.setLine(3, player.getName());
					signOwner.addSign(event.getBlock().getLocation(), player);
					fIO.updateSignFile(signOwner.getSignList());
				}

				if (event.getLine(1).equalsIgnoreCase(""))
				{
					player.sendMessage(ChatColor.RED + "[CautionSigns] You did not specify a strike radius on line 2! Strike radius has been defaulted to 5.");
					event.setLine(1, "" + 5);
				}
				else 
				{
					int strikeRadius = Integer.valueOf(event.getLine(1));
					
					if(strikeRadius > 25){
						player.sendMessage(ChatColor.GREEN + "[CautionSigns] The max range is 25. Setting sign strike radius to default.");
						event.setLine(1, "5");
					}else if(strikeRadius < 0){
						player.sendMessage(ChatColor.GREEN + "[CautionSigns] The min range is 0. Setting sign strike radius to default.");
						event.setLine(1, "5");
					}else{
						player.sendMessage(ChatColor.GREEN + "[CautionSigns] You have successfully created a CautionSign with a strike radius of " + strikeRadius + "!");
					}
				}
				if(!checkStrikeType(event.getLine(2))){
					event.setLine(2, "Lightning");
					player.sendMessage(ChatColor.RED + "[CautionSigns] You either typed an invalid strike type or did not specify a strike type on line 3. Defaulting to lightning.");
				}else{
					String tempString = event.getLine(2);
					String outString = (tempString.substring(0,1).toUpperCase() + tempString.substring(1).toLowerCase());

					event.setLine(2, outString);
				}
				
			}
			else 
			{
				player.sendMessage(ChatColor.RED + "[CautionSigns] You don't have permission to do that!");
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignBreak(BlockBreakEvent event)
	{
		if(!(event.getBlock().getType() == Material.WALL_SIGN || event.getBlock().getType() == Material.SIGN_POST)){
			return;
		}
		
		Sign sign = new CraftSign(event.getBlock());
		Player player = event.getPlayer();
		
		if (sign.getLine(0).equalsIgnoreCase(ChatColor.RED + "[Caution]"))
		{
			if (pc.onSignBreak(player, signOwner.getSignInfo(sign.getLocation())))
				{
					player.sendMessage(ChatColor.GREEN + "[CautionSigns] Sign destroyed successfully.");

					signOwner.removeSign(event.getBlock().getLocation());
					fIO.updateSignFile(signOwner.getSignList());
					
					return;						
				}
			else 
				{
					player.sendMessage(ChatColor.RED + "[CautionSigns] You don't have permission to do that!");
					event.setCancelled(true);
					return;
				}
		}
		
	}
	
	private boolean checkStrikeType(String line) {
		if(line.equalsIgnoreCase("Lightning")){
			return true;
		}else if(line.equalsIgnoreCase("SkyDive")){
			return true;
		}else if(line.equalsIgnoreCase("Ignite")){
			return true;
		}else if(line.equalsIgnoreCase("Explode")){
			return true;
		}else if(line.equalsIgnoreCase("Volley")){
			return true;
		}else if(line.equalsIgnoreCase("Silent")){
			return true;
		}else if(line.equalsIgnoreCase("Wolves")){
			return true;
		}else if(line.equalsIgnoreCase("LavaTrap")){
			return true;
		}else if(line.equalsIgnoreCase("WaterTrap")){
			return true;
		}else if(line.equalsIgnoreCase("Golem")){
			return true;
		}else if(line.equalsIgnoreCase("Random")){
			return true;
		} 
		return false;
		
	}
	
	private void startTimer(final StrikePackageHandler spH){
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				ArrayList<StrikePackageInfo> spi = spH.getStrikeInfo();
				for(int i = 0; i < spi.size(); i++){
					spi.get(i).getStrikePackage().attack(); 
				}
			
			}
			
		},0, 500);
	
	}
	
	public SignOwnership getSignOwnership(){
		return signOwner;
	}
	
	
	
}
