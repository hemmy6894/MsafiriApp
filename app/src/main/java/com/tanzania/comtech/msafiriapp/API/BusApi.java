package com.tanzania.comtech.msafiriapp.API;

/**
 * Created by programing on 4/4/2018.
 */

public class BusApi {
    public final  static String loginToSystem = Links.msfiriAppLink + "auth/login";
    public final  static String customerRegistration = Links.msfiriAppLink + "auth/signup";
    public final  static String customerVerification = Links.msfiriAppLink + "auth/verify_customer";
    public final  static String customerOtpResend = Links.msfiriAppLink + "auth/resend_otp";
    public final  static String busInformation = Links.msfiriAppLink + "buses/";
    public final  static String routeInformation = Links.msfiriAppLink + "get_schedulled_buses";
    public final  static String viewBusInformation = Links.msfiriAppLink + "bus_bookings_for_single_bus/";
    public final  static String companyInformation = Links.msfiriAppLink + "companies";
    public final  static String customerInformation = Links.msfiriAppLink + "customers";
}
