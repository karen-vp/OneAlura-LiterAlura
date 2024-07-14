package com.alura.apilibros.service;

public interface IConvierteDatos {

    // <T> T le especificamos que estamos trabajando con tipos de datos genericos
    <T> T obtenerDatos(String json, Class<T> clase);
}
