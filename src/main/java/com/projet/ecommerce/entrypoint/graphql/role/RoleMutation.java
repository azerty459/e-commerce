package com.projet.ecommerce.entrypoint.graphql.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.IRoleBusiness;
import com.projet.ecommerce.business.dto.RoleDTO;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMutation {

	@Autowired
	private IRoleBusiness roleBusiness;

	public TypeRuntimeWiring produitWiring() {

		TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
		builder.typeName("Mutation");

		builder.dataFetcher("addRole", (DataFetchingEnvironment environment) -> {
					ObjectMapper mapper = new ObjectMapper();
					Object rawInput = environment.getArgument("role");
					RoleDTO roleDTO = mapper.convertValue(rawInput, RoleDTO.class);
					return roleBusiness.add(roleDTO);
				}
		);

		builder.dataFetcher("updateRole", (DataFetchingEnvironment environment) -> {
					ObjectMapper mapper = new ObjectMapper();
					Object rawInput = environment.getArgument("role");
					RoleDTO roleDTO = mapper.convertValue(rawInput, RoleDTO.class);
					return roleBusiness.update(roleDTO);
				}
		);

		builder.dataFetcher("deleteRole", (DataFetchingEnvironment environment) ->
				roleBusiness.delete(environment.getArgument("id"))
		);
		return builder.build();

	}

}
