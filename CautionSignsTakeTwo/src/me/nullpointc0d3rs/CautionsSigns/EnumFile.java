package me.nullpointc0d3rs.CautionsSigns;

import java.util.ArrayList;

import me.nullpointc0d3rs.CautionsSigns.EnumFile.CSEnum;

public class EnumFile {
	public enum CSEnum{
		LIGHTNING, EXPLODE, GOLEM, IGNITE, LAVATRAP, RANDOM, SILENT, SKYDIVE, VOLLEY, WATERTRAP, WOLVES
	}

	public static ArrayList<CSEnum> getArray() {
		ArrayList<CSEnum> cseNum = new ArrayList<CSEnum>();
		
		cseNum.add(CSEnum.LIGHTNING);
		cseNum.add(CSEnum.EXPLODE);
		cseNum.add(CSEnum.GOLEM);
		cseNum.add(CSEnum.IGNITE);
		cseNum.add(CSEnum.LAVATRAP);
		cseNum.add(CSEnum.RANDOM);
		cseNum.add(CSEnum.SILENT);
		cseNum.add(CSEnum.SKYDIVE);
		cseNum.add(CSEnum.VOLLEY);
		cseNum.add(CSEnum.WATERTRAP);
		cseNum.add(CSEnum.WOLVES);
		
		return cseNum;
	}
	
	public static CSEnum getEnum(String s){
		if(s.equalsIgnoreCase("lightning")){
			return CSEnum.LIGHTNING;
		}else if(s.equalsIgnoreCase("explode")){
			return CSEnum.EXPLODE;
		}else if(s.equalsIgnoreCase("golem")){
			return CSEnum.GOLEM;
		}else if(s.equalsIgnoreCase("ignite")){
			return CSEnum.IGNITE;
		}else if(s.equalsIgnoreCase("lavatrap")){
			return CSEnum.LAVATRAP;
		}else if(s.equalsIgnoreCase("random")){
			return CSEnum.RANDOM;
		}else if(s.equalsIgnoreCase("silent")){
			return CSEnum.SILENT;
		}else if(s.equalsIgnoreCase("skydive")){
			return CSEnum.SKYDIVE;
		}else if(s.equalsIgnoreCase("volley")){
			return CSEnum.VOLLEY;
		}else if(s.equalsIgnoreCase("watertrap")){
			return CSEnum.WATERTRAP;
		}else if(s.equalsIgnoreCase("wolves")){
			return CSEnum.WOLVES;
		}
		return null;
	}
}
