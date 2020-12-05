package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.springboot.dao.EtudiantRepository;
import com.springboot.entities.Etudiant;

public class ImportXLSX {
	public List<Etudiant> readXLSX(byte[] mybytes) {

		List<Etudiant> listetd = new ArrayList<Etudiant>();

		InputStream fip = new ByteArrayInputStream(mybytes);
		//creating workbook instance that refers to .xls file  
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		//creating a Sheet object to retrieve the object  
		XSSFSheet sheet=wb.getSheetAt(0);  
		//evaluating cell type   
		int i=0;
		for(Row row: sheet)     //iteration over row using for each loop  
		{  
			Etudiant e = new Etudiant();
			int k=0;
			while(row.getCell(k)!= null) {
				if(i!=0) {
					String str= ""+row.getCell(k);
					switch(k) {
					case 0:
						e.setCne(str);
						System.out.println("massstr:"+str+" k:"+k);
						break;
					case 1:
						e.setNom_etud(str);;
						System.out.println("str:"+str);
						break;
					case 2:
						e.setPrenom_etud(str);
						System.out.println("str:"+str);
						break;

					case 3:
						e.setTel_etud(String.valueOf((int) Double.parseDouble(str)));
						System.out.println("str:"+str);
						break;
					case 4:
						e.setEmail_etud(str);
						System.out.println("str:"+str);
						break;
					case 5:
						//if(str.contains("etape")) e.setEtape(etape);

						Date dat= new Date();
						System.out.println("str:"+str);
						dat.setYear((int) Double.parseDouble(str));
						e.setAnnee_academique(dat);

						System.out.println("str:"+str);
						break;
					case 6:
						//if(str.contains("filiere")) e.setFiliere(filiere);
						break;
					case 7:
						//if(str.contains("nom")) e.setNom_etud(str);;
						break;
					case 8:
						//if(str.contains("nom")) e.setNom_etud(str);;
						break;

					}
				}
				k++;
			}
			i++;


			listetd.add(e);
		}
		return listetd;  
	}
}