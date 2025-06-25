package basededatos;

import basededatos.gui.LoginFrame;
import basededatos.servicios.DBInitService;
import basededatos.servicios.ServiceException;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            DBInitService.inicializarBaseDeDatos();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al inicializar la base de datos:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}