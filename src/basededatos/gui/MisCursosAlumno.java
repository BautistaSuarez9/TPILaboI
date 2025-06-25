package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;
import basededatos.entidad.Alumno;
import basededatos.servicios.CursoService;
import basededatos.servicios.InscripcionService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MisCursosAlumno extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonVolver;
    private final int idAlumno;

    private InscripcionService inscripcionService;

    public MisCursosAlumno(int idAlumno) {
        this.idAlumno = idAlumno;

        try {
            inscripcionService = new InscripcionService();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error en servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Mis Cursos");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarMisCursos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(new String[]{"Curso", "Profesor", "Estado", "Nota Final"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> {
            new VentanaAlumno().setVisible(true);
            this.dispose();
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarMisCursos() {
        try {
            List<Inscripcion> inscripciones = inscripcionService.obtenerTodas();
            modelo.setRowCount(0);

            for (Inscripcion ins : inscripciones) {
                if (ins.getAlumno().getId() == idAlumno) {
                    Curso curso = ins.getCurso();
                    String nombreCurso = curso.getNombre();
                    String nombreProfesor = (curso.getProfesor() != null) ? curso.getProfesor().getNombre() : "No asignado";
                    String estado = ins.getEstado();
                    String nota = (ins.getNotaFinal() != null) ? String.valueOf(ins.getNotaFinal()) : "-";

                    modelo.addRow(new Object[]{nombreCurso, nombreProfesor, estado, nota});
                }
            }

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar inscripciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
