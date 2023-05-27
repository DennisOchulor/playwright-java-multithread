package com.github.dennisochulor.playwright_java_multithread;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

@Internal
final class InternalPlaywrightThreadFactory implements ThreadFactory {
	
	private final Class<? extends InternalPlaywrightThread> clazz;
	private final CreateOptions createOptions;
	private final LaunchOptions launchOptions;

	InternalPlaywrightThreadFactory(Class<? extends InternalPlaywrightThread> clazz,CreateOptions createOptions,LaunchOptions launchOptions) {
		this.clazz = Objects.requireNonNull(clazz);
		this.createOptions = Objects.requireNonNull(createOptions, "createOptions");
		this.launchOptions = Objects.requireNonNull(launchOptions, "launchOptions");
	}

	@Override
	public final Thread newThread(Runnable r) {
		try {
			return clazz.getConstructor(Runnable.class,CreateOptions.class,LaunchOptions.class)
						.newInstance(r,createOptions,launchOptions);
		} 
		
		catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
