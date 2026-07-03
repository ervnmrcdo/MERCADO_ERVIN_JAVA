package day5;

import java.io.*;
import java.util.*;

public class Day5 {

	private static void generateJSONFile(ArrayList<Map<String, String>> studentArray, String[] headers) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("student.json"))) {
			bw.write("[");
			bw.newLine();
			for (int i = 0; i < studentArray.size(); i++) {
				bw.write("\t{");
				bw.newLine();
				for (int j = 0; j < studentArray.get(i).size(); j++) {
					bw.write("\t\t" + "\"" + headers[j] + "\": " + "\"" + studentArray.get(i).get(headers[j])
							+ "\"" + ((j != studentArray.get(i).size() - 1) ? "," : ""));
					bw.newLine();
				}
				bw.write("\t}" + ((i != studentArray.size() - 1) ? "," : ""));
				bw.newLine();
			}
			bw.write("]");
			bw.newLine();

		} catch (FileNotFoundException e) {
			System.out.println("FIle Not Found: " + e);
		} catch (IOException e) {
			System.out.println("IO Exception Error: " + e);
		}
	}

	private static void logStudentCSV(ArrayList<Map<String, String>> studentArray, String[] headers) {
		System.out.println("[");
		for (int i = 0; i < studentArray.size(); i++) {
			System.out.println("\t{");
			for (int j = 0; j < studentArray.get(i).size(); j++) {
				System.out.println("\t\t" + "\"" + headers[j] + "\": " + studentArray.get(i).get(headers[j])
						+ ((j != studentArray.get(i).size() - 1) ? "," : ""));
			}
			System.out.println("\t}" + ((i != studentArray.size() - 1) ? "," : ""));
		}
		System.out.println("]");
	}

	private static void convertCSVFile(String fileName) {
		ArrayList<Map<String, String>> studentArray = new ArrayList<>();
		String[] headers = {};

		try (BufferedReader br = new BufferedReader(new FileReader("student.csv"))) {
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String[] separatedLine = line.split(",");
				if (i == 0) {
					headers = separatedLine;
					i++;
					continue;
				}
				Map<String, String> tempHashMap = new HashMap<>();
				for (int j = 0; j < headers.length; j++) {
					tempHashMap.put(headers[j], separatedLine[j]);
				}
				studentArray.add(tempHashMap);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println(e);
		}

		logStudentCSV(studentArray, headers);
		generateJSONFile(studentArray, headers);
	}

	public static void main(String[] args) {

		convertCSVFile("student.csv");
	}
}
