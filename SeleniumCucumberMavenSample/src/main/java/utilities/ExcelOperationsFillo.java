package utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelOperationsFillo {

	public static ArrayList<Map<String, String>> readExcelTestCases(String filePath, String sheetName,
			String testCaseID) {

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

		String query = "";

		if (sheetName.contains("data3"))
			query = String.format("select EMAIL_ID from %s where TEST_CASE_ID = '%s' ", sheetName, testCaseID);
		else
			query = String.format("select USERNAME, PASSWORD  from %s where TEST_CASE_ID = '%s' ", sheetName,
					testCaseID);

		System.out.println(query);
		Fillo fillo = new Fillo();
		Connection conn = null;
		Recordset recordset = null;

		try {
			conn = fillo.getConnection(filePath);
			recordset = conn.executeQuery(query);
			System.out.println("number of records fetched are : " + recordset.getCount());

			while (recordset.next()) {

				Map<String, String> TestDataInMap = new LinkedHashMap<String, String>();

				for (String field : recordset.getFieldNames()) {
					TestDataInMap.put(field, recordset.getField(field));
				}

				list.add(TestDataInMap);
			}

			System.out.println("The size of the set is : " + list.size());

			recordset.close();

		} catch (FilloException e) {

			e.printStackTrace();
		}

		conn.close();
		return list;

	}

	public static String updateExcelAfterTest(String filePath, String sheetName, String testCaseID, String message1,
			String message2) {

		String strQuery = "";
		String message = "";
		if (sheetName.equalsIgnoreCase("data1")) {
			strQuery = String.format("update %s set ALERT_MESSAGE = '%s' where TEST_CASE_ID = '%s' ", sheetName,
					message1, testCaseID);
		} else if (sheetName.equalsIgnoreCase("data2")) {
			strQuery = String.format(
					"update %s set USERID_BLANK_ALERT_MESSAGE = '%s', PASSWORD_BLANK_ALERT_MESSAGE = '%s' where TEST_CASE_ID = '%s' ",
					sheetName, message1, message2, testCaseID);
		} else if (sheetName.equalsIgnoreCase("data3")) {
			strQuery = String.format("update %s set USERNAME = '%s', PASSWORD = '%s' where TEST_CASE_ID = '%s' ",
					sheetName, message1, message2, testCaseID);
		}

		System.out.println(strQuery);
		Fillo fillo = new Fillo();
		Connection conn = null;

		try {
			conn = fillo.getConnection(filePath);
			conn.executeUpdate(strQuery);
			message = "update success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "update failed";
		}
		conn.close();
		return message;
	}

	public static String updateExcelAfterTestWithStatus(String filePath, String sheetName, String testCaseID,
			String status) {

		String message = "";

		String strQuery = String.format("update %s set RUN_STATUS = '%s' where TEST_CASE_ID = '%s' ", sheetName, status,
				testCaseID);

		System.out.println(strQuery);
		Fillo fillo = new Fillo();
		Connection conn = null;

		try {
			conn = fillo.getConnection(filePath);
			conn.executeUpdate(strQuery);
			message = "run status update success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "run status update failed";
		}
		conn.close();
		return message;
	}
}
