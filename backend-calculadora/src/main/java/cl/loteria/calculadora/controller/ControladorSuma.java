package cl.loteria.calculadora.controller;

import cl.loteria.calculadora.model.SolicitudCalculadora;
import cl.loteria.calculadora.model.RespuestaCalculadora;
import cl.loteria.calculadora.service.ServicioSuma;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/calculadora")
@RequiredArgsConstructor
@Tag(name = "Calculadora", description = "API para operaciones de suma")
public class ControladorSuma {

    private static final Logger logger = LoggerFactory.getLogger(ControladorSuma.class);
    private final ServicioSuma servicioSuma;

    @PostMapping("/sumar")
    @Operation(
        summary = "Sumar dos números",
        description = "Realiza la suma de dos números y retorna el resultado"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Operación exitosa",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespuestaCalculadora.class),
                examples = @ExampleObject(value = "{\"resultado\": 8.0}")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "object")
            )
        )
    })
    public ResponseEntity<RespuestaCalculadora> sumar(
        @Parameter(
            description = "Números a sumar",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitudCalculadora.class),
                examples = @ExampleObject(
                    value = "{\"numero1\": 5.0, \"numero2\": 3.0}"
                )
            )
        )
        @RequestBody SolicitudCalculadora solicitud
    ) {
        logger.info("Recibida solicitud de suma: numero1={}, numero2={}", solicitud.getNumero1(), solicitud.getNumero2());
        double resultado = servicioSuma.sumar(solicitud);
        logger.info("Resultado de la suma: {}", resultado);
        return ResponseEntity.ok(new RespuestaCalculadora(resultado));
    }
} 