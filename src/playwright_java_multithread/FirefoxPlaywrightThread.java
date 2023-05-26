package playwright_java_multithread;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright.CreateOptions;

@Internal
final class FirefoxPlaywrightThread extends InternalPlaywrightThread {

	public FirefoxPlaywrightThread(Runnable r, CreateOptions createOptions, LaunchOptions launchOptions) {
		super(r, createOptions, launchOptions);
	}

	
	@Override
	PlaywrightThreadInitPackage init(CreateOptions createOptions, LaunchOptions launchOptions) {
		Playwright playwright = Playwright.create(createOptions);
		Browser firefox = playwright.firefox().launch(launchOptions);
		return new PlaywrightThreadInitPackage(playwright, null, firefox, null);
	}
	
}
