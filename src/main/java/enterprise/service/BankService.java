package enterprise.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import model.Event;

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
			@WebParam(name ="siege") long siege,
			@WebParam(name ="category") String cat) throws Exception {

		if(metier.reserverPlace(idEvent, idUser, siege,cat)){
		return "ok";
		}else{
			return "not ok";
		}
	}
	
	@WebMethod
	public String getEventsWeb(){
		
		List<Event> events =  metier.getEvents();
		String res = "";
		for(Event e: events){
			res+=e.toString()+"\n";
		}

		return res;
		
	}
	
	
	@WebMethod
	public String getCategories(int idEvent){
		return metier.getCategories(idEvent).toString();
	}
	
	
	@WebMethod
	public String getSiege(int idEvent, String category){
		List<Integer> l = metier.getSiege(idEvent, category.toCharArray()[0]);
		String s = "";
		for(int i =0; i < l.size();i++){
			s += l.get(i) +" ";
		}
		return s;
	}
	
	@WebMethod
	public String logIn(String email, String password){
		if(metier.logIn(email,password)){
			return "ok";
		}
		return "not ok";
	}
	
	public List<Event> getEvents(){
		
		return metier.getEvents();
	}
	
	@WebMethod
	public String payer(int idReservation){
	if(metier.payer(idReservation)){
		return "true";
	}else{
		return "false";
	}
	}
	
	@WebMethod
	public String seeEarning(int idAdmin){
		return metier.seeEarning(idAdmin);
	}
	
	
}
