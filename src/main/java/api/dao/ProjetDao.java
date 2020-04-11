package api.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import api.entities.Projet;


@Repository
public class ProjetDao {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	public Projet getProjetForEdit(Integer idProjet){
		
		try {
			return entityManager.createQuery(""
					+ "SELECT p FROM Projet p "
					
						+ "LEFT JOIN FETCH p.defaultMarche dfM "
							+ "LEFT JOIN FETCH dfM.currentTaux "
						
						+ "LEFT JOIN FETCH p.secteur "
						+ "LEFT JOIN FETCH p.srcFinancement "
						
						+ "LEFT JOIN FETCH p.indh _in "
							+ "LEFT JOIN FETCH _in.programme "
						
						+ "LEFT JOIN FETCH p.projetMaitreOuvrage pmo "
							+ "LEFT JOIN FETCH pmo.maitreOuvrage "
							
						+ "LEFT JOIN FETCH p.projetMaitreOuvrageDelegue pmo_ "
							+ "LEFT JOIN FETCH pmo_.maitreOuvrage "
						
						+ "LEFT JOIN FETCH p.localisations loc "
							+ "LEFT JOIN FETCH loc.commune "
							+ "LEFT JOIN FETCH loc.fraction "
							
						+ "LEFT JOIN FETCH p.projetPartenaires pp "
							+ "LEFT JOIN FETCH pp.partenaire "
							
						+ "LEFT JOIN FETCH p.chargeSuivi "
						
					+ "WHERE p.id = :idProjet "
					
					+ "", Projet.class)
					.setParameter("idProjet", idProjet)
					.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	

	

	
	




	

}
