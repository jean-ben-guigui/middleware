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

	
	public boolean signUp(String name, String password, String email) throws Exception;
	
	public void createEvent(String nameArtist, String date, String category) throws Exception;
	
	public long reserverPlace(long idEvent, long idUser, long siege,String cat) throws Exception;
	
	public List<Event> getEvents();
	
	public HashMap<Character,Integer> getCategories(int idEvent);
	
	public List<Integer> getSiege(int idEvent, char category);
	
	public long logIn(String email, String password);

	public boolean payer(int idReservation);
	
	public String seeEarning(int idAdmin);
	
	public String seeBookedPlace(int idAdmin, int idEvent);
}
