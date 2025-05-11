import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));

        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");

        handler.addServlet(HelloServlet.class, "/");
        server.setHandler(handler);

        server.start();
        server.join();
    }

    public static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
            try {
                resp.getWriter().write("CraftoriaS radi na Railway-u!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
