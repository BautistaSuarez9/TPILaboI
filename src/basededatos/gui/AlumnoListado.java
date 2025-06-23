package basededatos.gui;

import basededatos.entidad.Alumno;
import basededatos.servicios.AlumnoService;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AlumnoListado extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonActualizar;
    private JButton botonEliminar;
    private JButton botonVolver;
    private AlumnoService servicio;

    public AlumnoListado() {
        setTitle("Listado de Alumnos");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

            servicio = new AlumnoService();

        inicializarComponentes();
        cargarAlumnos();
    }


    private void inicializarComponentes() {
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Límite"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }
        };

        tabla = new JTable(modelo);

        modelo.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                try {
                    int id = (int) modelo.getValueAt(fila, 0);
                    String nombre = ((String) modelo.getValueAt(fila, 1)).trim();
                    String email = ((String) modelo.getValueAt(fila, 2)).trim();
                    int limite = Integer.parseInt(modelo.getValueAt(fila, 3).toString());

                    Alumno alumnoActualizado = new Alumno(id, nombre, email, limite);
                    servicio.modificarAlumno(alumnoActualizado);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El límite de cursos debe ser un número entero.",
                            "Formato inválido", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Error al modificar alumno", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(tabla);

        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonVolver = new JButton("Volver");

        botonActualizar.addActionListener(this::accionActualizar);
        botonEliminar.addActionListener(this::accionEliminar);
        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

        JPanel botones = new JPanel();
        botones.add(botonActualizar);
        botones.add(botonEliminar);
        botones.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarAlumnos() {
        modelo.setRowCount(0);
        try {
            ArrayList<Alumno> alumnos = servicio.obtenerTodos();
            for (Alumno a : alumnos) {
                modelo.addRow(new Object[]{
                        a.getId(), a.getNombre(), a.getEmail(), a.getLimiteCursos()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar alumnos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizar(ActionEvent e) {
        cargarAlumnos();
    }

    private void accionEliminar(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelo.getValueAt(fila, 0);
            String nombre = (String) modelo.getValueAt(fila, 1);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar al alumno \"" + nombre + "\"?\nEsta acción no se puede deshacer.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    servicio.eliminarAlumno(id);
                    cargarAlumnos();
                    JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un alumno para eliminar.");
        }
    }

}
