package crawler;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Crawler {

    private static String ACCESS_TOKEN = "VIKg0BHfxGAAAAAAAAAA_gtaWTEUhgR8MFw8T-BB20CSBLGw1xeJxEH-eg9NS-b7";


    private static String url = "http://www.amazon.com/";
    //amazon.com will be loaded in google chrome

    static void uploadFile(String path, String content) throws DbxException, IOException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/crawler-test").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        InputStream stream = new ByteArrayInputStream(content.getBytes("UTF-8"));

        FileMetadata metadata = client.files().uploadBuilder(path)
                .uploadAndFinish(stream);
    }

    public static void main(String[] args) throws DbxException, IOException {

        WebDriver driver = new ChromeDriver();

        driver.get(url);

        WebElement element = driver.findElement(By.id("nav-flyout-shopAll"));
        List<WebElement> linkElements = element.findElement(By.className("nav-tpl-itemList")).findElements(By.className("nav-link"));


        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        String fileName = "/results/" + timeInMillis + "_results.txt";

        List<String> departmentLinks = new ArrayList();
        for (WebElement linkElement : linkElements) {
            String departmentLink = linkElement.getAttribute("href");
            departmentLinks.add(departmentLink);
        }

        StringBuilder contentStringBuilder = new StringBuilder();
        for (String departmentLink : departmentLinks) {

            contentStringBuilder.append(departmentLink);
            contentStringBuilder.append(", ");

            try {

                driver.get(departmentLink);
                contentStringBuilder.append(driver.getTitle());
                contentStringBuilder.append(", ");

                HttpResponse<String> stringHttpResponse = Unirest.get(departmentLink).asString();

                int respCode = stringHttpResponse.getStatus();
                if (respCode >= 400) {
                    contentStringBuilder.append("Dead link");
                } else {
                    contentStringBuilder.append("OK");
                }

                contentStringBuilder.append("\n");

            } catch (UnirestException e) {
                e.printStackTrace();
            }

        }

        System.out.println(contentStringBuilder);

        uploadFile(fileName, contentStringBuilder.toString());
        driver.close();
    }

}




