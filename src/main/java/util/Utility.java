package util;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class Utility
{
    public static WebElement selectRandomElement(List<WebElement> elements) {

        Random random = new Random();
        int index = random.nextInt(elements.size());
        System.out.println("the index is " + index + " the size is " + elements.size());
        return elements.get(index);
    }
}
