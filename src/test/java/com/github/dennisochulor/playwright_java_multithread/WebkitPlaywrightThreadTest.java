package com.github.dennisochulor.playwright_java_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

final class WebkitPlaywrightThreadTest {

	@Test
	void testWebkitPlaywrightThreadWithoutOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofWebkit());
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.webkit().browserType().name(), "webkit");
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.firefox());
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.chromium());
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
	@Test
	void testWebkitPlaywrightThreadWithOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofWebkit(new CreateOptions(),new LaunchOptions()));
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.webkit().browserType().name(), "webkit");
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.firefox());
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.chromium());
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
}
