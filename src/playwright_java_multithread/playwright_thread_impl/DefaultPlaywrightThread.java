package playwright_java_multithread.playwright_thread_impl;

import playwright_java_multithread.PlaywrightThread;
import playwright_java_multithread.PlaywrightThreadInitPackage;

final class DefaultPlaywrightThread extends PlaywrightThread {

	public DefaultPlaywrightThread(Runnable r) {
		super(r);
	}

	@Override
	protected PlaywrightThreadInitPackage init() {
		return null; //TODO
	}

}
