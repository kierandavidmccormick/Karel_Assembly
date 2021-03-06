import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Assembler2 {
	public static final int END_VAL = 0;
	public static final int ADD_VAL = 1;
	public static final int INC_VAL = 2;
	public static final int SUB_VAL = 3;
	public static final int DEC_VAL = 4;
	public static final int MOV_VAL = 5;
	public static final int MVT_VAL = 6;
	public static final int MVF_VAL = 7;
	public static final int STO_VAL = 8;
	public static final int GET_VAL = 9;
	public static final int SET_VAL = 10;
	public static final int JMP_VAL = 11;
	public static final int JMZ_VAL = 12;
	public static final int JMS_VAL = 13;
	public static final int JZS_VAL = 14;
	public static final int PSH_VAL = 15;
	public static final int POP_VAL = 16;
	public static final int PEK_VAL = 17;
	public static final int NUL_VAL = 17;
	
	public static final int INS_OFFSET = 0;
	public static final int STK_OFFSET = 2;
	
	public static ArrayList<String[]> table;
	
	public static int endIndex;
	
	public static void main(String[] args) {
		table = new ArrayList<>();
		ArrayList<String[]> stringArray = new ArrayList<>();
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader(args[0]));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		String s;
		try {
			s = inFile.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		while (s != null) {
			try {
				stringArray.add(s.split("\\s"));
				s = inFile.readLine();
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
				return;
			}
		}
		try {
			inFile.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		//System.out.println(stringArray.size());
		ArrayList<String[]> temp1 = assemble1(stringArray);
		//System.out.println(temp1.size());
		/*
		for (String[] strArr : temp1) {
			for (String str : strArr) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		*/
		ArrayList<String[]> temp2 = assemble2(temp1);
		//System.out.println(temp2.size());
		/*
		for (String[] strArr : temp2) {
			for (String str : strArr) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		*/
		ArrayList<Integer[]> temp3 = assemble3(temp2);
		//System.out.println(temp3.size());
		/*
		for (Integer[] intArr : temp3) {
			for (Integer i : intArr) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
		*/
		ArrayList<String> fileStrings = assemble4(temp3);
		//System.out.println(fileStrings.size());
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new FileWriter(args[2]));
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
		for (String str : fileStrings) {
			outFile.println(str);
		}
		parseWorld(args[1], outFile, 7 + temp3.size(), 12 + temp3.size());
		outFile.println("Wall: (" + ( 12 + temp3.size()) + ", 9) west");
		outFile.println("Karel: (1, 2) east");
		outFile.println("");
		outFile.println("BeeperBag: INFINITE");
		outFile.println("Speed: 1.00");
		outFile.close();
	}
	
	//build MRK/FLG table, remove full line comments
	public static ArrayList<String[]> assemble1(ArrayList<String[]> inStringArray){
		ArrayList<String[]> newStringArray = new ArrayList<>();
		int prevOPS = 0;
		//for (String[] strAr : inStringArray) {
		for (int i = 0; i < inStringArray.size(); i++) {
			String[] strAr = inStringArray.get(i);
			if (getOPValue(strAr[0]) != -1) {
				newStringArray.add(strAr);
				if (strAr[0].equals("END")) {
					endIndex = prevOPS;
				}
				prevOPS++;
			} else {
				if (strAr[0].equals("FLG")) {
					String[] newStrArr = new String[2];
					newStrArr[0] = strAr[1];
					newStrArr[1] = Integer.toString(prevOPS);
					table.add(newStrArr);
				} else if (strAr[0].equals("MRK")) {
					String[] newStrArr = new String[2];
					newStrArr[0] = strAr[2];
					newStrArr[1] = strAr[1];
					table.add(newStrArr);
				}
			}
		}
		return newStringArray;
	}
	
	//translate MRK/FLG strings to replacements(relative or absolute)
	public static ArrayList<String[]> assemble2(ArrayList<String[]> inStringArray){
		ArrayList<String[]> newStringArray = new ArrayList<>(inStringArray.size());
		for (String[] strAr : inStringArray) {
			String[] newStrAr = new String[(strAr.length > 4 ? 4 : strAr.length)];
			newStrAr[0] = strAr[0];
			for (String s : strAr) {
			}
			for (int i = 1; i < (strAr.length > 4 ? 4 : strAr.length); i++) {
				newStrAr[i] = markToString(strAr[i]);
			}
			newStringArray.add(newStrAr);
		}
		return newStringArray;
	}
	
	//translate relative addresses to absolute addresses, translate to ints
	public static ArrayList<Integer[]> assemble3(ArrayList<String[]> inStringArray){
		ArrayList<Integer[]> newIntArray = new ArrayList<>(inStringArray.size());
		for (String[] strAr : inStringArray) {
			Integer[] newIntegerAr = new Integer[(strAr.length > 4 ? 4 : strAr.length)];
			newIntegerAr[0] = getOPValue(strAr[0]);
			for (int i = 1; i < (strAr.length > 4 ? 4 : strAr.length); i++) {
				newIntegerAr[i] = addressToInt(strAr[i], inStringArray.size());
			}
			newIntArray.add(newIntegerAr);
		}
		return newIntArray;
	}
	
	//translate ints to karel world file strings
	public static ArrayList<String> assemble4(ArrayList<Integer[]> inIntArray){
		ArrayList<String> newStringArray = new ArrayList<>();
		//char[] spaceString = new char[worldDataOffset];
		//Arrays.fill(spaceString, ' ');
		newStringArray.add("Dimension: (" + (inIntArray.get(endIndex)[2] + inIntArray.get(endIndex)[1] + inIntArray.size() + INS_OFFSET + STK_OFFSET) + ", 9)"/* + String.valueOf(spaceString)*/);
		for (int i = 0; i < inIntArray.size(); i++) {
			for (int j = 0; j < (inIntArray.get(i).length > 4 ? 4 : inIntArray.get(i).length); j++) {
				newStringArray.add("Beeper: (" + (i + 1) + ", " + (2 + j * 2) + ") " + inIntArray.get(i)[j]);
			}
		}
		return newStringArray;
	}
	
	//treats absolute values as the same as absolute addresses
	public static int addressToInt(String address, int validOPCodes) {
		if (address == null || address.equals("")) {
			return 0;
		}
		if (address.charAt(0) == '&') {
			return Integer.parseInt(address.replaceAll("&", "")) + validOPCodes;
		} else {
			return Integer.parseInt(address);
		}
	}
	
	public static String markToString(String mark) {
		if (mark.equals("") || java.lang.Character.isDigit(mark.charAt(0)) || mark.charAt(0) == '&'){
			return mark;
		}
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i)[0].equals(mark)) {
				return table.get(i)[1];
			}
		}
		return null;
	}
	
	public static int getOPValue(String test) {
		if (test == null) {
			return 0;
		}
		switch(test) {
			case "END": return END_VAL;
			case "ADD": return ADD_VAL;
			case "INC": return INC_VAL;
			case "SUB": return SUB_VAL;
			case "DEC": return DEC_VAL;
			case "MOV": return MOV_VAL;
			case "MVT": return MVT_VAL;
			case "MVF": return MVF_VAL;
			case "STO": return STO_VAL;
			case "GET": return GET_VAL;
			case "SET": return SET_VAL;
			case "JMP": return JMP_VAL;
			case "JMZ": return JMZ_VAL;
			case "JMS": return JMS_VAL;
			case "JZS": return JZS_VAL;
			case "PSH": return PSH_VAL;
			case "POP": return POP_VAL;
			case "PEK": return PEK_VAL;
			case "NUL": return NUL_VAL;
			default: return -1;
		}
	}
	
	public static void parseWorld(String args1, PrintWriter outFile, int baseOffset, int lineOffset) {
		ArrayList<String> stringArray = new ArrayList<>();
		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader(args1));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		String s;
		try {
			s = inFile.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return;
		}
		while (s != null) {
			try {
				stringArray.add(s);
				s = inFile.readLine();
			} catch (IOException ex) {
				System.err.println(ex.getMessage());
				return;
			}
		}
		try {
			inFile.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		int columns = Integer.parseInt(stringArray.get(0).substring(12).split(", ")[0]);
		int rows = Integer.parseInt(stringArray.get(0).substring(12).split(", ")[1].replaceAll("\\)", "").trim());
		int karel_x = -1;
		int karel_y = -1;
		int karel_beepers = -1;
		int karel_dir = -1;
		
		String[] worldStringsBeepers = new String[rows * columns];
		boolean[] worldBoolsWallsSouth = new boolean[rows * columns];
		boolean[] worldBoolsWallsWest = new boolean[rows * columns];		
				
		for (String str : stringArray) {
			if (str.startsWith("Beeper:")) {
				//String[] valStrings= stringParse(str);
				String[] temp = str.replaceAll("Beeper: \\(|,|\\)", "").split(" ");
				int x = Integer.parseInt(temp[0]) - 1;
				int y = Integer.parseInt(temp[1]) - 1;
				worldStringsBeepers[y * columns + x] = temp[2];
				
			} else if (str.startsWith("Wall:")) {
				String[] temp = str.replaceAll("Wall: \\(|,|\\)", "").split(" ");
				int x = Integer.parseInt(temp[0]) - 1;
				int y = Integer.parseInt(temp[1]) - 1;
				if (temp[2].equals("west")) {
					worldBoolsWallsWest[y * columns + x] = true;
				} else {
					worldBoolsWallsSouth[y * columns + x] = true;
				}
			} else if (str.startsWith("Karel:")) {
				String[] temp = str.replaceAll("Karel: \\(|,|\\)", "").split(" ");
				karel_x = Integer.parseInt(temp[0]) - 1;
				karel_y = Integer.parseInt(temp[1]) - 1;
				switch (temp[2]) {
					case "north": karel_dir = 0; break;
					case "east": karel_dir = 1; break;
					case "south": karel_dir = 2; break;
					default: karel_dir = 3; break;
				}
			} else if (str.startsWith("BeeperBag:")) {
				karel_beepers = str.replaceAll("BeeperBag: ", "").equals("INFINITE") ? -2 : Integer.parseInt(str.replaceAll("BeeperBag: ", ""));
			}
		}
		
		//outFile.println("Dimension: (" + (lineOffset + columns * rows) + ", 9)");
		
		outFile.println("Beeper: (" + (baseOffset + 1) + ", " + 6 + ") " + karel_x);
		outFile.println("Beeper: (" + (baseOffset + 2) + ", " + 6 + ") " + karel_y);
		outFile.println("Beeper: (" + (baseOffset + 3) + ", " + 6 + ") " + karel_dir);
		outFile.println("Beeper: (" + (baseOffset + 4) + ", " + 6 + ") " + (karel_beepers == -2 ? "INFINITE" : karel_beepers));
		outFile.println("Beeper: (" + (baseOffset + 5) + ", " + 6 + ") " + columns);
		outFile.println("Beeper: (" + (baseOffset + 6) + ", " + 6 + ") " + rows);
		outFile.println("Beeper: (" + (baseOffset + 7) + ", " + 6 + ") " + (lineOffset + 2));
		for (int i = 0; i < worldStringsBeepers.length; i++) {
			if (worldStringsBeepers[i] != null) {
				outFile.println("Beeper: (" + (i + lineOffset + 3) + ", " + 6 + ") " + worldStringsBeepers[i]);
			}
			if (worldBoolsWallsSouth[i]) {
				outFile.println("Beeper: (" + (i + lineOffset + 3) + ", " + 2 + ") 1");
			}
			if (worldBoolsWallsWest[i]) {
				outFile.println("Beeper: (" + (i + lineOffset + 3) + ", " + 8 + ") 1");
			}
		}
		//outFile.println("Karel: (1, 2) east");
		//outFile.println("BeeperBag: INFINITE");
		//outFile.println("Speed: 1.00");
		//NOTE: include as part of an existing file, does not generate all of the parts necessary for a full world file
	}
}
