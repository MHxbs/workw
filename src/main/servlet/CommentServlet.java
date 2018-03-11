import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Comment comment = new Comment();
        HttpSession session=request.getSession();
        if (session.getAttribute("username")!=null) {

            DBConnction dbConnction = new DBConnction();
            Connection con = dbConnction.getConnction();
            PrintWriter out = response.getWriter();

            comment.setComment(request.getParameter("comment"));
            comment.setUsername((String) session.getAttribute("username"));
            comment.setTousername(request.getParameter("tousername"));
            comment.setCalendar(request.getParameter("calendar"));
            comment.setFid(request.getParameter("Fid"));
            //comment.setId(request.getParameter("id"));
            comment.setId(request.getParameter("videoID"));

            try {
                String sql = "INSERT INTO comments (comment,username,tousername,calendar,Fid,videoID) VALUES " +
                        "(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, comment.getComment());
                preparedStatement.setString(2, comment.getUsername());
                preparedStatement.setString(3, comment.getTousername());
                preparedStatement.setTimestamp(4, comment.getCalendar());
            /*preparedStatement.setInt(5,comment.getId());*/
                preparedStatement.setInt(5, comment.getFid());
                preparedStatement.setInt(6, comment.getVideoID());
                int q = preparedStatement.executeUpdate();
                if (q == 1) {
                    out.print("{\"result\" : \"评论成功\"}");
                    System.out.println("插入评论成功");
                } else {
                    out.print("{\"result\" : \"评论失败\"}");
                    System.out.println("插入评论失败");
                }
                out.flush();
                out.close();
                preparedStatement.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {

            PrintWriter out=response.getWriter();
            out.print("{\"result\" : \"你还没登陆，请登录\"}");
            out.flush();
            out.close();
        }
    }
}