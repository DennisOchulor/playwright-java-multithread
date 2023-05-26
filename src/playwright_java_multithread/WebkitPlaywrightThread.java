package playwright_java_multithread;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

final class WebkitPlaywrightThread extends InternalPlaywrightThread {

	public WebkitPlaywrightThread(Runnable r, CreateOptions createOptions, LaunchOptions launchOptions) {
		super(r, createOptions, launchOptions);
	}
	
	
	@Override
	PlaywrightThreadInitPackage init(CreateOptions createOptions, LaunchOptions launchOptions) {
		Playwright playwright = Playwright.create(createOptions);
		Browser webkit = playwright.webkit().launch(launchOptions);
		return new PlaywrightThreadInitPackage(playwright, null, null, webkit);
	}

}
