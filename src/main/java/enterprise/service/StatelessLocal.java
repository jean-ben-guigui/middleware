package enterprise.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import model.Event;
import model.User;

@Remote
public interface StatelessLocal {

	public User getUser(String user);

	public String transferFunds( String fromAccountNo, String toAccountNo,
			BigDecimal amount) throws Exception;
	
	public void signUp(String name, String password, String email) throws Exception;
	
	public void createEvent(String nameArtist, String date, String category) throws Exception;
	
	public boolean reserverPlace(long idEvent, long idUser, long siege,String cat) throws Exception;
	
	public List<Event> getEvents();
	
	public HashMap<Character,Integer> getCategories(int idEvent);
	
	public List<Integer> getSiege(int idEvent, char category);
	
	public boolean logIn(String email, String password);

	public boolean payer(int idReservation);
	
	public String seeEarning(int idAdmin);
	
	public String seeBookedPlace(int idAdmin, int idEvent);
}
