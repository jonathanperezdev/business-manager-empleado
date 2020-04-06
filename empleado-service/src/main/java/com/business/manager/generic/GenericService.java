package com.business.manager.generic;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.manager.dao.entities.BaseEntity;
import com.business.manager.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericService<T extends BaseEntity, U extends BaseModel> {
	
	default List<U> findAll() {
		return getRepository()
				.findAll()
				.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	JpaRepository<T,Long> getRepository();
	U toModel(T entity);

}
