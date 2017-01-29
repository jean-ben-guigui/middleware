package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@Table(name="Users")
@NamedQueries(

{
 

@NamedQuery(name="Users.findAll", query="SELECT b FROM User b"),



@NamedQuery( name = "User.findByIdUsers", query = "SELECT b FROM User b WHERE b.idUsers = :idUsers" ),

@NamedQuery( name = "User.findByName", query = "SELECT b FROM User b WHERE b.name = :name" ),

@NamedQuery( name = "User.findByEmail", query = "SELECT b FROM User b WHERE b.email = :email" ),

@NamedQuery( name = "User.logIn", query = "SELECT b FROM User b WHERE b.email = :email AND b.password = :password")



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

		@Column(name="type")
		private String type;
		
		//bi-directional many-to-one association to BankAccount
		@ManyToMany
		@JoinColumn(name = "idEvents")
		private List<Event> events;
		
		
		

		/**
		 * Empty constructeur
		 */
		public User() {
			super();
			this.type = "client";
		}
		
		
		/**
		 * Constructeur
		 * @param password
		 * @param name
		 * @param email
		 * @param events
		 */
		public User(String password, String name,String email, List<Event> events) {
			super();
			this.password = password;
			this.name = name;
			this.events = events;
			this.email = email;
			this.type = "client";
		}


		/**
		 * getter pour le password
		 * @return
		 */
		public String getPassword() {
			return this.password;
		}
		/**
		 * Setter pour le password
		 * @param password
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		
		

		/**
		 * Getter de l'id
		 * @return
		 */
		public long getIdUsers() {
			return idUsers;
		}
		/**
		 * Setter de l'id
		 * @param idUser
		 */
		public void setIdUser(long idUser) {
			this.idUsers = idUser;
		}
		/**
		 * Getter du nom
		 * @return
		 */
		public String getName() {
			return name;
		}
		/**
		 * Setter du nom
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * Getter pour la liste d'event
		 * @return
		 */
		public List<Event> getEvents() {
			return events;
		}
		/**
		 * Setter pour la liste d'events
		 * @param events
		 */
		public void setEvents(List<Event> events) {
			this.events = events;
		}
		/**
		 * Setter de l'email
		 * @param email
		 */
		public void setEmail(String email){
			this.email=email;
		}
		/**
		 * Getter de l'email
		 * @return
		 */
		public String getEmail(){
			return this.email;
		}
		/**
		 * Setter du type
		 * @param type
		 */
		public void setType(String type){
			this.type = type;
		}
		/**
		 * Getter du type
		 * @return
		 */
		public String getType(){
			return this.type;
		}
		/** 
		 * Permet d'ajouter un event (ie une place dans la liste d'event)
		 * @param event
		 * @return
		 */
		public Event addEvent(Event event) {
			getEvents().add(event);
			event.getUsers().add(this);
			return event;
		}
		/**
		 * Retire une place de la liste d'event
		 * @param event
		 * @return
		 */
		public Event removeEvent(Event event) {
			getEvents().remove(event);
			event.getUsers().remove(this);

			return event;
		}

}
