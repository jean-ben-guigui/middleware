package enterprise.service;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import model.BankCustomer;

@Stateless
@WebService
public class BankService {
	@EJB(beanName = "BK")
	private StatelessLocal metier;

	@WebMethod
	public String getUserInfo(@WebParam(name = "username") String user) {

		return metier.getUser(user).getPassword();
	}

	@WebMethod
	public String transferMoney(@WebParam(name = "transferAmount") Double transferAmount,
			@WebParam(name = "fromAccount") String fromAccount, @WebParam(name = "toAccount") String toAccount)
			throws Exception {

		BigDecimal amount = new BigDecimal(transferAmount);
		try {
			return metier.transferFunds(fromAccount, toAccount, amount);
		} catch (Exception e) {
			return e.getMessage();
		}

	}
	
	@WebMethod
	public String Inscription(
			@WebParam(name = "name") String name ,
			@WebParam(name = "password") String password,
			@WebParam(name ="email") String email) throws Exception {

		metier.signUp(name, password, email);
		return "ok";
	}
	
	@WebMethod
	public String CreateEvent(
			@WebParam(name = "nameArtist") String nameArtist ,
			@WebParam(name = "date") String date,
			@WebParam(name ="category") String category) throws Exception {

		metier.createEvent(nameArtist,date, category);
		return "ok";
	}

	@WebMethod
	public String Reserver(
			@WebParam(name = "idEvent") long idEvent ,
			@WebParam(name = "idUser") long idUser,
			@WebParam(name ="siege") String siege) throws Exception {

		metier.reserverPlace(idEvent,idUser, siege);
		return "ok";
	}
}
