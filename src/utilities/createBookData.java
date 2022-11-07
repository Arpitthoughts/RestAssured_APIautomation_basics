package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;


public class createBookData {
	
	public static Object[][] BookData() throws IOException
	{
	
	FileInputStream fis = new FileInputStream("D:\\RestAssuredAPI_Automation\\DemoProject\\createBookData.xlsx");
	XSSFWorkbook xwork= new XSSFWorkbook(fis);
	XSSFSheet xsheet= xwork.getSheetAt(0);
	
	int totalRows=xsheet.getPhysicalNumberOfRows();
	int totalCols=xsheet.getRow(0).getLastCellNum();
	Object[][] data=new Object [totalRows-1][totalCols];
	System.out.println(totalRows+" "+totalCols);
	
	for(int i=0;i<totalRows-1;i++) {
		
		XSSFRow row=xsheet.getRow(i);
		
		for(int j=0;j<2;j++) {
			
			XSSFCell cellValue=row.getCell(j);
			//data[i][j]=cellValue.getStringCellValue();
					
					switch(cellValue.getCellType()) {
					case Cell.CELL_TYPE_STRING:data[i][j]=cellValue.getStringCellValue();break;
					case Cell.CELL_TYPE_NUMERIC:data[i][j]=cellValue.getStringCellValue();break;
					}
		}
		
	}
	
	return data;
	}
	
}
