package api.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import api.beans.LocalisationBean;
import api.beans.ProgrammeBean;
import api.beans.SteBean;
import api.dto.SimpleDto;
import api.entities.IndhProgramme;

@Repository
public class DiversDao {

	
	@PersistenceContext
	private EntityManager entityManager;
	

	public List<SimpleDto> getSocietesByName(String q){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(s.id, s.nom) FROM Societe s WHERE s.nom LIKE :q", SimpleDto.class)
				.setParameter("q", "%" + q + "%")
				.getResultList() ;
	}
	

	public SteBean fetchtSocietesById(Integer idSte){
		return entityManager.createQuery(""
				+ "SELECT new api.beans.SteBean(s.id, s.nom, s.adresse, rsp.nom, rsp.email, rsp.phones) FROM Societe s "
					+ "LEFT JOIN s.responsable rsp "
				+ "WHERE s.id = :idSte", SteBean.class)
				.setParameter("idSte", idSte)
				.getSingleResult() ;
	}
	

	public List<SimpleDto> getMarcheTypes(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(mt.id, mt.nom) FROM MarchesType mt", SimpleDto.class)
				.getResultList() ;
	}
	

	public List<SimpleDto> getSecteurs(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(c.id, c.nom) FROM Secteur c", SimpleDto.class)
				.getResultList() ;
	}
	

	public List<SimpleDto> getAcheteursByName(String q){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(c.id, c.nom) FROM Acheteur c WHERE c.nom LIKE :q", SimpleDto.class)
				.setParameter("q", "%" + q + "%")
				.getResultList() ;
	}

	public List<SimpleDto> getAcheteurs(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(c.id, c.nom) FROM Acheteur c", SimpleDto.class)
				.getResultList() ;
	}

	public List<SimpleDto> getSrcFinancements(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(s.id, s.label) FROM SrcFinancement s", SimpleDto.class)
				.getResultList() ;
	}
	

	

	public List<LocalisationBean> getCommunesWithFractions(){
		
		return entityManager.createQuery(" "
				
				+ " SELECT new api.beans.LocalisationBean(c.id, c.nom, f.id, f.nom) "
					+ " FROM Commune c "
					+ " LEFT JOIN c.fractions f "
					, LocalisationBean.class)
				
				.getResultList() ;
		
	}

	public List<SimpleDto> getCommunes(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(c.id, c.nom) FROM Commune c", SimpleDto.class)
				.getResultList() ;
	}

//	@SuppressWarnings("unchecked")
	public List<SimpleDto> getFinancements(Integer acheteur) {
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(sf.id, sf.label) "
					+ "FROM AcheteurSrcFinancement ach_sf "
						+ "JOIN ach_sf.srcFinancement sf "
						+ "WHERE ach_sf.acheteur.id = :maitreOuvrage")
				.setParameter("maitreOuvrage", acheteur)
				.getResultList() ;
	}

//	@SuppressWarnings("unchecked")
//	public List<SimpleDto> getProgrammes2() {
//		return entityManager.createQuery("SELECT new api.api.dto.SimpleDto(p.id, p.label) FROM Programme p")
//				.getResultList() ;
//	}
	

	public List<ProgrammeBean> getParentProgrammes() {
		return entityManager.createQuery(""
				+ "SELECT new api.beans.ProgrammeBean(p.id, p.label, parent.id, parent.label, p.phase) FROM IndhProgramme p "
					+ "LEFT JOIN p.parentProgramme parent "
				+ "WHERE p.parentProgramme IS NULL",
				ProgrammeBean.class
		)
		.getResultList();
	}


//	public List<IndhProgramme> getIndhProgrammes() {
//		return entityManager.createQuery("SELECT p FROM IndhProgramme p").getResultList();
//	}
	

	public List<ProgrammeBean> getIndhProgrammes2() {
		
		return entityManager.createQuery(""
				+ "SELECT new api.beans.ProgrammeBean(p.id, p.label, parent.id, parent.label, p.phase) FROM IndhProgramme p "
					+ "LEFT JOIN p.parentProgramme parent ",
				ProgrammeBean.class
		)
		.getResultList();
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SimpleDto> getOsTypes(){
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(os.id, os.label) FROM OsType os").getResultList() ;
	}


	@SuppressWarnings("unchecked")
	public List<SimpleDto> getMarcheEtats() {
		return entityManager.createQuery("SELECT new api.dto.SimpleDto(me.id, me.nom) FROM MarchesEtat me").getResultList() ;
	}

	
}
