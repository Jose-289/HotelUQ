package edu.hoteluq.patron.estructural.adapter;

import edu.hoteluq.modelo.DatosReporte;
import java.io.File;
import java.io.IOException;

public interface GeneradorReporte {
    void generarArchivo(DatosReporte datos, File archivoDestino) throws IOException;
    String getExtension();
    String getDescripcionFormato();
}