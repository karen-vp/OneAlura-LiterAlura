package com.alura.apilibros.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* Implementamos la interfaz IConvierteDatos porque necesitamos
   incluir su metodo y definir su funcionamiento
 */
public class ConvierteDatosAClase implements IConvierteDatos{
    /* ObjectMapper
     Nos permite leer los valores o mapearlos que vienen de la API
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    /*NOTA:
        <T> es el parámetro de tipo genérico.
        T es el nombre que le damos a ese parámetro.
        El metodo obtener datos es el que viene
        en la firma de la interfaz IConvierteDatos
     */
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
