package com.alura.apilibros.principal;

import com.alura.apilibros.model.DatosGenerales;
import com.alura.apilibros.model.DatosLibro;
import com.alura.apilibros.service.ConsumoAPI;
import com.alura.apilibros.service.ConvierteDatosAClase;

import javax.swing.text.html.Option;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatosAClase conversor = new ConvierteDatosAClase();
    private Scanner lectura = new Scanner(System.in);
    public void programaPrincipal(){

        /* Peticion get hacia la API, aqui se optiene el json que devuelve la API
           Esto funciona a traves del objeto del tipo ConsumoAPI en donde llamamos
           a su metodo obtenerDatos y le pasamos la URL de la API para hacer la
           peticion del tipo GET

           Dentro de la clase ConsumoAPI tenemos la logica para hacer que
           el cliente haga la peticion http
         */

        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

        /* Aqui se convierte ese json a un objeto del tipo class "Datos Generales", en donde
           se mapea el campo "results" que es una lista que contiene todos los libros de la
           API, entonces esto va a devolver una Lista (List)
         */
        var datosGenerales = conversor.obtenerDatos(json, DatosGenerales.class);
        System.out.println(datosGenerales);


        /* Vamos a mostrar el top 10 de libros mas descargados
        .sorted(Comparator.comparing(DatosLibro::cantidadDescargas).reversed()):
        Aqui estamos ordenando una lista de objetos del tipo DatosLibro de forma
        descendente, basandonos en el atributo cantidadDescargas.
        - .sorted(Comparator.comparing(...)): Este metodo se utiliza para ordenar
        una lista de objetos, toma como argumento Comparator, que es un objeto
        que define COMO se COMPARAN DOS OBJETOS, En este caso, estamos utilizando
        Comparator.comparing(...) para crear un Comparator que compara los objetos
        DatosLibro basándose en el atributo cantidadDescargas
        - (DatosLibro::cantidadDescargas): Aqui estamos haciendo una comparacion en
           base al parametro "cantidadDescargas" de la clase DatosLibro, para ello
           usamos una referencia de metodo para asi pasarlo como argumento.
        - .reversed(): es para mostrar los resultados de mayor a menor, por que
           por deafult se muestra de menor a mayor.

         */
        System.out.println("Top 10 libros mas descargados");
        datosGenerales.resultados().stream()
                .sorted(Comparator.comparing(DatosLibro::cantidadDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = lectura.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro
                .replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosGenerales.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado: \n"
                    + libroBuscado.get().titulo() + "\n"
                    + libroBuscado.get().generos()  + "\n"
                    + libroBuscado.get().informacionAutor()+ "\n"
                    + libroBuscado.get().cantidadDescargas()+ "\n"
                    + libroBuscado.get().lenguajes()+ "\n");
        }else{
            System.out.println("Libro no encontrado :(");
        }

        /* Trabajando con estadisticas
           .filter(d -> d.cantidadDescargas() > 0): Estamos filtrando el flujo de datos
           para quedarnos solo con aquellos libros que tienen una cantidad de descargas
           mayor que cero. Esto es importante para evitar que las descargas cero afecten
            nuestras estadísticas.

           .collect(Collectors.summarizingDouble(DatosLibro::cantidadDescargas)): Aquí es donde
            realmente calculamos las estadísticas.

            collect(): Este método nos permite recopilar los datos del flujo y aplicar una operación de reducción.
            Collectors.summarizingDouble(): Este método nos proporciona un DoubleSummaryStatistics que contiene
            las estadísticas de los datos.
            DatosLibro::cantidadDescargas: Esta es una referencia al método cantidadDescargas() de la clase
            DatosLibro. Le estamos diciendo a summarizingDouble() que use este método para obtener el valor de
             la descarga de cada libro.
         */
        DoubleSummaryStatistics est = datosGenerales.resultados().stream()
                .filter(d-> d.cantidadDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibro::cantidadDescargas));

        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad maxima de descargas: " + est.getMax());
        System.out.println("Cantidad minima de descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular las estadisticas: "
        + est.getCount());

    }
}
