package com.alura.apilibros.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title")
        String titulo,
        @JsonAlias("subjects")
        List<String> generos,
        @JsonAlias("authors")
        List<DatosAutor> informacionAutor,
        @JsonAlias("languages")
        List<String> lenguajes,
        @JsonAlias("download_count")
        Double cantidadDescargas

) {
}
