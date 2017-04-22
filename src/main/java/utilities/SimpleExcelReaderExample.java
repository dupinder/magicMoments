package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import user.UserDetails;

public class SimpleExcelReaderExample {

	private final int  NAME_FIELD_INDEX = 0;
	private final int EMAIL_FIELD_INDEX = 1;
	private final int CONTACT_FIELD_INDEX = 2;
	
	@SuppressWarnings("deprecation")
	public List<UserDetails> importStudents(File fileToImport) throws IOException {
		Map<String, UserDetails> users = null;
		Workbook workbook = null;
		FileInputStream inputStream = null;
		try
		{
			String excelFilePath = fileToImport.getPath();
			inputStream = new FileInputStream(new File(excelFilePath));

			workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			users = new HashMap<String, UserDetails>(); 
			
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				
				if(nextRow.getRowNum() == 0)
					nextRow = iterator.next();
				
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				UserDetails user = new UserDetails();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int cellIndex = cell.getColumnIndex();
					if(cellIndex == NAME_FIELD_INDEX)
					{
						String name = cell.getStringCellValue();
						if(StringTools.isValidString(name))
							user.setName(name);
						else
						{
							break;
						}
					}
					else if(cellIndex == EMAIL_FIELD_INDEX)
					{
						String email = cell.getStringCellValue();
						if(StringTools.isValidEmail(email) && !users.containsKey(email))
						{
							user.setEmailId(cell.getStringCellValue());
						}
						else
						{
							break;
						}
					}
					else if(cellIndex == CONTACT_FIELD_INDEX)
					{
						String contact = new String(""+new java.text.DecimalFormat("0").format(cell.getNumericCellValue())+"");
						if(StringTools.isValidString(contact))
							user.setPhoneNumber(contact);
						else
							break;
					}
					
					users.put(user.getEmailId(), user);
				}
				
			}

			workbook.close();
			inputStream.close();
			return new LinkedList<UserDetails>(users.values());
		}
		catch(Exception e)
		{
			if(workbook != null)
				workbook.close();
			
			if(inputStream != null)
				inputStream.close();
				
			return null;
		}
	}
}
