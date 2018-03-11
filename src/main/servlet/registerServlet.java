import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "registerServlet" , value = "/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        //判断username全是数字吗
        String regex="[0-9]*";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(username);
        if (matcher.matches()) {
            DBConnction dbConnction = new DBConnction();
            Connection con = dbConnction.getConnction();
            PrintWriter out = response.getWriter();
            try {

                String sql = "SELECT username FROM users where username = ? ";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, username);
                ResultSet resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    out.print("{\"how\" : 0}");
                } else {

                    String sql1 = "INSERT INTO users (username,password) VALUES (?,?)";
                    PreparedStatement pst2 = con.prepareStatement(sql1);
                    pst2.setString(1, username);
                    pst2.setString(2, password);
                    int result = pst2.executeUpdate();
                    if (result == 1) {
                        out.print("{\"how\" : 1}");
                        pst2.close();
                    } else {
                        response.sendRedirect("register_failure.jsp");
                    }
                }
                out.flush();
                out.close();
                resultSet.close();
                pst.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            PrintWriter out=response.getWriter();
            out.print("{\"result\" : \"填入的数字必须是数字\"}");
            out.flush();
            out.close();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
