package org.example.camunda.process.solution.service;

import org.hibersap.annotations.*;
import org.hibersap.bapi.BapiRet2;
import org.hibersap.conversion.BooleanConverter;

import java.util.List;

@Bapi("BAPI_SFLIGHT_GETLIST")
public class FlightListBapi {

    public FlightListBapi(String fromCountryKey,
                          String fromCity,
                          String toCountryKey,
                          String toCity,
                          String airlineCarrier,
                          boolean afternoon,
                          int maxRead) {

        this.fromCountryKey = fromCountryKey;
        this.fromCity = fromCity;
        this.toCountryKey = toCountryKey;
        this.toCity = toCity;
        this.airlineCarrier = airlineCarrier;
        this.afternoon = afternoon;
        this.maxRead = maxRead;
    }

    @Import
    @Parameter("FROMCOUNTRYKEY")
    private final String fromCountryKey;

    @Import
    @Parameter("FROMCITY")
    private final String fromCity;

    @Import
    @Parameter("TOCOUNTRYKEY")
    private final String toCountryKey;

    @Import
    @Parameter("TOCITY")
    private final String toCity;

    @Import
    @Parameter("AIRLINECARRIER")
    private final String airlineCarrier;

    @Import
    @Parameter("AFTERNOON")
    @Convert(converter = BooleanConverter.class)
    private final boolean afternoon;

    @Import
    @Parameter("MAXREAD")
    private final int maxRead;

    @Export
    @Parameter(value="RETURN", type = ParameterType.STRUCTURE)
    private BapiRet2 returnData;

    @Table
    @Parameter("FLIGHTLIST")
    private List<Flight> flightList;

    public String getFromCountryKey() {
        return this.fromCountryKey;
    }

    public String getFromCity() {
        return this.fromCity;
    }

    public String getToCountryKey() {
        return this.toCountryKey;
    }

    public String getToCity() {
        return this.toCity;
    }

    public String getAirlineCarrier() {
        return this.airlineCarrier;
    }

    public boolean getAfternoon() {
        return this.afternoon;
    }

    public int getMaxRead() {
        return this.maxRead;
    }

    public BapiRet2 getReturnData() {
        return this.returnData;
    }

    public List<Flight> getFlightList() {
        return this.flightList;
    }

    public void showResult(FlightListBapi flightList) {

        System.out.println( "AirlineId: " + flightList.getFromCountryKey() );
        System.out.println( "FromCity: " + flightList.getFromCity() );
        System.out.println( "ToCountryKey: " + flightList.getToCountryKey() );
        System.out.println( "ToCity: " + flightList.getToCity() );
        System.out.println( "AirlineCarrier: " + flightList
                .getAirlineCarrier() );
        System.out.println( "Afternoon: " + flightList.getAfternoon() );
        System.out.println( "MaxRead: " + flightList.getMaxRead() );

        System.out.println( "\nFlightData" );
        List<Flight> flights = flightList.getFlightList();
        for ( Flight flight : flights ) {
            System.out.print( "\t" + flight.getAirportFrom() );
            System.out.print( "\t" + flight.getAirportTo() );
            System.out.print( "\t" + flight.getCarrierId() );
            System.out.print( "\t" + flight.getConnectionId() );
            System.out.print( "\t" + flight.getSeatsMax() );
            System.out.print( "\t" + flight.getSeatsOccupied() );
            System.out.println( "\t" + flight.getDepartureTime() );
        }

        System.out.println( "\nReturn" );
        BapiRet2 returnStruct = flightList.getReturnData();
        System.out.println( "\tMessage: " + returnStruct.getMessage() );
        System.out.println( "\tNumber: " + returnStruct.getNumber() );
        System.out.println( "\tType: " + returnStruct.getType() );
        System.out.println( "\tId: " + returnStruct.getId() );
    }
}
