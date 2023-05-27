package com.github.dennisochulor.playwright_java_multithread;

import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

@Internal
abstract class InternalPlaywrightThread extends PlaywrightThread {

	public InternalPlaywrightThread(Runnable r, CreateOptions createOptions, LaunchOptions launchOptions) {
		super(r, createOptions, launchOptions);
	}
	
	protected PlaywrightThreadInitPackage init() {
		throw new UnsupportedOperationException(); 
	}
	
	@Override
	protected abstract PlaywrightThreadInitPackage init(CreateOptions createOptions, LaunchOptions launchOptions);
	
}
