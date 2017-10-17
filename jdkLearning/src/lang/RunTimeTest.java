package lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RunTimeTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		shutdownHook();
		Runtime runtime = Runtime.getRuntime();

		int availableProcessors = runtime.availableProcessors();// 可用进程数 4
		long maxMemory = runtime.maxMemory();// java 虚拟机试图获取最大内存937951232
		long freeMemory = runtime.freeMemory();// 返回虚拟机中空闲的内存数量 62809216
		long totalMemory = runtime.totalMemory();// 返回虚拟机中内存总量 64487424

		runtime.traceInstructions(true);// 启用／禁用指令跟踪。
		runtime.traceMethodCalls(true);// 启用／禁用方法调用跟踪。
		// 读不来  根本地类  c  c++有关
		// runtime.load("gdi32");//加载指定库名的本地动态库
		// runtime.loadLibrary("gdi32");// 加载具有指定库名的动态库。
		Process exec = runtime.exec("cmd nodepad");// 命令是SHELL命令
		// runtime.exec(cmdarray, envp, dir)//指定命令 ,指定运行环境, 指定目录
		String string = runtime.toString();
		exec.destroy();// 进程销毁
		runtime.exit(0);// 虚拟机退出 退出前都会运行钩子线程 0 RUNNING 1 HOOKS 2 FINALIZERS
		// runtime.halt(5);//强行终止当前虚拟机
		runtime.gc();//
	}

	private static void shutdownHook() {
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(3000);
				System.out.println("start");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Runtime runtime = Runtime.getRuntime();
		// 注册虚拟机关闭钩子
		runtime.addShutdownHook(t);
		// 取消注册虚拟机关闭钩子
		// boolean removeShutdownHook =
		// runtime.removeShutdownHook(Thread.currentThread());
	}

}
