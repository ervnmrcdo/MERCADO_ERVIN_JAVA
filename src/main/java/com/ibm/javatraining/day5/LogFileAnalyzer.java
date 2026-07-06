package com.ibm.javatraining.day5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MalformedLogEntryException extends Exception {
	public MalformedLogEntryException(String msg) {
		super(msg);
	}
}

class ServerLogAnalyzer {

}

class LogFileAnalyzer {
	
	private static boolean checkLogValidity(String line) {
		return (line.startsWith("[")
				&& line.contains("]")
				&& (line.contains("INFO")
						|| line.contains("WARN")
						|| line.contains("ERROR"))
				&& line.contains(":")) ? true : false;
	}

	private static void createLogSummaryReport(Map<String, Integer> logEntryCount,
			ArrayList<String> errorMessages,
			String oldestLog,
			String newestLog) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("summary.txt"))) {
			bw.write("Log Summary Report");
			bw.newLine();
			bw.write("------------------");
			bw.newLine();
			bw.write("Total Entries: " + logEntryCount.get("TOTAL"));
			bw.newLine();
			bw.write("INFO: " + logEntryCount.get("INFO"));
			bw.newLine();
			bw.write("WARN: " + logEntryCount.get("WARN"));
			bw.newLine();
			bw.write("ERROR: " + logEntryCount.get("ERROR"));
			bw.newLine();

			bw.newLine();
			bw.write("Error Messages:");
			bw.newLine();
			for (String errorMsg : errorMessages) {
				bw.write("- " + errorMsg);
				bw.newLine();
			}

			bw.newLine();
			bw.write("Earliest Timestamp: " + oldestLog);
			bw.newLine();
			bw.write("Latest Timestamp: " + newestLog);
			bw.newLine();

			System.out.println("summary.txt succesfully created");

		} catch (FileNotFoundException e) {
			System.out.println("");
		} catch (IOException e) {
			System.out.println("");
		}
	}

	public static void analyzeLogs(String fileName) {
		Map<String, Integer> logEntryCount = new HashMap<String, Integer>() {
			{
				put("INFO", 0);
				put("WARN", 0);
				put("ERROR", 0);
				put("TOTAL", 0);
			}
		};
		ArrayList<String> errorMessages = new ArrayList<>(); // Diamond operator (<>) cleans up the right side
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime oldestLog = null;
		LocalDateTime newestLog = null;

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (checkLogValidity(line)) {
					throw new MalformedLogEntryException("Invalid type of entry");
				} else {
					String regex = "\\[(.*?)\\]\\s+(\\w+):\\s+(.*)";
					Matcher matcher = Pattern.compile(regex).matcher(line);

					if (matcher.find()) {
						String level = matcher.group(2);
						LocalDateTime dateAndTime = LocalDateTime.parse(matcher.group(1), formatter);
						String logDetails = matcher.group(3);

						switch (level) {
							case "INFO":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								break;
							case "WARN":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								break;
							case "ERROR":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								errorMessages.add(logDetails);
						}
						oldestLog = (oldestLog == null || dateAndTime.isBefore(oldestLog)) ? dateAndTime : oldestLog;
						newestLog = (newestLog == null || dateAndTime.isAfter(newestLog)) ? dateAndTime : newestLog;
						logEntryCount.put("TOTAL", logEntryCount.get("TOTAL") + 1);
					}
				}
			}

			createLogSummaryReport(logEntryCount, errorMessages, oldestLog.format(formatter),
					newestLog.format(formatter));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		} catch (MalformedLogEntryException e) {
			System.out.println("Malformed Log Entry Exception");
		}
	}
	
	public static void main(String[] args) {
		analyzeLogs("yessir");
	}

}