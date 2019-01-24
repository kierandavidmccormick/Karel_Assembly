import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WorldReader {
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
		//System.out.println(stringArray.get(0));
		String[] temp = stringArray.get(0).replaceAll("Dimension: \\(|,|\\)", "").split(" ");
		int columns = Integer.parseInt(temp[0]);
		int rows = Integer.parseInt(temp[1]);
		String[][] numArray = new String[rows][columns];
		for (String str : stringArray) {
			if (str.startsWith("Beeper:")) {
				temp = str.replaceAll("Beeper: \\(|,|\\)", "").split(" ");
				numArray[Integer.parseInt(temp[1]) - 1][Integer.parseInt(temp[0]) - 1] = temp[2].equals("INFINITE") ? "&&&" : temp[2];
			}
		}
		for (int i = numArray.length - 1; i >= 0; i--) {
			for (int j = 0; j < numArray[i].length; j++) {
				if (numArray[i][j] != null && !numArray[i][j].equals("0")) {
					for (int k = 0; k < (4 - numArray[i][j].length()); k++){
						System.out.print(" ");
					}
					System.out.print(numArray[i][j]);
				} else {
					System.out.print("    ");
				}
			}
			System.out.println();
		}
		for (int i = 0; i < columns; i++) {
			String str = Integer.toString(i);
			for (int j = 0; j < (4 - str.length()); j++) {
				System.out.print(" ");
			}
			System.out.print(str);
		}
	}
}
