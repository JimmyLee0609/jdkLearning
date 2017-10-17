package lang;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;

public class ProcessTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();//先搁置
		String name = ProcessBuilder.Redirect.INHERIT.type().name();
		int ordinal = ProcessBuilder.Redirect.INHERIT.type().ordinal();
		File file = ProcessBuilder.Redirect.PIPE.file();
		
//		 返回此进程生成器环境的字符串映射视图。   系统环境变量信息
		Map<String, String> environment = processBuilder.environment();
		for (String key : environment.keySet()) {
			String val = environment.get(key);
			System.out.println(key+"-------"+val);
		}
		
//		返回此进程生成器的操作系统程序和参数。
		List<String> command = processBuilder.command();
		command.add("nodepade");
//		设置此进程生成器的工作目录。
		ProcessBuilder directory = processBuilder.directory(new File(""));
//		   返回此进程生成器的工作目录。
		File directory2 = processBuilder.directory();
		
		ProcessBuilder inheritIO = processBuilder.inheritIO();
//		设置此进程生成器的操作系统程序和参数。
		ProcessBuilder command2 = processBuilder.command(command);
		
		
		Redirect redirectError = processBuilder.redirectError();
		
//		通知进程生成器是否合并标准错误和标准输出。
		boolean redirectErrorStream = processBuilder.redirectErrorStream();
//		设置此进程生成器的 redirectErrorStream 属性。
		ProcessBuilder redirectErrorStream2 = processBuilder.redirectErrorStream(false);
		ProcessBuilder redirectError2 = processBuilder.redirectError(new File(""));
		ProcessBuilder redirectError3 = processBuilder.redirectError(redirectError);
		
		
		Redirect redirectInput = processBuilder.redirectInput();
		ProcessBuilder redirectInput2 = processBuilder.redirectInput(new File(""));
		ProcessBuilder redirectInput3 = processBuilder.redirectInput(redirectInput);
		
		Redirect redirectOutput = processBuilder.redirectOutput();
		ProcessBuilder redirectOutput2 = processBuilder.redirectOutput(new File(""));
		ProcessBuilder redirectOutput3 = processBuilder.redirectOutput(redirectOutput);
		
//		使用此进程生成器的属性启动一个新进程。
		Process start = processBuilder.start();
		
	}

}
