package playwright_java_multithread;

import java.util.Objects;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public abstract class PlaywrightThread extends Thread {
	
	private final Playwright playwright;
	private final Browser chromium;
	private final Browser firefox;
	private final Browser webkit;
	private Runnable r;
	
	protected abstract PlaywrightThreadInitPackage init();

	protected PlaywrightThread(Runnable r) {
		PlaywrightThreadInitPackage initPackage = init();
		this.r = r;
		this.playwright = initPackage.playwright();
		this.chromium = initPackage.chromium();
		this.firefox = initPackage.firefox();
		this.webkit = initPackage.webkit();
	}


	@Override
	public final void run() {
		try {  // try-with-resources using variables only Java 9+ :(
			if(r != null) {
				r.run();
			}
		}
		
		finally {
			if(chromium != null) chromium.close();
			if(firefox != null) firefox.close();
			if(webkit != null) webkit.close();
			playwright.close();  // cannot be null
		}
	}
	
	public final Playwright playwright() {
		return playwright;
	}
	
	public final Browser chromium() {
		Objects.requireNonNull(chromium, "The configuration of " + this.getClass().getName() + " does not use chromium.");
		return chromium;
	}
	
	public final Browser firefox() {
		Objects.requireNonNull(firefox, "The configuration of " + this.getClass().getName() + " does not use firefox.");
		return firefox;
	}
	
	public final Browser webkit() {
		Objects.requireNonNull(webkit, "The configuration of " + this.getClass().getName() + " does not use webkit.");
		return webkit;
	}
	
	
}
