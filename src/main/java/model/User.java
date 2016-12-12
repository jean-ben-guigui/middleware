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


@NamedQuery(name="Users.findAll", query="SELECT b FROM User b"),



@NamedQuery( name = "User.findByIdUsers", query = "SELECT b FROM User b WHERE b.idUsers = :idUsers" ),

@NamedQuery( name = "User.findByName", query = "SELECT b FROM User b WHERE b.name = :name" ),

// @NamedQuery( name = "User.createNewUser", query = "INSERT INTO User(name, password, email) VALUES(:name, :password, :email)")

})

public class User implements Serializable{

		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="idUsers")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long idUsers;

		@Column(name="password")
		private String password;

		@Column(name="name")
		private String name;
		
		@Column(name="email")
		private String email;

		//bi-directional many-to-one association to BankAccount
		@ManyToMany
		@JoinColumn(name = "idEvents")
		private List<Event> events;
		
		
		


		public User() {
			super();
		}
		
		

		public User(String password, String name,String email, List<Event> events) {
			super();
			this.password = password;
			this.name = name;
			this.events = events;
			this.email = email;
		}



		public String getPassword() {
			return this.password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		

		
		public long getIdUsers() {
			return idUsers;
		}

		public void setIdUser(long idUser) {
			this.idUsers = idUser;
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
		
		public void setEmail(String email){
			this.email=email;
		}
		public String getEmail(){
			return this.email;
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
