package com.gualteros.weaponsStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.gualteros.weaponsStore.models.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query("Select P from Producto P WHERE P.nombre LIKE :nombreProd")
	public List<Producto> getProductoByNombre(@Param("nombreProd") String nombre);
}
