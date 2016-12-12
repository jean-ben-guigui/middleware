package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BANK_CUSTOMERS database table.
 * 
 */
@Entity
@Table(name="BANK_CUSTOMERS")
@NamedQueries(

{



@NamedQuery(name="BankCustomer.findAll", query="SELECT b FROM BankCustomer b"),



@NamedQuery( name = "BankCustomer.findByBankCustomersPk", query = "SELECT b FROM BankCustomer b WHERE b.bankCustomersPk = :bankCustomersPk" ),

@NamedQuery( name = "BankCustomer.findByUsername", query = "SELECT b FROM BankCustomer b WHERE b.username = :username" )





})


public class BankCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="BANK_CUSTOMERS_PK")
	private long bankCustomersPk;

	private String password;

	private String username;

	//bi-directional many-to-one association to BankAccount
	@OneToMany(mappedBy="bankCustomer")
	private List<BankAccount> bankAccounts;

	public BankCustomer() {
	}

	public long getBankCustomersPk() {
		return this.bankCustomersPk;
	}

	public void setBankCustomersPk(long bankCustomersPk) {
		this.bankCustomersPk = bankCustomersPk;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<BankAccount> getBankAccounts() {
		return this.bankAccounts;
	}

	public void setBankAccounts(List<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public BankAccount addBankAccount(BankAccount bankAccount) {
		getBankAccounts().add(bankAccount);
		bankAccount.setBankCustomer(this);

		return bankAccount;
	}

	public BankAccount removeBankAccount(BankAccount bankAccount) {
		getBankAccounts().remove(bankAccount);
		bankAccount.setBankCustomer(null);

		return bankAccount;
	}

}