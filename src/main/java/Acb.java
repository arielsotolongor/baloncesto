import codigo.Jugador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;

public class Acb extends HttpServlet {

    private ModeloDatos bd;
    private static final Logger logger = Logger.getLogger(Acb.class.getName());

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
        String b1 = req.getParameter("B1");
        String b2 = req.getParameter("B2");

        if (b2 != null && b2.equals("Reset")) {
            logger.info("Entro en Reset");
            bd.reiniciarVotos();
            res.sendRedirect(res.encodeRedirectURL("index.html"));
        } else if (b1 != null && b1.equals("Votar")) {
            logger.info("Entro en Votar");
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
            logger.info("Entro en Ver Votos");
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
