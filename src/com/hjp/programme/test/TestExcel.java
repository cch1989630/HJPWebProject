package com.hjp.programme.test;

/**
 * Jun 25, 2012
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hjp.programme.util.DateStringUtils;
import com.hjp.programme.util.PwdEncryptor;

/**
 * Excel组件
 * 
 * @author Snowolf
 * @version 1.0
 * @since 1.0
 */
public abstract class TestExcel {

	/**
	 * Excel 2003
	 */
	private final static String XLS = "xls";
	/**
	 * Excel 2007
	 */
	private final static String XLSX = "xlsx";
	/**
	 * 分隔符
	 */
	private final static String SEPARATOR = "@";

	/**
	 * 由Excel文件的Sheet导出至List
	 * 
	 * @param file
	 * @param sheetNum
	 * @return
	 */
	public static List<String> exportListFromExcel(File file, int sheetNum)
			throws IOException {
		return exportListFromExcel(new FileInputStream(file),
				FilenameUtils.getExtension(file.getName()), sheetNum);
	}

	/**
	 * 由Excel流的Sheet导出至List
	 * 
	 * @param is
	 * @param extensionName
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	public static List<String> exportListFromExcel(InputStream is,
			String extensionName, int sheetNum) throws IOException {

		Workbook workbook = null;

		if (extensionName.toLowerCase().equals(XLS)) {
			workbook = new HSSFWorkbook(is);
		} else if (extensionName.toLowerCase().equals(XLSX)) {
			workbook = new XSSFWorkbook(is);
		}

		return exportListFromExcel(workbook, sheetNum);
	}

	/**
	 * 由指定的Sheet导出至List
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @return
	 * @throws IOException
	 */
	private static List<String> exportListFromExcel(Workbook workbook,
			int sheetNum) {

		Sheet sheet = workbook.getSheetAt(sheetNum);

		// 解析公式结果
		FormulaEvaluator evaluator = workbook.getCreationHelper()
				.createFormulaEvaluator();

		List<String> list = new ArrayList<String>();

		int minRowIx = sheet.getFirstRowNum();
		int maxRowIx = sheet.getLastRowNum();
		for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
			Row row = sheet.getRow(rowIx);
			StringBuilder sb = new StringBuilder();

			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
				Cell cell = row.getCell(new Integer(colIx));
				CellValue cellValue = evaluator.evaluate(cell);
				if (cellValue == null) {
					continue;
				}
				// 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
				// 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
				switch (cellValue.getCellType()) {
				case Cell.CELL_TYPE_BOOLEAN:
					//sb.append(SEPARATOR + cellValue.getBooleanValue());
					sb.append(SEPARATOR + cellValue.getStringValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					// 这里的日期类型会被转换为数字类型，需要判别后区分处理
					if (DateUtil.isCellDateFormatted(cell)) {
						sb.append(SEPARATOR + cell.getDateCellValue());
					} else {
						sb.append(SEPARATOR + cellValue.getNumberValue());
					}
					break;
				case Cell.CELL_TYPE_STRING:
					sb.append(SEPARATOR + cellValue.getStringValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					break;
				case Cell.CELL_TYPE_BLANK:
					break;
				case Cell.CELL_TYPE_ERROR:
					break;
				default:
					break;
				}
			}
			list.add(sb.toString());
		}
		return list;
	}
	
	public static void updateMerchantCard(List<String> list) throws Exception {
		FileWriter fileWriter=new FileWriter("D:\\Result.txt");
		 for (int i = 1; i < list.size(); i++) {
        	String sqlString = "update tf_f_member_card SET CARD_BALANCE = ";
				String listString = list.get(i);
				//System.out.println(listString);
				String[] stringArray = listString.split(",");
				String cardId= "";
				if (stringArray[1].length() == 7) {
					cardId = "0" + (int)Double.parseDouble(stringArray[1]);
				} else {
					cardId = (int)Double.parseDouble(stringArray[1]) + "";
				}
				sqlString = sqlString + (long)DateStringUtils.mul(Double.parseDouble(stringArray[2]), 100.0) + 
						" where CARD_ID = " + cardId+ ";\n";
				fileWriter.write(sqlString);
		 }
		 fileWriter.flush();
		 fileWriter.close();
	}
	
