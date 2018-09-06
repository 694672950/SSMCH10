package cn.bdqn.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(filterName = "EnCodingFilter",urlPatterns ={"/*"} ,initParams={@WebInitParam(name="charset",value ="utf-8"),
        @WebInitParam(name="contentType",value ="text/html;charset=utf-8")})
public class EnCodingFilter implements Filter {
    private String charset;
    private String contentType;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response= (HttpServletResponse) resp;
            request.setCharacterEncoding(charset);
            response.setCharacterEncoding(charset);
            response.setContentType(contentType);
            int i=0;
            chain.doFilter(request,response);
    }

    public void init(FilterConfig config) throws ServletException {
       charset = config.getInitParameter("charset");
       contentType = config.getInitParameter("contentType");
    }

}
