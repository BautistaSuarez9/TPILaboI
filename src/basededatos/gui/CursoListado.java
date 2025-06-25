package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.servicios.CursoService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CursoListado extends JFrame {
    private JTable tabla;
    private JButton botonVolver;

    public CursoListado() {
        setTitle("Listado de Cursos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        cargarCursos();
    }

    private void inicializarComponentes() {
        tabla = new JTable();
        botonVolver = new JButton("Volver");

        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botonVolver, BorderLayout.SOUTH);
    }

    private void cargarCursos() {
        try {
            CursoService servicio = new CursoService();
            List<Curso> cursos = servicio.obtenerTodos();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Precio");
            modelo.addColumn("Cupo Máximo");
            modelo.addColumn("Nota Aprobación");
            modelo.addColumn("Profesor");

            for (Curso c : cursos) {
                modelo.addRow(new Object[]{
                        c.getId(),
                        c.getNombre(),
                        c.getPrecio(),
                        c.getCupoMaximo(),
                        c.getNotaAprobacion(),
                        (c.getProfesor() != null) ? c.getProfesor().getNombre() : "Sin asignar"
                });
            }

            tabla.setModel(modelo);

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
