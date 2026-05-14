package cl.duoc.proyectotechstore.service;

import cl.duoc.proyectotechstore.dto.ProductoDTO;
import cl.duoc.proyectotechstore.dto.ProductoPatchDTO;
import cl.duoc.proyectotechstore.model.ProductoEntity;

import java.util.List;

public interface ProductoService {

    List<ProductoEntity> listarProductos();

    ProductoEntity obtenerProductoPorId(Long id);

    ProductoEntity crearProducto(ProductoDTO productoDTO);

    ProductoEntity actualizarProducto(Long id, ProductoDTO productoDTO);

    ProductoEntity actualizarParcialProducto(Long id, ProductoPatchDTO productoPatchDTO);

    void eliminarProducto(Long id);
}