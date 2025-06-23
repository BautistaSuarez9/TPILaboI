package basededatos.gui;

import basededatos.entidad.Curso;
import basededatos.servicios.CursoService;

import javax.swing.*;
import java.awt.*;

public class CursoFormulario extends JFrame {
    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JTextField campoCupo;
    private JTextField campoNota;
    private JButton botonGuardar;
    private JButton botonVolver;

    public CursoFormulario() {
        setTitle("Alta de Curso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        campoNombre = new JTextField();
        campoPrecio = new JTextField();
        campoCupo = new JTextField();
        campoNota = new JTextField();

        botonGuardar = new JButton("Guardar");
        botonVolver = new JButton("Volver");

        panel.add(new JLabel("Nombre del curso:"));
        panel.add(campoNombre);

        panel.add(new JLabel("Precio:"));
        panel.add(campoPrecio);

        panel.add(new JLabel("Cupo máximo:"));
        panel.add(campoCupo);

        panel.add(new JLabel("Nota de aprobación:"));
        panel.add(campoNota);

        panel.add(botonGuardar);
        panel.add(botonVolver);

        add(panel);

        botonGuardar.addActionListener(e -> guardarCurso());
        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });
    }

    private void guardarCurso() {
        try {
            String nombre = campoNombre.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            int cupo = Integer.parseInt(campoCupo.getText().trim());
            double nota = Double.parseDouble(campoNota.getText().trim());

            Curso curso = new Curso(nombre, precio, cupo, nota);
            CursoService servicio = new CursoService();
            servicio.guardarCurso(curso);

            JOptionPane.showMessageDialog(this, "Curso guardado correctamente.");

            campoNombre.setText("");
            campoPrecio.setText("");
            campoCupo.setText("");
            campoNota.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Precio, cupo y nota deben ser valores numéricos.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
