package tn.iset.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@CrossOrigin("*")
public class Impression {
	
		@Autowired
	private DataSource datasource;
		@RequestMapping(value="/Liste/{nom}")
		 private HttpEntity<byte[]> Agent(@PathVariable String nom,@RequestParam("paramName")String debut,@RequestParam("paramName2")String fin) throws JRException, IOException, ClassNotFoundException, SQLException, ParseException{
			 SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
				Date datedebut=formatter.parse(debut);
				Date datefin=formatter.parse(fin);
			 InputStream input=this.getClass().getResourceAsStream(nom);
			JasperDesign design=JRXmlLoader.load(input);
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		Map parameters=new HashMap<>();
		parameters.put("datedebut", datedebut);
		parameters.put("datefin", datefin);
		JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters,datasource.getConnection());
		HttpHeaders header = new HttpHeaders();
		final byte[] data;
	
		data = JasperExportManager.exportReportToPdf(jasperPrint);
		header.setContentType(MediaType.APPLICATION_PDF);
		header.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+"etat"+".pdf");
		header.setContentLength(data.length);
		return new HttpEntity<byte[]>(data, header);
		 }
		 
		 
		}
