package me.nullpointc0d3rs.CautionsSigns.Permission;

import java.util.ArrayList;

import org.bukkit.Location;

public class SignInfo {

	private Location signLocation;
	private String pOwner;
	private ArrayList<String> pFriend = new ArrayList<String>();
	
	public SignInfo(Location location, String player){
		signLocation = location;
		pOwner = player;
	}
	
	public String getOwner(){
		return pOwner;
	}
	
	public ArrayList<String> getFriends(){
		return pFriend;
	}
	
	public void addFriend(String player){
		pFriend.add(player);
	}
	
	public void removeFriend(String player){
		pFriend.remove(player);
	}

	public boolean locationEquals(Location location) {
		if(signLocation.getWorld() == location.getWorld()){
			if(signLocation.getX() == location.getX() && signLocation.getY() == location.getY() && signLocation.getZ() == location.getZ()){
				return true;
			}
		}
		
				
		return false;
	}
	
	public Location getLocation(){
		return signLocation;
	}
	
	
	public boolean equals(Object obj){
		if(obj instanceof SignInfo){
			SignInfo temp = (SignInfo) obj;
			if(temp.getOwner().equalsIgnoreCase(getOwner()) && temp.locationEquals(getLocation()) ){
				return true;
			}
		}
		return false;
	}
}
