import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class RcDemo {
    private static WebDriver driver;

    public static void main(String[] args) {

        try {


            driver = new FirefoxDriver();
            //comment the above line and uncomment below line to not staring browser windows
            //WebDriver driver = new HtmlUnitDriver();

//            String baseUrl = "https://internet1.ut.ac.ir/login/?username=&link-login=https%3A%2F%2Finternet.ut.ac.ir%2Flogin&link-orig=&error=&link-logout=http%3A%2F%2Finternet.ut.ac.ir%2Flogout";
            String baseUrl = "https://internet1.ut.ac.ir/login/?username=&link-login=https%3A%2F%2Finternet.ut.ac.ir%2Flogin&link-orig=&error=&link-logout=http%3A%2F%2Finternet.ut.ac.ir%2Flogout";
            String expectedTitle = "صفحه\u200Cی ورود به اینترنت";
            String actualTitle = "";

            // launch Fire fox and direct it to the Base URL
            driver.get(baseUrl);
            driver.manage().window().fullscreen();
            Thread.sleep(5000);

            // get the actual value of the title
            actualTitle = driver.getTitle();

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
            driver.close();
        }
    }
}
