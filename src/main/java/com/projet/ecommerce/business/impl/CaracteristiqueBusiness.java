package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.projet.ecommerce.business.ICaracteristiqueBusiness;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;

public class CaracteristiqueBusiness implements ICaracteristiqueBusiness {

	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;
	
	
	
	@Override
	public List<CaracteristiqueDTO> getAll(String referenceProduit) {
		Collection<Caracteristique> caracteristiqueCollection = new ArrayList<>();
		if (referenceProduit.equals(null)) {
			caracteristiqueCollection.addAll(caracteristiqueRepository.findAll());
		}
		else {
			throw new GraphQLCustomException("La liste est vide");
		}
		return new ArrayList<>(CaracteristiqueTransformer.listeEntityToDto(caracteristiqueCollection));
	}

	@Override
	public CaracteristiqueDTO updateCaracteristique(int idCaracteristique, String nouvelleValeurCaracteristique) {
		if(nouvelleValeurCaracteristique.isEmpty()) {
			throw new GraphQLCustomException("La nouvelle valeur de caracteristique entrée est vide, vueillez entrez une autre valeur");
		}
		
		Optional<Caracteristique> optionalCaracteristique = caracteristiqueRepository.findById(idCaracteristique);
		
		return optionalCaracteristique
				.map((caracteristique) -> {
					Caracteristique carac = optionalCaracteristique.get();
					carac.setValeurCaracteristique(nouvelleValeurCaracteristique);
					return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(carac));
				})
				.orElseThrow(() -> new GraphQLCustomException("La caractéristique demandé n'a pas été trouvé"));
	}

}
