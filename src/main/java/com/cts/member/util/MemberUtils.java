package com.cts.member.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cts.member.model.MemberDetail;
import com.cts.member.request.MemberRequest;

@Component
public class MemberUtils {

	
	
	private static String fileUrl;
	
	@Value("${excel.filepath}")
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}


	private final static Logger logger = LoggerFactory.getLogger(MemberUtils.class);
	public static SimpleDateFormat dateformat =  new SimpleDateFormat("MM/dd/yyyy");
	

	
	public static Date stringToDate(String input) {
		Date date = null;
		try {
			
			dateformat.setLenient(false);
			date=dateformat.parse(input);
		} catch (ParseException e) {
			logger.error("Error converting Date");
			e.printStackTrace();
		}
		return date;
	}

	
	  public static boolean isValidDate(String input) {
		  boolean validDate = false;
		  try {
			 
			 dateformat.setLenient(false);
			 dateformat.parse(input);
			 logger.info("date : "+dateformat);
			  validDate = true;
			} catch (ParseException e) {
				logger.error("Error converting Date");
			}
		  return validDate;
	  }

	  public static List<MemberDetail> readXml(String fileUrl){
		  
          //Create Workbook instance holding reference to .xlsx file
		  List<MemberDetail> memberList = new ArrayList<>();
		try {
			 FileInputStream file = new FileInputStream(new File(fileUrl));
			 XSSFWorkbook workbook =  new XSSFWorkbook(file);
			
			//Get first/desired sheet from the workbook
	          XSSFSheet sheet = workbook.getSheetAt(0);

	          //Iterate through each rows one by one
	          Iterator<Row> rowIterator = sheet.iterator();
	          rowIterator.next();
	        
	          while (rowIterator.hasNext()) 
	          {
	        	  MemberDetail member = new MemberDetail(); 
	              Row row = rowIterator.next();
	              //For each row, iterate through all the columns
	              Iterator<Cell> cellIterator = row.cellIterator();
	              while(cellIterator.hasNext()) {
	            	
	            	Cell cell = cellIterator.next(); 
	            	int columnIndex = cell.getColumnIndex();
	            	switch(columnIndex) {
	            		case 0:
	            			member.setMemberId((int)cell.getNumericCellValue());
	            			break;
	            		case 1:
	            			member.setName(cell.getStringCellValue());
	            			break;
	            		case 2:
	            			member.setDob(cell.getDateCellValue());
	            			break;
	            		case 3:
	            			member.setPlanId((int)cell.getNumericCellValue());
	            			break;
	            		case 4:
	            			member.setDependentsNo((int)cell.getNumericCellValue());
	            			break;
	            		case 5:
	            			member.setPlanStartDate(cell.getDateCellValue());
	            			break;
	            		case 6:
	            			member.setPlanEndDate(cell.getDateCellValue());
	            			break;
	            	}
	            	
	              }
	              memberList.add(member);
	          }
	          
              file.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return memberList;
      } 
		  
	  
	  public static boolean validateFileName(String fileName) {
		  
		  final String filePattern  = MemberConstants.FILE_PATTERN;
		  Pattern pattern = Pattern.compile(filePattern);
		  Matcher matcher = pattern.matcher(fileName);
		  return matcher.matches();
		  
	  }
	  public static boolean fileNameTypeCheck(String fileExtension) {
		  if(StringUtils.equalsIgnoreCase(fileExtension, "xlsx"))
			  return true;
		  else return false;
	  }
	  
	  
	  public static Map<String,String> validateMandatory(MemberRequest req, Map<String,String> errorMap){
		  
		  if(StringUtils.isEmpty(req.getFileName())) {
			  errorMap.put("fileName", "fileName is mandatory");
		  }else {
			  if(!validateFileName(req.getFileName().substring(0, req.getFileName().indexOf(".")-1)))
				  errorMap.put("fileName", "Invalid fileName");
			  if(!fileNameTypeCheck(req.getFileName().substring(req.getFileName().indexOf(".")+1)))
				  errorMap.put("fileName", "Invalid file type");
			  
			  if(!isExelExist(req.getFileName()))
				  errorMap.put("file", "requested file dosen't exist");
		  }
		  
		  if(StringUtils.isEmpty(req.getRequesterId())) {
			  errorMap.put("requesterId", "requesterId is mandatory");
		  }
		  
		  return errorMap;
	  }


	private static boolean isExelExist(String fileName) {
		boolean flag = false;
		 try {
			FileInputStream file = new FileInputStream(new File(fileUrl+fileName));
			file.close();
		 	flag = true;
		} catch (IOException e) {
			
			flag = false;
		}
		
		return flag;
	}
}
