package com.csvtodb.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.csvtodb.model.Model;

public class Solution {

	private static final List<Model> recordList = null;
	static String[] record = null;
	static int validColumnCount = 11;
	static int invalidColumn = 0;


	/**
	 * Read CSV file from the file path and process it
	 */
	private static void readCVSfile() {

		String csvFile = "ms3Interview.csv";
		List<Model> recordList = new ArrayList<Model>();
		boolean headerRow = true;
		String line = "";
		String delim = ",";

		try {
			File file = new File(csvFile);

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			if (headerRow) {
				String HeadRow = br.readLine();

				if (HeadRow == null || HeadRow.isEmpty()) {
					throw new FileNotFoundException(
							"No columns defined in given CSV file." + "Please check the CSV file format.");
				}
			}

			// checking till last record
			while ((line = br.readLine()) != null) {

				// use comma as separator
				record = line.split(delim);

				// checking valid and invalid column
				if (record.length > 0 && record.length == validColumnCount) {

					// Save the student details in Student object
					String mergeTwoRecords = record[4] + record[5];
					Model model = new Model(record[0], record[1], record[2], record[3], mergeTwoRecords, record[6],
							Float.parseFloat(record[7].replace('$', ' ')), record[8], record[9], record[10]);

					recordList.add(model);

				} else {

					invalidColumn++;
				}
			}
			
			//loading Data List into DB
			LoadCSVintoDatabase(recordList);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}


	/**
	 * This method connect Database
	 * set values and insert them into Database
	 * @param recordList
	 */
	private static void LoadCSVintoDatabase(List<Model> recordList) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:X.db");

			for (Model m : recordList) {

				String sql = "INSERT INTO X (A, B, C, D, E,F,G,H,I,J) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,? ,?)";

				pst = conn.prepareStatement(sql);

				pst.setString(1, m.getA());
				pst.setString(2, m.getB());
				pst.setString(3, m.getC());
				pst.setString(4, m.getD());
				pst.setString(5, m.getE());
				pst.setString(6, m.getF());
				double real = m.getG();
				double roundG = Math.round(real * 100.0) / 100.0;
				pst.setDouble(7, roundG);
				pst.setString(8, m.getH());
				pst.setString(9, m.getI());
				pst.setString(10, m.getJ());
				pst.executeUpdate();

			}

		} catch (NullPointerException NPE) {
			System.out.println("Records Loded");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
			System.exit(0);

		}
		writeStat(invalidColumn, record, recordList);
	}
	
	/**
	 * This method writes the statistic to the log file 
	 * Stastics include number of records successful, 
	 * failed and recieved	
	 * @param recordList
	 */
	public static void writeStat(int invalidColumn, String[] record, List<Model> recordList){
	int recordsRecieved = record.length;
	try {
		String logfile = "logFile.txt";	
		String statistics = "Following are the statistics :\r\n#"+recordsRecieved+ " of records received.\r\n#"
					+ recordList.size() + " of records processed. \r\n#" + invalidColumn + " of records failed.";
			FileWriter fileWriter = new FileWriter(logfile);
		    fileWriter.write(statistics);
		    fileWriter.close();
	   
	        } catch (IOException e) {
	            
	        }  catch(NullPointerException ex) {
	  
		     
	}
}
	

	public static void main(String[] args) {

		Solution sol = null;
		System.out.println("Reading file ...");
		readCVSfile();
		writeStat(invalidColumn, record, recordList);
		LoadCSVintoDatabase(sol.recordList);
		
	}

}
