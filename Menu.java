import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Menu {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Queue<ObjTurnos> cola = new LinkedList<>();
        Stack<ObjTurnos> pila = new Stack<>();
        Metodos m = new Metodos();
        Validaciones v = new Validaciones();
        boolean continuar = true;
        while (continuar) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Bienvenidos al banco Nacho lee 3");
            System.out.println("Que desea realizar");
            System.out.println("1) Registrar un cliente ");
            System.out.println("2) Consultar los clientes que estan esperando ");
            System.out.println("3) Llamar al siguiente cliente ");
            System.out.println("4) Marcar un cliente como atendido");
            System.out.println("5) Cambiar un cliente de atención normal a preferencial cuando presente la documentación correspondiente");
            System.out.println("6) Cancelar un turno");
            System.out.println("7) Buscar un cliente por identificacion");
            System.out.println("8) Consultar cuántas personas están esperando ");
            System.out.println("9) Mostrar cuántos clientes normales y preferenciales están pendientes");
            System.out.println("10) Salir");
            int opt = v.ValidarEntero(sc);
            System.out.println("------------------------------------------------------------------------");
            switch (opt) {
                case 1:
                    m.IngresarCliente(cola, m, v, sc);
                    break;
                case 2:
                    System.out.println(m.MostrarClientesEsperando(cola));                    
                    break;
                case 3:
                    System.out.println(m.LlamarSiguiente(cola));                   
                    break;
                case 4:
                    System.out.println(m.Atender(cola));                  
                    break;
                case 5:
                    System.out.println(m.CambiarAPreferencial(cola, sc, v));
                    break;
                case 6:
                    System.out.println(m.CancelarTurno(cola, sc, v));
                    break;
                case 7:
                    System.out.println(m.BuscarCliente(cola, sc, v));
                    break;
                case 8:
                    System.out.println(m.ConsultarPersonasEsperando(cola));
                    break;
                case 9:
                    System.out.println(m.MostrarPendientesPorTipo(cola));
                    break;                                      
                case 10:
                    System.out.println("Hasta luego");
                    continuar = false;
                    break;
                default:
                    System.out.println("esta opcion no existe");
                    break;
            }
        }
    }
    
}
