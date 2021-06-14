package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="HolaMundo", urlPatterns={"/HolaMundo"})
public class HolaMundo extends HttpServlet{
	//Peticion GET
	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rp) throws IOException{
		rp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = rp.getWriter();
		out.print("Hola Mundo con Servlets");
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Hola soy el título</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Bienvienidos alumnos DAW</h1>");
		out.println("</body>");
		out.println("</html>");
	}
}
