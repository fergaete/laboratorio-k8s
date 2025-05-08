package cl.loteria.calculadora.service.impl;

import cl.loteria.calculadora.model.SolicitudCalculadora;
import cl.loteria.calculadora.service.ServicioSuma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServicioSumaImpl implements ServicioSuma {
    
    private static final Logger logger = LoggerFactory.getLogger(ServicioSumaImpl.class);

    @Override
    public double sumar(SolicitudCalculadora solicitud) {
        logger.debug("Ejecutando operación de suma: {} + {}", solicitud.getNumero1(), solicitud.getNumero2());
        double resultado = solicitud.getNumero1() + solicitud.getNumero2();
        logger.debug("Operación de suma completada. Resultado: {}", resultado);
        return resultado;
    }
} 