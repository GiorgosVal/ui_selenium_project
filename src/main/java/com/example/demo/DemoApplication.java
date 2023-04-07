package com.example.demo;

public class DemoApplication {

//	public WebDriver getChromeDriver() {
//		System.out.println("?????? chrome");
//		ChromeOptions options = new ChromeOptions();
//		String pageStrategy = PageLoadStrategy.NORMAL.toString();
//		options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, pageStrategy);
//		options.addArguments("--disable-backgrounding-occluded-windows"); //chrome 87 freeze offscreen automation / https://support.google.com/chrome/thread/83911899?hl=en
//
//		//More options - for ZAP
//		options.addArguments("--ignore-certificate-errors");
//		//options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
//		options.addArguments("start-maximized");
//		options.addArguments("--remote-allow-origins=*");
//		String x = (System.getProperty("os.name").toLowerCase().contains("mac")) ? "--start-fullscreen" : "--start-maximized";
//		options.addArguments(x);
//
//		WebDriverManager.chromedriver().setup();
//		ChromeDriverService service = new ChromeDriverService.Builder().build();
//		ChromeDriver driver = new ChromeDriver(service, options);
//		driver.get("https://www.google.com");
//		return driver;
//	}
//
//	public WebDriver getFirefoxDriver() {
//		System.out.println("?????? firefox");
//		WebDriverManager.firefoxdriver().setup();
//		FirefoxDriver driver = new FirefoxDriver();
//		return driver;
//	}

}
