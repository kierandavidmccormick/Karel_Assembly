import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WorldWriter {
	//TODO: replace space-string based storage of world data offset with one based on a marker of some kind
	public static void main(String[] args) {
		ArrayList<String> stringArray = new ArrayList<>();
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
				//System.out.println(s);
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
		//String offsetString = stringArray.get(0);
		//offsetString = offsetString.replaceAll("Dimension: \\(\\d+, \\d+\\)", "");
		int worldDataOffsetVal = -1;
		//int worldDataOffset = -1;
		for (String str : stringArray) {
			if (str.startsWith("Wall:")){
				worldDataOffsetVal = Integer.parseInt(str.replaceAll("Wall: \\(|, \\d+\\) \\w+", ""));
				System.out.println(worldDataOffsetVal);
			}
		}
		/*
		for (int i = 0; i < stringArray.size(); i++) {
			if (stringArray.get(i).startsWith("Beeper:")) {
				String test = stringArray.get(i).replaceAll("\\(|\\)|,", "").split(" ")[1];
				if (test.equals(String.valueOf(worldDataOffsetVal))) {
					worldDataOffset = i;
					break;
				}
			}
		}
		*/
		int columns = Integer.parseInt(getStringWith(worldDataOffsetVal, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", ""));
		int rows = Integer.parseInt(getStringWith(worldDataOffsetVal + 1, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", ""));
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new FileWriter(args[1]));
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
		//int gridOffset = Integer.parseInt(stringArray.get(worldDataOffset + 3).replaceAll("Beeper: \\(|, \\d+\\) \\d+", ""));
		int gridOffset = worldDataOffsetVal + 3;
		//System.out.println(stringArray.get(worldDataOffset) + 3);
		//System.out.println(gridOffset + " " + worldDataOffsetVal);
		outFile.println("Dimension: (" + columns + ", " + rows + ")");
		System.out.println(getStringWith(worldDataOffsetVal + 2, stringArray, "Beeper"));
		for (int i = stringArray.indexOf(getStringWith(worldDataOffsetVal + 2, stringArray, "Beeper")) + 1; i < stringArray.size(); i++) {
			if (stringArray.get(i).startsWith("Beeper:")) {
				int row = Integer.parseInt(stringArray.get(i).replaceAll("Beeper: \\(|, \\d+\\) \\d+", ""));
				int column = Integer.parseInt(stringArray.get(i).replaceAll("Beeper: \\(\\d+, |\\) \\d+", ""));
				if (column == 6) {
					String beepers = stringArray.get(i).replaceAll("Beeper: \\(\\d+, \\d+\\) ", "");
					outFile.println("Beeper: (" + ((row - (gridOffset)) % columns + 1) + ", " + ((row - (gridOffset)) / columns + 1) + ") " + beepers);
				} else if (column == 2) {
					outFile.println("Wall: (" + ((row - (gridOffset)) % columns + 1) + ", " + ((row - (gridOffset)) / columns + 1) + ") south");
				} else if (column == 8) {
					outFile.println("Wall: (" + ((row - (gridOffset)) % columns + 1) + ", " + ((row - (gridOffset)) / columns + 1) + ") west");
				}
			}
		}
		//System.out.println(worldDataOffset);
		String karel_x = getStringWith(worldDataOffsetVal - 4, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", "");
		String karel_y = getStringWith(worldDataOffsetVal - 3, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", "");
		String karel_dir = getStringWith(worldDataOffsetVal - 2, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", "");
		if (karel_dir.equals("0")) {
			karel_dir = "north";
		} else if (karel_dir.equals("1")) {
			karel_dir = "east";
		} else if (karel_dir.equals("2")) {
			karel_dir = "south";
		} else {
			karel_dir = "west";
		}
		String karel_beepers = getStringWith(worldDataOffsetVal - 1, stringArray, "Beeper").replaceAll("Beeper: \\(\\d+, \\d+\\) ", "");
		outFile.println("Karel: (" + (Integer.parseInt(karel_x) + 1) + ", " + (Integer.parseInt(karel_y) + 1) + ") " + karel_dir);
		outFile.println();
		outFile.println("BeeperBag: " + karel_beepers);
		outFile.println("Speed: 1.00");
		outFile.close();
	}
	
	public static String getStringWith(int rowVal, ArrayList<String> stringArray, String startString) {
		for (String str : stringArray) {
			if (str.startsWith(startString)) {
				if (rowVal == Integer.parseInt(str.replaceAll("\\D", " ").replaceAll(" +", " ").trim().split(" ")[0])) {
					return str;
				}
			}
		}
		return "0";
	}
}
