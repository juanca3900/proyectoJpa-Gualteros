package com.gualteros.weaponsStore.models;

import java.util.List;

import com.gualteros.weaponsStore.models.dto.CategoriaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class Categoria {
	/*Relacion:
	 * 1-categoria-producto: ManyToMany*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombre_categoria")
	private String nombre;
	@Column(name = "descripcion")
	private String desc;
	@ManyToMany(mappedBy = "categorias")
	private List<Producto> productos;

	public void eliminarProductos() {
		List<Producto> productList = this.productos;
		productList.forEach(it->{	
			it.getCategorias().remove(this);
		});
		this.productos.clear();
	}
	public void agregarProducto(Producto producto){
		this.productos.add(producto);
		producto.getCategorias().add(this);
	}
	//type conversion to categoriaDto
	public CategoriaDto toCategoriaDto() {
		return CategoriaDto.builder()
				.nombreDto(this.nombre)
				.descDto(desc)
				.build();
	}
}
