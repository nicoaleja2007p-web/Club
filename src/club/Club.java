package club;
import java.util.ArrayList;
import club.Socio.Tipo;

/**
 * Clase que modela un club.
 */
public class Club
{
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Cantidad máxima de socios VIP que acepta el club.
     */
    public final static int MAXIMO_VIP = 3;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Lista de socios del club.
     */
    private ArrayList<Socio> socios;

    // -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     * Constructor de la clase. <br>
     * <b>post: </b> Se inicializó la lista de socios.
     */
    public Club( )
    {
        socios = new ArrayList<Socio>( );
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Retorna los socios afiliados al club.
     * @return Lista de socios.
     */
    public ArrayList<Socio> darSocios( )
    {
        return socios;
    }

    /**
     * Afilia un nuevo socio al club. <br>
     * <b>pre: </b> La lista de socios está inicializada. <br>
     * <b>post: </b> Se ha afiliado un nuevo socio en el club con los datos dados.
     * @param pCedula Cédula del socio a afiliar. pCedula != null && pCedula != "".
     * @param pNombre Nombre del socio a afiliar. pNombre != null && pNombre != "".
     * @param pTipo Es el tipo de subscripción del socio. pTipo != null.
     * @throws Exception Si ya existe un socio con esa cédula o si se alcanzó el límite de socios VIP.
     */
    public void afiliarSocio( String pCedula, String pNombre, Tipo pTipo ) throws Exception
    {
        Socio s = buscarSocio( pCedula );
        if( pTipo == Tipo.VIP && contarSociosVIP( ) == MAXIMO_VIP )
        {
            throw new Exception( "El club en el momento no acepta más socios VIP." );
        }
        if( s == null )
        {
            Socio nuevoSocio = new Socio( pCedula, pNombre, pTipo );
            socios.add( nuevoSocio );
        }
        else
        {
            throw new Exception( "El socio ya existe." );
        }
    }

    /**
     * Retorna el socio con la cédula dada. <br>
     * <b> pre:<b> La lista de socios está inicializada.<br>
     * @param pCedulaSocio Cédula del socio buscado. pCedulaSocio != null && pCedulaSocio != "".
     * @return El socio buscado, null si el socio buscado no existe.
     */
    public Socio buscarSocio( String pCedulaSocio )
    {
        Socio elSocio = null;
        boolean encontre = false;
        int numSocios = socios.size( );
        for( int i = 0; i < numSocios && !encontre; i++ )
        {
            Socio s = socios.get( i );
            if( s.darCedula( ).equals( pCedulaSocio ) )
            {
                elSocio = s;
                encontre = true;
            }
        }
        return elSocio;
    }

    /**
     * Retorna la cantidad de socios VIP que tiene el club.<br>
     * <b> pre: </b> La lista de socios está inicializada.
     * @return Número de socios VIP.
     */
    public int contarSociosVIP( )
    {
        int conteo = 0;
        for( Socio socio : socios )
        {
            if( socio.darTipo( ) == Tipo.VIP )
            {
                conteo++;
            }
        }
        return conteo;
    }

    /**
     * Retorna la lista de autorizados del socio con la cédula dada.<br>
     * <b> pre: </b> La lista de socios está inicializada.<br>
     * El socio buscado existe.
     * @param pCedulaSocio La cédula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @return La lista de autorizados del socio.
     * @throws Exception Si no existe un socio con la cédula dada.
     */
    public ArrayList<String> darAutorizadosSocio( String pCedulaSocio ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        ArrayList<String> autorizados = new ArrayList<String>( );
        autorizados.add( s.darNombre( ) );
        autorizados.addAll( s.darAutorizados( ) );
        return autorizados;
    }

    /**
     * Agrega una nueva persona autorizada por el socio con la cédula dada. <br>
     * <b>pre:<b/> El socio con la cédula dada existe. <b>post: </b> Se agregó el nuevo autorizado.
     * @param pCedulaSocio La cédula del socio al cual se va a agregar el autorizado. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreAutorizado El nombre de la persona a autorizar. pNombreAutorizado != null && pNombreAutorizado != "".
     * @throws Exception Si no existe el socio, el autorizado ya existe, no tiene fondos o el nombre es el mismo del socio.
     */
    public void agregarAutorizadoSocio( String pCedulaSocio, String pNombreAutorizado ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        s.agregarAutorizado( pNombreAutorizado );
    }

    /**
     * Elimina la persona autorizada por el socio con la cédula dada.
     * @param pCedulaSocio La cédula del socio que autorizó a la persona a eliminar. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreAutorizado El nombre del autorizado a eliminar. pNombreAutorizado != null && pNombreAutorizado != "".
     * @throws Exception Si no existe el socio o el autorizado tiene una factura sin pagar.
     */
    public void eliminarAutorizadoSocio( String pCedulaSocio, String pNombreAutorizado ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        s.eliminarAutorizado( pNombreAutorizado );
    }

    /**
     * Registra un consumo a un socio o a su autorizado. <br>
     * <b>post: </b> Se agregó una nueva factura al vector del socio.
     * @param pCedulaSocio La cédula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pNombreCliente El nombre la persona que realizó el consumo. pNombreCliente != null && pNombreCliente != "".
     * @param pConcepto El concepto del consumo. pConcepto != null && pConcepto != "".
     * @param pValor El valor del consumo. pValor >= 0.
     * @throws Exception Si no existe el socio o no tiene fondos suficientes.
     */
    public void registrarConsumo( String pCedulaSocio, String pNombreCliente, String pConcepto, double pValor ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        boolean esElSocio = s.darNombre( ).equals( pNombreCliente );
        boolean esAutorizado = s.darAutorizados( ).contains( pNombreCliente );
        if( !esElSocio && !esAutorizado )
        {
            throw new Exception( "El nombre '" + pNombreCliente + "' no corresponde al socio ni a ninguno de sus autorizados." );
        }
        s.registrarConsumo( pNombreCliente, pConcepto, pValor );
    }

    /**
     * Retorna la lista de facturas de un socio. <br>
     * <b>pre:<b> Existe el socio con la cédula dada.
     * @param pCedulaSocio La cédula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @return La lista de facturas del socio.
     * @throws Exception Si no existe un socio con la cédula dada.
     */
    public ArrayList<Factura> darFacturasSocio( String pCedulaSocio ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        return s.darFacturas( );
    }

    /**
     * Realiza el pago de la factura de un socio. <br>
     * <b>post: </b> Se borró la factura del vector del socio. <br>
     * @param pCedulaSocio La cédula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pFacturaIndice El índice de la factura a pagar. pFacturaIndice >= 0.
     * @throws Exception Si no existe el socio o no tiene fondos suficientes.
     */
    public void pagarFacturaSocio( String pCedulaSocio, int pFacturaIndice ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        s.pagarFactura( pFacturaIndice );
    }

    /**
     * Aumenta los fondos de un socio en la cantidad dada. <br>
     * <b>post: </b> Los fondos del socio aumentaron en el valor especificado.
     * @param pCedulaSocio La cédula del socio. pCedulaSocio != null && pCedulaSocio != "".
     * @param pValor Valor por el cual se desean aumentar los fondos. pValor >= 0.
     * @throws Exception Si no existe el socio o el monto excede el máximo permitido.
     */
    public void aumentarFondosSocio( String pCedulaSocio, double pValor ) throws Exception
    {
        Socio s = buscarSocio( pCedulaSocio );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedulaSocio );
        }
        s.aumentarFondos( pValor );
    }

    // -----------------------------------------------------------------
    // Métodos de Extensión
    // -----------------------------------------------------------------

    /**
     * Retorna el total de consumos del socio con la cédula dada. <br>
     * <b>pre: </b> La lista de socios está inicializada. <br>
     * @param pCedula Cédula del socio buscado. pCedula != null && pCedula != "".
     * @return El total de consumos del socio. Retorna 0 si no tiene consumos.
     * @throws Exception Si no existe un socio con la cédula dada.
     */
    public double totalConsumosSocio( String pCedula ) throws Exception
    {
        Socio s = buscarSocio( pCedula );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedula );
        }
        double total = 0;
        for( Factura f : s.darFacturas( ) )
        {
            total += f.darValor( );
        }
        return total;
    }

    /**
     * Indica si el socio con la cédula dada puede ser eliminado. <br>
     * <b>pre: </b> La lista de socios está inicializada. <br>
     * @param pCedula Cédula del socio. pCedula != null && pCedula != "".
     * @return true si el socio puede ser eliminado.
     * @throws Exception Si no existe el socio, es VIP, tiene facturas pendientes
     *                   o tiene más de un autorizado.
     */
    public boolean sePuedeEliminarSocio( String pCedula ) throws Exception
    {
        Socio s = buscarSocio( pCedula );
        if( s == null )
        {
            throw new Exception( "No existe un socio con la cédula: " + pCedula );
        }
        if( s.darTipo( ) == Tipo.VIP )
        {
            throw new Exception( "No se puede eliminar porque el socio es de tipo VIP." );
        }
        if( !s.darFacturas( ).isEmpty( ) )
        {
            throw new Exception( "No se puede eliminar porque el socio tiene facturas pendientes de pago." );
        }
        if( s.darAutorizados( ).size( ) > 1 )
        {
            throw new Exception( "No se puede eliminar porque el socio tiene más de un autorizado." );
        }
        return true;
    }
}