package basededatos.gui;

import basededatos.servicios.AlumnoService;
import basededatos.entidad.Alumno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlumnoFormulario extends JFrame {
    private JTextField campoNombre;
    private JTextField campoEmail;
    private JTextField campoLimite;
    private JButton botonGuardar;
    private JButton botonVolver;
    private boolean modoEdicion = false;
    private Alumno alumnoEditado;

    public AlumnoFormulario(Alumno alumno) {
        this();
        this.modoEdicion = true;
        this.alumnoEditado = alumno;

        campoNombre.setText(alumno.getNombre());
        campoEmail.setText(alumno.getEmail());
        campoLimite.setText(String.valueOf(alumno.getLimiteCursos()));
    }

    public AlumnoFormulario() {
        setTitle("Carga de Alumno");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel labelNombre = new JLabel("Nombre:");
        campoNombre = new JTextField();

        JLabel labelEmail = new JLabel("Email:");
        campoEmail = new JTextField();

        JLabel labelLimite = new JLabel("Límite de Cursos:");
        campoLimite = new JTextField();

        botonGuardar = new JButton("Guardar");
        botonVolver = new JButton("Volver");


        panel.add(labelNombre);
        panel.add(campoNombre);
        panel.add(labelEmail);
        panel.add(campoEmail);
        panel.add(labelLimite);
        panel.add(campoLimite);
        panel.add(new JLabel());
        panel.add(botonGuardar);

        add(panel);
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(botonGuardar);
        panelBotones.add(botonVolver);

        add(panelBotones, BorderLayout.SOUTH);


        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAlumno();
            }
        });
        botonVolver.addActionListener(e -> {
            new VentanaAdministrador().setVisible(true);
            this.dispose();
        });

    }

    private void guardarAlumno() {
        try {
            Alumno alumno = new Alumno();
            alumno.setNombre(campoNombre.getText().trim());
            alumno.setEmail(campoEmail.getText().trim());
            alumno.setLimiteCursos(Integer.parseInt(campoLimite.getText().trim()));

            AlumnoService servicio = new AlumnoService();

            if (modoEdicion && alumnoEditado != null) {
                alumno.setId(alumnoEditado.getId());
                servicio.modificarAlumno(alumno);
                JOptionPane.showMessageDialog(this, "Alumno modificado correctamente.");
            } else {
                servicio.guardarAlumno(alumno);
                JOptionPane.showMessageDialog(this, "Alumno guardado correctamente.");
                campoNombre.setText("");
                campoEmail.setText("");
                campoLimite.setText("");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El límite de cursos debe ser un número válido.",
                    "Error de Validación",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AlumnoFormulario().setVisible(true);
        });
    }*/
}
