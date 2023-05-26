package playwright_java_multithread;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

public final class PlaywrightThreadFactory implements ThreadFactory {
	
	private final Class<? extends PlaywrightThread> clazz;
	
	private PlaywrightThreadFactory(Class<? extends PlaywrightThread> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
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
	
	
	
	// static factory methods
	public static ThreadFactory ofDefault() {
		return new InternalPlaywrightThreadFactory(DefaultPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	public static ThreadFactory ofDefault(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(DefaultPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	public static ThreadFactory ofChromium() {
		return new InternalPlaywrightThreadFactory(ChromiumPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	public static ThreadFactory ofChromium(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(ChromiumPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	public static ThreadFactory ofFirefox() {
		return new InternalPlaywrightThreadFactory(FirefoxPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	public static ThreadFactory ofFirefox(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(FirefoxPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	public static ThreadFactory ofWebkit() {
		return new InternalPlaywrightThreadFactory(WebkitPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	public static ThreadFactory ofWebkit(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(WebkitPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	public static ThreadFactory ofCustom(Class<? extends PlaywrightThread> clazz) {
		return new PlaywrightThreadFactory(clazz);
	}

	
	

}
