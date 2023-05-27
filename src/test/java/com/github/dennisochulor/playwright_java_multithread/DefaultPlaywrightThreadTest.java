package com.github.dennisochulor.playwright_java_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

final class DefaultPlaywrightThreadTest {
	
	@Test
	void testDefaultPlaywrightThreadWithoutOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofDefault());
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.chromium().browserType().name(), "chromium");
			Assertions.assertEquals(t.firefox().browserType().name(), "firefox");
			Assertions.assertEquals(t.webkit().browserType().name(), "webkit");
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
	@Test
	void testDefaultPlaywrightThreadWithOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofDefault(new CreateOptions(),new LaunchOptions()));
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.chromium().browserType().name(), "chromium");
			Assertions.assertEquals(t.firefox().browserType().name(), "firefox");
			Assertions.assertEquals(t.webkit().browserType().name(), "webkit");
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
}
