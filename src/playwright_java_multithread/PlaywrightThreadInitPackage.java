package playwright_java_multithread;

import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public final class PlaywrightThreadInitPackage {
	
	private final Playwright playwright;
	private final Browser chromium;
	private final Browser firefox;
	private final Browser webkit;
	
	
	public PlaywrightThreadInitPackage(Playwright playwright, Browser chromium, Browser firefox, Browser webkit) {		
		try {
			this.playwright = Objects.requireNonNull(playwright);
			
			if(Objects.isNull(chromium) && Objects.isNull(firefox) && Objects.isNull(webkit)) {
				throw new NullPointerException("At least one browser must be non-null!");
			}
			
			if(chromium != null && !chromium.browserType().name().equals("chromium")) {
				throw new IllegalArgumentException("chromium : Expected Chromium browser! Received "
													+ chromium.browserType().name() + " instead.");
			}
			if(firefox != null && !firefox.browserType().name().equals("firefox")) {
				throw new IllegalArgumentException("firefox : Expected Firefox browser! Received "
													+ firefox.browserType().name() + " instead.");
			}
			if(webkit != null && !webkit.browserType().name().equals("webkit")) {
				throw new IllegalArgumentException("webkit : Expected Webkit browser! Received "
													+ webkit.browserType().name() + " instead.");
			}
			
			this.chromium = chromium;
			this.firefox = firefox;
			this.webkit = webkit;
		}
		
		catch(Throwable t) {
			// close all non-null resources in event of initialisation failure
			if(chromium != null) chromium.close();
			if(firefox != null) firefox.close();
			if(webkit != null) webkit.close();
			if(playwright != null) playwright.close();
			throw t;
		}
	}


	public final Playwright playwright() {
		return playwright;
	}

	public final Browser chromium() {
		return chromium;
	}

	public final Browser firefox() {
		return firefox;
	}

	public final Browser webkit() {
		return webkit;
	}

}