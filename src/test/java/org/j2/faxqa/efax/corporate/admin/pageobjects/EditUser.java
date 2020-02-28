package org.j2.faxqa.efax.corporate.admin.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.j2.faxqa.efax.common.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditUser extends NavigationBar {

	private WebDriver driver;
	private Logger logger;
	WebDriverWait wait;

	public EditUser() {
		this.driver = TLDriverFactory.getTLDriver();
		this.logger = LogManager.getLogger();
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 30);
		logger.info(driver.getTitle() + " - [" + driver.getCurrentUrl() + "]");
	}

	static final String password = "newPin";
	static final String confirmPassword = "confirmNewPin";
	static final String updateUserButton = "//*[@id=\"settingsPanel\"]/div[2]/button";
	static final String passwordValidation = "//div[@class='field']//span[@class='warning'][contains(text(),'password must not include any spaces')]";
	static final String messageSuccess = "//span[@id='successStatusMsg']";
	static final String deleteUser = "//div[@class='btn-text'][contains(text(),'Delete')]";
	static final String removeButton = "//button[@id='modalDeleteUserBtn']";
	static final String userFirstNameForm = "//label[@id='userfirstname']";
	static final String advancedSettingsButton = "//div[contains(@class,'advanced-settings-button')]";
	static final String inputHiddenStorageEnabled = "//input[@id='userEditProfile']";
	static final String inputHiddenEditProfile = "//input[@id='nonSecureStorageEnabled']";
	static final String preferencesTab = "//a[@class='faxOptionsPanel']";
	static final String storageDurationDropdown = "//span[@id='storageDurationType-button']//span[@class='ui-icon icon_ChevronDown']";
	static final String lifetimeStorageOption = "//li[contains(text(),'Lifetime')]";
	static final String customStorageOption = "//li[contains(text(),'Custom')]";
	static final String storageDurationDays = "//input[@id='storageDurationDays']";
	static final String userMayEditProfileTrigger = "//input[contains(@id,'userEditProfile')]";
	static final String inputUserEditProfile = "//span[contains(text(),'User May Edit Profile')]/parent::label/parent::div//div[@class='onoffswitch']/label";
	static final String updateUserButtonPrefTab = "//div[@id='faxOptionsPanel']//button[@class='create-user-btn']";
	static final String paginationForm = "//div[@class='users-search-section-line-2']//div[@class='print-and-pagination users-top-pagination']//div[@class='users-pagination-holder']//form";
	static final String userAdvanceSettingsPreferenceTab = "//a[@class='faxOptionsPanel']";
	static final String drpEmailDomain = "//span[@id='newSendEmailDomain-button']/span[@class='ui-selectmenu-text']";
	static final String largeFileTransferToggleButtonStatusSettingsTab = "//label[contains(text(),'Large File Transfer')]/parent::div//div[@class='onoffswitch']/input[@id='largeFileTransfer']";
	static final String largeFileTransferToggleButtonSettingsTab = "//label[contains(text(),'Large File Transfer')]/parent::div//div[@class='onoffswitch']";
	static final String receiveFaxFormatToggleBtnStatusPrefTab = "//input[@id='userEditInboxEmailFormat']";
	static final String receiveFaxFormatToggleBtnPrefTab = "//span[contains(text(),'User May Edit Received Fax Email Format')]/parent::label/parent::div//div[@class='onoffswitch']";
	static final String storageEnabledInputSettingsTab = "//input[@id='nonSecureStorageEnabled']";
	static final String storageEnabledToggleBtnSettingTab = "//label[contains(text(),'Storage Enabled')]/parent::div//div[@class='onoffswitch']";
	static final String userEnaDisableStorageInputSettingsTab = "//input[@id='editNonSecureStorage']";
	static final String userEnaDisableStorageToggleBtnSettingsTab = "//label[contains(text(),'User May Enable/Disable Storage')]/parent::div//div[@class='onoffswitch']";
	static final String updateUserButtonSettingsTab = "//div[@id='create-new-user-section']//div[@id='settingsPanel']//button[contains(.,'Update User')]";
	static final String mustResetPassword = "//label[@class='onoffswitch-label' and @for='mustResetPassword']/span[2]";
	static final String advancedOptions = "//div[@class='advanced-settings-button']";

	@FindBy(id = password)
	private WebElement passwordWebElement;

	@FindBy(id = confirmPassword)
	private WebElement confirmPasswordWebElement;

	@FindBy(xpath = updateUserButton)
	private WebElement updateUserButtonWebElement;

	@FindBy(xpath = passwordValidation)
	private WebElement passwordValidationWebElement;

	@FindBy(xpath = messageSuccess)
	private WebElement updateMessageSuccessWebElement;

	@FindBy(xpath = deleteUser)
	private WebElement deleteUserWebElement;

	@FindBy(xpath = removeButton)
	private WebElement removeButtonWebElement;

	@FindBy(xpath = advancedSettingsButton)
	private WebElement advancedSettingsButtonWebElement;

	@FindBy(xpath = inputHiddenStorageEnabled)
	private WebElement inputHiddenStorageEnabledWebElement;

	@FindBy(xpath = inputHiddenEditProfile)
	private WebElement inputHiddenEditProfileWebElement;

	@FindBy(xpath = preferencesTab)
	private WebElement preferencesTabWebElement;

	@FindBy(xpath = storageDurationDropdown)
	private WebElement storageDurationDropdownWebElement;

	@FindBy(xpath = lifetimeStorageOption)
	private WebElement lifetimeStorageOptionWebElement;

	@FindBy(xpath = customStorageOption)
	private WebElement customStorageOptionWebElement;

	@FindBy(xpath = storageDurationDays)
	private WebElement storageDurationDaysWebElement;

	@FindBy(xpath = userMayEditProfileTrigger)
	private WebElement userMayEditProfileTriggerWebElement;

	@FindBy(xpath = inputUserEditProfile)
	private WebElement inputUserEditProfileWebElement;

	@FindBy(xpath = updateUserButtonPrefTab)
	private WebElement updateUserButtonPrefTabWebElement;

	@FindBy(xpath = paginationForm)
	private WebElement paginationFormWebElement;

	@FindBy(xpath = userAdvanceSettingsPreferenceTab)
	private WebElement userAdvanceSettingsPreferenceTabWebElement;

	@FindBy(xpath = drpEmailDomain)
	private WebElement drpEmailDomainWebElement;

	@FindBy(xpath = largeFileTransferToggleButtonStatusSettingsTab)
	private WebElement largeFileTransferToggleButtonStatusSettingsTabWebElement;

	@FindBy(xpath = largeFileTransferToggleButtonSettingsTab)
	private WebElement largeFileTransferToggleButtonSettingsTabWebElement;

	@FindBy(xpath = receiveFaxFormatToggleBtnStatusPrefTab)
	private WebElement receiveFaxFormatToggleBtnStatusPrefTabWebElement;

	@FindBy(xpath = receiveFaxFormatToggleBtnPrefTab)
	private WebElement receiveFaxFormatToggleBtnPrefTabWebElement;

	@FindBy(xpath = storageEnabledInputSettingsTab)
	private WebElement storageEnabledInputSettingsTabWebElement;

	@FindBy(xpath = storageEnabledToggleBtnSettingTab)
	private WebElement storageEnabledToggleBtnSettingTabWebElement;

	@FindBy(xpath = userEnaDisableStorageInputSettingsTab)
	private WebElement userEnaDisableStorageInputSettingsTabWebElement;

	@FindBy(xpath = userEnaDisableStorageToggleBtnSettingsTab)
	private WebElement userEnaDisableStorageToggleBtnSettingsTabWebElement;

	@FindBy(xpath = updateUserButtonSettingsTab)
	private WebElement updateUserButtonSettingsTabWebElement;

	@FindBy(xpath = userFirstNameForm)
	private WebElement userFirstNameFormWebElement;

	@FindBy(xpath = mustResetPassword)
	private WebElement mustResetPasswordWebElement;

	@FindBy(xpath = advancedOptions)
	private WebElement advancedOptionsWebElement;

	public void userMustResetPasswordToggle(boolean flag) {
		wait.until(ExpectedConditions.elementToBeClickable(mustResetPasswordWebElement));
		this.scrollToTheSpecificWebelement(mustResetPasswordWebElement);
		if (!TLDriverFactory.getTLDriver().findElement(By.id("mustResetPassword")).isSelected() && flag)
			mustResetPasswordWebElement.click();
		else if (TLDriverFactory.getTLDriver().findElement(By.id("mustResetPassword")).isSelected() && !flag)
			mustResetPasswordWebElement.click();
	}

	public void clickAdvancedOptions() {
		advancedOptionsWebElement.click();
	}

	public void clickStorageDurationDropdown() {
		storageDurationDropdownWebElement.click();
	}

	public void enterPassword(String setPassword) {
		passwordWebElement.clear();
		passwordWebElement.sendKeys(setPassword);
	}

	public void enterConfirmPassword(String setConfirmPassword) {
		confirmPasswordWebElement.clear();
		confirmPasswordWebElement.sendKeys(setConfirmPassword);
	}

	public void clickUpdateUserButton() {
		wait.until(ExpectedConditions.elementToBeClickable(updateUserButtonWebElement));
		updateUserButtonWebElement.click();
	}

	public String getPasswordValidationMessage() {
		wait.until(ExpectedConditions.elementToBeClickable(passwordValidationWebElement));
		return passwordValidationWebElement.getText();
	}

	public String getSuccessMessage() {
		wait.until(ExpectedConditions.elementToBeClickable(updateMessageSuccessWebElement));
		return updateMessageSuccessWebElement.getText();
	}

	public void clickDeleteUser() {

		deleteUserWebElement.click();
	}

	public void clickRemoveButton() {

		removeButtonWebElement.click();
	}

	public void clickAdvancedSettingsButton() {
		this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);
		advancedSettingsButtonWebElement.click();
	}

	public String attributeValueOfStorageEnabledField(String attribute) {
		String attributeValue = inputHiddenStorageEnabledWebElement.getAttribute(attribute);
		return attributeValue;
	}

	public String attributeValueOfEditProfileField(String attribute) {
		String attributeValue = inputHiddenEditProfileWebElement.getAttribute(attribute);
		return attributeValue;
	}

	public void clickPreferencesTab() {
		this.scrollToTheSpecificWebelement(preferencesTabWebElement);
		preferencesTabWebElement.click();
	}

	public void setLifetimeStorageDuration() {
		clickStorageDurationDropdown();
		lifetimeStorageOptionWebElement.click();
	}

	public void setCustomStorageDuration(String numberOfDay) throws Exception {
		clickStorageDurationDropdown();
		customStorageOptionWebElement.click();
		storageDurationDaysWebElement.click();
		storageDurationDaysWebElement.clear();
		storageDurationDaysWebElement.sendKeys(numberOfDay);
	}

	public void changeUserSettingsEditProfileToggleButtonSwitch(String pagination_result, boolean checked) {
		int time = 3000;
		String[] pagination_split = pagination_result.split(" ");
		if (Integer.parseInt(pagination_split[2]) == 1) {
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();
			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);
			TLDriverFactory.getTLDriver().manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			if (userMayEditProfileTriggerWebElement.getAttribute("checked") == null && checked == true) {
				this.scrollToTheSpecificWebelement(inputUserEditProfileWebElement);
				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				inputUserEditProfileWebElement.click();
			} else if (userMayEditProfileTriggerWebElement.getAttribute("checked").equals("true") && checked == false) {
				this.scrollToTheSpecificWebelement(inputUserEditProfileWebElement);
				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				inputUserEditProfileWebElement.click();
			}

			updateUserButtonSettingsTabWebElement.click();
		}

	}

	public String getPaginationNumber() {
		String paginationNum = paginationFormWebElement.getText();
		return paginationNum;
	}

	public String getSelectedEmailDomain() {
		String emailDomain = drpEmailDomainWebElement.getText().toString().trim();
		return emailDomain;
	}

	public String getUserEmailDomainFromAdmin(String pagination_result) {
		String[] pagination_split = pagination_result.split(" ");
		String selectedEmailDomainValue = "";
		int time = 3000;

		if (Integer.parseInt(pagination_split[2]) == 1) {
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();

			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			userAdvanceSettingsPreferenceTabWebElement.click();
			this.scrollToTheSpecificWebelement(drpEmailDomainWebElement);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			selectedEmailDomainValue = getSelectedEmailDomain();
		}
		return selectedEmailDomainValue;
	}

	public void updateLargeFileTransferToggleBtn(String pagination_result, Integer Count) {
		String[] pagination_split = pagination_result.split(" ");
		int time = 3000;

		if (Integer.parseInt(pagination_split[2]) == 1) {
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();

			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();

			this.scrollToTheSpecificWebelement(largeFileTransferToggleButtonStatusSettingsTabWebElement);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			String statusToggleButton = largeFileTransferToggleButtonStatusSettingsTabWebElement.getAttribute("cheked");

			try {
				if (statusToggleButton != null && statusToggleButton.equals("true") && Count == 2) {
					largeFileTransferToggleButtonSettingsTabWebElement.click();
				} else if (statusToggleButton == null && Count == 1) {
					largeFileTransferToggleButtonSettingsTabWebElement.click();
				}
			} catch (Exception e) {

				System.out.println("Large File Transfer toggle button is ON");
			} finally {
				this.scrollToTheSpecificWebelement(updateUserButtonSettingsTabWebElement);
				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				updateUserButtonSettingsTabWebElement.click();
			}
		}
	}

	public void changeEditReceivedFaxEmailFormat(String pagination_result, boolean checked) {
		String[] pagination_split = pagination_result.split(" ");
		int time = 3000;

		if (Integer.parseInt(pagination_split[2]) == 1) {

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			userAdvanceSettingsPreferenceTabWebElement.click();

			this.scrollToTheSpecificWebelement(receiveFaxFormatToggleBtnPrefTabWebElement);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			System.out.println("Checked status" + receiveFaxFormatToggleBtnPrefTabWebElement.getAttribute("checked"));

			try {

				if (receiveFaxFormatToggleBtnStatusPrefTabWebElement.getAttribute("checked") == null
						&& checked == true) {

					receiveFaxFormatToggleBtnPrefTabWebElement.click();

				} else if (receiveFaxFormatToggleBtnStatusPrefTabWebElement.getAttribute("checked").equals("true")
						&& checked == false) {

					receiveFaxFormatToggleBtnStatusPrefTabWebElement.click();
				}
			} catch (Exception e) {
				System.out.println("Toggle button is alreay in OFF mode");

			} finally {
				this.scrollToTheSpecificWebelement(updateUserButtonPrefTabWebElement);

				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				updateUserButtonPrefTabWebElement.click();

				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				this.scrollToTheSpecificWebelement(updateMessageSuccessWebElement);

			}
		}
	}

	public void changeStorageEnabledAndUserMayEnableDisableToggleSettings(String pagination_result, Integer count) {
		String[] pagination_split = pagination_result.split(" ");
		int time = 3000;

		if (Integer.parseInt(pagination_split[2]) == 1) {

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();

			this.scrollToTheSpecificWebelement(userEnaDisableStorageInputSettingsTabWebElement);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			try {

				if (count == 2) {
					if (storageEnabledInputSettingsTabWebElement.getAttribute("checked") != null) {
						storageEnabledToggleBtnSettingTabWebElement.click();
					}
					if (userEnaDisableStorageInputSettingsTabWebElement.getAttribute("checked") != null) {
						userEnaDisableStorageToggleBtnSettingsTabWebElement.click();
					}
				} else if (count == 3) {
					if (storageEnabledInputSettingsTabWebElement.getAttribute("checked") == null) {
						storageEnabledToggleBtnSettingTabWebElement.click();
					}
					if (userEnaDisableStorageInputSettingsTabWebElement.getAttribute("checked") != null) {
						userEnaDisableStorageToggleBtnSettingsTabWebElement.click();
					}
				} else if (count == 1) {
					if (storageEnabledInputSettingsTabWebElement.getAttribute("checked") == null) {
						storageEnabledToggleBtnSettingTabWebElement.click();
					}
					if (userEnaDisableStorageInputSettingsTabWebElement.getAttribute("checked") == null) {
						userEnaDisableStorageToggleBtnSettingsTabWebElement.click();
					}
				}
			} catch (Exception e) {
				System.out.println("Toggle buttons is alreay in OFF mode");

			} finally {
				this.scrollToTheSpecificWebelement(updateUserButtonSettingsTabWebElement);

				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				updateUserButtonSettingsTabWebElement.click();

				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				this.scrollToTheSpecificWebelement(updateMessageSuccessWebElement);

			}
		}
	}

	public void changeStorageEnabledSettings(String pagination_result, boolean checked) {
		String[] pagination_split = pagination_result.split(" ");
		int time = 3000;

		if (Integer.parseInt(pagination_split[2]) == 1) {

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			userFirstNameFormWebElement.click();
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			this.scrollToTheSpecificWebelement(advancedSettingsButtonWebElement);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
			advancedSettingsButtonWebElement.click();

			this.scrollToTheSpecificWebelement(userEnaDisableStorageInputSettingsTabWebElement);

			driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);

			try {

				if (storageEnabledInputSettingsTabWebElement.getAttribute("checked") != null && checked == false) {
					storageEnabledToggleBtnSettingTabWebElement.click();
				} else if (storageEnabledInputSettingsTabWebElement.getAttribute("checked") == null
						&& checked == true) {
					storageEnabledToggleBtnSettingTabWebElement.click();
				}
			} catch (Exception e) {
			} finally {
				this.scrollToTheSpecificWebelement(updateUserButtonSettingsTabWebElement);
				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				updateUserButtonSettingsTabWebElement.click();

				driver.manage().timeouts().implicitlyWait(time, TimeUnit.MILLISECONDS);
				this.scrollToTheSpecificWebelement(updateMessageSuccessWebElement);
			}
		}

	}

	public boolean isStorageEnabled() {
		this.scrollToTheSpecificWebelement(inputHiddenStorageEnabledWebElement);
		return inputHiddenStorageEnabledWebElement.isSelected();
	}

	public boolean isEditProfileEnabled() {
		this.scrollToTheSpecificWebelement(inputHiddenEditProfileWebElement);
		return inputHiddenEditProfileWebElement.isSelected();
	}

}