package basededatos.gui;

import basededatos.servicios.AlumnoService;
import basededatos.entidad.Alumno;


import javax.swing.*;
import java.awt.*;

public class VentanaAlumno extends JFrame {
    private JButton botonVolver;
    private JTextField campoLegajo;
    private JButton botonBuscar;
    private JLabel labelResultado;
    private JButton botonInscribirse;
    private int idAlumnoValidado;
    private JButton botonVerMisCursos;



    public VentanaAlumno() {
        setTitle("Alumno");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentes();
    }

    private void buscarAlumno() {
        try {
            int id = Integer.parseInt(campoLegajo.getText().trim());
            AlumnoService servicio = new AlumnoService();
            Alumno alumno = servicio.buscarPorId(id);

            if (alumno != null) {
                idAlumnoValidado = alumno.getId(); // 🟢 Guardás el ID
                labelResultado.setText("<html>Bienvenido, <b>" + alumno.getNombre() + "</b><br/>" +
                        "Email: " + alumno.getEmail() + "<br/>" +
                        "Límite de cursos: " + alumno.getLimiteCursos() + "</html>");
                botonInscribirse.setEnabled(true);
                botonVerMisCursos.setEnabled(true);
            } else {
                labelResultado.setText("Legajo no encontrado.");
            }

        } catch (NumberFormatException e) {
            labelResultado.setText("Debe ingresar un número de legajo válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al buscar alumno", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void inicializarComponentes() {
           campoLegajo = new JTextField(8);
           botonBuscar = new JButton("Buscar");
           botonVolver = new JButton("Volver");
           labelResultado = new JLabel(" ", SwingConstants.CENTER);
           botonInscribirse = new JButton("Inscribirse a un curso");
           botonInscribirse.setEnabled(false);


            JPanel panelBusqueda = new JPanel(new FlowLayout());
           panelBusqueda.add(new JLabel("Ingrese su legajo:"));
           panelBusqueda.add(campoLegajo);
           panelBusqueda.add(botonBuscar);

           JPanel panel = new JPanel(new BorderLayout(10, 10));
           panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
           panel.add(panelBusqueda, BorderLayout.NORTH);
           panel.add(labelResultado, BorderLayout.CENTER);

           JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelInferior.add(botonInscribirse);
            botonInscribirse.addActionListener(e -> {
                new InscripcionAlumno(idAlumnoValidado).setVisible(true);
                this.dispose();
            });
            botonVerMisCursos = new JButton("Ver mis cursos");
            botonVerMisCursos.setEnabled(false);
            panelInferior.add(botonVerMisCursos);

            botonVerMisCursos.addActionListener(e -> {
                new MisCursosAlumno(idAlumnoValidado).setVisible(true);
                this.dispose();
            });



            panelInferior.add(botonVolver);

           add(panel, BorderLayout.CENTER);
           add(panelInferior, BorderLayout.SOUTH);

           botonBuscar.addActionListener(e -> buscarAlumno());

           botonVolver.addActionListener(e -> {
               new LoginFrame().setVisible(true);
               this.dispose();
           });
   }
}
