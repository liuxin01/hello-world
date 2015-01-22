package com.itshixun.write;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itshixun.com.Test;
import com.itshixun.entity.MyElement;

public class ExcelWrite {
	public ExcelWrite(String[] name,int[] indentation,String[] combineString,String[] headerString){
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("元素清单模板样式");
				
		int t = 0;
		for(int m = 1;m < name.length;m++){
			if(indentation[m] == -1){
				name[m] = combineString[t];
				t++;
			}
		}	
		/*
		for(int i = 0;i < Test.columncount;i++)
			System.out.println(headerString[i]);

		for(int i = 1;i < name.length;i++){
			System.out.println("--EXCEL "+indentation[i]+" "+name[i]);
		}
		 */
		//headerString
		Font headerFont = null;
		headerFont = wb.createFont();
		headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
		headerFont.setFontHeightInPoints((short)9);//9号
		
		CellStyle headerStyle = wb.createCellStyle();
		XSSFRow headerRow = sheet1.createRow(0);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		headerStyle.setFont(headerFont);
		XSSFCell headerCell;
		for(int i = 0;i < Test.columncount;i++){
			headerCell = headerRow.createCell(i);
			headerCell.setCellStyle(headerStyle);
			headerCell.setCellType(XSSFCell.CELL_TYPE_STRING);
			headerCell.setCellValue(headerString[i]);
		}	
		
		//Cell
		XSSFRow row;
		for(int i = 1;i < Test.rowcount;i++){	
			//System.out.println(indentation[i]);
			row = sheet1.createRow(i);
			if(indentation[i] == -1){					
				XSSFCell cell;
				Font cellFont = wb.createFont();
				CellStyle cellStyle = wb.createCellStyle();
				for(int j = 0;j < Test.columncount;j++){
					cellFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					cellFont.setFontHeightInPoints((short)9);
					cell = row.createCell(0);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(name[i]);
					cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
					cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
					cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
					cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
					cellStyle.setFont(cellFont);					
					cell.setCellStyle(cellStyle);
					sheet1.addMergedRegion(new CellRangeAddress(i, i, 0, Test.columncount-1));
				}
			}else{				
				XSSFCell cell;
				MyElement element = new MyElement();				
				for(int j = 1;j < Test.myElement.length;j++){
					if(Test.myElement[j].getName() == null) 
						continue;
					if(Test.myElement[j].getName().equals(name[i])){
						element = Test.myElement[j];
						System.out.println("------------");
						break;
					}					
				}
				for(int j = 0;j < Test.columncount;j++){
					cell = row.createCell(j);
					if(headerString[j].equals("前缀")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getPrefix());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("元素名称")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)indentation[i]);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getName());
						cell.setCellStyle(cellStyle);						
					}
					if(headerString[j].equals("标准标签（中文）")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getZnName());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("标准标签（英文）")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getEnName());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("数据类型")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getItemType());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("替换组")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getSubsitutionGroup());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("时期属性")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getPeriodType());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("借贷属性")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getBalance());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("是否虚元素")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getGhostElement());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("置空属性")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getNullable());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("参考")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getReference());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("展示标签（中文）")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getShowznLabel());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("展示标签（英文）")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getShowenLabel());
						cell.setCellStyle(cellStyle);
						
					}
					if(headerString[j].equals("首选标签")){
						Font cellFont = wb.createFont();
						CellStyle cellStyle = wb.createCellStyle();						
						cellFont.setFontHeightInPoints((short)9);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
						cellStyle.setFont(cellFont);	
						cellStyle.setIndention((short)0);
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(element.getFirstLabel());
						cell.setCellStyle(cellStyle);						
					}	
				
				}
			
			}			
		
		}	
		FileOutputStream out = null;
		
		try {
			
			out = new FileOutputStream("F:\\1.xlsx");
			wb.write(out);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {		
				e.printStackTrace();
			}
			
		}
		
	}

}
