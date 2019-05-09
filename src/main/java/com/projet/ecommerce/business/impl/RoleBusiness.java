package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IRoleBusiness;
import com.projet.ecommerce.business.dto.RoleDTO;
import com.projet.ecommerce.business.dto.UtilisateurDTO;
import com.projet.ecommerce.business.dto.transformer.RoleTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Role;
import com.projet.ecommerce.persistance.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service permettant de gérer les actions effectuées pour les produits.
 */

@Service
public class RoleBusiness implements IRoleBusiness {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<RoleDTO> getRole(int id, String nom) {
		Collection<Role> roleCollection = new ArrayList<>();

		// On va chercher les catégories
		if (nom != null) {
			Optional<Role> roleOptional = roleRepository.findByNom(nom);
			if (roleOptional.isPresent()) {
				roleCollection.add(roleOptional.get());
			} else {
				throw new GraphQLCustomException("Le rôle avec le nom recherché, n'a pas été trouvé");
			}
		} else if (id != 0) {
			Optional<Role> roleOptional = roleRepository.findById(id);
			if (roleOptional.isPresent()) {
				roleCollection.add(roleOptional.get());
			} else {
				throw new GraphQLCustomException("Le rôle avec l'id recherché, n'a pas été trouvé");
			}
		} else {
			roleCollection = roleRepository.findAllByOrderByNom();
		}
		return new ArrayList<>(RoleTransformer.entityToDto(roleCollection));
	}

	@Override
	public UtilisateurDTO add(RoleDTO roleDTO) {
		return null;
	}

	@Override
	public RoleDTO update(RoleDTO roleDTO) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Page<RoleDTO> getPage(int pageNumber, int nb) {
		return null;
	}

}
