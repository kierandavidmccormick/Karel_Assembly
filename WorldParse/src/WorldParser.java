import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WorldParser {
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
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new FileWriter(args[1]));
		} catch (IOException e) {
			System.err.print(e.getMessage());
		}
		int baseOffset = 10;
		int lineOffset = 17;
		int columns = Integer.parseInt(stringArray.get(0).substring(12).split(", ")[0]);
		int rows = Integer.parseInt(stringArray.get(0).substring(12).split(", ")[1].replaceAll("\\)", ""));
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
				karel_x = Integer.parseInt(temp[0]);
				karel_y = Integer.parseInt(temp[1]);
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
		outFile.println("Beeper: (" + (baseOffset + 7) + ", " + 6 + ") " + lineOffset);
		for (int i = 0; i < worldStringsBeepers.length; i++) {
			if (worldStringsBeepers[i] != null) {
				outFile.println("Beeper: (" + (i + lineOffset + 1) + ", " + 6 + ") " + worldStringsBeepers[i]);
			}
			if (worldBoolsWallsSouth[i]) {
				outFile.println("Beeper: (" + (i + lineOffset + 1) + ", " + 2 + ") 1");
			}
			if (worldBoolsWallsWest[i]) {
				outFile.println("Beeper: (" + (i + lineOffset + 1) + ", " + 8 + ") 1");
			}
		}
		//outFile.println("Karel: (1, 2) east");
		//outFile.println("BeeperBag: INFINITE");
		//outFile.println("Speed: 1.00");
		//NOTE: include as part of an existing file, does not generate all of the parts necessary for a full world file
		outFile.close();
	}
}
