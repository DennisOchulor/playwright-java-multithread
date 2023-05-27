package com.github.dennisochulor.playwright_java_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

final class FirefoxPlaywrightThreadTest {
	
	@Test
	void testFirefoxPlaywrightThreadWithoutOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofFirefox());
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.firefox().browserType().name(), "firefox");
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.chromium());
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.webkit());
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
	@Test
	void testFirefoxPlaywrightThreadWithOptions() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofFirefox(new CreateOptions(), new LaunchOptions()));
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.firefox().browserType().name(), "firefox");
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.chromium());
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.webkit());
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
}
