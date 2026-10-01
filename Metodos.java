import java.util.Queue;
import java.util.Scanner;

public class Metodos {
    private int siguienteTurno = 1;

    public Queue<ObjTurnos> IngresarCliente(Queue<ObjTurnos> cola, Metodos m, Validaciones v, Scanner sc) {
        boolean continuar = true;
        while (continuar) {
            ObjTurnos o = new ObjTurnos();
            System.out.println("Ingrese su numero de identificacion: ");
            o.setIdentificacion(v.ValidarString(sc));
            System.out.println("Ingrese su nombre: ");
            o.setNombre(v.ValidarString(sc));
            System.out.println("Ingrese su edad");
            o.setEdad(v.ValidarEntero(sc));
            o.setNumTurno(m.ValidarTurno());
            o.setTipoTramite(m.TipoTramite(sc, v));
            o.setCondicionAtencionEspecial(m.CondicionEspecial(sc, v));
            o.setEstado(1); // 1 pendiente
            cola.offer(o);
            System.out.println("Cliente registrado correctamente.");
            System.out.println("Su turno es: " + o.getNumTurno());
            System.out.println("Desea Agregar mas clientes 1) si , 2) no ");
            int opt = v.ValidarEntero(sc);
            while (opt < 1 || opt > 2) {
                System.out.println("Opción no válida. Seleccione 1 o 2.");
                opt = v.ValidarEntero(sc);
            }
            if (opt == 2) {
                System.out.println("Vuelve Pronto");
                continuar = false;
            }

        }
        return cola;
    }

    public int ValidarTurno() {
        int turno = siguienteTurno;
        siguienteTurno++;
        return turno;
    }

    public String TipoTramite(Scanner sc, Validaciones v) {
        String mensaje = "";
        System.out.println("Gracias por preferir al banco nacho lee 3, por favor eliga su tramite");
        System.out.println("1) Retiro de dinero");
        System.out.println("2) Consignacion dinero");
        System.out.println("3) Compra de servicios");
        System.out.println("4) Compra de divisas");
        System.out.println("5) Pago de creditos");
        System.out.println("6) Asesoria especializada");
        int opt = v.ValidarEntero(sc);
        while (opt < 1 || opt > 6) {
            System.out.println("Opcion no valida eliga un valor entre 1 y 6");
            opt = v.ValidarEntero(sc);
        }
        switch (opt) {
            case 1:
                mensaje = "Retiro de dinero";
                break;
            case 2:
                mensaje = "Consignacion dinero";
                break;
            case 3:
                mensaje = "Compra de servicios";
                break;
            case 4:
                mensaje = "Compra de divisas";
                break;
            case 5:
                mensaje = "Pago de creditos";
                break;

            case 6:
                mensaje = "Asesoria especializada";
                break;
        }
        return mensaje;
    }

    public int CondicionEspecial(Scanner sc, Validaciones v) {
        System.out.println("¿Tiene alguna condición para atención especial?");
        System.out.println("1) Sí");
        System.out.println("2) No");
        System.out.println("Recuerde que se solicitará validación");

        int opcion = v.ValidarEntero(sc);

        while (opcion < 1 || opcion > 2) {
            System.out.println("Opción no válida. Seleccione 1 o 2.");
            opcion = v.ValidarEntero(sc);
        }

        return opcion;
    }

    public String MostrarClientesEsperando(Queue<ObjTurnos> cola) {

        boolean hayPendientes = false;

        for (ObjTurnos o : cola) {

            if (o.getEstado() == 1) {

                hayPendientes = true;

                System.out.println("-----------------------------");
                System.out.println("Turno: " + o.getNumTurno());
                System.out.println("Identificación: " + o.getIdentificacion());
                System.out.println("Nombre: " + o.getNombre());
                System.out.println("Edad: " + o.getEdad());
                System.out.println("Tipo de trámite: " + o.getTipoTramite());

                if (o.getCondicionAtencionEspecial() == 1) {
                    System.out.println("Atención: Preferencial");
                } else {
                    System.out.println("Atención: Normal");
                }

                System.out.println("Estado: Pendiente");
            }
        }

        if (!hayPendientes) {
            return "No hay clientes esperando.";
        }

        return "Clientes que están esperando mostrados correctamente.";
    }

    public String LlamarSiguiente(Queue<ObjTurnos> cola) {
        ObjTurnos siguiente = null;
        // Primero buscamos un preferencial pendiente
        for (ObjTurnos o : cola) {
            if (o.getEstado() == 1 && o.getCondicionAtencionEspecial() == 1) {
                siguiente = o;
                break;
            }
        }
        // Si no hay preferencial, buscamos un normal pendiente
        if (siguiente == null) {
            for (ObjTurnos o : cola) {
                if (o.getEstado() == 1 &&
                        o.getCondicionAtencionEspecial() == 2) {
                    siguiente = o;
                    break;
                }
            }
        }
        // No hay nadie pendiente
        if (siguiente == null) {
            return "No hay clientes pendientes para llamar.";
        }
        // AQUÍ cambiamos el estado
        siguiente.setEstado(2);
        System.out.println("-----------------------------");
        System.out.println("Siguiente cliente:");
        System.out.println("Turno: " + siguiente.getNumTurno());
        System.out.println("Identificación: " + siguiente.getIdentificacion());
        System.out.println("Nombre: " + siguiente.getNombre());

        if (siguiente.getCondicionAtencionEspecial() == 1) {
            System.out.println("Atención: Preferencial");
        } else {
            System.out.println("Atención: Normal");
        }
        return "Cliente llamado correctamente.";
    }

}
