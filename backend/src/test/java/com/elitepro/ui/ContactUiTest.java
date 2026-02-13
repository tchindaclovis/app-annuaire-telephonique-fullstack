package com.elitepro.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ContactUiTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll /*s'exécute une seule fois avant tous les test de la classe*/
    static void setupClass(){
        WebDriverManager.chromedriver().setup(); /*on utilise WebDriverManager pour
        télécharger et configurer automatiquement la bonne version de chromedriver*/
    }

    @BeforeEach /*s'exécute avant chaque test*/
    void setup(){
        driver = new ChromeDriver(); /*on cré une instance de ChromeDriver qui est le navigateur piloté par selenium*/
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10)); /*on cré aussi un WebDriverWait avec
        un timeout de 5 secondes ; cela va permettre d'attendre que les éléments apparaissent*/
    }

    @AfterEach /*s'exécute après chaque test*/
    void teardown(){ /*on va fermer proprement le navigateur avec la methode quit de l'objet driver ;
    ça va éviter d'avoir plusieurs fenêtres chromes qui restent ouvertes en mémoire*/
        if (driver != null)
            driver.quit();
    }

    @Test
    void shouldCreateAndDeleteContactFromUI(){
        driver.get("http://localhost:5173/");
        /*pour remplir le formulaire, on va utiliser la méthode typeByPlaceholder
        cette methode va permettre de trouver un champ, effacer ce qu'il y a dedans
        et taper une chaine de caractère précise. C'est en ce moment du test que le
        formulaire va se remplir tout seul très rapidement*/
        typeByPlaceholder("Nom *", "TestNom");
        typeByPlaceholder("Prénom *", "TestPrenom");
        typeByPlaceholder("Téléphone *", "0600000000");
        typeByPlaceholder("Email", "test@demo.com");
        typeByPlaceholder("Intitulé", "Dev");
        typeByPlaceholder("Direction", "DSI");
        typeByPlaceholder("Bureau", "A01");

        driver.findElement(By.xpath("//button[normalize-space()='Créer']")).click(); /*on simule un clic
        sur le boutton "créer"*/

        typeByPlaceholder("Rechercher par nom…", "TestNom"); /*dans l'interface utilisateur, le
        dernier contact ajouté n'apparait pas forcément en haut de la liste tant que celle-ci n'est pas filtrée
         donc pour être sûr de le trouver, on va utiliser le champ "Recherche par nom*/

        /*ici on veut vérifier que le contact qu'on vient de créer apparait bien à l'écran ; on cherche donc
        n'importe quel élément qui contient TestNom et TestPrenom*/
        WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated( // [29]
                /*après la création du contact, il y a étape de recherche de ce contact*/
                By.xpath("//*[contains(., 'TestNom') and contains(., 'TestPrenom')]")
        ));
        assertThat(row).isNotNull();

        WebElement deleteBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[normalize-space()='Supprimer']")
                )
        );

        deleteBtn.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(  //[33]
                By.xpath("//*[contains(text(), 'TestNom') and contains(text(), 'TestPrenom')]")
        ));
    }


    private void typeByPlaceholder(String placeholder, String value){
        WebElement input = wait.until(ExpectedConditions.
                presenceOfElementLocated(
                        By.xpath("//input[contains(@placeholder,'" + placeholder + "')]")
                )
        );
        input.clear();
        input.sendKeys(value);
    }

}
