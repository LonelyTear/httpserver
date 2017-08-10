package king.server;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kingtool.TraceTool;

/**
 * ���ڽ�������http��ʽ������,���ѽ��ܵ���request��param��getInputStreamȫ��ӡ����
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
//			���"�����ı�����" ��UTF-8�����,����conn.setRequestProperty("content-type", "text/xml;charset=iso-8859-1");���͵��������,��ô��Ҫ�Բ������ݽ������½���
//			String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
//			System.out.println("name : "+ name);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher .forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
