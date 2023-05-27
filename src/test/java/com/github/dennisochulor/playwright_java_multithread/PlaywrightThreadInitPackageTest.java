package com.github.dennisochulor.playwright_java_multithread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

final class PlaywrightThreadInitPackageTest {
	
	@Test
	void throwNPEIfPlaywrightIsNull() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(NullPointerException.class,
				   () -> new PlaywrightThreadInitPackage(null,browser,null,null));
	}
	
	@Test
	void throwNPEIfAllBrowsersAreNull() {
		Playwright playwright = Playwright.create(); 	
		Assertions.assertThrowsExactly(NullPointerException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,null));
	}
	
	@Test
	void ExpectedChromiumButReceivedFirefox() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.firefox().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,browser,null,null));
	}
	
	@Test
	void ExpectedChromiumButReceivedWebkit() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.webkit().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,browser,null,null));
	}
	
	@Test
	void ExpectedFirefoxButReceivedChromium() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,browser,null));
	}
	
	@Test
	void ExpectedFirefoxButReceivedWebkit() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.webkit().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,browser,null));
	}
	
	@Test
	void ExpectedWebkitButReceivedChromium() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,browser));
	}
	
	@Test
	void ExpectedWebkitButReceivedFirefox() {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.firefox().launch();
		Assertions.assertThrowsExactly(IllegalArgumentException.class,
				   () -> new PlaywrightThreadInitPackage(playwright,null,null,browser));
	}
	
}
