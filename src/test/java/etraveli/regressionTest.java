package etraveli;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import etraveli.page.firstPage;
import etraveli.section.filterAirline;
import etraveli.section.filterPrice;
import etraveli.section.filterScales;
import etraveli.section.timeSection;
import etraveli.section.traverlTime;



public class regressionTest {

    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;
    static Page page;
    static firstPage firstPage;

    @BeforeAll
    static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();
        firstPage = new firstPage(page);
    }
     
    @Test
    void verifyApi() {
        System.out.println("----En este test validaremos que la api nos responda 200----");

        firstPage.firstSearch();
        api apiClass = new api(page);
      apiClass.apitest();        
    }

     @Test
    void maximumFilterOneScale() {
        System.out.println("----En este test validaremos el filtro de maximo una escala----");

        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.MaxOneScale();
        
    }
        
    @Test
    void FilterDirectFlight() {
        System.out.println("----En este test validaremos el filtro de vuelos directos----");
        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.DirectFight();     
    }
    @Test
    void AllFlight() {
        System.out.println("----En este test validaremos el filtro Cantidad de escalas todos los vuelos----");
        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.AllFight();     
    }
    
  @Test
  void ResetFilter() {
      System.out.println("----En este test validaremos el restablecimiento del filtro----");
      firstPage.firstSearch();
      filterScales filterScalesPage = new filterScales(page);
      filterScalesPage.resetFilter();     
  }
       
  @Test
  void SelfTransferFilter() {
    // Yo entiendo que "Mostrar vuelos sin Autotransferencia" es lo mismo que vuelos directos osea sin escalas
    // Este caso falla porque el boton no hace este filtro, quizas se espera otra cosa de este botòn y yo necesite mas informacion. 
      System.out.println("----En este test validaremos el filtro de vuelos con auto-transferencia ----");
      firstPage.firstSearch();
      filterScales filterScalesPage = new filterScales(page);
      filterScalesPage.SelfTransferFight();     
  }
       
  @Test
  void changeFilterDownPrice() {
          System.out.println("----En este test validaremos el filtro de precio----");
      firstPage.firstSearch();
      filterPrice filterPricePage = new filterPrice(page);
      filterPricePage.ChangeDownPrice();  
  }
 
  @Test
  void filterWithoutAirline() {
    System.out.println("----En este test validaremos que no se muestre la aerolinea que no fue seleccionada----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterWithoutThisAirline();
  }
  @Test
  void filterByAirline() {
    System.out.println("----En este test validaremos que muestra solo la aerolinea seleccionada----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterByAirline();
  }

  @Test
  void filterByAirlineShowMore() {
    System.out.println("----En este test validaremos el mostrar mas ----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterByAirlineShowMore();
  }
  
@Test
void DepartureSection() {
    System.out.println("----En este test validaremos cambiar de hora del tramo de salida----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
     timeSection.changeDepartureSection();
    
}

@Test
void ArribalSection() {
    System.out.println("----En este test validaremos cambiar de hora del tramo de arribo----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
    timeSection.changeArrivalSection();
    
}

    @Test
void resetValueDepartureSection() {
    System.out.println("----En este test validaremos que los valores sean reseteados correctamente----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
    timeSection.resetValueDepartureSection();
    
}

@Test
void changeTravelTime() {
    System.out.println("----En este test validaremos el cambio de hora total del viaje----");
    firstPage.firstSearch();
    traverlTime traverlTime = new traverlTime(page);
    traverlTime.ChangeTime();
    
}

    
     @AfterAll
    static void teardown() {
            page.waitForTimeout(20000);
             
        context.close();
        browser.close();
        playwright.close();
    }
       
}
