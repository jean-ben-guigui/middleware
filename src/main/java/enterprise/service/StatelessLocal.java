package enterprise.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import model.Event;
import model.User;
/**
 * Interface donnant la liste des fonction implementees
 * @author middleware
 *
 */
@Remote
public interface StatelessLocal {

	/**
	 * Permet d'avoir un user en fonction de son nom
	 * @param user
	 * @return
	 */
	public User getUser(String user);

	/**
	 * Permet d'inscrire un nouveau utilisateur
	 * @param name
	 * @param password
	 * @param email
	 * @return true si ça a marcher false sinon 
	 * @throws Exception
	 */
	public boolean signUp(String name, String password, String email) throws Exception;
	
	/**
	 * Ajoute un event
	 * @param nameArtist
	 * @param date
	 * @param category
	 * @throws Exception
	 */
	public void createEvent(String nameArtist, String date, String category) throws Exception;
	
	/**
	 * 
	 * @param idEvent
	 * @param idUser
	 * @param siege
	 * @param cat
	 * @return l'id de la place reservee, elle doit etre payer dans les 5 minutes suivantes
	 * @throws Exception
	 */
	public long reserverPlace(long idEvent, long idUser, long siege,String cat) throws Exception;
	
	/**
	 * Renvoit la liste des evenements à venir
	 * @return
	 */
	public List<Event> getEvents();
	
	/**
	 * Renvoit la liste des cat avec le nombre de place occupees
	 * @param idEvent
	 * @return
	 */
	public HashMap<Character,Integer> getCategories(int idEvent);
	
	/**
	 * Renvoit la liste des siege libre pour un event et une categorie donnee
	 * @param idEvent
	 * @param category
	 * @return
	 */
	public List<Integer> getSiege(int idEvent, char category);
	
	/**
	 * Permet de s'authentifier, renvoit l'id si l'autentification est réussie, -1 sinon
	 * @param email
	 * @param password
	 * @return
	 */
	public long logIn(String email, String password);

	/**
	 * Permet de simuler un payement, ie la place sera stocké dans la db et plus supprimée au bout de 5 minutes
	 * @param idReservation
	 * @return
	 */
	public boolean payer(int idReservation);
	
	/**
	 * Permet d'obtenir le total des gains, renvoit -1  si l'utilisateur n'est pas un admin
	 * @param idAdmin
	 * @return
	 */
	public long seeEarning(int idAdmin);
	
	/**
	 * Renvoit pour un event les places achetées
	 * @param idAdmin
	 * @param idEvent
	 * @return
	 */
	public String seeBookedPlace(int idAdmin, int idEvent);
}
