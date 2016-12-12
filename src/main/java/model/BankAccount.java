package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the BANK_ACCOUNT database table.
 * 
 */
@Entity
@Table(name = "BANK_ACCOUNT")
@NamedQuery(name = "BankAccountEntity.findByAccountNo", query = "SELECT b FROM BankAccount b WHERE b.accountNo = :accountNo")
public class BankAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BANK_ACCOUNT_PK")
	private long bankAccountPk;

	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	private BigDecimal balance;

	@Column(name = "BANK_ACCT_TYPE_PK")
	private long bankAcctTypePk;

	// bi-directional many-to-one association to BankCustomer
	@ManyToOne
	@JoinColumn(name = "BANK_CUSTOMERS_PK")
	private BankCustomer bankCustomer;

	public BankAccount() {
	}

	public long getBankAccountPk() {
		return this.bankAccountPk;
	}

	public void setBankAccountPk(long bankAccountPk) {
		this.bankAccountPk = bankAccountPk;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public long getBankAcctTypePk() {
		return this.bankAcctTypePk;
	}

	public void setBankAcctTypePk(long bankAcctTypePk) {
		this.bankAcctTypePk = bankAcctTypePk;
	}

	public BankCustomer getBankCustomer() {
		return this.bankCustomer;
	}

	public void setBankCustomer(BankCustomer bankCustomer) {
		this.bankCustomer = bankCustomer;
	}

	@Override
	public String toString() {
		return "BankAccount [bankAccountPk=" + bankAccountPk + ", accountNo=" + accountNo + ", balance=" + balance
				+ ", bankAcctTypePk=" + bankAcctTypePk + ", bankCustomer=" + bankCustomer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNo == null) ? 0 : accountNo.hashCode());
		result = prime * result + (int) (bankAccountPk ^ (bankAccountPk >>> 32));
		result = prime * result + (int) (bankAcctTypePk ^ (bankAcctTypePk >>> 32));
		return result;
	}

	@Override
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
	}

}