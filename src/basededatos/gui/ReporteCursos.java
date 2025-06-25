package basededatos.gui;

import basededatos.servicios.CursoService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReporteCursos extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonVolver;

    public ReporteCursos() {
        setTitle("Reporte de Recaudación por Curso");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        cargarReporte();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(
                new String[]{"Curso", "Inscriptos", "Precio", "Recaudación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void cargarReporte() {
        try {
            CursoService service = new CursoService();
            ArrayList<Object[]> datos = service.generarReporteCursos();
            modelo.setRowCount(0);
            for (Object[] fila : datos) {
                modelo.addRow(fila);
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
