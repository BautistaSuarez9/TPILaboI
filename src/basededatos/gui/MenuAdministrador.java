package basededatos.gui;

import javax.swing.*;
import java.awt.*;

public class MenuAdministrador extends JFrame {
    private JButton botonAdministrador;
    private JButton botonAlumno;
    private JButton botonProfesor;
    private JButton botonCerrarSesion;

    public MenuAdministrador() {
        setTitle("Sistema de Alumnos - Menu Principal");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new GridLayout(5, 1, 10, 10)); // ahora 5 filas
        JLabel titulo = new JLabel("Seleccione una opción", SwingConstants.CENTER);

        botonAdministrador = new JButton("Administrador");
        botonAlumno = new JButton("Vista Alumno");
        botonProfesor = new JButton("Vista Profesor");
        botonCerrarSesion = new JButton("Cerrar sesión");

        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.add(titulo);
        panelPrincipal.add(botonAdministrador);
        panelPrincipal.add(botonAlumno);
        panelPrincipal.add(botonProfesor);
        panelPrincipal.add(botonCerrarSesion);

        add(panelPrincipal);

        botonAdministrador.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

        botonAlumno.addActionListener(e -> {
            new VentanaAlumno().setVisible(true);
            this.dispose();
        });

        botonProfesor.addActionListener(e -> {
            new VentanaProfesor().setVisible(true);
            this.dispose();
        });

        botonCerrarSesion.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
    }

}
