package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.dao.EtudiantRepository;
import com.springboot.entities.Etudiant;  

public class DeliberationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ScriptEngineManagerUtils.listEngines();

		//@Autowired
		EtudiantRepository er;

		List<Etudiant> listetd = new ArrayList<Etudiant>();
		List<String> columns= new ArrayList<String>();
		//obtaining input bytes from a file  
		//File dude = new File();
		//FileInputStream fip = new FileInputStream(file)
		
		
		byte[] mybytes=null;
		try {
			mybytes = FileUtils.readFileToByteArray(new File("D:\\Bachelor\\S5\\JEE\\Gestion Deliberations\\test.xlsx"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
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
			int j=0;
			int k=0;
			while(row.getCell(k)!= null) {
				//System.out.println("row:"+k+": "+row.getCell(k));
				if(i==0) {
					columns.add(""+row.getCell(k));
				}
				else{
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
						//e.setAnnee_academique(dat);
						e.setAnnee_academique(str);

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
			/*for(Cell cell: row)    //iteration over cell using for each loop  
			{  
				if(i!=0)
					switch(formulaEvaluator.evaluateInCell(cell).getCellType())  
					{  
					case NUMERIC:   //field that represents numeric cell type  
						//getting the value of the cell as a number  
						System.out.print(cell.getNumericCellValue()+ "\t\t"); 
						switch(j) {
						case 5:
							Date d = new Date();
							d.setYear((int) cell.getNumericCellValue());
							e.setAnnee_academique(d);
							j++;
							break;
						case 6:
							Etape p = new Etape();
							p.setId_etape((long)cell.getNumericCellValue());
							e.setEtape(p);
							j++;
							break;
						case 8:
							InscriptionPedagogique in = new InscriptionPedagogique();
							in.setId_inscription_pedagogique((long) cell.getNumericCellValue());
							//e.setInscriptionPedagogiques(in);
							j++;
							break;
						}
						break;  
					case STRING:    //field that represents string cell type  
						//getting the value of the cell as a string  
						System.out.print(cell.getStringCellValue()+ "\t\t");
						switch(j) {
						case 0:
							e.setMassarEtud(cell.getStringCellValue());
							j++;
							break;
						case 1:
							e.setNom_etud(cell.getStringCellValue());
							j++;
							break;
						case 2:
							e.setPrenom_etud(cell.getStringCellValue());
							j++;
							break;
						case 3:
							e.setTel_etud(cell.getStringCellValue());
							j++;
							break;
						case 4:
							e.setEmail_etud(cell.getStringCellValue());
							j++;
							break;
						case 7:

						//	e.setFiliere(new Filiere().setNom_filiere(cell.getStringCellValue()));
							j++;
							break;
						}
						break;  
					}  
			}  */

			listetd.add(e);
			//er.save(e);
		}  

		System.out.println("nom: "+listetd.get(1).getNom_etud());
		System.out.println("prenom: "+listetd.get(1).getPrenom_etud());
		System.out.println("teletud: "+listetd.get(1).getTel_etud());
		System.out.println("mail: "+listetd.get(1).getEmail_etud());
		//System.out.println("annne acc: "+listetd.get(1).annee_academique);
		//System.out.println("etape: "+listetd.get(0).getEtape().getId_etape());
		//System.out.println("filier: "+listetd.get(1).getFiliere().getId_filiere());
		//System.out.println("insc pedag: "+listetd.get(0).getMassarEtud());
		System.out.println("chop");/*
		for(int v=0;v<columns.size();v++) {
			System.out.println("columns["+v+"]: "+columns.get(v));

		}*/

	}
	

}
