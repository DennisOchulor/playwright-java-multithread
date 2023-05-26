package playwright_java_multithread;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Playwright.CreateOptions;

@Internal
final class DefaultPlaywrightThread extends InternalPlaywrightThread {

	public DefaultPlaywrightThread(Runnable r, CreateOptions createOptions, LaunchOptions launchOptions) {
		super(r, createOptions, launchOptions);
	}

	@Override
	PlaywrightThreadInitPackage init(CreateOptions createOptions, LaunchOptions launchOptions) {
		Playwright playwright = Playwright.create(createOptions);
		Browser chromium = playwright.chromium().launch(launchOptions);
		Browser firefox = playwright.firefox().launch(launchOptions);
		Browser webkit = playwright.webkit().launch(launchOptions);
		return new PlaywrightThreadInitPackage(playwright,chromium,firefox,webkit);
	}

}
