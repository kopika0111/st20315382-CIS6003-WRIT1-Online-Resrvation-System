package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.restaurant.model.User;
import javax.servlet.http.HttpSession;

public final class dashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css\">\n");
      out.write("\n");

//    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
    String imagePath = "uploads/" + user.getImagePath();

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Dashboard</title>\n");
      out.write("    <link rel=\"stylesheet\" href=\"css/dashboard.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <div class=\"container\">\n");
      out.write("        <header>\n");
      out.write("            <h1>Welcome, ");
      out.print( user.getName() );
      out.write("</h1>\n");
      out.write("            <div class=\"header-right\">\n");
      out.write("                <button id=\"toggle-nav\" class=\"toggle-nav-btn\">☰</button>\n");
      out.write("                <img src=\"");
      out.print( imagePath );
      out.write("\" alt=\"Profile Picture\" class=\"profile-pic\">\n");
      out.write("                <a href=\"logout.jsp\" class=\"logout-btn\" onclick=\"confirmLogout()\">Logout</a>\n");
      out.write("            </div>\n");
      out.write("        </header>\n");
      out.write("        <nav id=\"sidebar\" class=\"nav-hidden\">\n");
      out.write("            <ul>\n");
      out.write("                <li><a href=\"viewReservations.jsp\">View Reservations</a></li>\n");
      out.write("                <li><a href=\"MenuController?action=view\">View Menu</a></li>\n");
      out.write("                ");
 if (user.getRoleID() == 1) { 
      out.write("\n");
      out.write("                    <li><a href=\"UserController?action=list\">Manage Users</a></li>\n");
      out.write("                    <li><a href=\"manageMenu.jsp\">Manage Menu</a></li>\n");
      out.write("                ");
 } else if (user.getRoleID() == 2) { 
      out.write("\n");
      out.write("                    <li><a href=\"viewReports.jsp\">View Reports</a></li>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                ");
 if (user.getRoleID() == 3) { 
      out.write("\n");
      out.write("                    <li><a href=\"orderHistory.jsp\">Order History</a></li>\n");
      out.write("                ");
 } 
      out.write("\n");
      out.write("                <li><a href=\"profile.jsp\">Profile</a></li>\n");
      out.write("            </ul>\n");
      out.write("        </nav>   \n");
      out.write("        <section class=\"content content-shown\" id=\"content\">\n");
      out.write("            <h2>Dashboard</h2>\n");
      out.write("            <p>Select an option from the menu to proceed.</p>\n");
      out.write("            ");
 if(user.getRoleID() != 3) { //System.out.println("dashboard count:" + request.getAttribute("userCount"));
      out.write("\n");
      out.write("            <div class=\"dashboard-grid\">\n");
      out.write("                <div class=\"dashboard-item\">\n");
      out.write("                    <h3>Users</h3>\n");
      out.write("                    <p><i class=\"fas fa-users\"></i> ");
      out.print( session.getAttribute("userCount") );
      out.write(" users</p>\n");
      out.write("                    <!-- Add status or more info if needed -->\n");
      out.write("                </div>\n");
      out.write("                <div class=\"dashboard-item\">\n");
      out.write("                    <h3>Orders</h3>\n");
      out.write("                    <p><i class=\"fas fa-utensils\"></i> ");
      out.print( session.getAttribute("orderCount") );
      out.write(" orders</p>\n");
      out.write("                    <!-- Add status or more info if needed -->\n");
      out.write("                </div>\n");
      out.write("                <div class=\"dashboard-item\">\n");
      out.write("                    <h3>Reservations</h3>\n");
      out.write("                    <p><i class=\"fas fa-calendar-alt\"></i> ");
      out.print( session.getAttribute("reservationCount") );
      out.write(" reservations</p>\n");
      out.write("                </div>\n");
      out.write("            </div>\n");
      out.write("            ");
 } 
      out.write("\n");
      out.write("        </section>\n");
      out.write("        <footer>\n");
      out.write("            <p>&copy; 2024 ABC Restaurant. All rights reserved.</p>\n");
      out.write("        </footer>\n");
      out.write("    </div>\n");
      out.write("    <script src=\"js/dashboard.js\"></script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
