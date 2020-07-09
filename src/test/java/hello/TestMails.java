package hello;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import Persistence.DAO.DeviceDAO;
import Persistence.Model.Device;

public class TestMails {
	@Test
	public void TestQuitarRegistroMAsViejo() {
		DeviceDAO devdao= new DeviceDAO();
		Device device= devdao.retrieveBySerialNumber("DSC010000000002");
		int posicionmasviejo=0;
		Date fechamasviejo=null;
		Collections.sort(device.getUltimaszonas());  
		SimpleDateFormat formatter=new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		try {
		for(int i=0; i<device.getUltimaszonas().size();i++) {
			String[] vector=device.getUltimaszonas().get(i).split(Pattern.quote(";"));
			if(i==0) {
				 fechamasviejo= formatter.parse(vector[2]);
				 System.out.println("llego al DATO: "+ fechamasviejo.toString());
			}else if(fechamasviejo.after(formatter.parse(vector[2])))
				posicionmasviejo=i;
		}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		device.getUltimaszonas().remove(posicionmasviejo);
		devdao.update(device);
	}
	
	//@Test
	public void TestMailsConTabla() {
		DeviceDAO devdao= new DeviceDAO();
		Device device= devdao.retrieveBySerialNumber("DSC010000000002");
		String cabecera = "<HTML><head>\n" + 
				"  <style>\n" + 
				"  table {\n" + 
				"    width:100%;\n" + 
				"  }\n" + 
				"  table, th, td {\n" + 
				"    border: 1px solid black;\n" + 
				"    border-collapse: collapse;\n" + 
				"  }\n" + 
				"  th, td {\n" + 
				"    padding: 15px;\n" + 
				"    text-align: left;\n" + 
				"  }\n" + 
				"  tr:nth-child(even) {\n" + 
				"    background-color: #eee;\n" + 
				"  }\n" + 
				"  tr:nth-child(odd) {\n" + 
				"   background-color: #fff;\n" + 
				"  }\n" + 
				"  th {\n" + 
				"    background-color: black;\n" + 
				"    color: white;\n" + 
				"  }\n" + 
				"  </style>\n" + 
				"  </head><BODY><br/> <br/>";
		String tablaprevia="";
		for(int i=0; i<device.getUltimaszonas().size(); i++) {
			tablaprevia=tablaprevia+"<tr>";
			String[] vector = device.getUltimaszonas().get(i).split(Pattern.quote(";"));
			tablaprevia=tablaprevia+"<td>"+vector[0]+"</td>";
			tablaprevia=tablaprevia+"<td>"+vector[1]+"</td>";
			tablaprevia=tablaprevia+"<td>"+vector[2]+"</td>";
			tablaprevia=tablaprevia+"</tr>";
		}	
		String body= "<h1>Su alarma paso a Estado"+"Trigger"+" </h1> <br/> "
				+ "<h3>Se agregan los 10 regostros previos a que se ejecute su Alarma</h3> "
				+ "<table >\n" + 
				"  <tr>\n" + 
				"    <th>Zona</th>\n" + 
				"    <th>Estado</th> \n" + 
				"    <th>Fecha</th>\n" + 
				"  </tr>\n" + tablaprevia+ 
				"</table>"
				+ "<br/>";
		String pie = "<br/> <br/> <footer><p> Dash</p></footer></BODY></HTML>";
		String formulario = String.format("%s%s%s%s", cabecera, body, "<br/> <br/>", pie);
		MailController.sendMail(formulario, "leandrogabrielguzman@gmail.com");
		System.out.println("termino");
	}
}
