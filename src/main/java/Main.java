import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private final static Logger log = Logger.getLogger(Main.class);

    private static WebDriver driver;
    private static Properties prop = new Properties();
    private static String loginUrl;
    private static String logoutUrl;

    static {

    }

    private static void loadProperties(String propertyFilePath) {
        try (InputStream input = new FileInputStream(propertyFilePath)) {

            prop.load(input);

            if ("Firefox".equals(prop.getProperty("browser")))
                driver = new FirefoxDriver(); // as you can see it's firefox :)
            else
                driver = new PhantomJSDriver(); // load browser without ui

            log.info("browser " + prop.getProperty("browser") + " was chosen");

            loginUrl = prop.getProperty("loginUrlPath");
            log.info("loginUrl : " + loginUrl );

            logoutUrl = prop.getProperty("logoutUrlPath");
            log.info("logoutUrl : " + logoutUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initialCheck(int argsLength) {
        if (argsLength <= 0) {
            log.error("You should provide config file path");
            System.exit(-1);
        }

        // todo check selenium and start it :)
        // todo check Phantom and firefox
    }

    public static void main(String[] args) {

        initialCheck(args.length);

        loadProperties(args[0]);

        // todo get command
        try {

            String expectedTitle = "صفحه\u200Cی ورود به اینترنت";
            String actualTitle = "";

            // launch Fire fox and direct it to the Base URL
            driver.get(loginUrl);
//            driver.manage().window().fullscreen();
            Thread.sleep(5000);

            // get the actual value of the title
            actualTitle = driver.getTitle();

            driver.findElement(By.xpath(".//*[@id='usename-field']")).sendKeys("sina_kashipazha");
            String username = driver.findElement(By.xpath(".//*[@id='usename-field']")).getText();
            driver.findElement(By.xpath(".//*[@id='login-form']/div[3]/input")).sendKeys("");
            String password = driver.findElement(By.xpath(".//*[@id='login-form']/div[3]/input")).getText();

            driver.findElement(By.id("login-btn")).click();
            Thread.sleep(5000);

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
            if (actualTitle.contentEquals(expectedTitle)) {
                System.out.println("Test Passed!");
            } else {
                System.out.println("Test Failed");
            }

            //close Fire fox
            driver.close();

        } catch (Exception e) {
            e.printStackTrace();
            driver.close();
        }
    }
}