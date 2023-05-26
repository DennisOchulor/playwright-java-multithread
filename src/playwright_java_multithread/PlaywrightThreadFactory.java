package playwright_java_multithread;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ThreadFactory;

public final class PlaywrightThreadFactory implements ThreadFactory {
	
	private final Class<? extends PlaywrightThread> clazz;
	
	private PlaywrightThreadFactory(Class<? extends PlaywrightThread> clazz) {
		this.clazz = clazz;
	}
	
	
	// static factory methods
	public static PlaywrightThreadFactory ofCustom(Class<? extends PlaywrightThread> clazz) {
		return new PlaywrightThreadFactory(clazz);
	}

	
	@Override
	public final PlaywrightThread newThread(Runnable r) {
		try {
			return clazz.getConstructor(Runnable.class).newInstance(r);
		} 
		
		catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}	
	}

}
