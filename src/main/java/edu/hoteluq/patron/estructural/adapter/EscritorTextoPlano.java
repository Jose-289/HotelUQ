package edu.hoteluq.patron.estructural.adapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EscritorTextoPlano {

    public void escribirLineasEnArchivo(File destino, List<String> lineas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino, StandardCharsets.UTF_8))) {
            for (String linea : lineas) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }
}