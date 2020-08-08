package co.desofsi.tiendavirtual.data;

public class Constant {

    public static final String URL = "http://192.168.0.105:8000/";
    //public static  final String URL = "https://www.tiendavirtualutc.xyz/";
    //public static  final String URL = "http://tiendavirtualutc.herokuapp.com/";
    public static final String HOME = URL + "api";
    public static final String LOGIN = HOME + "/login";
    public static final String REGISTER = HOME + "/register";
    public static final String SAVE_PROFILE = HOME + "/save-profile-user";
    public static final String TYPE_COMPANES = HOME + "/api-companies-type";
    public static final String COMPANIES = HOME + "/api-companies";
    public static final String CATEGORIES = HOME + "/api-categories";
    public static final String PRODUCTS = HOME + "/api-products";
    public static final String SEND_ORDER = HOME + "/api-send-order";

    //RUTAS PARA LAS ORDENES

    public static final String ORDERS = HOME + "/api-orders";
    public static final String ORDER_DETAIL = HOME + "/api-detail-order";
    public static final String ORDER_DOWNLOAD = HOME + "/api-download-order";

    //RUTA PARA LOS REPARTIDORES
    public static final String DELIVERYMAN = HOME + "/api-deliveriman";

}
