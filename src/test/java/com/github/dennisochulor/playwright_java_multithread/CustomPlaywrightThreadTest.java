/*
 * MIT License
 *
 * Copyright (c) 2023 Dennis Ochulor
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
