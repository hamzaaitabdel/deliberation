package com.springboot.service;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;

// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.springboot.dao.AnneeUniversitaireRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportXLSX {
	
	
	
	Long s ;
	
	public void readXLSX(byte[] mybytes, AnneeUniversitaireRepository anneeUniversitaireRepository,
			FiliereRepository filiereRepository,
			InscriptionEnligneRepository ier,
			InscriptionAdministrativeRepository iar,
			EtudiantRepository er) {
		
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
			InscriptionEnligne ie = new InscriptionEnligne();
			InscriptionAdministrative ia = new InscriptionAdministrative();
			if(row.getCell(0)==null) break;
			int k=0;
			while(row.getCell(k)!= null) {
				if(i!=0) {
					String str= ""+row.getCell(k);
					switch(k) {
					case 0:
						e.setCne(str);
						ie.setCne(str);
						ia.setCne(str);
						System.out.println("massstr:"+str+" k:"+k);
						break;
					case 1:
						e.setNom(str);
						ie.setNomFr(str);
						ia.setNom(str);
						System.out.println("str:"+str);
						break;
					case 2:
						e.setPrenom(str);
						ie.setPrenomFr(str);
						ia.setPrenom(str);
						System.out.println("str:"+str);
						break;

					case 3:
						e.setTelephone(str);
						ia.setTelephone(str);
						System.out.println("str:"+str);
						break;
					case 4:
						e.setEmail(str);
						ia.setEmailEtud(str);
						
						System.out.println("str:"+str);
						break;
					case 5:
						float c1 = Float.parseFloat(str);
						int d2 = (int) c1;
						Long fl1 = (long) d2;
						System.out.println("fl1: "+fl1);
						AnneeUniversitaire au = anneeUniversitaireRepository.findById(fl1).get();
						//au.toString();
						ia.setAnneeUniversitaire(au);
						System.out.println("str:"+str);
						
						System.out.println("Annee:"+str+" k:"+k);
						break;
					case 6:
						//if(str.contains("filiere")) e.setFiliere(filiere);
						float c = Float.parseFloat(str);
						int d = (int) c;
						Long fl = (long) d;
						System.out.println("fl:"+fl); 
						Filiere f = filiereRepository.findById(fl).get();
						e.setFiliere(f);
						ia.setFiliere(f);
						//ayoub ,Filiere ?
							
						System.out.println("seven hedbuaffei");
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
			ie.toString();
			if(i!=0) {
				listetd.add(e);
				
				ier.save(ie);
				ia.setInscriptionEnligne(ie);
				iar.save(ia);
				er.save(e);
			}
			i++;

			
			
		}
		//return listetd;  
	}
}
