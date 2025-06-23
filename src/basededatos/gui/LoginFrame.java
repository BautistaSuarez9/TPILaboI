package basededatos.gui;

import basededatos.entidad.Usuario;
import basededatos.servicios.ServiceException;
import basededatos.servicios.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIngresar;

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        campoUsuario = new JTextField();
        panel.add(campoUsuario);

        panel.add(new JLabel("Contraseña:"));
        campoContrasena = new JPasswordField();
        panel.add(campoContrasena);

        panel.add(new JLabel());
        botonIngresar = new JButton("Ingresar");
        panel.add(botonIngresar);

        add(panel);

        botonIngresar.addActionListener(e -> {
            try {
                String usuario = campoUsuario.getText();
                String contrasena = new String(campoContrasena.getPassword());

                UsuarioService servicio = new UsuarioService();
                Usuario u = servicio.login(usuario, contrasena);

                switch (u.getRol().toLowerCase()) {
                    case "admin":
                        new MenuAdministrador().setVisible(true);
                        break;
                    case "alumno":
                        new VentanaAlumno().setVisible(true);
                        break;
                    case "profesor":
                        new VentanaProfesor().setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Rol no reconocido.");
                        return;
                }

                this.dispose();

            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
