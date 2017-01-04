package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the BANK_ACCOUNT database table.
 * 
 */
@Entity
@Table(name = "EVENTS")
@NamedQueries({
@NamedQuery(name = "Event.findById", query = "SELECT b FROM Event b WHERE b.idEvents = :idEvents"),
@NamedQuery(name = "Event.getAll", query = "SELECT b FROM Event b WHERE b.date > CURRENT_TIMESTAMP")
})

public class Event implements Serializable{
	

		
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "idEvents")
		@GeneratedValue(strategy=GenerationType.IDENTITY)

		private long idEvents;

		@Column(name = "artistname")
		private String artistName;

		@Column(name = "date")
		private Timestamp date;

		@Column(name = "category")
		private String category;

		// bi-directional many-to-one association to BankCustomer
		@ManyToMany(mappedBy="Events")
		private List<User> users;
		
		
		

		public Event() {
			super();
		}
		
		

		public Event(String artistName, Timestamp date, String category, List<User> users) {
			super();
			this.artistName = artistName;
			this.date = date;
			this.category = category;
			this.users = users;
		}
		
		public Event(String artistName, Timestamp date, String category) {
			super();
			this.artistName = artistName;
			this.date = date;
			this.category = category;
			this.users = new ArrayList<User>();
		}

		public long getIdEvents() {
			return idEvents;
		}



		public String getArtistName() {
			return artistName;
		}

		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public Timestamp getDate() {
			return date;
		}

		public void setDate(Timestamp date) {
			this.date = date;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}

		

		@Override
		public String toString(){
			return this.artistName +" "+this.date+" "+this.category;
		}

		/*@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
			result = prime * result + (int) (bankAccountPk ^ (bankAccountPk >>> 32));
			result = prime * result + (int) (bankAcctTypePk ^ (bankAcctTypePk >>> 32));
			return result;
		}*/

		/*@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BankAccount other = (BankAccount) obj;
			if (accountNo == null) {
				if (other.accountNo != null)
					return false;
			} else if (!accountNo.equals(other.accountNo))
				return false;
			if (bankAccountPk != other.bankAccountPk)
				return false;
			if (bankAcctTypePk != other.bankAcctTypePk)
				return false;
			return true;
		}*/

}
