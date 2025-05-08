package cl.loteria.calculadora.service.impl;

import cl.loteria.calculadora.model.SolicitudCalculadora;
import cl.loteria.calculadora.service.ServicioResta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicioRestaImpl implements ServicioResta {
    
    private static final Logger logger = LoggerFactory.getLogger(ServicioRestaImpl.class);

    @Override
    public double restar(SolicitudCalculadora solicitud) {
        logger.debug("Ejecutando operación de resta: {} - {}", solicitud.getNumero1(), solicitud.getNumero2());
        double resultado = solicitud.getNumero1() - solicitud.getNumero2();
        logger.debug("Operación de resta completada. Resultado: {}", resultado);
        return resultado;
    }
} 