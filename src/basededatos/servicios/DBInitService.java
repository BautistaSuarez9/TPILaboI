package basededatos.servicios;

import basededatos.dao.AlumnoDAO;
import basededatos.dao.DAOException;

public class DBInitService {
    public static void inicializarBaseDeDatos() throws ServiceException {
        try {
            AlumnoDAO dao = new AlumnoDAO();
            dao.crearTablaSiNoExiste();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar la base de datos", e);
        }
    }
}
