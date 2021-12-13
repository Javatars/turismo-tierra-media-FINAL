package services;

import java.util.ArrayList;
import java.util.List;

import model.Atraccion;
import model.Sugerible;
import model.TipoAtraccion;
import model.Usuario;
import persistence.AtraccionDAO;
import persistence.commons.DAOFactory;

public class UserService {

	public List<Usuario> list() {
		return DAOFactory.getUserDAO().findAll();
	}

	public Usuario create(String username, String password, Integer coins, Double time, TipoAtraccion tipo) {
		Usuario user = new Usuario(username, password, coins, time, false, tipo, new ArrayList<Sugerible>());
		user.setPassword(password);

		if (user.isValid()) {
			DAOFactory.getUserDAO().insert(user);
			// XXX: si no devuelve "1", es que hubo más errores
		}

		return user;
	}
}
