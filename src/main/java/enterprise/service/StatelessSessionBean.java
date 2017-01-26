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
import java.util.HashMap;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.glassfish.grizzly.http.util.TimeStamp;

import model.Event;
import model.Reservation;
import model.User;

import static java.lang.Math.toIntExact;
/**
 * Implementation des fonction décritent dans statelessSessionBean
 * @author middleware
 *
 */
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
	public boolean signUp(String name, String password, String email)throws Exception {
		
		try {

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
			return true;

			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;

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
	public long reserverPlace(long idEvent, long idUser, long siege, String cat) throws Exception{
		Reservation reservation = new Reservation(idEvent,idUser);
		reservation.setSiege(siege);
		reservation.setCategory(cat);
		// reservation.setState(String.valueOf(System.currentTimeMillis()));
		
		//On regarde si l'utilisateur a deja 4 places
		Query query = em.createNamedQuery("Reservation.getNbPlace");
		query.setParameter("idUser", idUser);
		query.setParameter("idEvent",idEvent);
		long i = (long)query.getSingleResult();
		System.out.println(i);
		if( i > 3){
			return -1;
		}
		
		//On regarde si la place est libre
		Query querybis = em.createNamedQuery("Reservation.checkPlace");
		querybis.setParameter("idEvent", idUser);
		querybis.setParameter("siege", siege);
		querybis.setParameter("cat", cat);
		long j = (long)querybis.getSingleResult();
		if( j > 0){
			return -1;
		}
		
		System.out.println("ouverture transaction");
		UserTransaction utx = context.getUserTransaction();
		utx.begin();
		System.out.println("persist");
		em.persist(reservation);
		utx.commit();
		System.out.println("fin");
		Query queryter = em.createNamedQuery("Reservation.findByEventbySiegebycat");
		queryter.setParameter("idEvent", idEvent);
		queryter.setParameter("siege", siege);
		queryter.setParameter("cat", cat);
		long k = (long)queryter.getSingleResult();
		
		return k;
				
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
	
	@Override
	public HashMap<Character,Integer> getCategories(int idEvent){
		Query query = em.createNamedQuery("Reservation.getCat");
		char[] cat = {'A','B','C','D'};
		HashMap<Character, Integer> map = new HashMap<>();
		for(char c : cat){
			query.setParameter("Cat", Character.toString(c));
			query.setParameter("idEvent", idEvent);
			long i =  (long) query.getSingleResult();
			map.put(c, toIntExact(i));
		}
		
		return map;
	}
	
	@Override
	public List<Integer> getSiege(int idEvent,char category){
		Query query = em.createNamedQuery("Reservation.getSiege");
		query.setParameter("idEvent", idEvent);
		query.setParameter("cat", Character.toString(category) );
		List<Long> l = query.getResultList();
		Integer[] nbSiege = {25,45,100,500};
		List<Integer> res = new ArrayList();
		switch (category) {
		case 'A':
			for (int i =1; i < 26;i++) {
				boolean aux = false;
				for(int j=0; j < l.size();j++){
					if(l.get(j) == i){
						aux = true;
					}
				}
				if(!aux){
					res.add(i);
				}
	

			}
			break;
		
		case 'B':
			for (int i =1; i < 46;i++) {
				boolean aux = false;
				for(int j=0; j < l.size();j++){
					if(l.get(j) == i){
						aux = true;
					}
				}
				if(!aux){
					res.add(i);
				}
	
			}
			break;
		
		case 'C':
			for (int i =1; i < 101;i++) {
				boolean aux = false;
				for(int j=0; j < l.size();j++){
					if(l.get(j) == i){
						aux = true;
					}
				}
				if(!aux){
					res.add(i);
				}
	
			}
			break;
			
		case 'D':
			for (int i =1; i < 501;i++) {
				boolean aux = false;
				for(int j=0; j < l.size();j++){
					if(l.get(j) == i){
						aux = true;
					}
				}
				if(!aux){
					res.add(i);
				}
	
			}
			break;

		default:
			break;
		}
		return res;
	}
	
	/**
	 * Return the id if it worked correctly, -1 if not
	 */
	@Override
	public long logIn(String email, String password){
		Query query = em.createNamedQuery("User.logIn");
		query.setParameter("email", email);
		query.setParameter("password", password );
		List<User> l = query.getResultList();
		if( l.size() > 0){
			return l.get(0).getIdUsers();
		}
		return -1;
	}

	
	@Override
	public boolean payer(int idReservation){
		Query query = em.createNamedQuery("Reservation.findByID");
		query.setParameter("idReservation", idReservation );
		Reservation res = (Reservation) query.getSingleResult();
		if(res != null){
			res.setState("paye");
			UserTransaction utx = context.getUserTransaction();
			try {
				utx.begin();
				em.merge(res);
				utx.commit();
			} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}
		return false;
	}
	
	@Override
	public long seeEarning(int idAdmin){
		Query query = em.createNamedQuery("User.findByIdUsers");
		query.setParameter("idUsers", idAdmin );
		User user = (User) query.getSingleResult();
		if(user != null){
			if(user.getType().equals("admin")){
				Query querybis = em.createNamedQuery("Reservation.getAllPaid");
				List<Reservation> resList = querybis.getResultList();
				Query querytris = em.createNamedQuery("Event.getAll");
				List<Event> eventList = querytris.getResultList();
				int somme = 0;
				for(Reservation r : resList){
					String cat =r.getCategory();
					long idEvent = r.getIdEvent();
					String catEvent ="";
					for(int i =0; i < eventList.size();i++){
						if(eventList.get(i).getIdEvents() == idEvent){
							catEvent = eventList.get(i).getCategory();
							break;
						}
					}
					int prixEvent =0;
					double coefPlace =0;
					switch(catEvent){
					case "C1" : prixEvent = 5;break;
					case "C2" : prixEvent = 10;break;
					case "C3" : prixEvent = 20;break;
					case "C4" : prixEvent = 50;break;
					}
					switch(cat){
					case "A" :coefPlace = 3;break;
					case "B" :coefPlace = 2.5;break;
					case "C" :coefPlace = 2;break;
					case "D" :coefPlace = 1;break;
					}
					somme += prixEvent*coefPlace;
				}
				return somme;
			}
		}
		return -1;
	}
	
	@Override
	public String seeBookedPlace(int idAdmin, int idEvent){
		String result = "";
		Query query = em.createNamedQuery("Reservation.findByEventPaye");
		query.setParameter("idEvent", idEvent);
		List<Reservation> resList = query.getResultList();
		int[] nbParCat = {0,0,0,0};
		for(Reservation res: resList){
			String cat = res.getCategory();
			switch(cat){
			case "A" : nbParCat[0]++; break;
			case "B" : nbParCat[1]++; break;
			case "C" : nbParCat[2]++; break;
			case "D" : nbParCat[3]++; break;
				
			}

		}
		result += "A :"+nbParCat[0]+"/25"+" B :"+nbParCat[1]+"/45"+"C :"+nbParCat[2]+"/100"+"D :"+nbParCat[3]+"/500";
		return result;
	}
}
