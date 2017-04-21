package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import user.UserDetails;

public class SimpleExcelReaderExample {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		String excelFilePath = "MM_userDetails.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		List<UserDetails> users = new LinkedList<UserDetails>(); 
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			
			if(nextRow.getRowNum() == 0)
				nextRow = iterator.next();
			
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			UserDetails user = new UserDetails();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				cell.getColumnIndex();
				
				switch (cell.getColumnIndex()) {
				case 0:
					user.setName(cell.getStringCellValue());
				break;

				case 1:
					String email = cell.getStringCellValue();
					if(StringTools.isValidEmail(email))
						user.setEmailId(cell.getStringCellValue());
					else
						throw new InputMismatchException();
				break;

				case 2:
					user.setPhoneNumber(new String(""+new java.text.DecimalFormat("0").format(cell.getNumericCellValue())+""));
				break;

				default:
					break;
				}
			}
			users.add(user);
			System.out.println();
		}

		System.err.println(users);
		workbook.close();
		inputStream.close();
	}

}
