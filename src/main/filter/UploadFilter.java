import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public  class UploadFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //得到session
        HttpSession session=request.getSession();
        //判断是否登陆

        if (session.getAttribute("username")==null){//未登录
            response.sendRedirect("login_failure.jsp");
        }else {
            //登陆成功，请求上传
            filterChain.doFilter(request,response);
        }
    }
    public void destroy() {

    }

}
