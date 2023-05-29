# playwright-java-multithread
Make multithreading with [Playwright Java](https://github.com/microsoft/playwright-java) easier.

Taken from https://github.com/microsoft/playwright-java#is-playwright-thread-safe
> **Is Playwright thread-safe?** No, Playwright is not thread safe, i.e. all its methods as well as methods on all objects created by it (such as BrowserContext, Browser, Page etc.) are expected to be called on the same thread where Playwright object was created or proper synchronization should be implemented to ensure only one thread calls Playwright methods at any given time. Having said that it's okay to create multiple Playwright instances each on its own thread.

Since Playwright isn't thread-safe, it is often difficult to multithread with it in an intuitive manner. This mini library aims to provide a simple and easy to use API for multithreading with Playwright Java.

## Summary
1. [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread#playwrightthread)
2. [PlaywrightThreadFactory](https://github.com/DennisOchulor/playwright-java-multithread#playwrightthreadfactory)
3. [Custom PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread#custom-playwrightthread)
4. [Download](https://github.com/DennisOchulor/playwright-java-multithread#download)

## PlaywrightThread
At the core of this library is the [PlaywrightThread](https://github.com/DennisOchulor/playwright-java-multithread/blob/main/src/main/java/com/github/dennisochulor/playwright_java_multithread/PlaywrightThread.java) which acts as an extension of the Java Thread class. It binds a Playwright instance and its corresponding Browsers directly onto a thread. Methods to access the underlying Playwright and Browsers instances are provided. The Playwright and Browsers instances will be closed automatically when the PlaywrightThread completes execution either normally or exceptionally.
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
```java
public class MultithreadingPlaywright {
	
	static final ExecutorService executor = Executors.newFixedThreadPool(5, PlaywrightThreadFactory.ofChromium());

	public static void main(String[] args) throws Exception {
		List<String> list = Files.readAllLines(Path.of("assets/QuadraticABCList.txt"));
		
		for(String formula : list) {
			executor.execute( () -> {
				PlaywrightThread playwrightThread = (PlaywrightThread) Thread.currentThread();
				try(BrowserContext context = playwrightThread.chromium().newContext()) {
					Page page = context.newPage();
					page.navigate("https://dennisochulor.github.io/QuadraticStuff/");
					page.click("#btnSolver");
					
					String[] arr = formula.split(",", 3);
					String a = arr[0];
					String b = arr[1];
					String c = arr[2];
					
					page.fill("input[name=\"a\"]", a);
					page.fill("input[name=\"b\"]", b);
					page.fill("input[name=\"c\"]", c);
					page.click("#btnSubmit");
					page.screenshot(new ScreenshotOptions().setPath(Path.of("assets/" + a + " " + b + " " + c + ".png")));
				}
				catch(PlaywrightException e) { // avoids thread termination to allow the thread to be reused for subsequent tasks
					e.printStackTrace();
				}
			});
		}
		
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.MINUTES);
	}

}
```

If `list.size()` is 100, 100 tasks would be submitted for execution but the `FixedThreadPoolExecutor` would only launch 5 PlaywrightThreads thus at most 5 tasks will run concurrently at any given time (which is good as launching 100 PlaywrightThreads would probably be too resource instensive). The max number of threads can be configured based on the capabilities of your machine. Each time a task completes, the executor does not terminate the PlaywrightThread but rather gives it another task to execute. This ensures no time and extra resources are wasted relaunching Playwright and Browser instances. The PlaywrightThreads will only terminate after `executor.shutdown()` is called and there are no pending tasks left (or if execution of a task throws an exception in which case the executor launches a new one).

## Custom PlaywrightThreads
As mentioned earlier, it is possible to create custom PlaywrightThreads. Consider the following example:
```java
public class CustomPlaywrightThread extends PlaywrightThread {

	public CustomPlaywrightThread(Runnable r) {
		super(r);
	}

	@Override
	protected PlaywrightThreadInitPackage init() {
		Playwright playwright = Playwright.create();
		Browser chromium = playwright.chromium().launch();
		Browser webkit = playwright.webkit().launch(new LaunchOptions().setTimeout(15000));
		return new PlaywrightThreadInitPackage(playwright, chromium, null, webkit);
	}

}
```

To create a custom PlaywrightThread, extend the PlaywrightThread class and provide an implementation for the `init()` method and a public constructor that takes a single `Runnable` parameter. Then, you can use it via `PlaywrightThreadFactory.ofCustom(Class<? extends PlaywrightThread>)`.
```java
ExecutorService executor = Executors.newFixedThreadPool(5, PlaywrightThreadFactory.ofCustom(CustomPlaywrightThread.class));
```

## Download
playwright-java-multithread requires Java 8+ and [Playwright Java](https://github.com/microsoft/playwright-java) v1.23.0 or higher.
