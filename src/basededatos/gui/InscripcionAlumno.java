package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;
import basededatos.servicios.CursoService;
import basededatos.servicios.InscripcionService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InscripcionAlumno extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonInscribirse;
    private JButton botonVolver;
    private int idAlumno;

    private CursoService cursoService;
    private InscripcionService inscripcionService;

    public InscripcionAlumno(int idAlumno) {
        this.idAlumno = idAlumno;

        try {
            cursoService = new CursoService();
            inscripcionService = new InscripcionService();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Inscripción a Cursos");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarCursos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Cupo", "Nota Aprobación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        botonInscribirse = new JButton("Inscribirse");
        botonVolver = new JButton("Volver");

        botonInscribirse.addActionListener(e -> inscribirseEnCurso());
        botonVolver.addActionListener(e -> {
            new VentanaAlumno().setVisible(true);
            this.dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonInscribirse);
        panelBotones.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCursos() {
        try {
            ArrayList<Curso> cursos = cursoService.obtenerTodos();
            modelo.setRowCount(0);
            for (Curso c : cursos) {
                modelo.addRow(new Object[]{
                        c.getId(), c.getNombre(), c.getPrecio(), c.getCupoMaximo(), c.getNotaAprobacion()
                });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inscribirseEnCurso() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso.");
            return;
        }

        int cursoId = (int) modelo.getValueAt(fila, 0);

        try {
            cursoService.inscribirAlumno(idAlumno, cursoId);
            JOptionPane.showMessageDialog(this, "Inscripción exitosa.");
            this.dispose();
            new VentanaAlumno().setVisible(true);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al inscribir", JOptionPane.ERROR_MESSAGE);
        }

    }
}
