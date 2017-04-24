package servletfilters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserSessionFilter implements Filter
{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession();
		if(session == null || session.getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY) == null)
		{
			httpResponse.sendRedirect("Login.html");
			response.getWriter().write("AuthenticationFailed");
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		
	}

}
