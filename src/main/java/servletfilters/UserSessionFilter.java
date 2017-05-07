package servletfilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserSessionFilter implements Filter
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ServletContext context;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession(false);

		res.setContentType("text/html;charset=UTF-8");
		res.setHeader("pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");

		if((session == null || session.getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY) == null))
		{
			res.sendRedirect(req.getContextPath()+"/Login.html");
		}
		else
		{
			chain.doFilter(request, response);
		}	
		
	}
	
	@Override
	public void destroy() {
		//close any resources here
	}


}
  