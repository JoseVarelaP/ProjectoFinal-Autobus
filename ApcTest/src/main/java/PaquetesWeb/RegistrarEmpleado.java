package PaquetesWeb;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// @WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class RegistrarEmpleado extends HttpServlet{
	//Peticion GET
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws IOException{
		rp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = rp.getWriter();
		out.print("Datos que obtuve. <br>");
		
		out.print( String.format(
			"%s<br>%s<br>%s<br>%s<br>%s<br>",
			rq.getParameter("PNombre"),
			rq.getParameter("SNombre"),
			rq.getParameter("AplP"),
			rq.getParameter("AplM"),
			rq.getParameter("Dir")
		) );

		out.print("Terminé. <br>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
	  doGet(request, response);
   }
}