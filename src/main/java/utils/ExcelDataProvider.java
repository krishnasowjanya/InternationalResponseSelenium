package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exceptions.ExcelColumnDuplicateException;
import exceptions.ExcelColumnEmptyException;
import exceptions.TestCaseIDNotFoundException;

public class ExcelDataProvider{

	public static String[][] getExcelData(String workBookName,String dataSheetName) {

		String[][] data = null ;

		try {
			FileInputStream fis = new FileInputStream("./data/"+workBookName+".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			//XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFSheet sheet = workbook.getSheet(dataSheetName);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];


			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = "";
							try{
								cellValue = row.getCell(j).getStringCellValue();								
							}catch(NullPointerException e){

							}

							data[i-1][j]  = cellValue; // add to the data array
						} catch (Exception e) {
 							e.printStackTrace();
						}				
					}

				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}

		return data;		
		
	}
	
	public static String getCellDataByFilter_Copy(String workBookName,String dataSheetName,String searchString,int columnIndexToRetrieve) {

		String data = null ;

		try {
			 // Create the input stream from file
			FileInputStream fis = new FileInputStream("./data/"+workBookName+".xlsx");
	       
	        // Create Workbook instance for input stream
	        Workbook workbook = null;
/*	        if (ipFile.toLowerCase().endsWith("xlsx")) {
	             workbook = new XSSFWorkbook(fis);
	        } else if (ipFile.toLowerCase().endsWith("xls")) {
	             workbook = new HSSFWorkbook(fis);
	        }*/
			
			
			//XSSFWorkbook workbook = new XSSFWorkbook(fis);			
			Sheet sheet = workbook.getSheet(dataSheetName);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();	


			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					Row row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = "";
							try{
								System.out.println("row: " + j);
								Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
								cellValue = row.getCell(j).getStringCellValue();
								if(cellValue.equalsIgnoreCase(searchString))
								return row.getCell(columnIndexToRetrieve).getStringCellValue();												
												
							}catch(NullPointerException e){
								e.printStackTrace();
							}
							
						} catch (Exception e) {
 							e.printStackTrace();
						}				
					}
					

				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}

		return data;

	}

	
	public static String getCellDataByFilter(String workBookName,String dataSheetName,String searchString,int columnIndexToRetrieve) {

		String data = null ;

		try {
			FileInputStream fis = new FileInputStream("./data/"+workBookName+".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);			
			XSSFSheet sheet = workbook.getSheet(dataSheetName);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();	


			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						try {
							String cellValue = "";
							try{
/*								Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
								if(cell==null) {
									cellValue="";
									continue;	
								}else
								cellValue = cell.getStringCellValue();*/
								
								cellValue=row.getCell(j).getStringCellValue();								
								if(cellValue.equalsIgnoreCase(searchString))
								return row.getCell(columnIndexToRetrieve).getStringCellValue();												
												
							}catch(NullPointerException e){
								e.printStackTrace();
							}
							
						} catch (Exception e) {
 							e.printStackTrace();
						}				
					}
					

				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}

		return data;

	}

	public static HashMap<String,String> getExcelDataAsCollection(String workBookName,String dataSheetName) {
		
		HashMap<String,String> Exldata=new HashMap<String,String>();
		String key,value;
		
		try {
			FileInputStream fis = new FileInputStream("./data/"+workBookName+".xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook(fis);			
			XSSFSheet sheet = workbook.getSheet(dataSheetName);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();	


			// loop through the rows
			for(int i=1; i <rowCount+1; i++){
				try {
					XSSFRow row = sheet.getRow(i);					
					try{
						key = row.getCell(0).getStringCellValue();
						value = row.getCell(1).getStringCellValue();
						Exldata.put(key, value);
						
					}catch(NullPointerException e){
						e.printStackTrace();
					}			

				} catch (Exception e) {
 					e.printStackTrace();
				}
			}
			fis.close();
			workbook.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}

		return Exldata;

	}
	
	public static HashMap<String,String> getExcelRowAsHashMapByTestID(String workBookName,String dataSheetName,String TestID) {
		HashMap<String,String> Exldata=new HashMap<String,String>();
		String key,value;
		FileInputStream	fis = null;
		XSSFWorkbook workbook = null;
		try {
			 fis = new FileInputStream("./data/"+workBookName+".xlsx");
			 workbook = new XSSFWorkbook(fis);			
			XSSFSheet sheet = workbook.getSheet(dataSheetName);

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();	
			
			LinkedHashSet<String> Exlheaders = new LinkedHashSet<String>();
			for(int j=0; j<columnCount; j++) {
				XSSFRow row = sheet.getRow(0);	
				if (!row.getCell(j).getStringCellValue().isEmpty()) {
					if(!Exlheaders.add(row.getCell(j).getStringCellValue())) 
						throw new ExcelColumnDuplicateException("One of the Column in Excel is duplicate");
						}
				else 
					throw new ExcelColumnEmptyException("One of the Column is Empty in Excel");
				}
			
			// loop through the rows
			int testRow=-1;
			for(int i=1; i < rowCount+1; i++){
					XSSFRow row = sheet.getRow(i);				
						for(int j=0; j<columnCount; j++) {
							value = row.getCell(j,MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
							if(value.equals(TestID)) {
							 testRow = i;	
							 break;
								}
							}
							if(testRow!=-1)
								break;
				}
			
				if(testRow == -1)
					throw new TestCaseIDNotFoundException("No row with given TestID is present in excel.");
			 
		//put the row data to hashmap
			XSSFRow row = sheet.getRow(testRow);	
			int iCounter =0;
			Iterator<String> iterator = Exlheaders.iterator(); 
		      while (iterator.hasNext() && iCounter < columnCount ){
		        key = (String) iterator.next();
				value = row.getCell(iCounter,MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
				Exldata.put(key, value);
				iCounter++;
		      }	
		     
		}
																catch(TestCaseIDNotFoundException ez){
																	ez.printStackTrace();
																}
													catch(ExcelColumnDuplicateException ey){
														ey.printStackTrace();
													}
								catch(ExcelColumnEmptyException ex){
									ex.printStackTrace();
								}		
		
						catch(NullPointerException e){
						e.printStackTrace();
					}			

				 catch (Exception e) {
 					e.printStackTrace();
				 }
		finally {
				try {
			 
				if(fis!=null && workbook!=null) {
					workbook.close();					
					fis.close();
				}
			}
		catch(IOException i){
			i.printStackTrace();
		}
		}
			return Exldata;
			
		} 
		
	
	}


