/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.BookingSessionBeanLocal;
import ejb.session.stateless.CountrySessionBeanLocal;
import ejb.session.stateless.ServiceRateSessionBeanLocal;
import ejb.session.stateless.ServiceSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import ejb.session.stateless.TravelItinerarySessionBeanLocal;
import entity.Booking;
import entity.Business;
import entity.Country;
import entity.Customer;
import entity.Service;
import entity.ServiceRate;
import entity.Staff;
import entity.Tag;
import entity.TravelItinerary;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.ChargeType;
import util.enumeration.EmployeeRole;
import util.enumeration.RateType;
import util.enumeration.ServiceType;
import util.exception.AccountNotFoundException;
import util.exception.ConstraintViolationException;
import util.exception.CreateNewBookingException;
import util.exception.CreateNewServiceException;
import util.exception.CreateNewServiceRateException;
import util.exception.PasswordNotAcceptedException;
import util.exception.ServiceNotFoundException;
import util.exception.TagAlreadyExistException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class dataInitBean {

    @EJB
    private TravelItinerarySessionBeanLocal travelItinerarySessionBeanLocal;

    @EJB
    private BookingSessionBeanLocal bookingSessionBeanLocal;

    @EJB
    private ServiceRateSessionBeanLocal serviceRateSessionBeanLocal;

    @EJB
    private ServiceSessionBeanLocal serviceSessionBeanLocal;

    @EJB
    private CountrySessionBeanLocal countrySessionBeanLocal;

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;
    
    
    
    

    @PersistenceContext(unitName = "OptimalTravelPlan-ejbPU")
    private EntityManager em;

    public dataInitBean() {
    }

    @PostConstruct
    public void postConstruct() {
        if (em.createQuery("SELECT s FROM Staff s").getResultList().size() < 1) {
            try {
                //default manager
                Staff manager = new Staff("manager1", "password", "admin1", EmployeeRole.ADMINISTRATOR);
                em.persist(manager);

                Staff customerService = new Staff("staff123", "password", "staff1", EmployeeRole.CUSTOMER_SERVICE);
                em.persist(customerService);

                Business optimalTravelPlan = new Business("OTP themselves!", "www.OTP.com", "99999999", "OTP address", "optimal123", "password");
                em.persist(optimalTravelPlan);
                em.flush();
                
                Business business1 = new Business("company1", "www.company1.com", "0000001", "address1", "company1", "password");
                em.persist(business1);

                Business business2 = new Business("company2", "www.company2.com", "0000002", "address2", "company2", "password");
                em.persist(business2);

                Business business3 = new Business("company3", "www.company3.com", "0000003", "address3", "company3", "password");
                em.persist(business3);

                Business business4 = new Business("company4", "www.company4.com", "0000004", "address4", "company4", "password");
                em.persist(business4);

                Business business5 = new Business("company5", "www.company5.com", "0000005", "address5", "company5", "password");
                em.persist(business5);

                Customer customer1 = new Customer("customer1", "123456789", "000000001", "customer1@email.com", Boolean.TRUE, "customer1", "password");
                em.persist(customer1);

                //Create data here
                Tag familyTag = tagSessionBeanLocal.createNewTag(new Tag("family"));
                Tag natureTag = tagSessionBeanLocal.createNewTag(new Tag("nature"));
                Tag cultureTag = tagSessionBeanLocal.createNewTag(new Tag("culture"));
                Tag nightTag = tagSessionBeanLocal.createNewTag(new Tag("night"));
                Tag testTag = tagSessionBeanLocal.createNewTag(new Tag("empty test tag"));

                List<Long> tagList1 = new ArrayList<>();
                tagList1.add(familyTag.getTagId());

                List<Long> tagList2 = new ArrayList<>();
                tagList2.add(natureTag.getTagId());

                List<Long> tagList3 = new ArrayList<>();
                tagList3.add(cultureTag.getTagId());

                List<Long> tagList4 = new ArrayList<>();
                tagList4.add(nightTag.getTagId());

                List<Long> tagList5 = new ArrayList<>();
                tagList5.add(testTag.getTagId());

                Country singapore = countrySessionBeanLocal.createNewCountry(new Country("Singapore"));
                Country japan = countrySessionBeanLocal.createNewCountry(new Country("Japan"));
                Country Taiwan = countrySessionBeanLocal.createNewCountry(new Country("Taiwan"));

                Long service1 = serviceSessionBeanLocal.createNewService(
                        new Service(
                                business1,
                                singapore,
                                ServiceType.HOTEL,
                                Boolean.TRUE,
                                "address1",
                                "Melion Hotel"),
                        business1.getBusinessId(),
                        tagList1,
                        singapore.getCountryId());
                Long ServiceRate1 = serviceRateSessionBeanLocal.createNewServiceRate(new ServiceRate(new Date(2022, 02, 26), new Date(2022, 03, 26), BigDecimal.valueOf(500.00), RateType.NORMAL, Boolean.TRUE, Boolean.TRUE, ChargeType.ENTRY), service1);

                Long service2 = serviceSessionBeanLocal.createNewService(
                        new Service(
                                business2, 
                                singapore, 
                                ServiceType.FOOD_AND_BEVERAGE, 
                                Boolean.TRUE, 
                                "address2", 
                                "Encik Tan")
                        , business2.getBusinessId()
                        , tagList2
                        , singapore.getCountryId());
                Long ServiceRate2 = serviceRateSessionBeanLocal.createNewServiceRate(new ServiceRate(new Date(2022, 02, 26), new Date(2022, 03, 26), BigDecimal.valueOf(100.00), RateType.NORMAL, Boolean.TRUE, Boolean.TRUE, ChargeType.ENTRY), service2);
                em.flush();

                Long service3 = serviceSessionBeanLocal.createNewService(new Service(business3, singapore, ServiceType.ENTERTAINMENT, Boolean.TRUE, "address3", "Singapore Flyer"), business3.getBusinessId(), tagList3, singapore.getCountryId());
                Long ServiceRate3 = serviceRateSessionBeanLocal.createNewServiceRate(new ServiceRate(new Date(2022, 02, 26), new Date(2022, 03, 26), BigDecimal.valueOf(100.00), RateType.NORMAL, Boolean.TRUE, Boolean.TRUE, ChargeType.ENTRY), service3);
                em.flush();

                Long service4 = serviceSessionBeanLocal.createNewService(new Service(business4, singapore, ServiceType.ENTERTAINMENT, Boolean.TRUE, "address4", "Singapore Zoo"), business4.getBusinessId(), tagList4, singapore.getCountryId());
                Long ServiceRate4 = serviceRateSessionBeanLocal.createNewServiceRate(new ServiceRate(new Date(2022, 02, 26), new Date(2022, 03, 26), BigDecimal.valueOf(10.00), RateType.NORMAL, Boolean.TRUE, Boolean.TRUE, ChargeType.ENTRY), service4);
                em.flush();

                Long service5 = serviceSessionBeanLocal.createNewService(new Service(business5, singapore, ServiceType.FOOD_AND_BEVERAGE, Boolean.TRUE, "address5", "Gong Cha"), business5.getBusinessId(), tagList5, singapore.getCountryId());
                Long ServiceRate5 = serviceRateSessionBeanLocal.createNewServiceRate(new ServiceRate(new Date(2022, 02, 26), new Date(2022, 03, 26), BigDecimal.valueOf(100.00), RateType.NORMAL, Boolean.TRUE, Boolean.TRUE, ChargeType.ENTRY), service5);
                em.flush();
                
                Date startDate = new Date();
                Date endDate = new Date();
                endDate.setTime(endDate.getTime() + 2160000000l);
                TravelItinerary travelItinerary1 = new TravelItinerary(customer1, startDate, endDate, singapore);
                Long travel1 = travelItinerarySessionBeanLocal.createNewTravelItinerary(travelItinerary1, customer1.getAccountId(), singapore.getCountryId());
                em.flush();
                
                Booking booking1 = new Booking(startDate, endDate, null, null);
                Long book1 = bookingSessionBeanLocal.createBooking(booking1, service1, travel1);
                em.flush();
               


            } catch (CreateNewBookingException | AccountNotFoundException | TagAlreadyExistException | UnknownPersistenceException | ConstraintViolationException | CreateNewServiceException | CreateNewServiceRateException | PasswordNotAcceptedException ex) {
                System.out.println(ex.getMessage());
            }

            em.flush();
        }
    }
}
