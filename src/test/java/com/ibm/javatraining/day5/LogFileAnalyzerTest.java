package com.ibm.javatraining.day5;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.*;


import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class LogFileAnalyzerTest {

	@Test
	void should_Throw_FileNotFoundException_When_File_Not_Found() {
		String fileName = "doesNotExist.txt";
		Executable executable = () -> LogFileAnalyzer.analyzeLogs(fileName);
		assertThrows(FileNotFoundException.class, executable);
	}

}