	public static void merchantCard(List<String> list) throws Exception {
		FileWriter fileWriter=new FileWriter("D:\\Result.txt");
		 for (int i = 1; i < list.size(); i++) {
         	String sqlString = "INSERT INTO tf_f_member_card VALUES (";
				String listString = list.get(i);
				//System.out.println(listString);
				String[] stringArray = listString.split(",");
				String cardTypeCode = "";
				if (stringArray[2].equals("1000元卡")) {
					cardTypeCode = "04";
				} else if (stringArray[2].equals("2000元卡")) {
					cardTypeCode = "05";
				} else if (stringArray[2].equals("5000元卡")) {
					cardTypeCode = "06";
				} else if (stringArray[2].equals("欢乐卡")) {
					cardTypeCode = "01";
				} else if (stringArray[2].equals("金卡")) {
					cardTypeCode = "03";
				} else if (stringArray[2].equals("银卡")) {
					cardTypeCode = "02";
				}
				
				String cardId= "";
				if (stringArray[1].length() == 7) {
					cardId = "0" + (int)Double.parseDouble(stringArray[1]);
				} else {
					cardId = (int)Double.parseDouble(stringArray[1]) + "";
				}
				
				sqlString = sqlString + "'" + cardId + "', '贵宾', '" + cardTypeCode + "', '', " + 
						(long)DateStringUtils.mul(Double.parseDouble(stringArray[3]), 100.0) + ", 1," + "now()," 
						+ "10000);"+ "\n";
				System.out.println(sqlString);
				
				fileWriter.write(sqlString);
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	public static void staff(List<String> list) throws Exception {
		FileWriter fileWriter=new FileWriter("D:\\Result.txt");
		 for (int i = 1; i < list.size(); i++) {
         	String sqlString = "INSERT INTO tf_m_staff VALUES (";
				String listString = list.get(i);
				System.out.println(listString);
				String[] stringArray = listString.split(",");
				
				String passWord = PwdEncryptor.encryptByMD5("111111",(int)Double.parseDouble(stringArray[2])+"");
				
				sqlString = sqlString + (int)Double.parseDouble(stringArray[2]) + ", '" + stringArray[3] + "', '" + passWord + "', " 
						+ (int)Double.parseDouble(stringArray[1]) + ", 10000, now(), '111111');" +"\n";
				System.out.println(sqlString);
				
				fileWriter.write(sqlString);
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	public static void staffRole(List<String> list) throws Exception {
		FileWriter fileWriter=new FileWriter("D:\\Result.txt");
		 for (int i = 1; i < list.size(); i++) {
         	String sqlString = "INSERT INTO tf_m_staff_role VALUES (";
				String listString = list.get(i);
				System.out.println(listString);
				String[] stringArray = listString.split(",");
				
				
				sqlString = sqlString + (int)Double.parseDouble(stringArray[2]) + ", 'ROLE_LOGIN');" +"\n";
				System.out.println(sqlString);
				fileWriter.write(sqlString);
				sqlString = "INSERT INTO tf_m_staff_role VALUES (";
				sqlString = sqlString + (int)Double.parseDouble(stringArray[2]) + ", 'ROLE_STORE');" +"\n";
				System.out.println(sqlString);
				fileWriter.write(sqlString);
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	public static void qcAdd(List<String> list) throws Exception {
		FileWriter fileWriter=new FileWriter("D:\\Result.txt");
		Long id = 10001093l;
		 for (int i = 0; i < list.size(); i++) {
         	String sqlString = "INSERT INTO tf_m_qc VALUES (";
				String listString = list.get(i);
				System.out.println(listString);
				String[] stringArray = listString.split("@");
				sqlString = sqlString + id + ",'" + stringArray[2] +"','";
				BigDecimal bigDecimal = new BigDecimal(stringArray[4]);
				String result = bigDecimal.toString();
				sqlString = sqlString + result + "','";
				sqlString = sqlString + stringArray[1] + "','" + stringArray[3] + "','" + id + ".jpg'," + "1," + "now(),now());" + "\n"; 
				System.out.println(sqlString);
				fileWriter.write(sqlString);
				id = id + 1;
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	public static void main(String[] args) throws Exception {
		String path = "D:/非食材3类.xls";  
        List<String> list = null;  
        try {  
            list = exportListFromExcel(new File(path), 0);  
            qcAdd(list);
        } catch (IOException e) {  
        }
	}
}
