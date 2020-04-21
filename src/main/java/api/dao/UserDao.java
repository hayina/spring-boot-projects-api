package api.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import api.dao.interfaces.IUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import api.beans.UserBean;
import api.dto.SimpleDto;
import api.entities.User;
import api.enums.RoleEnum;
import api.security.models.UserPrincipal;


@Repository
public class UserDao implements IUserDao {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public User checkUser(String login, String password){
		
		try {
			return entityManager.createQuery(""
					
					 + " SELECT u FROM User u "
					 	+ " WHERE u.login = :login AND u.password = :password ", User.class)
					
			.setParameter("login", login)
			.setParameter("password", password)
			.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	

	public List<UserBean> getListUsers(){
		
		return entityManager.createQuery("SELECT new api.beans.UserBean(u.id, u.nom, u.prenom) FROM User u", UserBean.class)
				.getResultList();
	}


	public User getUserForEdit(Integer idUser) {
		try {
			return entityManager.createQuery(""
					+ "SELECT u FROM User u "
						+ "LEFT JOIN FETCH u.roles "
					+ "WHERE u.id = :idUser", User.class)
					.setParameter("idUser", idUser)
					.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	
	public Integer getChargeSuiviByProj(Integer idProj) {


		return entityManager.createQuery("SELECT p.chargeSuivi.id FROM Projet p WHERE p.id = :idProj", Integer.class)
					.setParameter("idProj", idProj)
					.getResultList()
					.stream().findFirst().orElse(null);


	}
	
	
	public Integer getChargeSuiviByMarche(Integer idMarch) {
		
		
		return entityManager.createQuery(""
				+ "SELECT p.chargeSuivi.id FROM Marches m "
					+ "JOIN m.projet p "
				+ "WHERE m.id = :idMarch", Integer.class)
				.setParameter("idMarch", idMarch)
				.getResultList()
				.stream().findFirst().orElse(null);
		
		
	}



	public List<SimpleDto> getListRoles() {
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(r.id, r.label) FROM Role r", SimpleDto.class).getResultList();
	}
	
	public List<SimpleDto> getPermissions() {
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(p.id, p.label) FROM Permission p", SimpleDto.class).getResultList();
	}
	

	public List<SimpleDto> getDivisions() {
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(d.id, d.nom) FROM Division d", SimpleDto.class).getResultList() ;
	}


//	public List<User> getChargesSuivi() {
//		return entityManager.createQuery("SELECT u FROM User u WHERE u.chargeSuivi = 1", User.class).getResultList();
//	}
	
	public List<User> getChargesSuiviWithDivision() {
		return entityManager.createQuery("SELECT u FROM User u "
				+ " JOIN u.roles r "
				+ " LEFT JOIN FETCH u.division "
				+ "WHERE r.id = :CHARGE_SUIVI", User.class)
				.setParameter("CHARGE_SUIVI", RoleEnum.CHARGE_SUIVI.getValue())
				.getResultList();
	}







	public User fetchUserByLogin(String username) throws UsernameNotFoundException {

		try {
			
			return entityManager
							.createQuery("SELECT u FROM User u "
									+ " LEFT JOIN FETCH u.roles r "
										+ " LEFT JOIN FETCH r.permissions "
									+ " WHERE u.login = :login", User.class)
							.setParameter("login", username)
							.getSingleResult();

		}
		catch (NoResultException e) {
			throw new UsernameNotFoundException(username);
		}
		
		
		
	}



	@Override
	public UserDetails loadUserByUsername(String username) {
		return new UserPrincipal(fetchUserByLogin(username));
	}


}





