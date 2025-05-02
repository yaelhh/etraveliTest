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
        System.out.println("----In this test, we will validate that the API responds with a 200 status code.----");

        firstPage.firstSearch();
        api apiClass = new api(page);
      apiClass.apitest();        
    }

     @Test
    void maximumFilterOneScale() {
        System.out.println("----In this test, we will validate the filter for a maximum of one stopover.----");

        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.MaxOneScale();
        
    }
        
    @Test
    void FilterDirectFlight() {
        System.out.println("----In this test, we will validate the non-stop flights filter.----");
        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.DirectFight();     
    }
    @Test
    void AllFlight() {
        System.out.println("----In this test, we will validate the stopovers filter with the 'All flights' option selected.----");
        firstPage.firstSearch();
        filterScales filterScalesPage = new filterScales(page);
        filterScalesPage.AllFight();     
    }
    
  @Test
  void ResetFilter() {
      System.out.println("----In this test, we will validate the reset functionality of the filter.----");
      firstPage.firstSearch();
      filterScales filterScalesPage = new filterScales(page);
      filterScalesPage.resetFilter();     
  }
       
  @Test
  void SelfTransferFilter() {
    // Yo entiendo que "Mostrar vuelos sin Autotransferencia" es lo mismo que vuelos directos osea sin escalas
    // Este caso falla porque el boton no hace este filtro, quizas se espera otra cosa de este botòn y yo necesite mas informacion. 
      System.out.println("----In this test, we will validate the filter for flights with self-transfer. ----");
      firstPage.firstSearch();
      filterScales filterScalesPage = new filterScales(page);
      filterScalesPage.SelfTransferFight();     
  }
       
  @Test
  void changeFilterDownPrice() {
          System.out.println("----In this test, we will validate the price filter.----");
      firstPage.firstSearch();
      filterPrice filterPricePage = new filterPrice(page);
      filterPricePage.ChangeDownPrice();  
  }
 
  @Test
  void filterWithoutAirline() {
    System.out.println("----In this test, we will validate that unselected airlines are not shown.----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterWithoutThisAirline();
  }
  @Test
  void filterByAirline() {
    System.out.println("----In this test, we will validate that only the selected airline is displayed.----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterByAirline();
  }

  @Test
  void filterByAirlineShowMore() {
    System.out.println("----In this test, we will validate the show more functionality.----");
firstPage.firstSearch();
filterAirline filterAirlinePage = new filterAirline(page);
filterAirlinePage.filterByAirlineShowMore();
  }
  
@Test
void DepartureSection() {
    System.out.println("----In this test, we will validate changing the departure time of the segment----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
     timeSection.changeDepartureSection();
    
}

@Test
void ArribalSection() {
    System.out.println("----In this test, we will validate changing the arrival time of the segment----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
    timeSection.changeArrivalSection();
    
}

    @Test
void resetValueDepartureSection() {
    System.out.println("----In this test, we will validate that the values are reset correctly----");
    firstPage.firstSearch();
    timeSection timeSection = new timeSection(page);
    timeSection.resetValueDepartureSection();
    
}

@Test
void changeTravelTime() {
    System.out.println("----In this test, we will validate changing the total travel time----");
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
