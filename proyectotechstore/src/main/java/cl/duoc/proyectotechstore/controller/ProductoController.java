package cl.duoc.proyectotechstore.controller;

import cl.duoc.proyectotechstore.dto.ProductoDTO;
import cl.duoc.proyectotechstore.dto.ProductoPatchDTO;
import cl.duoc.proyectotechstore.model.ProductoEntity;
import cl.duoc.proyectotechstore.service.ProductoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // LISTAR PRODUCTOS
    @GetMapping
    public ResponseEntity<List<ProductoEntity>> listarProductos() {

        List<ProductoEntity> productos = productoService.listarProductos();

        return ResponseEntity.ok(productos);
    }

    // OBTENER PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> obtenerProductoPorId(@PathVariable Long id) {

        ProductoEntity producto = productoService.obtenerProductoPorId(id);

        return ResponseEntity.ok(producto);
    }

    // CREAR PRODUCTO
    @PostMapping
    public ResponseEntity<ProductoEntity> crearProducto(
            @Valid @RequestBody ProductoDTO productoDTO) {

        ProductoEntity nuevoProducto = productoService.crearProducto(productoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // ACTUALIZAR PRODUCTO COMPLETO (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ProductoEntity> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {

        ProductoEntity productoActualizado =
                productoService.actualizarProducto(id, productoDTO);

        return ResponseEntity.ok(productoActualizado);
    }

    // ACTUALIZAR PRODUCTO PARCIALMENTE (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<ProductoEntity> actualizarParcialProducto(@PathVariable Long id,@RequestBody ProductoPatchDTO productoPatchDTO) {

        ProductoEntity productoActualizado =
                productoService.actualizarParcialProducto(id, productoPatchDTO);

        return ResponseEntity.ok(productoActualizado);
    }

    // ELIMINAR PRODUCTO (BORRADO LOGICO)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}