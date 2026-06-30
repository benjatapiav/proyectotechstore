package cl.duoc.proyectotechstore.service;

import cl.duoc.proyectotechstore.dto.AuditEvent;

public interface SqsService {
    void enviarMensaje(AuditEvent evento);
}