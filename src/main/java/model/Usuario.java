package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Crypt;

public class Usuario {
	private String nombre, password;
	private int presupuesto;
	private double tiempoDisponible;
	private boolean admin;
	private TipoAtraccion tipoAtraccionPreferida;
	private Itinerario itinerario;
	private HashMap<String, String> errors;

	public Usuario(String nombre, String password, int presupuesto, double tiempo, Boolean admin,TipoAtraccion tipoAtraccionPreferida,List<Sugerible> sugerenciasAceptadas) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.presupuesto = presupuesto;
		this.tiempoDisponible = tiempo;
		this.admin = admin;
		this.tipoAtraccionPreferida = tipoAtraccionPreferida;
		this.itinerario = new Itinerario(sugerenciasAceptadas);
	}

	public void addToItinerary(Atraccion attraction) {
		this.presupuesto -= attraction.getCost();
		this.tiempoDisponible -= attraction.getDuration();
		// TODO agregar a su lista
	}

	public boolean canAfford(Atraccion attraction) {
		return attraction.getCost() <= this.presupuesto;
	}

	public boolean canAttend(Atraccion attraction) {
		return attraction.getDuration() <= this.tiempoDisponible;
	}

	public boolean checkPassword(String password) {
		// this.password en realidad es el hash del password
		return Crypt.match(password, this.password);
	}

	public Boolean getAdmin() {
		return admin;
	}

	public String getPassword() {
		return password;
	}

	public double getTiempoDisponible() {
		return tiempoDisponible;
	}

	public int getPresupuesto() {
		return presupuesto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public boolean isNull() {
		return false;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public void setPresupuesto(Integer presupuesto) {
		this.presupuesto = presupuesto;
	}

	public void setPassword(String password) {
		this.password = Crypt.hash(password);
	}

	public void setTiempoDisponible(Double tiempoDisponible) {
		this.tiempoDisponible = tiempoDisponible;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void disminuirPresupuesto(int presupuesto) {
		this.presupuesto -= presupuesto;
	}

	public TipoAtraccion getTipoAtraccionPreferida() {
		return this.tipoAtraccionPreferida;
	}

	public void disminuirTiempoDisponible(double tiempo) {
		this.tiempoDisponible -= tiempo;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}
	
	public boolean isValid() {
		validate();
		return errors.isEmpty();
	}
	
	public void validate() {
		errors = new HashMap<String, String>();

		if (presupuesto < 0) {
			errors.put("coins", "No debe ser negativo");
		}
		if (tiempoDisponible < 0) {
			errors.put("time", "No debe ser negativo");
		}
	}

	public boolean puedeComprar(Sugerible sugerencia) {
		return this.presupuesto >= sugerencia.costoTotal() && Double.doubleToLongBits(this.tiempoDisponible) >= Double.doubleToLongBits(sugerencia.tiempoTotal())
				&& sugerencia.hayCupo() && !this.itinerario.incluyeAtraccion(sugerencia);
	}

	public void comprar(Sugerible sugerencia) {
		this.itinerario.agregarSugerencia(sugerencia);
		this.disminuirPresupuesto(sugerencia.costoTotal());
		this.disminuirTiempoDisponible(sugerencia.tiempoTotal());
		sugerencia.disminuirCupo();
	}
	
	public Map<String, String> getErrors() {
		return errors;
	}
}
