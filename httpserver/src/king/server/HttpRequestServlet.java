package king.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kingtool.TraceTool;

/**
 * 用于接受所有http形式的请求,并把接受到的request中param及getInputStream全打印出来
 * @author King
 *
 */
public class HttpRequestServlet  extends HttpServlet{
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		TraceTool.traceParent();
		System.out.println("get start ----------"+sdf.format(new Date()));
		try {
			request.setCharacterEncoding("ISO8859-1");
			StringBuilder paramSB = new StringBuilder();
			Map<String,String[]> map = (Map<String,String[]>)request.getParameterMap();  
            for(String name:map.keySet()){
                String[] values = map.get(name);
                String valueNotDecode = Arrays.toString(values);
                paramSB.append(name+"="+ valueNotDecode +"   \n");
            }
			System.out.println("我是服务器,通过request.getParameterValues(key)读取的参数如下:\n"+paramSB.toString());
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();  
			writer.write("通过request.getParameterValues(key)读取的参数如下:\n"+paramSB.toString());  
			writer.flush();  
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		TraceTool.traceParent();
		System.out.println("post start ----------"+sdf.format(new Date()));
		BufferedReader br = null;
		System.out.println("我是服务器,从客户端取得文本内容类型为:"+request.getContentType());
		StringBuilder paramSB1 = new StringBuilder();
		StringBuilder paramSB2 = new StringBuilder();
		try {
			request.setCharacterEncoding("UTF-8");
			System.err.println("我是服务器,通过request.getParameterValues(key)获取的参数开始-----------------------\n");
			//方式一
			Map<String,String[]> map = (Map<String,String[]>)request.getParameterMap();  
            for(String name:map.keySet()){  
                String[] values = map.get(name); 
                System.out.println(name+"="+Arrays.toString(values));  
                paramSB1.append(name+"="+Arrays.toString(values)+"   \n");
                paramSB1.append("\n");
            }
           
//			response.setContentType("text/html;charset=utf-8");
//			如果"您的文本内容" 用UTF-8编码后,再以conn.setRequestProperty("content-type", "text/xml;charset=iso-8859-1");发送到本服务端,那么需要对参数内容进行如下解码
//			String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
//			System.out.println("name : "+ name);
            
            //方式二
//			Enumeration<?> paramNames=request.getParameterNames();
//			while(paramNames.hasMoreElements()){
//			    String name=(String)paramNames.nextElement();
//			    String[] valuesNotDecode=request.getParameterValues(name);
//			    int size = java.lang.reflect.Array.getLength(valuesNotDecode); 
//			    StringBuilder builder = new StringBuilder();
//			    for(String valueNotDecode : valuesNotDecode	){
//			    	String valueDecode=new String(valueNotDecode.getBytes("UTF-8"),"UTF-8");//这样写是为了方便转码
//			    	builder.append(valueDecode+",");
//			    }
//			    String appendStr = builder.substring(0, builder.length()-1);
//			    System.out.println(name + "==" + appendStr);
//			    paramSB2.append(name + "==" + appendStr+"\n");
//			}
			System.err.println("我是服务器,通过request.getParameterValues(key)获取的参数结束-----------------------\n");
			
			System.out.println("我是服务器,通过request.getInputStream()读取的参数开始____________________\n");
			//使用apache的httpcomponents 的post请求时:formparams.add(new BasicNameValuePair("key","value"));  也只能从这里获取到!
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8")); 
			String line = null;
			StringBuilder streamSB = new StringBuilder();
			while ((line = br.readLine()) != null) {
				streamSB.append(line).append(LINE_SEPARATOR);
			}
			String decode = URLDecoder.decode(streamSB.toString(), "UTF-8");
			System.out.println(decode);
			System.out.println("我是服务器,通过request.getInputStream()读取的参数结束____________________\n");
			
			 Integer delayInt = 0;
            if(map.get("delay") != null){
            	String delayStr = map.get("delay")[0];
            	if(delayStr != null){
            		delayInt = Integer.parseInt(delayStr);
            	}
            	System.out.println("准备sleep"+delayInt+"秒");
            	Thread.currentThread().sleep(delayInt*1000);
            }
            System.out.println("sleep,已返回");
	        
			response.setCharacterEncoding("UTF-8");//第一优先级高于第二优先级,只能用来设置out输出流中所采用的编码
//			response.setContentType("text/html;charset=UTF-8");//第二优先级,不仅能用来设置out输出流中所采用的编码,也可以设置浏览器接收到这些字符后以什么编码方式来解码
			PrintWriter writer = response.getWriter();  
			writer.write("通过request.getParameterValues(key)读取的参数如下:\n"+paramSB1.toString());  
			writer.write("通过request.getInputStream()读取的参数如下:\n"+decode);  
			writer.flush(); 
			writer.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (br != null)
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 客户端通过conn.setRequestProperty(key,value),
	 * 即可在服务端通过request.getHeader(key)获取到对应的head协议头值
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void tip(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//在客户端用
		HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/httpserver").openConnection();
		conn.setRequestProperty("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
		//就能在本服务端用
		request.getHeader("Accept");//获取到Head协议头中的值.
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(sdf.format(new Date()));
	}
}
