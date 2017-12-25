package nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;
import java.util.Arrays;
import java.util.List;

public class WatchServiceTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {
		// 获取文件观察服务
		WatchService newWatchService = FileSystems.getDefault().newWatchService();
		// 注册观察服务,只会监控当前文件夹下，子目录的不会监控
		
		@SuppressWarnings("unchecked")
		Kind<Path>[] swek = (Kind<Path>[]) Arrays.asList(StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW).toArray();
		
		Modifier modifier = new WatchEvent.Modifier(){
			@Override
			public String name() {
				int b=0;
				return null;
			}};
//			Modifier是没有实现的
//		WatchKey register3 = Paths.get("d:/temp").register(newWatchService,swek,modifier);
		WatchKey register4 = Paths.get("d:/temp/A").register(newWatchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW);

		
		out: while (true) {
			// 获取第一个观察的键，会阻塞等待
			WatchKey take = newWatchService.take();
			// 获取观察键的事件
			List<WatchEvent<?>> pollEvents2 = take.pollEvents();
			for (WatchEvent<?> watchEvent : pollEvents2) {
				
				System.out.println("watchEvent.context   " + watchEvent.context().toString() +"      watchEvent.count  "+ watchEvent.count() + "  watchEvent.kind     "+watchEvent.kind());
//				判断事件的种类来执行相关的操作
				if(watchEvent.kind().name()==StandardWatchEventKinds.ENTRY_MODIFY.name())
				System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.ENTRY_MODIFY.name());
				if(watchEvent.kind().name()==StandardWatchEventKinds.OVERFLOW.name())
					System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.OVERFLOW.name());
				if(watchEvent.kind().name()==StandardWatchEventKinds.ENTRY_DELETE.name())
					System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.ENTRY_DELETE.name());
		
			}
			// 返回创建观察服务的对象
			Watchable watchable = take.watchable();
//			每次获取完key就重置，里面的信息，下一次获取就不会有重复的信息，count是记录事件出现的次数
			boolean reset = take.reset();
			// 判断观察键是否有效，只有服务关闭，键取消，才会是无效
			if(!take.isValid()){
				// 取消键服务
				take.cancel();
				break;
				}
		}
		// 获取观察的键，不会阻塞，获取不到就直接返回Null
		WatchKey poll = newWatchService.poll();

		String string = newWatchService.toString();
		// 关闭服务
		newWatchService.close();

	}

}
