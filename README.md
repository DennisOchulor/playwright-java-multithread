# playwright-java-multithread
Make multithreading with [Playwright Java](https://github.com/microsoft/playwright-java) easier.

Taken from https://github.com/microsoft/playwright-java#is-playwright-thread-safe
> **Is Playwright thread-safe?** No, Playwright is not thread safe, i.e. all its methods as well as methods on all objects created by it (such as BrowserContext, Browser, Page etc.) are expected to be called on the same thread where Playwright object was created or proper synchronization should be implemented to ensure only one thread calls Playwright methods at any given time. Having said that it's okay to create multiple Playwright instances each on its own thread.

Since Playwright isn't thread-safe, it is often difficult to multithread with it in an intuitive manner. This mini library aims to provide a simple and easy to use API for multithreading with Playwright Java.

## Summary
1. [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread/#playwrightthread)
2. [PlaywrightThreadFactory](https://github.com/DennisOchulor/playwright-java-multithread/#playwrightthreadfactory)

## PlaywrightThread
At the core of this library is the [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread/blob/main/src/main/java/com/github/dennisochulor/playwright_java_multithread/PlaywrightThread.java) which acts as an extension of the Java Thread class. It binds a Playwright instance and its corresponding Browsers directly onto a thread. Methods to access the underlying Playwright and Browsers instances are provided. The Playwright and Browsers instances will be closed automatically when the PlaywrightThread completed execution either normally or exceptionally.
```java
public class PlaywrightThreadExample {

	public static void main(String[] args) throws InterruptedException {
		Runnable r = () -> {
			PlaywrightThread playwrightThread = (PlaywrightThread) Thread.currentThread();
			Playwright playwright = playwrightThread.playwright();
			Browser chromium = playwrightThread.chromium();
			Browser firefox = playwrightThread.firefox();
			Browser webkit = playwrightThread.webkit();
		};
		
		PlaywrightThread thread = (PlaywrightThread) PlaywrightThreadFactory.ofDefault().newThread(r);
		thread.start();  // thread.run() won't work because main thread isn't a PlaywrightThread
		thread.join();
	}

}
```

## PlaywrightThreadFactory
[PlaywrightThreadFactory](https://github.com/DennisOchulor/playwright-java-multithread/blob/main/src/main/java/com/github/dennisochulor/playwright_java_multithread/PlaywrightThreadFactory.java) is an implementation of Java's ThreadFactory interface which produces PlaywrightThreads. This is the main way users will use this library. PlaywrightThreadFactory contains several useful static factory methods, they are:
- `PlaywrightThreadFactory.ofDefault()` and `PlaywrightThreadFactory.ofDefault(CreateOptions, LaunchOptions)` creates PlaywrightThreads with all three browsers
- `PlaywrightThreadFactory.ofChromium()` and `PlaywrightThreadFactory.ofChromium(CreateOptions, LaunchOptions)` creates PlaywrightThreads with only the Chromium browser
- `PlaywrightThreadFactory.ofFirefox()` and `PlaywrightThreadFactory.ofFirefox(CreateOptions, LaunchOptions)` creates PlaywrightThreads with only the Firefox browser
- `PlaywrightThreadFactory.ofWebkit()` and `PlaywrightThreadFactory.ofWebkit(CreateOptions, LaunchOptions)` creates PlaywrightThreads with only the Webkit browser
- `PlaywrightThreadFactory.ofCustom(Class<? extends PlaywrightThread>)` creates custom user-defined PlaywrightThreads

### PlaywrightThreadFactory and the ExecutorService API
PlaywrightThreadFactory is designed specifically to work well the Java's ExecutorService API which is commonly used in multithreading. Consider the example below.

