package com.gualteros.weaponsStore.models;

import java.util.List;

import com.gualteros.weaponsStore.models.dto.ProductoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class Producto {
	/*Relacion:
	 * 1-producto-categoria: ManyToMany
	 * 2-producto-itemFactura: OneToMany*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;
    @Column(name = "nombre_producto")
    private String nombre;
    @Column(name = "precio_producto")
    private Double precio;
    @Column(name = "stock")
    private Integer stock;
    @ManyToMany
    @JoinTable(name = "producto_categoria"
    , joinColumns = @JoinColumn(name = "producto_id")
    , inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Categoria> categorias;
    
    //metodo para restar el stock por cada venta
    public void vender(Integer cantidad){
        if(stock >= cantidad){
            this.stock -= cantidad;
        }else{
            throw new RuntimeException("No hay stock suficiente");
        }
    }
    public void agregarProductos(Integer canidad){
        this.stock += canidad;
    }
/*     public void actualizarProducto(Producto productoNuevo, List<Categoria> categoriasNuevas){
        this.setNombre(productoNuevo.getNombre());
        this.setPrecio(productoNuevo.getPrecio());
        this.setStock(productoNuevo.getStock());
        this.setCategorias(categoriasNuevas);
    } */
    public void actualizarProducto(ProductoDto productoNuevo){
        this.setNombre(productoNuevo.getNombreDto());
        this.setPrecio(productoNuevo.getPrecioDto());
        this.setStock(productoNuevo.getStockDto());

    }
    public void agregarCategoria(Categoria categoria){
        this.getCategorias().forEach(it->{
            if(it.equals(categoria)){
                throw new RuntimeException("El producto ya pertenece a esta Categoria");
            }
        });
    }

    public void eliminarCategoria(Categoria categoria){
        this.categorias.remove(categoria);
    }
    //type conversion
    public ProductoDto toProductoDto() {
        return ProductoDto.builder().nombreDto(this.nombre)
        		.precioDto(this.precio).stockDto(this.stock)
        		.categoriasDto(this.categorias.stream()
        				.map(it->it.getNombre()).toList()).build();
    }
}

