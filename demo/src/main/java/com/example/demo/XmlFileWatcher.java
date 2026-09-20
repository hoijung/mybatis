package com.example.demo;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
@Profile("!prod")
public class XmlFileWatcher {

	@Autowired
	private MyBatisXmlReloader xmlReloader;

	@PostConstruct
	public void start() {

		Thread thread = new Thread(() -> {

			try {
				WatchService watchService = FileSystems.getDefault().newWatchService();

				Path path = Paths.get("src/main/resources/mapper");

				path.register(
						watchService,
					    StandardWatchEventKinds.ENTRY_CREATE,
					    StandardWatchEventKinds.ENTRY_MODIFY,
					    StandardWatchEventKinds.ENTRY_DELETE
					);
//				path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

				while (true) {

					WatchKey key = watchService.take();

					for (WatchEvent<?> event : key.pollEvents()) {

						WatchEvent.Kind<?> kind = event.kind();

					    Path file = (Path) event.context();

					    if (!file.toString().toLowerCase().endsWith(".xml")) {
					        continue;
					    }
					    
					    Path fullPath = path.resolve(file);

//					    waitForFileStable(fullPath);

					    System.out.println(
					        "[FILE] " + kind.name() + " : " + file
					    );

					    if (kind == StandardWatchEventKinds.ENTRY_MODIFY ||
					        kind == StandardWatchEventKinds.ENTRY_CREATE) {
					    	
//					    	 waitForFileStable(fullPath);
					    	 Thread.sleep(1500);

					    	 xmlReloader.reload("mapper/UserMapper.xml");
					    }
					    
//						Path file = (Path) event.context();
//
//						if (file.toString().endsWith(".xml")) {
//
//							System.out.println("[XML 변경] " + file);
//
//							// 여기서 MyBatis reload 호출
////							xmlReloader.reload();
//							 xmlReloader.reload("mapper/UserMapper.xml");
//						}
					}

					key.reset();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		thread.setDaemon(true);
		thread.start();
	}
	
	private void waitForFileStable(Path file)
	        throws Exception {

	    long previousSize = -1;

	    while (true) {

	        long size = Files.size(file);

	        if (size == previousSize) {
	            break;
	        }

	        previousSize = size;

	        Thread.sleep(200);
	    }
	}
}