package com.business.manager.generic;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.business.manager.dao.entities.BaseEntity;
import com.business.manager.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.CollectionUtils;

public interface GenericService<T extends BaseEntity, U extends BaseModel> {
	
	default List<U> findAll() {
		return getRepository()
				.findAll()
				.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}

	default U findById(Long id){
		return toModel(getRepository().findById(id).get());
	}

	default void delete(Long id){
		getRepository().deleteById(id);
	}

	default U create(U model){
		T entity = toEntity(model);
		return toModel(getRepository().save(entity));
	}

	default U update(Long id, U model){
		model.setId(id);
		T entity = toEntity(model);
		return toModel(getRepository().save(entity));
	}

	JpaRepository<T,Long> getRepository();
	U toModel(T entity);
	T toEntity(U model);

}
