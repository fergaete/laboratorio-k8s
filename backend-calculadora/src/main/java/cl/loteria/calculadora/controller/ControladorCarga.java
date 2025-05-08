package cl.loteria.calculadora.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/calculadora")
@Tag(name = "Calculadora", description = "API para operaciones de carga")
public class ControladorCarga {

    private static final Logger logger = LoggerFactory.getLogger(ControladorCarga.class);

    @GetMapping("/carga")
    @Operation(
        summary = "Generar carga de CPU",
        description = "Genera una carga de CPU por 60 segundos"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Carga generada exitosamente"
        )
    })
    public ResponseEntity<String> estresar() {
        logger.info("Iniciando generación de carga de CPU por 60 segundos");
        new Thread(() -> {
            long start = System.currentTimeMillis();
            int iterations = 0;
            while (System.currentTimeMillis() - start < 60_000) {
                double result = Math.pow(Math.random(), Math.random());
                iterations++;
                if (iterations % 1000000 == 0) {
                    logger.debug("Carga en progreso: {} iteraciones completadas", iterations);
                }
            }
            logger.info("Carga de CPU completada. Total de iteraciones: {}", iterations);
        }).start();
        return ResponseEntity.ok("Carga generada por 60 segundos.");
    }    
}
