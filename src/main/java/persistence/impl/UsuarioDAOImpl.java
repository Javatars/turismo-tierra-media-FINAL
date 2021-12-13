package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dao.factory.DAOFactory;
import dao.interfaces.AtraccionDAO;
import dao.interfaces.PromocionDAO;
import excepciones.SQLExceptionCreated;
import model.Atraccion;
import model.Promocion;
import model.Sugerible;
import model.TipoAtraccion;
import model.Usuario;
import model.nullobjects.NullUser;
import persistence.UsuarioDAO;
import persistence.commons.ConnectionProvider;
import persistence.commons.MissingDataException;

public class UsuarioDAOImpl implements UsuarioDAO {

	public int insert(Usuario user) {
		try {
			String sql = "INSERT INTO USERS (USERNAME, PASSWORD, COINS, TIME) VALUES (?, ?, ?, ?)";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setInt(3, user.getCoins());
			statement.setDouble(4, user.getTime());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int update(Usuario user) {
		try {
			String sql = "UPDATE USERS SET COINS = ?, TIME = ? WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, user.getCoins());
			statement.setDouble(2, user.getTime());
			statement.setDouble(3, user.getId());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public int delete(Usuario user) {
		try {
			String sql = "DELETE FROM USERS WHERE USERNAME = ?";
			Connection conn = ConnectionProvider.getConnection();

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, user.getUsername());
			int rows = statement.executeUpdate();

			return rows;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	public Usuario findByUsername(String username) {
		try {
			String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			ResultSet resultados = statement.executeQuery();

			Usuario user = NullUser.build();

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new MissingDataException(e);
		}
	}

	public Usuario find(Integer id) {
		try {
			String sql = "SELECT * FROM USERS WHERE ID = ?";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultados = statement.executeQuery();

			Usuario user = NullUser.build();

			if (resultados.next()) {
				user = toUser(resultados);
			}

			return user;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

/*	public int countAll() {
		try {
			String sql = "SELECT COUNT(1) AS TOTAL FROM USERS";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			resultados.next();
			int total = resultados.getInt("TOTAL");

			return total;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}*/

	public List<Usuario> findAll() {
		try {
			String sql = "SELECT * FROM Usuario";
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet resultados = statement.executeQuery();

			List<Usuario> usuarios = new ArrayList<Usuario>();
			while (resultados.next()) {
				usuarios.add(toUser(resultados));
			}

			return usuarios;
		} catch (Exception e) {
			throw new MissingDataException(e);
		}
	}

	private Usuario toUser(ResultSet resultados) throws SQLException{
			AtraccionDAO atraccionDao = DAOFactory.getAtraccionDAO();
			PromocionDAO promocionDao = DAOFactory.getPromocionDAO();
			List<Sugerible> sugerenciasAceptadas = new ArrayList<Sugerible>();
			List<Atraccion> atracciones = atraccionDao.findAtraccionesByNombreUsuario(resultados.getString(1));
			sugerenciasAceptadas.addAll(atracciones);
			List<Promocion> promociones = promocionDao.findPromocionesByNombreUsuario(resultados.getString(1));
			sugerenciasAceptadas.addAll(promociones);
			return new Usuario(resultados.getString(1),resultados.getInt(2),resultados.getDouble(3),TipoAtraccion.valueOf(resultados.getString(4)),sugerenciasAceptadas);
	}
/*	private Usuario toUser(ResultSet userRegister) throws SQLException {
		return new Usuario(userRegister.getString(1), userRegister.getString(2),
				userRegister.getInt(4), userRegister.getDouble(5), userRegister.getBoolean(3));
	}*/

}
