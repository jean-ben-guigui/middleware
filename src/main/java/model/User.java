package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BANK_CUSTOMERS database table.
 * 
 */
@Entity
@Table(name="Users")
@NamedQueries(

{


@NamedQuery(name="Users.findAll", query="SELECT b FROM Users b"),



@NamedQuery( name = "Users.findByBankCustomersPk", query = "SELECT b FROM Users b WHERE b.idUsers = :userID" ),

@NamedQuery( name = "Users.findByName", query = "SELECT b FROM Users b WHERE b.name = :name" ),

@NamedQuery( name = "Users.createNewUser", query = "INSERT INTO Users(name, password, email) VALUES(:name, :password, :email)")

})

public class User implements Serializable{

		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="idUsers")
		private int userID;

		@Column(name="password")
		private String password;

		@Column(name="name")
		private String name;

		//bi-directional many-to-one association to BankAccount
		@OneToMany
		@JoinColumn(name = "idEvents")
		private List<Event> events;
		
		
		


		public User() {
			super();
		}
		
		

		public User(String password, String name, List<Event> events) {
			super();
			this.password = password;
			this.name = name;
			this.events = events;
		}



		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		

		
		public long getUserID() {
			return userID;
		}

		public void setUserID(int userID) {
			this.userID = userID;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<Event> getEvents() {
			return events;
		}

		public void setEvents(List<Event> events) {
			this.events = events;
		}

		public Event addEvent(Event event) {
			getEvents().add(event);
			event.getUsers().add(this);
			return event;
		}

		public Event removeEvent(Event event) {
			getEvents().remove(event);
			event.getUsers().remove(this);

			return event;
		}

}
