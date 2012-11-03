package me.nullpointc0d3rs.CautionsSigns;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.block.CraftSign;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CautionSigns extends JavaPlugin {
	private Logger log;
	private FileIO fIO;
	private CautionSignsListener csl;
	
	public void onEnable(){
		log = this.getServer().getLogger();
		csl = new CautionSignsListener(this);
			
		this.getServer().getPluginManager().registerEvents(csl, this);
		
		initStartup();
	}
	
	public void onDisable(){
		
	}
	
	private void initStartup() {		
		PluginDescriptionFile pdfFile = getDescription();
		File configLocation =  new File("plugins/CautionSigns/config.yml");
		File oldConfiglocation = new File("plugins/CautionSigns/OLDconfig.yml");
		
		if (!configLocation.exists()) {
		      saveDefaultConfig();
		      log.log(Level.INFO, "[CautionSigns] Default Config.yml copied.");
		    }
		    if (!pdfFile.getVersion().equals(getConfig().getString("version"))) {
		      log.log(Level.INFO, "[CautionSigns] Version of config file doesn't match with current plugin version.");
		      getConfig().getDefaults();
		      try {
		        getConfig().save(oldConfiglocation);
		        log.log(Level.INFO, "[CautionSigns] Config version: " + getConfig().getString("version"));
		        log.log(Level.INFO, "[CautionSigns] Plugin version: " + pdfFile.getVersion());
		        log.log(Level.INFO, "[CautionSigns] Old Config.yml saved.");
		      } catch (IOException ex) {
		        log.log(Level.INFO, "[CautionSigns] Saving of old config file failed. " + ex.getMessage());
		      }
		      configLocation.delete();
		      saveDefaultConfig();
		    }
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if(sender instanceof Player){
			player = (Player)sender;
		}
		
		if(cmd.getName().equalsIgnoreCase("cs") || cmd.getName().equalsIgnoreCase("cautionsigns")){
			if(args[0].equalsIgnoreCase("info")){
				if(commandInfo(sender, cmd, label, args, player)){
					return true;
				}else{
					return false; 
				}
				
			}else if(args[0].equalsIgnoreCase("addfriend")){
				if(commandAddFriend(sender, cmd, label, args, player)){
					player.sendMessage(ChatColor.LIGHT_PURPLE + "You added " + ChatColor.RED + args[1] + ChatColor.LIGHT_PURPLE + " to this sign.");
					player.performCommand("cautionsigns info");
					return true;
				}else{
					return false;
				}
			}else if(args[0].equalsIgnoreCase("removefriend")){
				if(commandRemoveFriend(sender, cmd, label, args, player)){
					player.sendMessage(ChatColor.LIGHT_PURPLE + "You removed " + ChatColor.RED + args[1] + ChatColor.LIGHT_PURPLE + " from this sign.");
					player.performCommand("cautionsigns info");
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	
	private boolean commandInfo(CommandSender sender, Command cmd, String label, String[] args, Player player){
		Block block = player.getTargetBlock(null, 20);
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN){
			Sign sign = new CraftSign(block);
			if(sign.getLine(0).equals(ChatColor.RED + "[Caution]")){
				player.sendMessage(csl.getSignOwnership().getInfo(sign.getLocation()));
				return true;
			}
		}
		
		return false;
	}
	
	private boolean commandAddFriend(CommandSender sender, Command cmd, String label, String[] args, Player player){
		Block block = player.getTargetBlock(null, 20);
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN){
			Sign sign = new CraftSign(block);
			if(sign.getLine(0).equals(ChatColor.RED + "[Caution]")){
				if(!(getConfig().getBoolean("NotOP.useTheseRules")) || (getConfig().getBoolean("NotOP.useTheseRules") && csl.getSignOwnership().numOfFriends(sign.getLocation()) < getConfig().getInt("NotOP.maxNumOfFriendPerSign"))){
					if(csl.getSignOwnership().addFriendToSign(sign.getLocation(), args[1])){
						return true;
					}else{
						return false; // could not add (error unknown :O)
					}
				}else{
					// you have too many signs
				}
				
			}
		}
		
		return false; // not looking at a [caution] sign
	}

	private boolean commandRemoveFriend(CommandSender sender, Command cmd, String label, String[] args, Player player){
		Block block = player.getTargetBlock(null, 20);
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN){
			Sign sign = new CraftSign(block);
			if(sign.getLine(0).equals(ChatColor.RED + "[Caution]")){
				if(csl.getSignOwnership().removePlayerFromSign(sign.getLocation(), args[1])){
					return true;
				}else{
					return false; // wrong name?
				}
				
			}
		}
		
		return false;
	}
}