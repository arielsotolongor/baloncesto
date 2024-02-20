package codigo;

public class Jugador {

    private String nombre;
    private int votos;

    public Jugador() {}

    public Jugador(String nombre, int votos) {
        this.nombre = nombre;
        this.votos = votos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}
