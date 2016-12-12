package enterprise.service;

import java.math.BigDecimal;

import javax.ejb.Local;
import model.User;

@Local
public interface StatelessLocal {

	public User getUser(String user);

	public String transferFunds( String fromAccountNo, String toAccountNo,
			BigDecimal amount) throws Exception;
	
	public void signUp(String name, String password, String email) throws Exception;

}
