package by.rgs.demo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(0)
public class CORSFilter extends OncePerRequestFilter {

	private void setCORSHeaders(HttpServletRequest request, HttpServletResponse response) {
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		setCORSHeaders(request, response);
		String requestMethod = request.getMethod();
		if(requestMethod.equals("OPTIONS")) {
			response.setStatus(HttpStatus.OK.value());
			return;
		}
		filterChain.doFilter(request, response);
	}

}