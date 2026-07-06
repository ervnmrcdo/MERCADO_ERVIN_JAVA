package com.ibm.javatraining.day6;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class MalformedLogEntryException extends Exception {
    public MalformedLogEntryException(String msg) {
        super(msg);
    }
}

public class LogAnalyzer {

    public static void main(String[] args) {
        //String filename = "resources/server.log";
    	String filename = args[0];
        Map<String, Integer> levelCount = new HashMap<>();
        levelCount.put("INFO", 0);
        levelCount.put("WARN", 0);
        levelCount.put("ERROR", 0);

        List<String> errorMessages = new ArrayList<>();
        LocalDateTime earliest = null;
        LocalDateTime latest = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    // Validate and parse
                    if (!line.startsWith("[") || !line.contains("]"))
                        throw new MalformedLogEntryException("Missing timestamp brackets");

                    int endBracket = line.indexOf("]");
                    String timestampStr = line.substring(1, endBracket);
                    LocalDateTime timestamp = LocalDateTime.parse(timestampStr, formatter);

                    String rest = line.substring(endBracket + 2); // skip "] "
                    String[] parts = rest.split(": ", 2);

                    if (parts.length < 2)
                        throw new MalformedLogEntryException("Missing message");

                    String level = parts[0];
                    String message = parts[1];

                    if (!level.equals("INFO") && !level.equals("WARN") && !level.equals("ERROR"))
                        throw new MalformedLogEntryException("Invalid log level");

                    // Count levels
                    levelCount.put(level, levelCount.get(level) + 1);

                    // Collect error messages
                    if (level.equals("ERROR")) {
                        errorMessages.add(message);
                    }

                    // Track earliest and latest timestamps
                    if (earliest == null || timestamp.isBefore(earliest))
                        earliest = timestamp;

                    if (latest == null || timestamp.isAfter(latest))
                        latest = timestamp;

                } catch (MalformedLogEntryException e) {
                    System.out.println("Skipping malformed line: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Log file not found.");
            return;
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        // Write summary
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/summary.txt"))) {
            bw.write("Log Summary Report\n");
            bw.write("------------------\n");
            bw.write("Total Entries: " + (levelCount.get("INFO") + levelCount.get("WARN") + levelCount.get("ERROR")) + "\n");
            bw.write("INFO: " + levelCount.get("INFO") + "\n");
            bw.write("WARN: " + levelCount.get("WARN") + "\n");
            bw.write("ERROR: " + levelCount.get("ERROR") + "\n\n");

            bw.write("Error Messages:\n");
            for (String msg : errorMessages) {
                bw.write("- " + msg + "\n");
            }

            bw.write("\nEarliest Timestamp: " + earliest + "\n");
            bw.write("Latest Timestamp: " + latest + "\n");

        } catch (IOException e) {
            System.out.println("Error writing summary file.");
        }

        System.out.println("Analysis complete. Summary written to summary.txt");
    }
}