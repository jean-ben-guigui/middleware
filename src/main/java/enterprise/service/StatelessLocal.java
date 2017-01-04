package enterprise.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import model.Event;
import model.User;

@Local
public interface StatelessLocal {

	public User getUser(String user);

	public String transferFunds( String fromAccountNo, String toAccountNo,
			BigDecimal amount) throws Exception;
	
	public void signUp(String name, String password, String email) throws Exception;
	
	public void createEvent(String nameArtist, String date, String category) throws Exception;
	
	public void reserverPlace(long idEvent, long idUser, String siege,String cat) throws Exception;
	
	public List<Event> getEvents();

}
