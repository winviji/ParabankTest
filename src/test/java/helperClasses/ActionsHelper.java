package helperClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


//Helper class with methods for ui interaction and utility functions
public class ActionsHelper {
	
	protected static WebDriver driver;
	
	public ActionsHelper(WebDriver driver){
		this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);

	}
	
	//Method to enter text in text field
	public void type(By by,String text) {
		waitForElement(by);
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(text);
	}

	//Method to click
	public void click(By by) {
		waitForElement(by);
		driver.findElement(by).click();
	}
	
	//Method to wait for given element
	public void waitForElement(By by) {
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		//wait.until(ExpectedConditions.(by));

	}
	
	//Method to select given value from select/dropdown field
	public void select(By by, String value) throws InterruptedException {
		waitForElement(by);
		new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElementLocated(by, value));
		WebElement we = driver.findElement(by);
		Select select = new Select(we);
		select.selectByVisibleText(value);
	}

	//Method to validated given field exists
	public boolean verifyElementExists(By by) {
		return driver.findElements(by).size()>0;
		
	}

	//Method to return text 
	public String getText(By by) {
		
		return driver.findElement(by).getText().trim();
	}

	

}
