package king.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kingtool.TraceTool;

/**
 * 用于接受所有http形式的请求,并把接受到的request中param及getInputStream全打印出来
 * @author King
 *
 */
public class IndexServlet  extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			TraceTool.traceParent();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
			dispatcher .forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		TraceTool.traceParent();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
//			如果"您的文本内容" 用UTF-8编码后,再以conn.setRequestProperty("content-type", "text/xml;charset=iso-8859-1");发送到本服务端,那么需要对参数内容进行如下解码
//			String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
//			System.out.println("name : "+ name);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher .forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
