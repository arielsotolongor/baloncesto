import codigo.Jugador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class Acb extends HttpServlet {

    private ModeloDatos bd;

    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);
        String nombreP = req.getParameter("txtNombre");
        String nombre = req.getParameter("R1");
        String accion = req.getParameter("B2");

        if (accion != null && accion.equals("Reset")) {
            bd.reiniciarVotos();
            res.sendRedirect(res.encodeRedirectURL("index.html"));
        } else if (accion != null && accion.equals("Votar")) {
            if (nombre.equals("Otros")) {
                nombre = req.getParameter("txtOtros");
            }
            if (bd.existeJugador(nombre)) {
                bd.actualizarJugador(nombre);
            } else {
                bd.insertarJugador(nombre);
            }
            s.setAttribute("nombreCliente", nombreP);
            // Llamada a la página jsp que nos da las gracias
            res.sendRedirect(res.encodeRedirectURL("TablaVotos.jsp"));
        } else {
            List<Jugador> jugadoresDB = bd.obtenerJugadores();
            s.setAttribute("jugadores", (jugadoresDB != null && !jugadoresDB.isEmpty()) ? jugadoresDB : new ArrayList<>());
            res.sendRedirect(res.encodeRedirectURL("VerVotos.jsp"));

        }
    }

    @Override
    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
