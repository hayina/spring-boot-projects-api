package api.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.beans.ProjetSearchBean;
import api.dao.SearchProjetDao;
import api.dto.PageResult;
import api.dto.ProjetDto;

@Service
public class ProjetSearch {

	@Autowired
	private SearchProjetDao searchProjetDao;
	
	
	public PageResult getListProjets(ProjetSearchBean bean){
		
		PageResult page = searchProjetDao.getListProjets(bean);

		
		Long a,b;
		Map<Integer, ProjetDto> projetsMap = new LinkedHashMap<Integer, ProjetDto>();
		
		
		a = System.currentTimeMillis();
		page.content.forEach((proj) -> {

			if (!projetsMap.containsKey(proj.id)) {
				projetsMap.put(proj.id, proj);
			}
			
			if(!projetsMap.get(proj.id).localisations.containsKey(proj.communeId)) {
				projetsMap.get(proj.id).localisations.put(proj.communeId, proj.communeLabel);
			}
			
			if( proj.marcheId != null ) {
				
				if(!projetsMap.get(proj.id).marches.containsKey(proj.marcheId)) {
					projetsMap.get(proj.id).marches.put(proj.marcheId, proj.marcheType);
				} 
			}

		});
		
		page.content = projetsMap.values();
		
		b = System.currentTimeMillis();
		System.out.println(">> Prepare result -> " + (b - a));
		
		return page;
	}
}
