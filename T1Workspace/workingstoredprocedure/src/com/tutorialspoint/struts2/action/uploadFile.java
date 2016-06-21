package com.tutorialspoint.struts2.action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*import org.apache.catalina.connector.Request;*/
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;;



public class uploadFile extends ActionSupport{
   private File myFile;
   private String myFileContentType;
   private String myFileFileName;
   private String destPath;
   
   
//   private HttpServletRequest servletRequest;
   private static Connection  con= null;
   
	private Connection conn = null;
	private PreparedStatement pstCheckExist = null;
	private ResultSet resultset = null;
	private Statement stmt1=null;
	 public static ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();

   public String execute()
   {
	  
	      /* Copy file to a safe location */
		   destPath = "E:/OKKYOProject/OKKYOProject_14-08-2015_new/WebContent/imageUploads/";
	      
	     
	    	  System.out.println("-----uploadFile----");
	    	  
	    	  //String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
	          //System.out.println("Server path:" + filePath);
	    	  
	    	 String kp = ServletActionContext.getRequest().getContextPath();
	    	  System.out.println("-----kp111s----"+kp);
	     	 System.out.println("Src File name: " + myFile);
	     	 System.out.println("Dst File name: " + myFileFileName);
	      String path = myFile +"/"+myFileFileName;
	     	 System.out.println("-----keshav-------");
	     	 System.out.println("File Name is:"+getMyFileFileName());  
	     	 System.out.println("File Content Type is:"+getMyFileContentType()); 
	     	 System.out.println( File.separator );
	    
	     	 
	       	//String filename = file.getName();
	      	String extension = myFileFileName.substring(myFileFileName.lastIndexOf(".") + 1, myFileFileName.length());

	      	     		int Count = 0;
			String s1 = null ;
			String sValue0 = null, line =null;
			String sValue1 = null;
			String sValue2 = null;
			String sValue3 = null;
			String sValue4 = null;
			String sValue5 = null, sValue6 = null, sValue7 = null,sValue8 = null,sValue9 = null ,sValue10 = null,
					sValue11 = null,sValue12 = null,sValue13 = null,sValue14 = null,sValue15 = null,sValue16 = null
					,sValue17 = null,sValue18 = null,sValue19 = null,sValue20 = null,sValue21 = null;

			 try{
				 
			con = DBconnect.getConnectionStatus();
		    	 
	        String query1 = "delete from DAT_Lane_hist;";
	    	System.out.println(query1);	        	
	    	pstCheckExist = con.prepareStatement(query1);	
	    	pstCheckExist.executeUpdate();
	    	
	    	
	    	String excel = "xls";
	    	if (extension != excel) {
	    		System.out.println("came inside csv"); 
	    	    
	    		
	    		    FileReader fileReader = new FileReader(myFile);

	    	        // Always wrap FileReader in BufferedReader.
	    	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	    	            while((line = bufferedReader.readLine()) != null) 
	    	            {
	    	            	System.out.println("reading csv"); 
	    		    	    
	    	            	ArrayList<String> list = new ArrayList<String>();
	    	               
	    	                String[] tokens = line.split(",");
	    	                
	    	                for(int i=0;i<tokens.length; i++)
	    	                {
	    	                	list.add(tokens[i]);
	    	            	}
	    	                nodes.add(list);
	    	        		
	    	            }
	    	            
	    	            for (ArrayList<String> innerList : nodes) 
		    	    	{
	    	            	String query = "Insert into DAT_Lane_hist ([PC-Miler Practical Mileage],[Spot Avg Linehaul Rate] ,[Spot Low Linehaul Rate],[Spot High Linehaul Rate],[Spot Fuel Surcharge],[Spot Avg Accessorial Excludes Fuel],[Spot Time Frame],[Spot Origin Geo Expansion],[Spot Destination Geo Expansion],[Spot_num of Companies],[Spot_num of Reports],[Spot Linehaul Rate StdDev],[Spot Your Own Avg Linehaul Rate],[Spot Your Own # of Reports] ,[Spot Error],[Orig City],[Orig State] ,[Orig Postal Code],[Dest City],[Dest State],[Dest Postal Code] ,[Truck Type]) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	    		        	System.out.println(query);	        	
	    		        	pstCheckExist = con.prepareStatement(query);            	
	    		        	pstCheckExist.setString(1, innerList.get(0));
	    		        	pstCheckExist.setString(2, innerList.get(1));
	    		        	pstCheckExist.setString(3, innerList.get(2));
	    		        	pstCheckExist.setString(4, innerList.get(3));
	    		        	pstCheckExist.setString(5, innerList.get(4));
	    		        	pstCheckExist.setString(6, innerList.get(5));
	    		        	pstCheckExist.setString(7, innerList.get(6));
	    		        	pstCheckExist.setString(8, innerList.get(7));
	    		        	pstCheckExist.setString(9, innerList.get(8));
	    		        	pstCheckExist.setString(10, innerList.get(9));
	    		        	pstCheckExist.setString(11, innerList.get(10));
	    		        	pstCheckExist.setString(12, innerList.get(11));
	    		        	pstCheckExist.setString(13, innerList.get(12));
	    		        	pstCheckExist.setString(14, innerList.get(13));
	    		        	pstCheckExist.setString(15, innerList.get(14));
	    		        	pstCheckExist.setString(16, innerList.get(15));
	    		        	pstCheckExist.setString(17, innerList.get(16));
	    		        	pstCheckExist.setString(18, innerList.get(17));
	    		        	pstCheckExist.setString(19, innerList.get(18));
	    		        	pstCheckExist.setString(20, innerList.get(19));
	    		        	pstCheckExist.setString(21, innerList.get(20));
	    		        	pstCheckExist.setString(22, innerList.get(21));
	    		        
	    		        	pstCheckExist.executeUpdate();
	    		        	
	    		       
		    	    	}
	    	            String queryv = "delete from DAT_Lane_hist WHERE [PC-Miler Practical Mileage] = 'PC-Miler Practical Mileage';";
				    	System.out.println(queryv);	        	
				    	pstCheckExist = con.prepareStatement(queryv);	
				    	pstCheckExist.executeUpdate();
				 
				    	//file.close();
	    	}
	    	else{
	    	
	    	
	    	FileInputStream file = new FileInputStream(myFile);
	     	//Create Workbook instance holding reference to .xlsx file
	     	System.out.println("new------------------%%%"+file);
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			/*
			while (rowIterator.hasNext()) 
			{*/
			
			 for (Row row : sheet) {
		            // avoid first row as it is header
				 	// count first row cell no. 
		            if (row.getRowNum() == 0) {
		            	System.out.println("====NumberOfCells======"+row.getPhysicalNumberOfCells());
		            }

				 for (int count = 0; count < row.getLastCellNum(); count++) {
		                Cell cell = row.getCell(count);
		                System.out.println("cell==========="+cell);
		               // whenever we get blank cell value, we avoid it and continues the loop
		                if(count==0){
			                if (cell == null) {
			            	   sValue0 = "--";
			                    continue;
			               }}
		                if(count==1){
		                if (cell == null) {
		            	   sValue1 = "--";
		                    continue;
		               }}
		                if(count==2){
			                if (cell == null) {
			            	   sValue2 = "--";
			                    continue;
			               }}
		                if(count==3){
			                if (cell == null) {
			            	   sValue3 = "--";
			                    continue;
			               }}
		                if(count==4){
			                if (cell == null) {
			            	   sValue4 = "--";
			                    continue;
			               }}
		                if(count==5){ if (cell == null) { sValue5 = "--"; continue;
		                	}}
		                if(count==6){ if (cell == null) { sValue6 = "--"; continue;
	                	}}
		                if(count==7){ if (cell == null) { sValue7 = "--"; continue;
	                	}}
		                if(count==8){ if (cell == null) { sValue8 = "--"; continue;
	                	}}
		                if(count==9){ if (cell == null) { sValue9 = "--" ; continue;
	                	}}
		                if(count==10){ if (cell == null) { sValue10 = "--"; continue;
	                	}}
		                if(count==11){ if (cell == null) { sValue11 = "--"; continue;
	                	}}
		                if(count==12){ if (cell == null) { sValue12 = "--"; continue;
	                	}}
		                if(count==13){ if (cell == null) { sValue13 = "--"; continue;
	                	}}
		                if(count==14){ if (cell == null) { sValue14 = "--"; continue;
	                	}}
		                if(count==15){ if (cell == null) { sValue15 = "--"; continue;
	                	}}
		                if(count==16){ if (cell == null) { sValue16 = "--"; continue;
	                	}}
		                if(count==17){ if (cell == null) { sValue17 = "--"; continue;
	                	}}
		                if(count==18){ if (cell == null) { sValue18 = "--"; continue;
	                	}}
		                if(count==19){ if (cell == null) { sValue19 = "--"; continue;
	                	}}
		                if(count==20){ if (cell == null) { sValue20 = "--"; continue;
	                	}}
		                if(count==21){ if (cell == null) { sValue21 = "--"; continue;
	                	}}
	                
	                
	                
		                
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
//				System.out.println("------------ keshav --------");
				

					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_NUMERIC:
							s1=Double.toString(cell.getNumericCellValue());
							System.out.println("111--"+cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_STRING:
							s1=cell.getStringCellValue();
							System.out.println("222--"+cell.getStringCellValue() + "\t");
							break;

					}
					
					
					if(count==0)
						sValue0 = s1;
					if(count==1)
						sValue1 = s1;
					if(count==2)
						sValue2 = s1;
					if(count==3)
						sValue3 = s1;
					if(count==4)
						sValue4 = s1;
					if(count==5)
						sValue5 = s1;
					if(count==6)
						sValue6 = s1;
					if(count==7)
						sValue7 = s1;
					if(count==8)
						sValue8 = s1;
					if(count==9)
						sValue9 = s1;
					if(count==10)
						sValue10 = s1;
					if(count==11)
						sValue11 = s1;
					if(count==12)
						sValue12 = s1;
					if(count==13)
						sValue13 = s1;
					if(count==14)
						sValue14 = s1;
					if(count==15)
						sValue15 = s1;
					if(count==16)
						sValue16 = s1;
					if(count==17)
						sValue17 = s1;
					if(count==18)
						sValue18 = s1;
					if(count==19)
						sValue19 = s1;
					if(count==20)
						sValue20 = s1;
					if(count==21)
						sValue21 = s1;
					
						//System.out.println("==nul27"+sValue4);
				
					
				}
				
				System.out.println("all valuse====="+sValue0+"-----"+sValue1+"---"+sValue2+"---"+sValue3+"---"+sValue4+"---"+sValue5+"--"+sValue6+"--"
				+sValue7+"--"+sValue8+"--"+sValue9+"--"+sValue10+"--"+sValue11+"--"+sValue12+"--"+sValue13+"--"+sValue14+"--"
				+sValue15+"--"+sValue16+"--"+sValue17+"--"+sValue18+"--"+sValue19+"--"+sValue20+"--"+sValue21+"--");
				
				if(row.getRowNum()!=1){
				String query = "insert into try1 (A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16,A17,A18,A19,A20,A21,A22)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	        	System.out.println(query);	        	
	        	pstCheckExist = con.prepareStatement(query);            	
	        	pstCheckExist.setString(1, sValue0);
	        	pstCheckExist.setString(2, sValue1);
	        	pstCheckExist.setString(3, sValue2);
	        	pstCheckExist.setString(4, sValue3);
	        	pstCheckExist.setString(5, sValue4);
	        	pstCheckExist.setString(6, sValue5);
	        	pstCheckExist.setString(7, sValue6);
	        	pstCheckExist.setString(8, sValue7);
	        	pstCheckExist.setString(9, sValue8);
	        	pstCheckExist.setString(10, sValue9);
	        	pstCheckExist.setString(11, sValue10);
	        	pstCheckExist.setString(12, sValue11);
	        	pstCheckExist.setString(13, sValue12);
	        	pstCheckExist.setString(14, sValue13);
	        	pstCheckExist.setString(15, sValue14);pstCheckExist.setString(16, sValue15);pstCheckExist.setString(17, sValue16);
	        	pstCheckExist.setString(18, sValue17);pstCheckExist.setString(19, sValue18);pstCheckExist.setString(20, sValue19);
	        	pstCheckExist.setString(21, sValue20);pstCheckExist.setString(22, sValue21);
	        
	        	pstCheckExist.executeUpdate();
				}
				if(row.getRowNum()==1){
					
				String	query2 ="select id from title";

					 String tID = null ;      	
		        	
		        	
		        	stmt1=con.createStatement();
			    	ResultSet rs = stmt1.executeQuery(query2);
			    	
			    	while(rs.next())
			        {

			    		tID = rs.getString("id");
			        }
			    	
			    	String query = " update title set A16='"+sValue0+"',A17='"+sValue1+"',A18='"+sValue2+"',A19='"+sValue3+"',A20='"+sValue4+"',A21='"+sValue5+"'," +
							" A22='"+sValue6+"', A23='"+sValue7+"',A24='"+sValue8+"',A25='"+sValue9+"',A26='"+sValue10+"',A27='"+sValue11+"',A28='"+sValue12+"'," +
							" A29='"+sValue13+"',A30='"+sValue14+"', A31='"+sValue15+"',A32='"+sValue16+"',A33='"+sValue17+"',A34='"+sValue18+"',A35='"+sValue19+"'," +
							" A36='"+sValue20+"',A37='"+sValue21+"' where id = "+tID+";";
		        	System.out.println(query);	 
			    
		        //	resultset = stmt1.executeQuery(query);
		        	pstCheckExist = con.prepareStatement(query);	
			    	pstCheckExist.executeUpdate();
					}
				
				System.out.println("");
			}
				
		    	
		    	 String queryv = "update try1 set A15 = CONCAT(A15,' ' ,A16) , A18 = concat(A18, ' ', A19);";
			    	System.out.println(queryv);	        	
			    	pstCheckExist = con.prepareStatement(queryv);	
			    	pstCheckExist.executeUpdate();
			 
			file.close();
	     	//FilesUtil.saveFile(getMyFile(),getMyFileFileName(),kp+File.separator+getMyFileContentType());
	      }
	      }catch(Exception e){
	         e.printStackTrace();
	         return ERROR;
	      }
	System.out.println("rohitis grat programer ");
	      return SUCCESS;
	   }
   
   public File getMyFile() {
      return myFile;
   }
   public void setMyFile(File myFile) {
      this.myFile = myFile;
   }
   public String getMyFileContentType() {
      return myFileContentType;
   }
   public void setMyFileContentType(String myFileContentType) {
      this.myFileContentType = myFileContentType;
   }
   public String getMyFileFileName() {
      return myFileFileName;
   }
   public void setMyFileFileName(String myFileFileName) {
      this.myFileFileName = myFileFileName;
   }
   
  // public void setServletRequest(HttpServletRequest servletRequest) {
  //    this.servletRequest = servletRequest;

 //  }
}