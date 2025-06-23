package basededatos.dao;

import basededatos.dao.DAOException;
import java.util.ArrayList;

public interface IDAO<T> {
    void guardar(T elemento) throws DAOException;
    void modificar(T elemento) throws DAOException;
    void eliminar(int id) throws DAOException;
    T buscar(int id) throws DAOException;
    ArrayList<T> buscarTodos() throws DAOException;
}
