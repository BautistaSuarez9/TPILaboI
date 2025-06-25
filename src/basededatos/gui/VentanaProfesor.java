package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.servicios.CursoService;
import basededatos.servicios.ServiceException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaProfesor extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonGestionar;
    private JButton botonVolver;
    private CursoService cursoService;

    private static final int COL_ID = 0;
    private static final int COL_NOMBRE = 1;
    private static final int COL_CUPO = 2;
    private static final int COL_PRECIO = 3;
    private static final int COL_NOTA = 4;

    public VentanaProfesor() {
        try {
            cursoService = new CursoService();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar el servicio de cursos: " + e.getMessage());
            return;
        }

        setTitle("Cursos del Profesor");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
        cargarCursos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Cupo", "Precio", "Nota Aprobación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == COL_NOMBRE;
            }
        };

        tabla = new JTable(modelo);

        modelo.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                if (columna == COL_NOMBRE) {
                    try {
                        int id = (int) modelo.getValueAt(fila, COL_ID);
                        String nuevoNombre = modelo.getValueAt(fila, COL_NOMBRE).toString().trim();

                        if (nuevoNombre.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                            return;
                        }

                        Curso curso = cursoService.buscarPorId(id);
                        curso.setNombre(nuevoNombre);
                        cursoService.modificarCurso(curso);
                        JOptionPane.showMessageDialog(this, "Nombre del curso actualizado.");

                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar curso: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
                    }
                }
            }
        });

        botonGestionar = new JButton("Gestionar alumnos");
        botonVolver = new JButton("Cerrar sesión");

        botonGestionar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int cursoId = (int) modelo.getValueAt(fila, COL_ID);
                new GestionAlumnosCurso(cursoId).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un curso.");
            }
        });

        botonVolver.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonGestionar);
        panelBotones.add(botonVolver);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCursos() {
        try {
            ArrayList<Curso> cursos = cursoService.obtenerTodos();
            modelo.setRowCount(0);
            for (Curso c : cursos) {
                modelo.addRow(new Object[]{
                        c.getId(),
                        c.getNombre(),
                        c.getCupoMaximo(),
                        c.getPrecio(),
                        c.getNotaAprobacion()
                });
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los cursos: " + e.getMessage());
        }
    }
}
