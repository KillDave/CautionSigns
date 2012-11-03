package me.nullpointc0d3rs.CautionsSigns;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import me.nullpointc0d3rs.CautionsSigns.Permission.*;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileIO {
	private File filePath = new File("plugins"+File.separator+"CautionSigns"+File.separator);
	private File file = new File("plugins"+File.separator+"CautionSigns"+File.separator+"SignData.txt");
	private JavaPlugin jp;

	public FileIO(JavaPlugin jp){
		this.jp = jp;
		if (!(file.exists())){ 
			filePath.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public SignOwnership readFromFile(){
		Scanner sc = null;
		SignOwnership so = new SignOwnership(jp);
		try {
			sc = new Scanner(file);
			
			while(sc.hasNext()){
				String[] tempString = sc.nextLine().split(" ");
				Location tempLoc = new Location(jp.getServer().getWorld(tempString[3]), Double.parseDouble(tempString[0]),Double.parseDouble(tempString[1]), Double.parseDouble(tempString[2]));
				so.addSign(tempLoc, tempString[4]);
				for(int i = 5; i < tempString.length; i++){
					so.addFriendToSign(tempLoc, tempString[i]);
				}
			}
			return so;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally{
			try{
			sc.close();
			}catch (Exception e){
				// Nothing
			}
		}

		
	}
	
	public void updateSignFile(ArrayList<SignInfo> signList){
		
		PrintWriter pw = null;
		file.delete(); 
		
		try{
			 try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			 	for(SignInfo signInfo: signList){
			 		String line = null;
			 		int x, y, z;
			 		Location loc = signInfo.getLocation();
			 		x = (int) loc.getX();
			 		y = (int) loc.getY();
			 		z = (int) loc.getZ();
			 		line = x + " " + y + " " + z + " " + loc.getWorld().getName() + " ";
			 		
			 		line += signInfo.getOwner();
			 		for(String s: signInfo.getFriends()){
			 			line += " " + s;
			 		}
			 			
			 		
			 		pw.println(line);
			 	}
		} finally{
			pw.close();
		}
	}

	public FileConfiguration getConfig() {
		this.getConfig();
		return null;
	}	

}
