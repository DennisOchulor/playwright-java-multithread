package com.github.dennisochulor.playwright_java_multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class CustomPlaywrightThreadTest {
	
	@Test
	void testCustomPlaywrightThread() throws Throwable {
		ExecutorService executor = Executors.newSingleThreadExecutor(PlaywrightThreadFactory.ofCustom(CustomPlaywrightThread.class));
		Runnable test = () -> {
			PlaywrightThread t = (PlaywrightThread)(Thread.currentThread());
			Assertions.assertNotNull(t.playwright());
			Assertions.assertEquals(t.chromium().browserType().name(), "chromium");
			Assertions.assertThrowsExactly(NullPointerException.class, () -> t.firefox());
			Assertions.assertEquals(t.webkit().browserType().name(), "webkit");
		};
		
		executor.submit(test).get();
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.MINUTES);
	}
	
}
