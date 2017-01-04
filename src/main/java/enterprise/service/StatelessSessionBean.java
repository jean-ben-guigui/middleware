/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package enterprise.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.glassfish.grizzly.http.util.TimeStamp;

import model.BankAccount;
import model.BankCustomer;
import model.Event;
import model.Reservation;
import model.User;

@Stateless(name = "BK")
@TransactionManagement(TransactionManagementType.BEAN)
public class StatelessSessionBean implements StatelessLocal {

	@Resource
	private EJBContext context;
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager em;

	@Override
	public User getUser(String user) {

		Query query = em.createNamedQuery("User.findByName");
		query.setParameter("name", user);

		User userResult = (User) query.getSingleResult();

		return userResult;
	}

	@Override
	public String transferFunds(String fromAccountNo, String toAccountNo, BigDecimal amount) throws Exception {
		try {
			UserTransaction utx = context.getUserTransaction();

			utx.begin();
			// Check for amount greater than 0
			// if ( amount.doubleValue() <= 0 )
			// {
			// throw new Exception( "Invalid transfer amount" );
			// }

			// Get source bank account entity
			Query query = em.createNamedQuery("BankAccountEntity.findByAccountNo");
			query.setParameter("accountNo", fromAccountNo);
			BankAccount fromBankAccountEntity = null;
			fromBankAccountEntity = (BankAccount) query.getSingleResult();
			System.out.println("--- THe first account is --- " + fromBankAccountEntity.getAccountNo());

			query.setParameter("accountNo", toAccountNo);
			BankAccount toBankAccountEntity = (BankAccount) query.getSingleResult();
			System.out.println("--- THe secound account is --- " + toBankAccountEntity.getAccountNo());
			// Check if there are enough funds in the source account for the
			// transfer
			BigDecimal sourceBalance = fromBankAccountEntity.getBalance();
			System.out.println("Balance source = " + sourceBalance);
			System.out.println("Amount " + amount);
			BigDecimal bankCharge = new BigDecimal(2);

			// Perform the transfer
			sourceBalance = sourceBalance.subtract(amount).subtract(bankCharge);
			fromBankAccountEntity.setBalance(sourceBalance);
			System.out.println(fromBankAccountEntity);
			BigDecimal targetBalance = toBankAccountEntity.getBalance();
			toBankAccountEntity.setBalance(targetBalance.add(amount));
			System.out.println(toBankAccountEntity);
			System.out.println("Transfer Completed");
			// Update all the accounts
			// em.merge(toBankAccountEntity);
			//
			// em.merge(fromBankAccountEntity);
			utx.commit();
			return "Done - Balances after operation \r\n " + fromBankAccountEntity.getAccountNo() + ": "
					+ fromBankAccountEntity.getBalance() + " \r\n" + toBankAccountEntity.getAccountNo() + ": "
					+ toBankAccountEntity.getBalance();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return e.getLocalizedMessage();
		}

	}
	
	@Override
	public void signUp(String name, String password, String email)throws Exception {
		try {
			/*Query query = em.createNamedQuery("Users.createNewUser");
			query.setParameter("name", name);
			query.setParameter("password", password);
			query.setParameter("email", email);
			int res = query.executeUpdate();
			System.out.println("res ="+res);*/
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			System.out.println("ouverture transaction");
			UserTransaction utx = context.getUserTransaction();
			utx.begin();
			System.out.println("persist");
			em.persist(user);
			utx.commit();
			System.out.println("fin");
		

			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	@Override
	public void createEvent(String nameArtist, String date, String category) throws Exception{
		// convertion de la date de string a timestamp
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedDate = dateFormat.parse(date);
	    Timestamp timestamp = new Timestamp(parsedDate.getTime());
		
		Event event = new Event(nameArtist, timestamp, category);
		System.out.println("ouverture transaction");
		UserTransaction utx = context.getUserTransaction();
		utx.begin();
		System.out.println("persist");
		em.persist(event);
		utx.commit();
		System.out.println("fin");
				
	}
	
	@Override
	public void reserverPlace(long idEvent, long idUser, String siege, String cat) throws Exception{
		Reservation reservation = new Reservation(idEvent,idUser);
		reservation.setSiege(siege);
		reservation.setCategory(cat);
		System.out.println("ouverture transaction");
		UserTransaction utx = context.getUserTransaction();
		utx.begin();
		System.out.println("persist");
		em.persist(reservation);
		utx.commit();
		System.out.println("fin");
				
	}
	
	@Override
	public List<Event> getEvents(){
		// Method pour obtenir la liste des events
		Query query = em.createNamedQuery("Event.getAll");
		List<Event> events =  query.getResultList();
		String res = "";
		for(Event e: events){
			res+=e.toString()+"\n";
		}

		return events;
		
	}

}
