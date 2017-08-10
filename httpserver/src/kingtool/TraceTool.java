package kingtool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author King
 * @see
 * @version createTM：2017年5月5日 上午10:26:21
 */
public class TraceTool {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

	public static void main(String[] args) {
		do1();
	}

	public static void do1() {
		do2();
	}

	public static void do2() {
		do3();
	}

	public static void do3() {
		traceAll();
	}

	/**
	 * 
	 * @time 2017年5月5日 上午10:26:21
	 * @author King
	 */
	public static String traceParent() {
		String datetime = sdf.format(new Date());
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		StackTraceElement stack = stacks[1];
		sb.append("[").append(datetime).append("]→[").append(stack.getClassName()).append(".").append(stack.getMethodName()).append("(at line ").append(":").append(stack.getLineNumber()).append(")]↓");
		System.out.println(sb);
		return sb.toString();
	}

	/**
	 * 
	 * @time 2017年5月5日 上午10:32:21
	 * @author King
	 */
	public static String traceAll() {
		String datetime = sdf.format(new Date());
		StringBuilder sb = new StringBuilder();
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		for (int i = 0; i < stacks.length; i++) {
			StackTraceElement stack = stacks[i];
			sb.append("[").append(datetime).append("]→[").append(stack.getClassName()).append(".").append(stack.getMethodName()).append("(at line ").append(":").append(stack.getLineNumber()).append(")]↓").append(LINE_SEPARATOR);
		}
		System.out.println(sb);
		return sb.toString();
	}
}
