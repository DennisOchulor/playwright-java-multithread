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

package io.github.dennisochulor.playwright_java_multithread;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

/**
 * 
 * This class is an implementation of {@link ThreadFactory} for {@link PlaywrightThread}s. Useful static factory methods are
 * provided to get common implementations of {@link PlaywrightThread}s. 
 *
 */
public final class PlaywrightThreadFactory implements ThreadFactory {
	
	private final Class<? extends PlaywrightThread> clazz;
	
	private PlaywrightThreadFactory(Class<? extends PlaywrightThread> clazz) {
		this.clazz = Objects.requireNonNull(clazz, "clazz");
	}
	
	@Override
	public final PlaywrightThread newThread(Runnable r) {
		try {
			return clazz.getConstructor(Runnable.class).newInstance(r);
		} 
		catch (InstantiationException e) {
			throw new RuntimeException("Cannot instantiate an abstract class!",e);
		} 
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}  
		catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} 
		catch (NoSuchMethodException e) {
			throw new RuntimeException("Constuctor " + e.getLocalizedMessage() + " must be public!",e);
		}	
	}
	
	
	
	// static factory methods
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utitlise all three Playwright browsers with their
	 * default configurations. 
	 * @return The default {@link ThreadFactory}
	 */
	public static ThreadFactory ofDefault() {
		return new InternalPlaywrightThreadFactory(DefaultPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utitlise all three Playwright browsers with the
	 * given configuration options.
	 * @param createOptions The {@link CreateOptions} for {@link com.microsoft.playwright.Playwright#create(CreateOptions) Playwright.create(CreateOptions)}
	 * @param launchOptions The {@link LaunchOptions} for {@link com.microsoft.playwright.BrowserType#launch() BrowserType.launch(LaunchOptions)}
	 * @return The {@link ThreadFactory} with the given configurations.
	 * @throws NullPointerException if {@code createOptions} or {@code launchOptions} is {@code null}.
	 */
	public static ThreadFactory ofDefault(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(DefaultPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utilise only the Chromium browser.
	 * @return The {@link ThreadFactory} as described above. 
	 */
	public static ThreadFactory ofChromium() {
		return new InternalPlaywrightThreadFactory(ChromiumPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utitlise only the Chromium browser
	 * with the given configuration options.
	 * @param createOptions The {@link CreateOptions} for {@link com.microsoft.playwright.Playwright#create(CreateOptions) Playwright.create(CreateOptions)}
	 * @param launchOptions The {@link LaunchOptions} for {@link com.microsoft.playwright.BrowserType#launch() BrowserType.launch(LaunchOptions)}
	 * @return The {@link ThreadFactory} as described above.
	 * @throws NullPointerException if {@code createOptions} or {@code launchOptions} is {@code null}.
	 */
	public static ThreadFactory ofChromium(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(ChromiumPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utilise only the Firefox browser.
	 * @return The {@link ThreadFactory} as described above. 
	 */
	public static ThreadFactory ofFirefox() {
		return new InternalPlaywrightThreadFactory(FirefoxPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utitlise only the Firefox browser
	 * with the given configuration options.
	 * @param createOptions The {@link CreateOptions} for {@link com.microsoft.playwright.Playwright#create(CreateOptions) Playwright.create(CreateOptions)}
	 * @param launchOptions The {@link LaunchOptions} for {@link com.microsoft.playwright.BrowserType#launch() BrowserType.launch(LaunchOptions)}
	 * @return The {@link ThreadFactory} as described above.
	 * @throws NullPointerException if {@code createOptions} or {@code launchOptions} is {@code null}.
	 */
	public static ThreadFactory ofFirefox(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(FirefoxPlaywrightThread.class, createOptions, launchOptions);
	}
	
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utilise only the Webkit browser.
	 * @return The {@link ThreadFactory} as described above. 
	 */
	public static ThreadFactory ofWebkit() {
		return new InternalPlaywrightThreadFactory(WebkitPlaywrightThread.class, new CreateOptions(), new LaunchOptions());
	}
	
	/**
	 * Returns a {@link ThreadFactory} that produces {@link PlaywrightThread}s that utitlise only the Webkit browser
	 * with the given configuration options.
	 * @param createOptions The {@link CreateOptions} for {@link com.microsoft.playwright.Playwright#create(CreateOptions) Playwright.create(CreateOptions)}
	 * @param launchOptions The {@link LaunchOptions} for {@link com.microsoft.playwright.BrowserType#launch() BrowserType.launch(LaunchOptions)}
	 * @return The {@link ThreadFactory} as described above.
	 * @throws NullPointerException if {@code createOptions} or {@code launchOptions} is {@code null}.
	 */
	public static ThreadFactory ofWebkit(CreateOptions createOptions, LaunchOptions launchOptions) {
		return new InternalPlaywrightThreadFactory(WebkitPlaywrightThread.class, createOptions, launchOptions);
	}
	
	/**
	 * Returns a {@link ThreadFactory} that produces custom user-defined {@link PlaywrightThread}s.
	 * Custom {@link PlaywrightThread}s can be created by extending the {@link PlaywrightThread} class. For example:
	 * 
	 * <pre>
	 * public class CustomPlaywrightThread extends PlaywrightThread {
     *
	 *	public CustomPlaywrightThread(Runnable r) {
	 *	    super(r);
	 * 	}
	 *
     *	{@literal @Override}
	 *	protected PlaywrightThreadInitPackage init() {
	 *	    Playwright playwright = Playwright.create();
	 *	    Browser chromium = playwright.chromium().launch();
	 *	    Browser webkit = playwright.webkit().launch(new LaunchOptions().setTimeout(10000));
	 *
	 *            // browser order is IMPORTANT!       
	 *            // null means that browser will not be used.
	 *	    return new PlaywrightThreadInitPackage(playwright, chromium, null, webkit); 
	 *	}
     *
	 *}
	 * </pre>
	 * 
	 * @param clazz The user-defined {@link PlaywrightThread} class literal e.g. {@code CustomPlaywrightThread.class}
	 * @return The {@link ThreadFactory} as described above.
	 * @throws NullPointerException if {@code clazz} is {@code null}.
	 */
	public static ThreadFactory ofCustom(Class<? extends PlaywrightThread> clazz) {
		return new PlaywrightThreadFactory(clazz);
	}

}
