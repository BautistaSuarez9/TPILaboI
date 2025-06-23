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

public class MisCursosAlumno extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonVolver;
    private int idAlumno;

    private InscripcionService inscripcionService;
    private CursoService cursoService;

    public MisCursosAlumno(int idAlumno) {
        this.idAlumno = idAlumno;

        try {
            inscripcionService = new InscripcionService();
            cursoService = new CursoService();
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
        modelo = new DefaultTableModel(new String[]{"Curso", "Estado", "Nota Final"}, 0) {
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
            ArrayList<Inscripcion> inscripciones = inscripcionService.obtenerTodas();
            modelo.setRowCount(0);

            for (Inscripcion ins : inscripciones) {
                if (ins.getAlumnoId() == idAlumno) {
                    Curso curso = cursoService.buscarPorId(ins.getCursoId());
                    String nombreCurso = (curso != null) ? curso.getNombre() : "Curso no encontrado";
                    String estado = ins.getEstado();
                    String nota = (ins.getNotaFinal() != null) ? String.valueOf(ins.getNotaFinal()) : "-";
                    modelo.addRow(new Object[]{nombreCurso, estado, nota});
                }
            }

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar inscripciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
