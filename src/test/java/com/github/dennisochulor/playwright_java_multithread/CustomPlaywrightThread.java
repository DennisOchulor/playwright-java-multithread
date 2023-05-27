package com.github.dennisochulor.playwright_java_multithread;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

final class CustomPlaywrightThread extends PlaywrightThread {

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
