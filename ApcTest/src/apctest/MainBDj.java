/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apctest;

/**
 *
 * @author joseluis
 */
import java.sql.*;
import java.util.StringTokenizer;

public class MainBDj{
    public static void main (String []args){
		MiBDDaw conexion = new MiBDDaw();
		ResultSet result;
		String appaterno,nombre,apmaterno;
		
                System.out.println("buscando");
		result = conexion.getQuery("SELECT * FROM Alumnos");
		try{
                    while(result.next()){
			// nss = result.getString("nss");
			nombre = result.getString("nombre");
                        appaterno = result.getString("appaterno");
                        apmaterno = result.getString("apmaterno");
                        
                        if( apmaterno == null )
                        {
                            apmaterno = "";
                        }
			// f_nac = result.getString("f_nac");
				
			//System.out.println("\nNSS: " + nss);
                        
                        System.out.print("Nombre alumno: ");
                        System.out.print( nombre + " " + appaterno + " " + apmaterno );
                        // Comienza a dividir lo que contenga el nombre, y manda el resultado.
                        /*
                        StringTokenizer st = new StringTokenizer( nombre ," ");
                        while (st.hasMoreTokens()) {
                            System.out.print(st.nextToken());
                        }
                        */
                        System.out.print("\n");
                                
			//System.out.println("Fecha Nacimiento: " + f_nac);
                    }
		}
		catch (SQLException e){
			e.printStackTrace();
		}
    }
}
