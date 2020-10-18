/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenprueba;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.db4o.*;
/**
 *
 * @author Montejeitor
 */
public class ExamenPrueba {
    
    static Scanner  scan = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        boolean salir = false;
        
        do {

            System.out.println("Introduce el usuario (X para salir): ");

            String usuario = scan.nextLine().toLowerCase();

            switch (usuario) {

                case "vendedor":
                    vendedor();
                    break;

                case "admin":
                    System.out.println("Introduce la contraseña: ");
                    String pass = scan.nextLine();
                    if (pass.equals("2345"))
                        administrador();
                    else
                        System.out.println("Contraseña incorrecta");
                    
                    break;

                case "x":
                    salir = true;
                    break;

                default:
                    System.out.println("Usuario incorrecto intentelo de nuevo");
                    break;
            }
        }while (!salir);
        
    }
    
    public static void administrador () {
        
        boolean salir = false;
        boolean lista = false;
        
        do {
            
            System.out.println("1) Obtener lista de ventas.");
            System.out.println("2) Exportar lista de ventas.");
            System.out.println("X) Salir del panel de administracion.");
            
            String opcion = scan.nextLine().toLowerCase();
            
            switch (opcion) {
                
                case "1":
                    break;
                    
                case "2":
                    if (lista) {
                        
                    }
                        
                    else
                        System.out.println("Tienes que obtener una lista antes");
                    
                    break;
                    
                case "x":
                    salir = true;
                    break;
                    
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
            
        }while(!salir);
    }
    
    public static void vendedor () {
        
        boolean salir = false;
        double total = 0;
        
        do {

            System.out.println
            ("Introduce un numero (f para finalizar, x para salir: ");

            String numero = scan.nextLine();
            double precio;
            
            //Ventasimple
            if (numero.length() < 4) {
                
                precio = Double.parseDouble(numero);
                total += precio;
                ventaSimple(precio);
                
            }
            
            //ventaCompleja
            else if (numero.length() > 4) {
                
                precio = Double.parseDouble(numero);
                total += ventaCompleja(precio);
            }
            
            else if ("f".equals(numero.toLowerCase())) {
                System.out.println(total);
                total = 0;
            }
            
            else if ("x".equals(numero.toLowerCase())) {
                salir = true;
            }
            
            else {
                System.out.println("Opcion no valida repita la operacion :");
            }
                
        }while(!salir);
        
    }
    
    public static void ventaSimple (double precio) {
        
        try {
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/tienda";
            String usr = "postgres";
            String pass = "03059420";
            
            Connection con = DriverManager.getConnection(url, usr, pass);
            Statement statement = con.createStatement();
            
            Date fecha = new Date();
            
            String sentenciaSQL = "INSERT INTO ventas VALUES "+
            "(DEFAULT," + precio +"," + fecha + " );";
            int cantidad = statement.executeUpdate(sentenciaSQL);
            System.out.println("Datos insertados: " + cantidad);
            
            con.close();
        }
        catch (ClassNotFoundException ce) {
        System.out.println("PostgreSQL no accesible");
        }
        catch (SQLException se) {
        System.out.println("No se ha podido crear la tabla");
        }
        System.out.println("Terminado!");
    
       
    }
    
    public static double ventaCompleja (double codigo) {
        
        double precio = 0;
        
        try {
            
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/tienda";
            String usr = "postgres";
            String pass = "03059420";
            
            Connection con = DriverManager.getConnection(url, usr, pass);
            Statement statement = con.createStatement();
            
            Date fecha = new Date();
            
            String sentenciaSQL = "SELECT * FROM personas ORDER BY nombre";
            ResultSet rs = statement.executeQuery(sentenciaSQL);
            
            precio = Double.parseDouble(rs.getString(1)); 
            
            
            String sentenciaSQL1 = "INSERT INTO ventasDetalladas VALUES "+
            "(DEFAULT," + precio +"," + fecha + "," + codigo + " );";
            int cantidad1 = statement.executeUpdate(sentenciaSQL);
            System.out.println("Datos insertados: " + cantidad1);
            
            rs.close();
            con.close();
        }
        catch (ClassNotFoundException ce) {
        System.out.println("PostgreSQL no accesible");
        }
        catch (SQLException se) {
        System.out.println("No se ha podido crear la tabla");
        }
        System.out.println("Terminado!");
        
        return precio;
    }
}
