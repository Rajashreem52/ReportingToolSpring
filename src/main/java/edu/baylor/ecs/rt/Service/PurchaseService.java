package edu.baylor.ecs.rt.Service;

import edu.baylor.ecs.rt.model.License;
import edu.baylor.ecs.rt.model.LicenseType;
import edu.baylor.ecs.rt.model.Payment;
import edu.baylor.ecs.rt.model.Sale;
import edu.baylor.ecs.rt.model.auth.User;
import edu.baylor.ecs.rt.repository.LicenseRepository;
import edu.baylor.ecs.rt.repository.PaymentRepository;
import edu.baylor.ecs.rt.repository.SaleRepository;
import edu.baylor.ecs.rt.repository.UserRepository;
import edu.baylor.ecs.rt.security.payload.request.PurchaseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spotify.docker.client.shaded.org.apache.http.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.io.IOException;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.mail.*;
import javax.mail.internet.*;


@Repository
@Transactional
public class PurchaseService {
	@PersistenceContext
	EntityManager em;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	LicenseRepository licenseRepository;

	@Autowired
	SaleRepository saleRepository;

	public void purchase(PurchaseRequest request) {

		String userName = request.getUsername();

		Payment payment = new Payment();
		payment.setUseName(userName);

		// String licenseType = request.getTypel();
		BigInteger licenseId = request.getTypel();

		System.out.println(licenseId.longValue());
            
		Optional<License> licenses = licenseRepository.findById(licenseId.longValue());
             
		License license = licenses.get();

		System.out.println(license);
		Sale sale = new Sale();
		sale.setLicense(license);
		sale.setPrice(request.getPrice().doubleValue());
		sale.setPayment(payment);

		User user = userRepository.findByUsername(userName).get();
		System.out.println(user);

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date latestExpirationdate = (Date) GetLatestExpirationDate(user.getId());
		if(latestExpirationdate!=null)
		{
		Calendar c = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c.setTime(latestExpirationdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (license.getType() == LicenseType.MONTHLY) {
			c.add(Calendar.DAY_OF_MONTH, 30);
		}
		if (license.getType() == LicenseType.PERVISIT) {
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		if (license.getType() == LicenseType.YEARLY) {
			c.add(Calendar.DAY_OF_MONTH, 365);
		}
		sale.setExpiredDate(c.getTime());
		}
		else
		{
			latestExpirationdate = new Date();
			Calendar c = Calendar.getInstance();
			try {
				// Setting the date to the given date
				c.setTime(latestExpirationdate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (license.getType() == LicenseType.MONTHLY) {
				c.add(Calendar.DAY_OF_MONTH, 30);
			}
			if (license.getType() == LicenseType.PERVISIT) {
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			if (license.getType() == LicenseType.YEARLY) {
				c.add(Calendar.DAY_OF_MONTH, 365);
			}
			sale.setExpiredDate(c.getTime());
		}
		Date purchasedate = new Date();
		Calendar c2 = Calendar.getInstance();
		try {
			// Setting the date to the given date
			c2.setTime(purchasedate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

		sale.setPurchaseDate(c2.getTime());
		sale.setUser(user);

		saleRepository.save(sale);

		payment.setSale(sale);

		paymentRepository.save(payment);
		// em.persist(license);
		// userName = "Jingya";
		/*
		 * Optional<User> userOptional =
		 * userRepository.findByUsername(license.getUsername()); User user =
		 * userOptional.get(); Sale sale = new Sale(); sale.setUser(user);
		 * 
		 * int license_id= sale.setLicense(license_id); em.persist(sale);
		 * user.getSales().add(sale);
		 */
	}

	private Date GetLatestExpirationDate(Long id) {

		Connection c = null;

		Statement stmt = null;
		Date edate = null;

		try {

			// Query query = em.createQuery("SELECT MAX(s.expired_date) FROM Sale s WHERE
			// s.user_id=?0 GROUP BY s.user_id");
			User user = userRepository.findById(id).get();
			Query query = em.createQuery("SELECT MAX(s.expiredDate) FROM Sale s WHERE s.user = ?0 GROUP BY s.user");
			query.setParameter(0, user);
			List<Date> list = (List<Date>) query.getResultList();

			edate = list.get(0);

			return edate;

		} catch (Exception e) {

			// System.exit(0);

		}
		return edate;

	}
	public void sendMail(Sale sale) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		long id = sale.getId();
		//long id=1;
		User user = sale.getUser();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("reportingtoolsystem@gmail.com", "rt2020fall");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("reportingtoolsystem@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		msg.setSubject("Reporting Tool: License Purchase Confirmation");
		//String content = "Dear Customer, Thanks for your purchase. Your order is submitted.";
		//msg.setContent(content, "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		String body ="Dear "+ user.getFirstName() +" "+ user.getLastName()+",<br />" + "<p>Thanks for your purchase.\n Your order #"+ sale.getId().toString()+"  is submitted successfully.<br /></p>"
				+"<p> Order date: "+ sale.getPurchaseDate().toString()+"<br />"
				+"License Type: "+ sale.getLicense().getType().toString()+"<br />"
				+"Price: "+ sale.getLicense().getPrice()+"<br />"
				+"Expired at: "+ sale.getExpiredDate().toString()+"<br /></p>"
				+"<p>Sincerely,<br /><br /> Reporting Tool Team<br /></p>";
		messageBodyPart.setContent(body, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		//MimeBodyPart attachPart = new MimeBodyPart();

		//attachPart.attachFile("/var/tmp/image19.png");
		//multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
