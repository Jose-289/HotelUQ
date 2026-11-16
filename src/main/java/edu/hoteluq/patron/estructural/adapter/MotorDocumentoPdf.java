package edu.hoteluq.patron.estructural.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class MotorDocumentoPdf {

    public void renderizarPdf(File destino, String encabezado, List<String> parrafos) throws IOException {
        StringBuilder flujo = new StringBuilder();
        flujo.append("BT\n/F1 14 Tf\n50 780 Td\n");
        flujo.append("(").append(limpiarTextoPdf(encabezado)).append(") Tj\n");
        flujo.append("/F1 10 Tf\n0 -22 Td\n");

        for (String p : parrafos) {
            flujo.append("(").append(limpiarTextoPdf(p)).append(") Tj\n0 -15 Td\n");
        }
        flujo.append("ET\n");

        byte[] streamBytes = flujo.toString().getBytes(StandardCharsets.ISO_8859_1);
        ByteArrayOutputStream pdf = new ByteArrayOutputStream();
        List<Integer> offsets = new ArrayList<>();

        escribir(pdf, "%PDF-1.4\n");

        offsets.add(pdf.size());
        escribir(pdf, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

        offsets.add(pdf.size());
        escribir(pdf, "2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n");

        offsets.add(pdf.size());
        escribir(pdf, "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 842] " +
                "/Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>\nendobj\n");

        offsets.add(pdf.size());
        escribir(pdf, "4 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Courier >>\nendobj\n");

        offsets.add(pdf.size());
        escribir(pdf, "5 0 obj\n<< /Length " + streamBytes.length + " >>\nstream\n");
        pdf.write(streamBytes);
        escribir(pdf, "\nendstream\nendobj\n");

        int xrefPos = pdf.size();
        escribir(pdf, "xref\n0 " + (offsets.size() + 1) + "\n");
        escribir(pdf, "0000000000 65535 f \n");
        for (int off : offsets) {
            escribir(pdf, String.format("%010d 00000 n \n", off));
        }

        escribir(pdf, "trailer\n<< /Size " + (offsets.size() + 1) + " /Root 1 0 R >>\n");
        escribir(pdf, "startxref\n" + xrefPos + "\n%%EOF");

        try (FileOutputStream fos = new FileOutputStream(destino)) {
            fos.write(pdf.toByteArray());
        }
    }

    private String limpiarTextoPdf(String texto) {
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalizado.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    private void escribir(ByteArrayOutputStream out, String s) throws IOException {
        out.write(s.getBytes(StandardCharsets.ISO_8859_1));
    }
}