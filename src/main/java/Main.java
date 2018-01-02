import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private final static Logger log = LogManager.getLogger(Main.class);

    private static WebDriver driver;
    private static Properties prop = new Properties();
    private static String loginUrl;
    private static String logoutUrl;
    private static WebDriverWait wait;

    static {

    }

    private static void loadProperties(String propertyFilePath) {
        try (InputStream input = new FileInputStream(propertyFilePath)) {

            prop.load(input);

            if ("Firefox".equals(prop.getProperty("browser")))
                driver = new FirefoxDriver(); // as you can see it's firefox :)
            else
                driver = new PhantomJSDriver(); // load browser without ui

            driver.manage().window().setSize(new Dimension(400, 300));

            wait = new WebDriverWait(driver, 15);

            log.info("browser " + prop.getProperty("browser") + " was chosen");

            loginUrl = prop.getProperty("loginUrlPath");
            log.info("loginUrl : " + loginUrl);

            logoutUrl = prop.getProperty("logoutUrlPath");
            log.info("logoutUrl : " + logoutUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isSeleniumUp() {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://localhost:4444/wd/hub/status")
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();
            String body = response.body().string();
            JSONObject JBody = new JSONObject(body);

            log.info("selenium is ready : " + JBody.getJSONObject("value").get("ready"));

            return (boolean) JBody.getJSONObject("value").get("ready");

        } catch (IOException e) {
            return false;
        }
    }

    private static void initialCheck(int argsLength) {
        if (argsLength <= 4) {
            log.error("Insufficient arguments, usage : java -jar UT-CAS.jar <config file path> <command> <username> <password>");
            System.exit(-1);
        }

        if (!isSeleniumUp()) {
            log.error("Please start selenium server first.");
            System.exit(-1);
        }
    }

    private static void login(String username, String password) throws InterruptedException {
        driver.get(loginUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("loginTest"))));

        driver.findElement(By.xpath(prop.getProperty("usernameXpath"))).sendKeys(username);

        driver.findElement(By.xpath(prop.getProperty("passwordXpath"))).sendKeys(password);

        driver.findElement(By.id("login-btn")).click();
        // todo test login and remove exception
        Thread.sleep(5000);
    }

    private static void logout() {
        driver.get(logoutUrl);
    }

    public static void main(String[] args) throws InterruptedException {

        initialCheck(args.length);

        loadProperties(args[0]);

        String command = args[1];
        if ("login".equals(command)) {
            login(args[2], args[3]);
        } else if ("logout".equals(command)) {
            logout();
        } else {
            log.error("Unknown command.");
        }

        driver.close();
    }
}
