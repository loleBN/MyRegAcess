package basics;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




public class Utils {
	public static String convertDateToString(Date dateInString){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = null;
        try {
            date = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }
	
	public static String convertDateToStringBR(Date dateInString){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = null;
        try {
            date = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return date;
    }
	public static String getSoMes(Date dateInString) {
        DateFormat format = new SimpleDateFormat("MM/yyyy");
        String mes = null;
        try {
            mes = format.format(dateInString);
        } catch (Exception e) {
        	System.out.println("Erro = "+e.getMessage());
        }
        return mes;
	}
	public static String getSoData(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(data);
			String soDia = new SimpleDateFormat("dd/MM/yyyy").format(date);
			return soDia;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getSoHorario(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(data);
			String SoHorario = new SimpleDateFormat("HH:mm").format(date);
			return SoHorario;
		} catch (ParseException e) {
			System.out.println("Erro = "+e.getMessage());
		}
		return null;
	}
	public static String getDTH(String data) {
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy-HH:mm").parse(data);
			String SoHorario = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
			return SoHorario;
		} catch (ParseException e) {
			System.out.println("Erro = "+e.getMessage());
		}
		return null;
	}
	public static Date getInicioSemana() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());  
			int day = cal.get(Calendar.DAY_OF_WEEK);
			cal.add(Calendar.DATE, -day+1);
			return cal.getTime();
		} catch (Exception e) {
			System.out.println("Erro = "+e.getMessage());
		}
		return null;
	}
	
	public static void save(ArrayList<RegIN> array ) {
		try{
			String path="";

			FileWriter arq = new FileWriter(path);
		    PrintWriter gravarArq = new PrintWriter(arq);
		 
		    gravarArq.printf("REGISTROS%n");
		    for (int i=0; i<array.size(); i++) {
		      gravarArq.printf(getDTH(array.get(i).getData_hora())+ " " + array.get(i).getNome() +"%n");
		    }
		    gravarArq.printf("%n");
		 
		    arq.close();	

		} catch (Exception e){
			e.getMessage();
			}
	}
}
