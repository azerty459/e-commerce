package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet.ecommerce.business.ICaracteristique;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import graphql.GraphQLException;

@Service
public class CaracteristiqueBusiness implements ICaracteristique{

	@Autowired
	private CaracteristiqueRepository caracteristiqueRepository;

	//Entity => DTO
	@Override
	public List<CaracteristiqueDTO> getAllCaracteristique() {

		List<CaracteristiqueDTO> caracteristiqueDTO = new ArrayList<>();
		caracteristiqueDTO.addAll(CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.findAll()));

		return caracteristiqueDTO;
	}

	//Entity => DTO
	@Override
	public CaracteristiqueDTO getCaracteristique(CaracteristiqueDTO caracteristiqueDTO) {

		List<CaracteristiqueDTO> caracteristiqueDTOs = getAllCaracteristique();

		for(CaracteristiqueDTO caracteristiqueDto : caracteristiqueDTOs) {

			if(caracteristiqueDto.getIdCaracteristique() ==  caracteristiqueDTO.getIdCaracteristique())
				return caracteristiqueDto;
		}

		return null;
	}

	//Création d'un Objet Entity de type Caracteristique
	@Override
	public CaracteristiqueDTO createCaracteristique(CaracteristiqueDTO caracteristiqueDTO) {
		
		Caracteristique caracteristique = new Caracteristique();
		String libelle = caracteristiqueDTO.getLibelle();
		
		if(!libelle.isEmpty())
			caracteristique.setLibelle(libelle);
		else
			throw new GraphQLException("Le libelle ne peut-être vide !!");
		
		return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
	}

	@Override
	public void deleteCaracteristique(CaracteristiqueDTO caracteristiqueDTO) {

		if(caracteristiqueRepository.findById(caracteristiqueDTO.getIdCaracteristique()).isPresent())
			
			caracteristiqueRepository.deleteById(caracteristiqueDTO.getIdCaracteristique());
		else
			
			throw new GraphQLException("Ce caracteristique n'existe pas dans la base");
	}

	@Override
	public CaracteristiqueDTO updateCaracteristique(CaracteristiqueDTO caracteristiqueDTO) {
		
		Caracteristique caracteristique = new Caracteristique();
		
		if(caracteristiqueDTO == null)
			return null;
		
		if(caracteristiqueDTO.getLibelle().isEmpty())
			throw new GraphQLException("Le libelle ne peut-être vide !!");

		if(caracteristiqueRepository.findById(caracteristiqueDTO.getIdCaracteristique()).isPresent())
			caracteristique = CaracteristiqueTransformer.dtoToEntity(caracteristiqueDTO);
		else
			throw new GraphQLException("Ce caracteristique n'existe pas dans la base");
		
		return CaracteristiqueTransformer.entityToDto(caracteristiqueRepository.save(caracteristique));
	}






}
