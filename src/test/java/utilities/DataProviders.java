package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\OpenCart_LoginData.xlsx";  //taking xl file from test data
	
		ExcelUtility xlutil=new ExcelUtility(path); //creating an object for XLUtility
		
		int totalrows=xlutil.getRowCount("sheet1");
		int totalcols=xlutil.getCellCount("sheet1", 1);
		
		String logindata[][]=new String[totalrows][totalcols];  //created for two dimensional array
		
		for(int i=1; i<=totalrows;i++)		//1   //read the data from xl storing in two dimensional array
		{
			for(int j=0; j<totalcols;j++)		//0 i is rows j is col
			{
				logindata[i-1][j]=xlutil.getCellData("sheet1", i, j);  //in java counts from 0
			}
		}
		return logindata;		//returning two dimensional array
	}
	
	//DataProvider2
	
	//DataProvider3
	
	//DataProvider4
	

}
