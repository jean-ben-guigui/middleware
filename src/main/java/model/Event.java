package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * The persistent class for the BANK_ACCOUNT database table.
 * 
 */
@Entity
@Table(name = "EVENT")
@NamedQuery(name = "BankAccountEntity.findByAccountNo", query = "SELECT b FROM BankAccount b WHERE b.accountNo = :accountNo")


public class Event implements Serializable{
	

		
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "idEvents")
		private int idEvent;

		@Column(name = "artistname")
		private String artistName;

		@Column(name = "date")
		private Date date;

		@Column(name = "category")
		private String category;

		// bi-directional many-to-one association to BankCustomer
		@ManyToMany(mappedBy="Events")
		private List<User> users;
		
		
		

		public Event() {
			super();
		}
		
		

		public Event(String artistName, Date date, String category, List<User> users) {
			super();
			this.artistName = artistName;
			this.date = date;
			this.category = category;
			this.users = users;
		}



		public long getIdEvent() {
			return idEvent;
		}

		public void setIdEvent(int idEvent) {
			this.idEvent = idEvent;
		}

		public String getArtistName() {
			return artistName;
		}

		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
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

		

		/*@Override
		public String toString() {
			return "Event [event ID = " + idEvent + ", artist name : " + artistName + ", Date : " + balance
					+ ", bankAcctTypePk=" + bankAcctTypePk + ", bankCustomer=" + bankCustomer + "]";
		}*/

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
