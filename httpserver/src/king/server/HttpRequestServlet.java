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
 * ���ڽ�������http��ʽ������,���ѽ��ܵ���request��param��getInputStreamȫ��ӡ����
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
			System.out.println("���Ƿ�����,ͨ��request.getParameterValues(key)��ȡ�Ĳ�������:\n"+paramSB.toString());
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();  
			writer.write("ͨ��request.getParameterValues(key)��ȡ�Ĳ�������:\n"+paramSB.toString());  
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
		System.out.println("���Ƿ�����,�ӿͻ���ȡ���ı���������Ϊ:"+request.getContentType());
		StringBuilder paramSB1 = new StringBuilder();
		StringBuilder paramSB2 = new StringBuilder();
		try {
			request.setCharacterEncoding("UTF-8");
			System.err.println("���Ƿ�����,ͨ��request.getParameterValues(key)��ȡ�Ĳ�����ʼ-----------------------\n");
			//��ʽһ
			Map<String,String[]> map = (Map<String,String[]>)request.getParameterMap();  
            for(String name:map.keySet()){  
                String[] values = map.get(name); 
                System.out.println(name+"="+Arrays.toString(values));  
                paramSB1.append(name+"="+Arrays.toString(values)+"   \n");
                paramSB1.append("\n");
            }
           
//			response.setContentType("text/html;charset=utf-8");
//			���"�����ı�����" ��UTF-8�����,����conn.setRequestProperty("content-type", "text/xml;charset=iso-8859-1");���͵��������,��ô��Ҫ�Բ������ݽ������½���
//			String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
//			System.out.println("name : "+ name);
            
            //��ʽ��
//			Enumeration<?> paramNames=request.getParameterNames();
//			while(paramNames.hasMoreElements()){
//			    String name=(String)paramNames.nextElement();
//			    String[] valuesNotDecode=request.getParameterValues(name);
//			    int size = java.lang.reflect.Array.getLength(valuesNotDecode); 
//			    StringBuilder builder = new StringBuilder();
//			    for(String valueNotDecode : valuesNotDecode	){
//			    	String valueDecode=new String(valueNotDecode.getBytes("UTF-8"),"UTF-8");//����д��Ϊ�˷���ת��
//			    	builder.append(valueDecode+",");
//			    }
//			    String appendStr = builder.substring(0, builder.length()-1);
//			    System.out.println(name + "==" + appendStr);
//			    paramSB2.append(name + "==" + appendStr+"\n");
//			}
			System.err.println("���Ƿ�����,ͨ��request.getParameterValues(key)��ȡ�Ĳ�������-----------------------\n");
			
			System.out.println("���Ƿ�����,ͨ��request.getInputStream()��ȡ�Ĳ�����ʼ____________________\n");
			//ʹ��apache��httpcomponents ��post����ʱ:formparams.add(new BasicNameValuePair("key","value"));  Ҳֻ�ܴ������ȡ��!
			br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8")); 
			String line = null;
			StringBuilder streamSB = new StringBuilder();
			while ((line = br.readLine()) != null) {
				streamSB.append(line).append(LINE_SEPARATOR);
			}
			String decode = URLDecoder.decode(streamSB.toString(), "UTF-8");
			System.out.println(decode);
			System.out.println("���Ƿ�����,ͨ��request.getInputStream()��ȡ�Ĳ�������____________________\n");
			
			 Integer delayInt = 0;
            if(map.get("delay") != null){
            	String delayStr = map.get("delay")[0];
            	if(delayStr != null){
            		delayInt = Integer.parseInt(delayStr);
            	}
            	System.out.println("׼��sleep"+delayInt+"��");
            	Thread.currentThread().sleep(delayInt*1000);
            }
            System.out.println("sleep,�ѷ���");
	        
			response.setCharacterEncoding("UTF-8");//��һ���ȼ����ڵڶ����ȼ�,ֻ����������out������������õı���
//			response.setContentType("text/html;charset=UTF-8");//�ڶ����ȼ�,��������������out������������õı���,Ҳ����������������յ���Щ�ַ�����ʲô���뷽ʽ������
			PrintWriter writer = response.getWriter();  
			writer.write("ͨ��request.getParameterValues(key)��ȡ�Ĳ�������:\n"+paramSB1.toString());  
			writer.write("ͨ��request.getInputStream()��ȡ�Ĳ�������:\n"+decode);  
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
	 * �ͻ���ͨ��conn.setRequestProperty(key,value),
	 * �����ڷ����ͨ��request.getHeader(key)��ȡ����Ӧ��headЭ��ͷֵ
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void tip(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//�ڿͻ�����
		HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8080/httpserver").openConnection();
		conn.setRequestProperty("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
		//�����ڱ��������
		request.getHeader("Accept");//��ȡ��HeadЭ��ͷ�е�ֵ.
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(sdf.format(new Date()));
	}
}
