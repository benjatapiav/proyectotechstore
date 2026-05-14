package cl.duoc.proyectotechstore.repository;

import cl.duoc.proyectotechstore.model.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    

    //Listar por nombre de producto
    List<ProductoEntity> findByNombreProductoContainingIgnoreCase(String nombreProducto);
    
    //Listar por tipo de producto
    List<ProductoEntity> findByTipoProductoContainingIgnoreCase(String tipoProducto);
    
    //Listar por precio de producto
    List<ProductoEntity> findByPrecioProducto(Double precioProducto);
    
    //Listar por productos con precios menores a...
    List<ProductoEntity> findByPrecioProductoLessThan(Double precioProducto);
    
    //Listar por productos con precios mayores a...
    List<ProductoEntity> findByPrecioProductoGreaterThan(Double precioProducto);

    List<ProductoEntity> findByActivo(Boolean activo);
}
