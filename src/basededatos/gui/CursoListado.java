package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.servicios.CursoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CursoListado extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton botonActualizar;
    private JButton botonEliminar;
    private JButton botonVolver;
    private CursoService servicio;

    public CursoListado() {
        setTitle("Listado de Cursos");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            servicio = new CursoService();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al inicializar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        inicializarComponentes();
        cargarCursos();
    }

    private void inicializarComponentes() {
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Cupo", "Nota Aprobación"}, 0) {
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
                    String nuevoNombre = modelo.getValueAt(fila, 1).toString();
                    double nuevoPrecio = Double.parseDouble(modelo.getValueAt(fila, 2).toString());

                    Curso curso = servicio.buscarPorId(id);
                    curso.setNombre(nuevoNombre);
                    curso.setPrecio(nuevoPrecio);
                    servicio.modificarCurso(curso);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabla);

        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonVolver = new JButton("Volver");

        botonActualizar.addActionListener(e -> cargarCursos());

        botonEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modelo.getValueAt(fila, 0);
                String nombre = modelo.getValueAt(fila, 1).toString();

                int confirmacion = JOptionPane.showConfirmDialog(this,
                        "¿Desea eliminar el curso \"" + nombre + "\"?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    try {
                        servicio.eliminarCurso(id);
                        cargarCursos();
                        JOptionPane.showMessageDialog(this, "Curso eliminado.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un curso para eliminar.");
            }
        });

        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonVolver);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCursos() {
        modelo.setRowCount(0);
        try {
            ArrayList<Curso> cursos = servicio.obtenerTodos();
            for (Curso c : cursos) {
                modelo.addRow(new Object[]{
                        c.getId(), c.getNombre(), c.getPrecio(), c.getCupoMaximo(), c.getNotaAprobacion()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
