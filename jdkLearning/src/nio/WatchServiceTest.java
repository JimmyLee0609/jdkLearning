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
		// ��ȡ�ļ��۲����
		WatchService newWatchService = FileSystems.getDefault().newWatchService();
		// ע��۲����,ֻ���ص�ǰ�ļ����£���Ŀ¼�Ĳ�����
		
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
//			Modifier��û��ʵ�ֵ�
//		WatchKey register3 = Paths.get("d:/temp").register(newWatchService,swek,modifier);
		WatchKey register4 = Paths.get("d:/temp/A").register(newWatchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.OVERFLOW);

		
		out: while (true) {
			// ��ȡ��һ���۲�ļ����������ȴ�
			WatchKey take = newWatchService.take();
			// ��ȡ�۲�����¼�
			List<WatchEvent<?>> pollEvents2 = take.pollEvents();
			for (WatchEvent<?> watchEvent : pollEvents2) {
				
				System.out.println("watchEvent.context   " + watchEvent.context().toString() +"      watchEvent.count  "+ watchEvent.count() + "  watchEvent.kind     "+watchEvent.kind());
//				�ж��¼���������ִ����صĲ���
				if(watchEvent.kind().name()==StandardWatchEventKinds.ENTRY_MODIFY.name())
				System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.ENTRY_MODIFY.name());
				if(watchEvent.kind().name()==StandardWatchEventKinds.OVERFLOW.name())
					System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.OVERFLOW.name());
				if(watchEvent.kind().name()==StandardWatchEventKinds.ENTRY_DELETE.name())
					System.out.println(watchEvent.kind().name()+"---------"+StandardWatchEventKinds.ENTRY_DELETE.name());
		
			}
			// ���ش����۲����Ķ���
			Watchable watchable = take.watchable();
//			ÿ�λ�ȡ��key�����ã��������Ϣ����һ�λ�ȡ�Ͳ������ظ�����Ϣ��count�Ǽ�¼�¼����ֵĴ���
			boolean reset = take.reset();
			// �жϹ۲���Ƿ���Ч��ֻ�з���رգ���ȡ�����Ż�����Ч
			if(!take.isValid()){
				// ȡ��������
				take.cancel();
				break;
				}
		}
		// ��ȡ�۲�ļ���������������ȡ������ֱ�ӷ���Null
		WatchKey poll = newWatchService.poll();

		String string = newWatchService.toString();
		// �رշ���
		newWatchService.close();

	}

}
