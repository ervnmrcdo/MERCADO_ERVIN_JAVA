package com.ibm.javatraining.day6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.ibm.javatraining.day6.LogAnalyzer;

class LogAnalyzerTest {

    private final PrintStream originalOut = System.out;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@BeforeEach
	public void setup () {
		System.setOut(new PrintStream(outContent));
	}
	
	@AfterEach 
	public void closing () {
		System.setOut(originalOut);
		
	}
	
	// (Normal)
	// checks for the standard execution
	@Test
	void exec001() throws IOException {
		String expectedFile = Files.readString(Path.of("src/main/resources/exec001/summary.txt"));

		String file = "src/main/resources/exec001/server.log";
		
		LogAnalyzer.main(new String[] {file});
		
		String summaryFile = Files.readString(Path.of("resources/summary.txt"));

		assertEquals(expectedFile, summaryFile);
	}
	
	// (Abnormal)
	// Test for File Not Found Exception 
	@Test 
	void exec002() throws IOException {		

		String file = "src/main/resources/exec002/server.log";
		
		LogAnalyzer.main(new String[] {file});
		
		assertTrue(outContent.toString().contains("Log file not found."));	
	}
	
	// (Abnormal)
	// Test for missing brackets 
	@Test
	void exec003() throws IOException {		
		String file = "src/main/resources/exec003/server.log";
		LogAnalyzer.main(new String[] {file});
		assertTrue(outContent.toString().contains("Skipping malformed line: 2024-05-10 09:00:00]"));	
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:03"));
		assertTrue(outContent.toString().contains("Skipping malformed line: 2024-05-10 09:00:06"));		
	}
	
	// (Abnormal)
	// Test for missing/incorrect levels 
	@Test
	void exec004() throws IOException {		
		String file = "src/main/resources/exec004/server.log";
		LogAnalyzer.main(new String[] {file});
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] INFORMATION: Server started successfully"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:03] LEVELING: Configuration file loaded"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:06] WARNING: Database connection established"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:09] NONEXISTENT: Listening on port 8080"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] info: Server started successfully"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] level: Server started successfully"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] warn: Server started successfully"));
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] : Server started successfully"));
	}
	
	// (Abnormal)
	// Test for missing time stamp, level, or details.
	@Test
	void exec005() throws IOException {		
		String file = "src/main/resources/exec005/server.log";
		LogAnalyzer.main(new String[] {file});
		assertTrue(outContent.toString().contains("Skipping malformed line: [2024-05-10 09:00:00] INFO:"));
		assertTrue(outContent.toString().contains("Skipping malformed line: : Configuration file loaded"));
	}
	
	// (Abnormal)
	// Test for non-writeable server logs
	@Test
	void exec006() throws IOException {		
		String file = "src/main/resources/exec006/server.log";
		File readOnlySummary = new File("resources/summary.txt");
		readOnlySummary.setReadOnly();
		System.out.println("ello");
		LogAnalyzer.main(new String[] {file});
		assertTrue(outContent.toString().contains("Error writing summary file."));
		readOnlySummary.setWritable(true);
		readOnlySummary.delete();
	}
	
	// (Abnormal)
	// Test for missing non-readable server logs  
	@Test
	void exec007() throws IOException {		
		String file = "src/main/resources/exec007/server.log";
		Path nonReadableFile= Path.of(file);
		FileChannel.open(nonReadableFile, StandardOpenOption.READ, StandardOpenOption.WRITE).lock();
		
		LogAnalyzer.main(new String[] {file});
		assertTrue(outContent.toString().contains("Error reading file."));
	}
	
	// Test for summary.txt not created 
	@Test
	void exec008 () throws IOException {
		Executable executable = () -> Files.readString(Path.of("resources/summary.txt"));
		assertThrows(NoSuchFileException.class, executable);
	}
}
