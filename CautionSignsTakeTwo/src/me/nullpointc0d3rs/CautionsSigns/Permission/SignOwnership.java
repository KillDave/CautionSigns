package me.nullpointc0d3rs.CautionsSigns.Permission;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SignOwnership {

	private ArrayList<SignInfo> sPL;
	private JavaPlugin jp;
	
	public SignOwnership(JavaPlugin jp){
		sPL = new ArrayList<SignInfo>();
		this.jp = jp;
	}
	
	public ArrayList<Location> getSignLocations(){
		ArrayList<Location> loc = new ArrayList<Location>();
		for(SignInfo sInfo: sPL){
			loc.add(sInfo.getLocation());
		}
		return loc;
	}
	
	public boolean addFriendToSign(Location location, String player){
		for(int i = 0; i < sPL.size(); i++){
			if(sPL.get(i).locationEquals(location)){
				sPL.get(i).addFriend(player);
				return true;
			}
		}
		return false;
	}
	
	public boolean removePlayerFromSign(Location location, String player){
		for(int i = 0; i < sPL.size(); i++){
			if(sPL.get(i).locationEquals(location)){
				sPL.get(i).removeFriend(player);
				return true;
			}
		}
		return false;
	}
	
	public void removeSign(Location location){
		for(int i = 0; i < sPL.size(); i++){
			if(sPL.get(i).locationEquals(location)){
				sPL.remove(i);
				return;
			}
		}
	}
	
	public boolean isPlayerFriendlyToSign(Location location, Player player){
		SignInfo signPL = getSignInfo(location);
				
			if(signPL.getOwner().equalsIgnoreCase(player.getName())){
				return true;
			}else{
				for(String p: signPL.getFriends()){
					if(p.equalsIgnoreCase(player.getName())){
						return true;
					}
				}
			}
				
		return false;
	}
	
	public void addSign(Location location, Player player){
		sPL.add(new SignInfo(location, player.getName()));
	}
	
	public void addSign(Location location, String player){
		sPL.add(new SignInfo(location, player));
	}
	
	public boolean isPlayerSignOwner(Location location, Player player) {
		for(SignInfo s: sPL){
			if(s.locationEquals(location)){
				if(s.getOwner() == player.getName()){
					return true;
				}
			}
		}
		return false;
	}
	
	public SignInfo getSignInfo(Location location){
		for(SignInfo s: sPL){
			if(s.locationEquals(location)){
				return s;
			}
		}
		return null;
	}

	public String getInfo(Location location) {
		String info = null;
		SignInfo sPL = getSignInfo(location);
		info = "" + ChatColor.GREEN;
		info += "Owner: " + sPL.getOwner() +"\n";
		info += "Friends: ";
		for(String s: sPL.getFriends()){
			info += s + ", ";
		}
		info += "\n";
		info += "Location: " + sPL.getLocation().getX() + " " + sPL.getLocation().getY() + " " + sPL.getLocation().getZ();
		return info;
	}
	
	public ArrayList<SignInfo> getSignList(){
		return sPL;
	}

	public int numOfFriends(Location location) {
		return getSignInfo(location).getFriends().size();
	}
	
	public int numOfSignForOwner(String player){
		int intCnt = 0;
		for(SignInfo s: sPL){
			if(s.getOwner().equals(player)){
				intCnt++;				
			}
		}
		return intCnt;
	}
}
