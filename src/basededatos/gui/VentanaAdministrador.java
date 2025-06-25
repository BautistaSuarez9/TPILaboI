package basededatos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaAdministrador extends JFrame {
    private JButton botonFormulario;
    private JButton botonListado;
    private JButton botonCursosForm;
    private JButton botonCursos;
    private JButton botonVolver;


    public VentanaAdministrador() {
        setTitle("Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        botonFormulario = new JButton("Agregar Alumno");
        botonListado = new JButton("Ver/Editar Alumnos");
        botonVolver = new JButton("Volver al Menú");
        botonCursos = new JButton("Crear Curso");
        botonCursosForm = new JButton("Ver/Editas Cursos");

        JButton botonReporte = new JButton("Reporte de Recaudación");
        botonReporte.addActionListener(e -> {
            new ReporteCursos().setVisible(true);
            this.dispose();
        });




        panel.add(new JLabel("Administrador - Acciones", SwingConstants.CENTER));
        panel.add(botonFormulario);
        panel.add(botonListado);
        panel.add(botonCursos);
        panel.add(botonCursosForm);
        panel.add(botonReporte);
        panel.add(botonVolver);


        add(panel);

        botonFormulario.addActionListener((ActionEvent e) -> {
            new AlumnoFormulario().setVisible(true);
            this.dispose();
        });

        botonCursos.addActionListener(e -> {
            new CursoFormulario().setVisible(true);
            this.dispose();
        });

        botonCursosForm.addActionListener(e -> {
            new CursoListado().setVisible(true);
            this.dispose();
        });



        botonListado.addActionListener((ActionEvent e) -> {
            new AlumnoListado().setVisible(true);
            this.dispose();
        });

        botonVolver.addActionListener((ActionEvent e) -> {
            new MenuAdministrador().setVisible(true);
            this.dispose();
        });
    }
}
