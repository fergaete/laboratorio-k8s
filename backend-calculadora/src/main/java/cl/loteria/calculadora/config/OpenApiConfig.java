package cl.loteria.calculadora.config;

import cl.loteria.calculadora.model.SolicitudCalculadora;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.Components;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.version}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Calculadora")
                        .version(apiVersion)
                        .description("API para realizar operaciones matemáticas básicas")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("desarrollo@loteria.cl")
                                .url("https://www.loteria.cl"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addResponses("200", new ApiResponse()
                                .description("Operación exitosa")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/RespuestaCalculadora")))))
                        .addResponses("400", new ApiResponse()
                                .description("Solicitud inválida")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().type("object")))))
                        .addExamples("sumaEjemplo", new Example()
                                .value(new SolicitudCalculadora(5.0, 3.0)))
                        .addExamples("restaEjemplo", new Example()
                                .value(new SolicitudCalculadora(10.0, 4.0))));
    }
} 