package uk.co.cristalski.core;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class BaseClass {

	public static final String FILE_PATH_SEPERATOR = System.getProperty("file.separator");
	public static final String SYSTEM_USER_DIR = System.getProperty("user.dir");
	public static final String PATH_TO_DRIVERS = SYSTEM_USER_DIR + FILE_PATH_SEPERATOR + "drivers"
			+ FILE_PATH_SEPERATOR;
	static Properties prop = new Properties();
	public Properties configProperties = getProperties("config.properties");
	public WebDriver driver;

	
	public void launchBrowser() {
		if(configProperties.getProperty("DRIVER").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", PATH_TO_DRIVERS+"chromedriver.exe");
			driver=new ChromeDriver();
		}else if(configProperties.getProperty("DRIVER").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", PATH_TO_DRIVERS+"geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(configProperties.getProperty("DRIVER").equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", PATH_TO_DRIVERS+"MicrosoftWebDriver.exe");
			driver=new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	}
	
	
	@Test
	public void navigateToTheApplication() {
		launchBrowser();
		String url=configProperties.getProperty("URL");
		driver.get(url);
	}

	public Properties getProperties(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(
					SYSTEM_USER_DIR + FILE_PATH_SEPERATOR + "configurations" + FILE_PATH_SEPERATOR + fileName);
			prop.load(fis);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
}
