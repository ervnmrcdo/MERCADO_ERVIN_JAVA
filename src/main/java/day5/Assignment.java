package day5;
import java.io.*;
import java.util.*;
import java.time.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.format.DateTimeFormatter;

class MalformedLogEntryException extends Exception {
    public MalformedLogEntryException(String msg) {
        super(msg);
    }
}

class ServerLogAnalyzer {
	private static boolean checkLogValidity(String line) {
		return (line.startsWith("[") 
				&& line.contains("]") 
				&& ( 	line.contains("INFO")
						&& line.contains("WARN")
						&& line.contains("ERROR"))
				&& line.contains(":")
				) ? true : false;
	}
	
	private static void createLogSummaryReport (Map<String, Integer> logEntryCount,
												ArrayList<String> errorMessages,
												String  oldestLog,
												String newestLog											
												) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("summary.txt"))){
			bw.write("Log Summary Report"); bw.newLine();
			bw.write("------------------"); bw.newLine(); bw.newLine();
			bw.write("Total Entries:" + logEntryCount.get("TOTAL")); bw.newLine();
			bw.write("INFO:" + logEntryCount.get("INFO")); bw.newLine();
			bw.write("WARN:" + logEntryCount.get("WARN")); bw.newLine();
			bw.write("ERROR:" + logEntryCount.get("ERROR")); bw.newLine();
			
			bw.newLine();
			bw.write("Error Messages:"); bw.newLine();
			for (String errorMsg : errorMessages){
				bw.write("\t- " + errorMsg); bw.newLine();
			}
			
			bw.newLine();			
			bw.write("Earliest Timestamp: " + oldestLog); bw.newLine();
			bw.write("Latest Timestamp: " + newestLog); bw.newLine();

			
		} catch (FileNotFoundException e){
			System.out.println("");
		} catch (IOException e){
			System.out.println("");		
		}
		
		
		
		//System.out.println(logEntryCount);
		//System.out.println(errorMessages);
		//System.out.println(oldestLog);
		//System.out.println(newestLog);
		
	}
	
	
	public static void analyzeLogs(String fileName) {
		Map<String, Integer> logEntryCount = new HashMap<>();
		ArrayList<String> errorMessages = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		logEntryCount.put("INFO", 0);
		logEntryCount.put("WARN", 0);
		logEntryCount.put("ERROR", 0);
		logEntryCount.put("TOTAL", 0);
		LocalDateTime oldestLog = null;
		LocalDateTime newestLog = null;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while((line = br.readLine()) != null) {
				if (checkLogValidity(line)) {
					throw new MalformedLogEntryException("Invalid type of entry");
				} else {					
					String regex = "\\[(.*?)\\]\\s+(\\w+):\\s+(.*)";
					Matcher matcher = Pattern.compile(regex).matcher(line);
					
					if (matcher.find()){
						String level = matcher.group(2);
						LocalDateTime dateAndTime = LocalDateTime.parse(matcher.group(1), formatter); 
						String logDetails = matcher.group(3);
		
						switch (level){
							case "INFO":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								break;
							case "WARN":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								break;
							case "ERROR":
								logEntryCount.put(level, logEntryCount.get(level) + 1);
								errorMessages.add(logDetails);
								break;
						}
						oldestLog = (oldestLog == null || dateAndTime.isBefore(oldestLog)) ? dateAndTime : oldestLog;
						newestLog = (newestLog == null || dateAndTime.isAfter(newestLog)) ? dateAndTime : newestLog;
						logEntryCount.put("TOTAL", logEntryCount.get("TOTAL") + 1);
					}
				}
			}
		createLogSummaryReport(logEntryCount, errorMessages, oldestLog.format(formatter), newestLog.format(formatter));
		//System.out.println(oldestLog);
		//System.out.println(newestLog);
		} catch (FileNotFoundException e) {
			System.out.println("");
		}catch (IOException e) {
			System.out.println("");
		} catch (MalformedLogEntryException e) {
			
		}
	}
}

class Assignment {
	public static void main (String[] args) {
		ServerLogAnalyzer.analyzeLogs("server.log");
	}
	
}