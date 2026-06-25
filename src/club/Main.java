package club;
import java.util.ArrayList;
import java.util.Scanner;
import club.Socio.Tipo;

public class Main
{
    // -----------------------------------------------------------------
    // Métodos auxiliares
    // -----------------------------------------------------------------

    /**
     * Valida que la cédula ingresada tenga exactamente 10 dígitos numéricos. <br>
     * @param pCedula La cédula a validar. pCedula != null.
     * @throws Exception Si la cédula no tiene exactamente 10 dígitos numéricos.
     */
    private static void validarCedula( String pCedula ) throws Exception
    {
        if( pCedula.length( ) != 10 || !pCedula.matches( "[0-9]+" ) )
        {
            throw new Exception( "La cédula debe tener 10 dígitos numéricos." );
        }
    }

    // -----------------------------------------------------------------
    // Método principal
    // -----------------------------------------------------------------

    public static void main( String[] args )
    {
        Scanner sc = new Scanner( System.in );
        int op;
        Club c = new Club( );

        do
        {
            System.out.println( "1. Afiliar un socio al club." );
            System.out.println( "2. Registrar una persona autorizada por un socio." );
            System.out.println( "3. Pagar una factura." );
            System.out.println( "4. Registrar un consumo en la cuenta de un socio" );
            System.out.println( "5. Aumentar fondos de la cuenta de un socio" );
            System.out.println( "6. Salir" );
            System.out.print( "Ingrese una opcion: " );
            op = Integer.parseInt( sc.next( ) );

            switch( op )
            {
                case 1:
                {
                    try
                    {
                        System.out.print( "Cédula: " );
                        String cedula = sc.next( );
                        validarCedula( cedula );
                        System.out.print( "Nombre: " );
                        String nombre = sc.next( );
                        System.out.print( "Tipo (VIP/REGULAR): " );
                        Tipo tipo = Tipo.valueOf( sc.next( ).toUpperCase( ) );
                        c.afiliarSocio( cedula, nombre, tipo );
                        System.out.println( "Socio afiliado correctamente." );
                    }
                    catch( Exception e )
                    {
                        System.out.println( "Error: " + e.getMessage( ) );
                    }
                } break;

                case 2:
                {
                    try
                    {
                        System.out.print( "Cédula del socio: " );
                        String cedula = sc.next( );
                        validarCedula( cedula );
                        System.out.print( "Nombre del autorizado: " );
                        String autorizado = sc.next( );
                        c.agregarAutorizadoSocio( cedula, autorizado );
                        System.out.println( "Autorizado registrado correctamente." );
                    }
                    catch( Exception e )
                    {
                        System.out.println( "Error: " + e.getMessage( ) );
                    }
                } break;

                case 3:
                {
                    try
                    {
                        System.out.print( "Cédula del socio: " );
                        String cedula = sc.next( );
                        validarCedula( cedula );
                        ArrayList<Factura> facturas = c.darFacturasSocio( cedula );
                        if( facturas.isEmpty( ) )
                        {
                            throw new Exception( "El socio no tiene facturas pendientes de pago." );
                        }
                        System.out.println( "Facturas pendientes:" );
                        for( int i = 0; i < facturas.size( ); i++ )
                        {
                            System.out.println( i + ". " + facturas.get( i ).toString( ) );
                        }
                        System.out.print( "Ingrese el número de la factura a pagar: " );
                        int indice = Integer.parseInt( sc.next( ) );
                        c.pagarFacturaSocio( cedula, indice );
                        System.out.println( "Factura pagada correctamente." );
                    }
                    catch( Exception e )
                    {
                        System.out.println( "Error: " + e.getMessage( ) );
                    }
                } break;

                case 4:
                {
                    try
                    {
                        System.out.print( "Cédula del socio: " );
                        String cedula = sc.next( );
                        validarCedula( cedula );
                        System.out.print( "Nombre del cliente: " );
                        String nombre = sc.next( );
                        System.out.print( "Concepto: " );
                        String concepto = sc.next( );
                        System.out.print( "Valor: " );
                        double valor = Double.parseDouble( sc.next( ) );
                        if( valor <= 0 )
                        {
                            throw new Exception( "El valor del consumo debe ser mayor a 0." );
                        }
                        c.registrarConsumo( cedula, nombre, concepto, valor );
                        System.out.println( "Consumo registrado correctamente." );
                    }
                    catch( Exception e )
                    {
                        System.out.println( "Error: " + e.getMessage( ) );
                    }
                } break;

                case 5:
                {
                    try
                    {
                        System.out.print( "Cédula del socio: " );
                        String cedula = sc.next( );
                        validarCedula( cedula );
                        System.out.print( "Valor a aumentar: " );
                        double valor = Double.parseDouble( sc.next( ) );
                        if( valor <= 0 )
                        {
                            throw new Exception( "El valor a aumentar debe ser mayor a 0." );
                        }
                        c.aumentarFondosSocio( cedula, valor );
                        System.out.println( "Fondos aumentados correctamente." );
                    }
                    catch( Exception e )
                    {
                        System.out.println( "Error: " + e.getMessage( ) );
                    }
                } break;

                case 6:
                {
                    System.out.println( "Gracias!" );
                } break;

                default:
                    System.out.println( "Opción inválida." );
            }

        } while( op != 6 );
    }
}