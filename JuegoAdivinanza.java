import java.util.Random;

class NumeroOculto {
    private int numeroAdivinar;
    private boolean adivinado = false;

    public NumeroOculto(int numero) {
        this.numeroAdivinar = numero;
    }

    public synchronized int propuestaNumero(int num) {
        if (adivinado) {
            return -1;
        }
        if (num == numeroAdivinar) {
            adivinado = true; 
            return 1;
        }
        return 0; 
    }
}

class HiloAdivinador extends Thread {
    private NumeroOculto numeroOculto;

    public HiloAdivinador(String nombre, NumeroOculto numeroOculto) {
        super(nombre); 
        this.numeroOculto = numeroOculto;
    }

    @Override
    public void run() {
        Random r = new Random();
        
        while (true) {
            int propuesta = r.nextInt(101); 
            int resultado = numeroOculto.propuestaNumero(propuesta);

            if (resultado == 1) {
                System.out.println(getName() + " Acerto el número " + propuesta);
                break; 
            } else if (resultado == -1) {
                System.out.println(getName() + " Otro hilo ya acerto");
                break; 
            }
        }
    }
}

public class JuegoAdivinanza {
    public static void main(String[] args) {
        Random r = new Random();
        int numeroGenerado = r.nextInt(101); 
        System.out.println("Número oculto generado: " + numeroGenerado);

        NumeroOculto recurso = new NumeroOculto(numeroGenerado);

        for (int i = 1; i <= 10; i++) {
            HiloAdivinador hilo = new HiloAdivinador("Hilo-" + i, recurso);
            hilo.start(); 
        }
    }
}