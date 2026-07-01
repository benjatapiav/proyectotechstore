package cl.duoc.proyectotechstore.dto;

import lombok.Data;

@Data
public class AuditEvent {

    private String accion;
    private Long productoId;
    private String nombreProducto;
}