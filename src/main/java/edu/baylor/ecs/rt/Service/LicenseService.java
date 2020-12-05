package edu.baylor.ecs.rt.Service;

import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.Sale;
import edu.baylor.ecs.rt.model.auth.User;
import edu.baylor.ecs.rt.repository.LicenseRepository;
import edu.baylor.ecs.rt.repository.SaleRepository;
import edu.baylor.ecs.rt.repository.UserRepository;
import edu.baylor.ecs.rt.security.payload.response.LicenseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

//Spring annotations, feel free to ignore it
@Repository
@Transactional
public class LicenseService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    SaleRepository saleRepository;

    public void saveLicense(License license) {
        em.persist(license);
    }

    public void updateLicense(long id, License license) {
        License lse = em.find(License.class, id);
        lse.setPrice(license.getPrice());
        //lse.setType(license.getType());
        lse.setYear(license.getYear());
        em.persist(lse);
    }

    public void deleteLicense(License license) {
        em.remove(license);
    }

    public License findById(long id) {
        return em.find(License.class, id);
    }
    public List<LicenseResponse> findByUserId(long userid) {
        User user = userRepository.findById(userid).get();

//        Query query = em.createQuery("SELECT s.license FROM Sale s WHERE s.user = ?0");
//        query.setParameter(0, user);
        Query query = em.createQuery("SELECT s FROM Sale s WHERE s.user = ?0");
        query.setParameter(0, user);
        List<LicenseResponse> licenseResponses = new ArrayList<>();
        List<Sale> sales = query.getResultList();

        for(Sale sale : sales) {
            License license = sale.getLicense();
            LicenseResponse response = new LicenseResponse();
            response.setPrice(license.getPrice());
            response.setType(license.getType());
            response.setYear(license.getYear());
            response.setExpiredDate(sale.getExpiredDate());
            response.setPurchaseDate(sale.getPurchaseDate());
            response.setSaleid(sale.getId());
            response.setActive(sale.isActive());
            licenseResponses.add(response);
        }
        return licenseResponses;
    }
    
    public List<License> findallLicenseCategories() {
    	Query query = em.createQuery("SELECT s FROM License s where s.id=1 or s.id=2  or s.id=3");
    	
    	
        List<License> licenses = query.getResultList();
        
      
        return licenses;
    }

    public List<LicenseResponse> findAll() {
    	


      Query query = em.createQuery("SELECT s FROM Sale s");
     
      List<LicenseResponse> licenseResponses = new ArrayList<>();
      List<Sale> sales = query.getResultList();
      //User user = userRepository.findById().get();

      for(Sale sale : sales) {
          License license = sale.getLicense();
          User user = sale.getUser();
          LicenseResponse response = new LicenseResponse();
          response.setPrice(license.getPrice());
          response.setType(license.getType());
          response.setYear(license.getYear());
          response.setExpiredDate(sale.getExpiredDate());
          response.setPurchaseDate(sale.getPurchaseDate());
          response.setSaleid(sale.getId());
          response.setUsername(user.getFirstName());
          licenseResponses.add(response);
      }
      return licenseResponses;
    }
}

