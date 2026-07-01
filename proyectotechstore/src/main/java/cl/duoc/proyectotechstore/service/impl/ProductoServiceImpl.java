package cl.duoc.proyectotechstore.service.impl;

import cl.duoc.proyectotechstore.dto.ProductoDTO;
import cl.duoc.proyectotechstore.dto.ProductoPatchDTO;
import cl.duoc.proyectotechstore.model.ProductoEntity;
import cl.duoc.proyectotechstore.repository.ProductoRepository;
import cl.duoc.proyectotechstore.service.ProductoService;
import cl.duoc.proyectotechstore.dto.AuditEvent;
import cl.duoc.proyectotechstore.service.SqsService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private SqsService sqsService;

    @Override
    public List<ProductoEntity> listarProductos() {
        return productoRepository.findByActivo(true);
    }

    @Override
    public ProductoEntity obtenerProductoPorId(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public ProductoEntity crearProducto(ProductoDTO productoDTO) {

        ProductoEntity producto = new ProductoEntity();

        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setTipoProducto(productoDTO.getTipoProducto());
        producto.setPrecioProducto(productoDTO.getPrecioProducto());
        producto.setStockProducto(productoDTO.getStockProducto());
        producto.setDescripcionProducto(productoDTO.getDescripcionProducto());
        producto.setActivo(true);

        ProductoEntity guardado = productoRepository.save(producto);

        AuditEvent evento = new AuditEvent();
        evento.setAccion("CREAR");
        evento.setProductoId(guardado.getId());
        evento.setNombreProducto(guardado.getNombreProducto());

        sqsService.enviarMensaje(evento);

        return guardado;
    }

    @Override
    public ProductoEntity actualizarProducto(Long id, ProductoDTO productoDTO) {

        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setTipoProducto(productoDTO.getTipoProducto());
        producto.setPrecioProducto(productoDTO.getPrecioProducto());
        producto.setStockProducto(productoDTO.getStockProducto());
        producto.setDescripcionProducto(productoDTO.getDescripcionProducto());
        producto.setActivo(productoDTO.getActivo());

        ProductoEntity actualizado = productoRepository.save(producto);

        AuditEvent evento = new AuditEvent();
        evento.setAccion("ACTUALIZAR");
        evento.setProductoId(actualizado.getId());
        evento.setNombreProducto(actualizado.getNombreProducto());

        sqsService.enviarMensaje(evento);

        return actualizado;
    }

    @Override
    public ProductoEntity actualizarParcialProducto(Long id, ProductoPatchDTO productoPatchDTO) {

        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (productoPatchDTO.getNombreProducto() != null) {
            producto.setNombreProducto(productoPatchDTO.getNombreProducto());
        }

        if (productoPatchDTO.getTipoProducto() != null) {
            producto.setTipoProducto(productoPatchDTO.getTipoProducto());
        }

        if (productoPatchDTO.getPrecioProducto() != null) {
            producto.setPrecioProducto(productoPatchDTO.getPrecioProducto());
        }

        if (productoPatchDTO.getStockProducto() != null) {
            producto.setStockProducto(productoPatchDTO.getStockProducto());
        }

        if (productoPatchDTO.getDescripcionProducto() != null) {
            producto.setDescripcionProducto(productoPatchDTO.getDescripcionProducto());
        }

        if (productoPatchDTO.getActivo() != null) {
            producto.setActivo(productoPatchDTO.getActivo());
        }

        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProducto(Long id) {

        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(false);

        productoRepository.save(producto);

        AuditEvent evento = new AuditEvent();
        evento.setAccion("ELIMINAR");
        evento.setProductoId(producto.getId());
        evento.setNombreProducto(producto.getNombreProducto());

        sqsService.enviarMensaje(evento);
    }

    @PostConstruct
    public void test() {
        System.out.println("SqsService = " + sqsService);
    }   
}