package basededatos.gui;

import basededatos.entidad.Alumno;
import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;
import basededatos.servicios.AlumnoService;
import basededatos.servicios.CursoService;
import basededatos.servicios.InscripcionService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GestionAlumnosCurso extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonGuardar, botonVolver;
    private int cursoId;
    private Curso curso;

    private AlumnoService alumnoService;
    private InscripcionService inscripcionService;
    private CursoService cursoService;

    public GestionAlumnosCurso(int cursoId) {
        this.cursoId = cursoId;

        try {
            alumnoService = new AlumnoService();
            inscripcionService = new InscripcionService();
            cursoService = new CursoService();
            this.curso = cursoService.buscarPorId(cursoId);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
            return;
        }

        setTitle("Gestión de Alumnos - " + curso.getNombre());
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarInscriptos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(new String[]{"ID Alumno", "Nombre", "Email", "Nota Final", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return (columnIndex == 3) ? Integer.class : String.class;
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        botonGuardar = new JButton("Guardar Notas");
        botonVolver = new JButton("Volver");

        botonGuardar.addActionListener(e -> guardarNotas());
        botonVolver.addActionListener(e -> {
            new VentanaProfesor().setVisible(true);
            this.dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonGuardar);
        panelBotones.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarInscriptos() {
        try {
            ArrayList<Inscripcion> inscripciones = inscripcionService.obtenerTodas();
            modelo.setRowCount(0);

            for (Inscripcion ins : inscripciones) {
                if (ins.getCursoId() == cursoId) {
                    Alumno alumno = alumnoService.buscarPorId(ins.getAlumnoId());
                    if (alumno != null) {
                        modelo.addRow(new Object[]{
                                alumno.getId(),
                                alumno.getNombre(),
                                alumno.getEmail(),
                                ins.getNotaFinal() != null ? ins.getNotaFinal() : null,
                                ins.getEstado()
                        });
                    }
                }
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar inscripciones: " + e.getMessage());
        }
    }

    private void guardarNotas() {
        try {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idAlumno = (int) modelo.getValueAt(i, 0);
                Object notaObj = modelo.getValueAt(i, 3);

                if (notaObj == null) continue;

                int nota = ((Number) notaObj).intValue();
                inscripcionService.actualizarNotaYDeterminarEstado(idAlumno, cursoId, nota);

            }

            JOptionPane.showMessageDialog(this, "Notas guardadas y estados actualizados.");
            cargarInscriptos();

        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }
}
