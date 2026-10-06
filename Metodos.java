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
            o.setEstado(1); // 1 = Pendiente
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
            case 1: mensaje = "Retiro de dinero"; break;
            case 2: mensaje = "Consignacion dinero"; break;
            case 3: mensaje = "Compra de servicios"; break;
            case 4: mensaje = "Compra de divisas"; break;
            case 5: mensaje = "Pago de creditos"; break;
            case 6: mensaje = "Asesoria especializada"; break;
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


    public String ObtenerNombreEstado(int estado) {
        switch (estado) {
            case 1: 
            return "Pendiente";
            case 2: 
            return "En atención / Llamado";
            case 3: 
            return "Atendido";
            case 4: 
            return "Cancelado";
            default: 
            return "Estado desconocido";
        }
    }

    public String ObtenerTipoAtencion(int condicion) {
        if (condicion == 1) {
            return "Preferencial";
        } else {
            return "Normal";
        }
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
                System.out.println("Atención: " + ObtenerTipoAtencion(o.getCondicionAtencionEspecial()));
                System.out.println("Estado: " + ObtenerNombreEstado(o.getEstado()));
            }
        }
        if (!hayPendientes) {
            return "No hay clientes esperando.";
        }
        return "Clientes que están esperando mostrados correctamente.";
    }

    public String LlamarSiguiente(Queue<ObjTurnos> cola) {
        ObjTurnos siguiente = null;
        
        // Primero busca el preferencial pendiente más antiguo
        for (ObjTurnos o : cola) {
            if (o.getEstado() == 1 && o.getCondicionAtencionEspecial() == 1) {
                siguiente = o;
                break;
            }
        }        
        // Si no hay preferencial, busca el cliente normal pendiente más antiguo
        if (siguiente == null) {
            for (ObjTurnos o : cola) {
                if (o.getEstado() == 1 && o.getCondicionAtencionEspecial() == 2) {
                    siguiente = o;
                    break;
                }
            }
        }        
        if (siguiente == null) {
            return "No hay clientes pendientes para llamar.";
        }
        
        // Cambiamos el estado a 2 (Llamado / En atención)
        siguiente.setEstado(2);
        
        System.out.println("-----------------------------");
        System.out.println("Siguiente cliente:");
        System.out.println("Turno: " + siguiente.getNumTurno());
        System.out.println("Identificación: " + siguiente.getIdentificacion());
        System.out.println("Nombre: " + siguiente.getNombre());
        System.out.println("Atención: " + ObtenerTipoAtencion(siguiente.getCondicionAtencionEspecial()));
        
        return "Cliente llamado correctamente.";
    }

    public String Atender(Queue<ObjTurnos> cola) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }        
        ObjTurnos llamado = null;
        for (ObjTurnos o : cola) {
            if (o.getEstado() == 2) {
                llamado = o;
                break;
            }
        }        
        if (llamado == null) {
            return "No hay clientes llamados para atender, intente primero llamando al siguiente cliente.";
        }        
        llamado.setEstado(3);
        
        System.out.println("-----------------------------");
        System.out.println("Cliente atendido con éxito:");
        System.out.println("Turno: " + llamado.getNumTurno());
        System.out.println("Identificación: " + llamado.getIdentificacion());
        System.out.println("Nombre: " + llamado.getNombre());
        System.out.println("Trámite realizado: " + llamado.getTipoTramite());
        System.out.println("Atención: " + ObtenerTipoAtencion(llamado.getCondicionAtencionEspecial()));
        
        return "Cliente marcado como atendido correctamente.";
    }

    public String CambiarAPreferencial(Queue<ObjTurnos> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }        
        System.out.println("Ingrese el número de identificación del cliente:");
        String id = v.ValidarString(sc);        
        ObjTurnos encontrado = null;
        for (ObjTurnos o : cola) {
            if (o.getIdentificacion().equalsIgnoreCase(id)) {
                encontrado = o;
                break;
            }
        }        
        if (encontrado == null) {
            return "No se encontró ningún cliente con la identificación: " + id;
        }
        if (encontrado.getEstado() != 1) {
            return "No se puede cambiar la condición. El cliente ya fue llamado, atendido o cancelado.";
        }
        if (encontrado.getCondicionAtencionEspecial() == 1) {
            return "El cliente ya se encuentra en atención preferencial.";
        }        
        encontrado.setCondicionAtencionEspecial(1);
        
        System.out.println("-----------------------------");
        System.out.println("Documentación validada con éxito.");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Identificación: " + encontrado.getIdentificacion());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Nueva condición: " + ObtenerTipoAtencion(encontrado.getCondicionAtencionEspecial()));
        
        return "Cliente cambiado a atención preferencial correctamente.";
    }

    public String CancelarTurno(Queue<ObjTurnos> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }        
        System.out.println("Ingrese el número de turno que desea cancelar:");
        int turno = v.ValidarEntero(sc);        
        ObjTurnos encontrado = null;
        for (ObjTurnos o : cola) {
            if (o.getNumTurno() == turno) {
                encontrado = o;
                break;
            }
        }        
        if (encontrado == null) {
            return "No existe ningún registro con el turno número: " + turno;
        }
        if (encontrado.getEstado() == 3) {
            return "Operación rechazada: El turno " + turno + " ya fue atendido y no se puede cancelar.";
        }
        if (encontrado.getEstado() == 4) {
            return "El turno " + turno + " ya se encuentra cancelado.";
        }        
        encontrado.setEstado(4); // 4 = Cancelado
        
        System.out.println("-----------------------------");
        System.out.println("Turno cancelado:");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Identificación: " + encontrado.getIdentificacion());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Estado: " + ObtenerNombreEstado(encontrado.getEstado()));
        
        return "El turno se canceló correctamente.";
    }

    public String BuscarCliente(Queue<ObjTurnos> cola, Scanner sc, Validaciones v) {
        if (cola.isEmpty()) {
            return "No hay clientes registrados en el sistema.";
        }        
        System.out.println("Ingrese el número de identificación del cliente a buscar:");
        String id = v.ValidarString(sc);
        
        ObjTurnos encontrado = null;
        for (ObjTurnos o : cola) {
            if (o.getIdentificacion().equalsIgnoreCase(id)) {
                encontrado = o;
                break;
            }
        }        
        if (encontrado == null) {
            return "No se encontró ningún cliente con la identificación: " + id;
        }        
        System.out.println("-----------------------------");
        System.out.println("Información del cliente:");
        System.out.println("Turno: " + encontrado.getNumTurno());
        System.out.println("Identificación: " + encontrado.getIdentificacion());
        System.out.println("Nombre: " + encontrado.getNombre());
        System.out.println("Edad: " + encontrado.getEdad());
        System.out.println("Tipo de trámite: " + encontrado.getTipoTramite());
        System.out.println("Atención: " + ObtenerTipoAtencion(encontrado.getCondicionAtencionEspecial()));
        System.out.println("Estado actual: " + ObtenerNombreEstado(encontrado.getEstado()));
        
        return "Búsqueda finalizada con éxito.";
    }

    public String ConsultarPersonasEsperando(Queue<ObjTurnos> cola) {
        if (cola.isEmpty()) {
            return "No hay clientes en el sistema.";
        }
        int contador = 0;
        for (ObjTurnos o : cola) {
            if (o.getEstado() == 1) {
                contador++;
            }
        }
        System.out.println("-----------------------------");
        System.out.println("Personas en sala de espera: " + contador);

        return "Conteo de clientes en espera realizado correctamente.";
    }

    public String MostrarPendientesPorTipo(Queue<ObjTurnos> cola) {
        if (cola.isEmpty()) {
            return "No hay clientes en el sistema.";
        }
        int preferenciales = 0;
        int normales = 0;

        for (ObjTurnos o : cola) {
            if (o.getEstado() == 1) {
                if (o.getCondicionAtencionEspecial() == 1) {
                    preferenciales++;
                } else {
                    normales++;
                }
            }
        }
        System.out.println("-----------------------------");
        System.out.println("Clientes pendientes por atender:");
        System.out.println("- Preferenciales: " + preferenciales);
        System.out.println("- Normales: " + normales);
        System.out.println("- Total en espera: " + (preferenciales + normales));

        return "Reporte por tipo de atención generado correctamente.";
    }
}