package model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the Reservation database table.
 * 
 */
@Entity
@Table(name = "RESERVATION")
@NamedQueries({
@NamedQuery(name = "Reservation.findByEvent", query = "SELECT b FROM Reservation b WHERE b.idEvent = :idEvent"),
@NamedQuery(name = "Reservation.findByEventbySiegebycat", query = "SELECT b.idReservation FROM Reservation b WHERE b.idEvent = :idEvent AND b.siege = :siege AND b.category = :cat"),
@NamedQuery(name = "Reservation.findByEventPaye", query = "SELECT b FROM Reservation b WHERE b.idEvent = :idEvent AND b.state = \"paye\""),
@NamedQuery(name = "Reservation.findByReservation", query = "SELECT b FROM Reservation b WHERE b.idReservation = :idReservation"),
@NamedQuery(name = "Reservation.findByUser", query = "SELECT b FROM Reservation b WHERE b.idUser = :idUser"),
@NamedQuery(name = "Reservation.getCat", query = "SELECT Count(b) FROM Reservation b WHERE b.category = :Cat AND b.idEvent = :idEvent"),
@NamedQuery(name = "Reservation.getSiege", query = "SELECT b.siege FROM Reservation b WHERE b.idEvent = :idEvent AND b.category = :cat"),
@NamedQuery(name = "Reservation.getNbPlace", query = "SELECT Count(b) FROM Reservation b WHERE b.idEvent = :idEvent AND b.idUser = :idUser"),
@NamedQuery(name = "Reservation.findByID", query = "SELECT b FROM Reservation b WHERE b.idReservation = :idReservation"),
@NamedQuery(name = "Reservation.getAllPaid", query = "SELECT b FROM Reservation b WHERE b.state = \"paye \" "),
@NamedQuery(name = "Reservation.checkPlace", query = "SELECT Count(b) FROM Reservation b WHERE b.idEvent = :idEvent AND b.siege = :siege AND b.category = :cat")
})
public class Reservation {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "idReservation")
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private long idReservation;

	@Column(name = "idEvent")
	private long idEvent;
	
	@Column(name = "idUser")
	private long idUser;

	@Column(name = "Siege")
	private long siege;
	
	@Column(name ="Category")
	private String category;

	@Column(name = "State")
	private String state;
	
	@Column(name = "DateRes")
	private Timestamp dateRes;
	
	
	
	public Reservation() {
		super();
		
	}
	
	
	
	public Reservation(long idEvent, long idUser) {
		super();
		this.idEvent = idEvent;
		this.idUser = idUser;
		this.siege =0;
		this.category="z";
		this.state ="pending";
		this.dateRes = new Timestamp(System.currentTimeMillis());
	}



	public long getIdReservation() {
		return idReservation;
	}


	public long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public long getSiege() {
		return siege;
	}

	public void setSiege(long siege) {
		this.siege = siege;
	}
	public void setCategory(String cat){
		this.category = cat;
	}
	public String getCategory(){
		return this.category;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



	


	
}
