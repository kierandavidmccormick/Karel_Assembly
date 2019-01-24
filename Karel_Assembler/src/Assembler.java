import java.io.*;
import java.util.ArrayList;

//https://docs.oracle.com/javase/tutorial/essential/io/charstreams.html
//https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
//https://stackoverflow.com/questions/2088037/trim-characters-in-java

public class Assembler {
	public static final int END_VAL = 0;
	public static final int ADD_VAL = 1;
	public static final int SUB_VAL = 2;
	public static final int MOV_VAL = 3;
	public static final int STO_VAL = 4;
	public static final int JMP_VAL = 5;
	public static final int JMZ_VAL = 6;
	public static final int JNZ_VAL = 7;
	public static final int NUL_VAL = 8;
	
	//expects args[0] to contain path to input file, args[1] to contain path to output file
	public static void main(String[] args) {
		BufferedReader inFile = null;
		ArrayList<String[]> stringArray = new ArrayList<>();
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
				stringArray.add(s.split(" "));
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
		ArrayList<int[]> assembledValues = new ArrayList<>();
		assemble(stringArray, assembledValues);
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new FileWriter(args[1]));
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
		outFile.println("Dimension: (" + (assembledValues.get(assembledValues.size() - 1)[1] + assembledValues.size()) + ", 7)");
		for (int i = 0; i < assembledValues.size(); i++) {
			for (int j = 0; j < assembledValues.get(i).length; j++) {
				outFile.println("Beeper: (" + (i + 1) + ", " + ( 2 + j * 2) + ") " + assembledValues.get(i)[j]);
			}
		}
		outFile.println("Karel: (1, 2) east");
		outFile.println();
		outFile.println("BeeperBag: 999999");
		outFile.println("Speed: .90");
		outFile.close();
	}
	
	public static void assemble(ArrayList<String[]> stringArray, ArrayList<int[]> finalVals) {
		int validOPCodes = 0;
		ArrayList<String> flagCodes = new ArrayList<>();
		ArrayList<Integer> flagValues = new ArrayList<>();
		ArrayList<String[]> newStringArray = new ArrayList<>();
		for (String[] s : stringArray) {
			if (getOPValue(s[0]) != -1){
				newStringArray.add(s);
				validOPCodes++;
			} else if (s[0].equals("FLG")) {
				flagCodes.add(s[1]);
				flagValues.add(new Integer(validOPCodes));
			}
		}
		for (String[] s : newStringArray) {
			int[] newLine = new int[3];
			newLine[0] = getOPValue(s[0]);
			if (s.length > 1) {				//TODO: fix this
				newLine[1] = opToInt(s[1], validOPCodes, flagCodes, flagValues) + 0;
			} else {
				newLine[1] = 0;
			}
			if (s.length > 2) {
				newLine[2] = opToInt(s[2], validOPCodes, flagCodes, flagValues) + 0;
			} else {
				newLine[2] = 0;
			}
			finalVals.add(newLine);
		}
	}
	
	public static int opToInt(String op, int validOPCodes, ArrayList<String> flagCodes, ArrayList<Integer> flagValues) {
		if (java.lang.Character.isDigit(op.charAt(0))){
			return Integer.parseInt(op);
		} else if (op.charAt(0) == '&') {
			return Integer.parseInt(op.replaceAll("&", "")) + validOPCodes;
		}
		for (int i = 0; i < flagCodes.size(); i++) {
			if (flagCodes.get(i).equals(op)) {
				return flagValues.get(i);
			}
		}
		return -1;
	}
	
	public static int getOPValue(String test) {
		switch(test) {
			case "END": return END_VAL;
			case "ADD": return ADD_VAL;
			case "SUB": return SUB_VAL;
			case "MOV": return MOV_VAL;
			case "STO": return STO_VAL;
			case "JMP": return JMP_VAL;
			case "JMZ": return JMZ_VAL;
			case "JNZ": return JNZ_VAL;
			case "NUL": return NUL_VAL;
			default: return -1;
		}
	}
}
