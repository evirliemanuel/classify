package com.remswork.project.alice.web.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.remswork.project.alice.model.Student;

@Component
public class XcellHelperBean {
	
	public XcellHelperBean() {
		super();
	}
	
	public List<Student> loadFile(File file) {
		List<Student> studentList = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			final int size = sheet.getLastRowNum();
			for(int row=1; row<=size; row++ ) {
				Student student = new Student();
				student.setStudentNumber((long) sheet.getRow(row).getCell(0).getNumericCellValue());
				student.setFirstName(sheet.getRow(row).getCell(1).getStringCellValue());
				student.setLastName(sheet.getRow(row).getCell(2).getStringCellValue());
				student.setMiddleName(sheet.getRow(row).getCell(3).getStringCellValue());
				studentList.add(student);
			}
			return studentList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return studentList;
		} catch (IOException e) {
			e.printStackTrace();
			return studentList;
		} catch(RuntimeException e) {
			e.printStackTrace();
			return studentList;
		}
	}
	
	public List<Student> loadFile(File file, boolean withFormat) {
		List<Student> studentList = new ArrayList<>();
		try {
			if(!withFormat)
				return loadFile(file);
				
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			final int size = sheet.getLastRowNum();
			final int colSize = sheet.getRow(0).getLastCellNum() > 6 ? 6 : sheet.getRow(0).getLastCellNum();
			final int header[] = new int[colSize];
			
			for(int i=0; i<colSize; i++) {
				if(sheet.getRow(0).getCell(i).getCellType() != Cell.CELL_TYPE_STRING)
					continue;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("studentNumber"))
					header[0] = i;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("firstName"))
					header[1] = i;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("lastName"))
					header[2] = i;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("middleName"))
					header[3] = i;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("gender"))
					header[4] = i;
				if(sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase("age"))
					header[5] = i;
			}
			for(int row=1; row<=size; row++ ) {
				Student student = new Student();
				if(sheet.getRow(row).getCell(header[0]).getCellType() == Cell.CELL_TYPE_NUMERIC)
					student.setStudentNumber((long) sheet.getRow(row).getCell(header[0]).getNumericCellValue());
				else
					student.setStudentNumber(0);
				if(sheet.getRow(row).getCell(header[1]).getCellType() == Cell.CELL_TYPE_STRING)
					student.setFirstName(sheet.getRow(row).getCell(header[1]).getStringCellValue());
				else
					student.setFirstName("none");
				if(sheet.getRow(row).getCell(header[2]).getCellType() == Cell.CELL_TYPE_STRING)
					student.setLastName(sheet.getRow(row).getCell(header[2]).getStringCellValue());
				else
					student.setLastName("none");
				if(sheet.getRow(row).getCell(header[3]).getCellType() == Cell.CELL_TYPE_STRING)
					student.setMiddleName(sheet.getRow(row).getCell(header[3]).getStringCellValue());
				else
					student.setMiddleName("none");
				if(sheet.getRow(row).getCell(header[4]).getCellType() == Cell.CELL_TYPE_STRING)
					student.setGender(sheet.getRow(row).getCell(header[4]).getStringCellValue());
				else
					student.setGender("Male");
				if(sheet.getRow(row).getCell(header[5]).getCellType() == Cell.CELL_TYPE_NUMERIC)
					student.setAge((int) sheet.getRow(row).getCell(header[5]).getNumericCellValue());
				else
					student.setAge(0);
				studentList.add(student);
			}
			return studentList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return studentList;
		} catch (IOException e) {
			e.printStackTrace();
			return studentList;
		} catch(RuntimeException e) {
			e.printStackTrace();
			return studentList;
		}
	}
	
	public File convert(MultipartFile file) {    
		try {
			File convFile = new File(file.getOriginalFilename());
		    convFile.createNewFile(); 
		    FileOutputStream fos;
			fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close(); 
			return convFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
