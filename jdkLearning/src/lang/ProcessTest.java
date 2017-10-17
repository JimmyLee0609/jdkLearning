package lang;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Map;

public class ProcessTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();//�ȸ���
		String name = ProcessBuilder.Redirect.INHERIT.type().name();
		int ordinal = ProcessBuilder.Redirect.INHERIT.type().ordinal();
		File file = ProcessBuilder.Redirect.PIPE.file();
		
//		 ���ش˽����������������ַ���ӳ����ͼ��   ϵͳ����������Ϣ
		Map<String, String> environment = processBuilder.environment();
		for (String key : environment.keySet()) {
			String val = environment.get(key);
			System.out.println(key+"-------"+val);
		}
		
//		���ش˽����������Ĳ���ϵͳ����Ͳ�����
		List<String> command = processBuilder.command();
		command.add("nodepade");
//		���ô˽����������Ĺ���Ŀ¼��
		ProcessBuilder directory = processBuilder.directory(new File(""));
//		   ���ش˽����������Ĺ���Ŀ¼��
		File directory2 = processBuilder.directory();
		
		ProcessBuilder inheritIO = processBuilder.inheritIO();
//		���ô˽����������Ĳ���ϵͳ����Ͳ�����
		ProcessBuilder command2 = processBuilder.command(command);
		
		
		Redirect redirectError = processBuilder.redirectError();
		
//		֪ͨ�����������Ƿ�ϲ���׼����ͱ�׼�����
		boolean redirectErrorStream = processBuilder.redirectErrorStream();
//		���ô˽����������� redirectErrorStream ���ԡ�
		ProcessBuilder redirectErrorStream2 = processBuilder.redirectErrorStream(false);
		ProcessBuilder redirectError2 = processBuilder.redirectError(new File(""));
		ProcessBuilder redirectError3 = processBuilder.redirectError(redirectError);
		
		
		Redirect redirectInput = processBuilder.redirectInput();
		ProcessBuilder redirectInput2 = processBuilder.redirectInput(new File(""));
		ProcessBuilder redirectInput3 = processBuilder.redirectInput(redirectInput);
		
		Redirect redirectOutput = processBuilder.redirectOutput();
		ProcessBuilder redirectOutput2 = processBuilder.redirectOutput(new File(""));
		ProcessBuilder redirectOutput3 = processBuilder.redirectOutput(redirectOutput);
		
//		ʹ�ô˽�������������������һ���½��̡�
		Process start = processBuilder.start();
		
	}

}
