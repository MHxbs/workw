import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "loginServlet",value = "/loginServlet")
public class loginServlet extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
        doPost(reqest,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String regex="[0-9]*";
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(username);
        if (matcher.matches()) {
            DBConnction dbConnction = new DBConnction();
            Connection con = dbConnction.getConnction();
            PrintWriter out = response.getWriter();
            try {

                String sql1 = "SELECT username,password FROM users where username= ?";
                PreparedStatement pst = con.prepareStatement(sql1);
                pst.setString(1, username);
                ResultSet resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    String passwordFromMysql = resultSet.getString("password");
                    if (password.equals(passwordFromMysql)) {
                        out.print("{\"how\" : 2}");
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                    } else {
                        out.print("{\"how\" : 1}");
                    }
                } else {
                    out.print("{\"how\" : 0}");
                }
                out.flush();
                out.close();
                resultSet.close();
                pst.close();
                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            PrintWriter out=response.getWriter();
            out.print("{\"result\" : \"填入的数字必须是数字\"}");
            out.flush();
            out.close();
        }


    }
}
