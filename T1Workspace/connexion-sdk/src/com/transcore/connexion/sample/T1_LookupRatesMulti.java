package com.transcore.connexion.sample;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.DriverManager;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.org.util.DBconnect;

import cltool4j.GlobalConfigProperties;
import cltool4j.args4j.EnumAliasMap;

import com.tcore.tcoreTypes.ServiceError;
import com.tcore.tcoreTypes.SessionToken;
import com.tcore.tcoreTypes.StateProvince;
import com.tcore.tfmiFreightMatching.CityAndState;
import com.tcore.tfmiFreightMatching.Place;
import com.tcore.tfmiFreightMatching.TfmiFreightMatchingServiceStub;
import com.tcore.tfmiRates.CurrentContractRateReport;
import com.tcore.tfmiRates.CurrentSpotRateReport;
import com.tcore.tfmiRates.HistoricContractRateReport;
import com.tcore.tfmiRates.HistoricSpotRateReport;
import com.tcore.tfmiRates.LookupHistoricContractRatesOperation;
import com.tcore.tfmiRates.LookupHistoricContractRatesRequest;
import com.tcore.tfmiRates.LookupHistoricContractRatesRequestDocument;
import com.tcore.tfmiRates.LookupHistoricContractRatesResponse;
import com.tcore.tfmiRates.LookupHistoricContractRatesResponseDocument;
import com.tcore.tfmiRates.LookupHistoricContractRatesSuccessData;
import com.tcore.tfmiRates.LookupHistoricSpotRatesOperation;
import com.tcore.tfmiRates.LookupHistoricSpotRatesRequest;
import com.tcore.tfmiRates.LookupHistoricSpotRatesRequestDocument;
import com.tcore.tfmiRates.LookupHistoricSpotRatesResponse;
import com.tcore.tfmiRates.LookupHistoricSpotRatesResponseDocument;
import com.tcore.tfmiRates.LookupHistoricSpotRatesSuccessData;
import com.tcore.tfmiRates.LookupRateOperation;
import com.tcore.tfmiRates.LookupRateRequest;
import com.tcore.tfmiRates.LookupRateRequestDocument;
import com.tcore.tfmiRates.LookupRateResponse;
import com.tcore.tfmiRates.LookupRateResponseDocument;
import com.tcore.tfmiRates.LookupRateSuccessData;
import com.tcore.tfmiRates.RateEquipmentCategory;
import com.tcore.tfmiRates.SpotRateReport;

/**
 * Rate lookup example.
 * 
 * currentLookup - Current Rate Estimated, including contract and spot rates.
 * 
 * historicSpotLookup - 13-month historic estimates for spot.
 * 
 * historicContractLookup - 13-month historic estimates for contract.
 * 
 */
public class T1_LookupRatesMulti extends BaseSampleClient {

	private RateEquipmentCategory.Enum equipmentCategory = RateEquipmentCategory.VANS;
	   
	private Connection  conn;
	private PreparedStatement pstCheckExist;
	   
	   
    @Override
    protected void run() throws Exception {
        final SessionToken sessionToken = loginUser1();
        
        currentLookup(sessionToken);
        
        }
    //    historicContractLookup(sessionToken);
    //    historicSpotLookup(sessionToken);
	
    

    private void currentLookup(final SessionToken sessionToken) throws RemoteException {

        //final String origin = GlobalConfigProperties.singleton().getProperty("origin");
        //final String destination = GlobalConfigProperties.singleton().getProperty("destination");
         
        System.out.println("\n=== Current Rate Lookup ===");
        System.out
                .println("Type        | RateEst | RateLow | RateHigh | TotalEst | TotalLow | TotalHigh | Conf | Origin                    | Destination               | FuelSur | Contr | Moves | DaysBack ");
    	
         String origin = "49504";
    	 String destination = "Detroit, MI";	
    	 Float A1;
    	 Float A2;
    	 Float A3;
    	 

    		
    	 for (int j = 0; j < 2; j++){		
         LookupRateRequestDocument lookupRateRequestDoc = LookupRateRequestDocument.Factory
                .newInstance();
         LookupRateRequest request = lookupRateRequestDoc.addNewLookupRateRequest();

         LookupRateOperation operation = request.addNewLookupRateOperations();
        operation.setOrigin(place(origin));
        operation.setDestination(place(destination));
        operation.setEquipment(equipmentCategory);
        operation.setIncludeContractRate(false);
        operation.setIncludeSpotRate(true);


        
        operation.setOrigin(place(origin));
        operation.setDestination(place(destination));
        
         TfmiFreightMatchingServiceStub stub = new TfmiFreightMatchingServiceStub(endpointUrl);

         LookupRateResponseDocument responseDoc = stub.lookupRate(lookupRateRequestDoc, null, null,
                sessionHeaderDocument(sessionToken));
         LookupRateResponse response = responseDoc.getLookupRateResponse();


        for (int i = 0; i < response.sizeOfLookupRateResultsArray(); i++) {
       //     System.out.format("--- %s -> %s ---\n", origin, destination);

            if (response.getLookupRateResultsArray(i).isSetServiceError()) {
                System.out.println(summaryString(response.getLookupRateResultsArray(i).getServiceError()));
                System.out.println();
                continue;
            }

             LookupRateSuccessData successData = response.getLookupRateResultsArray(i)
                    .getLookupRateSuccessData();

            if (successData.isSetSpotRate()) {
                final CurrentSpotRateReport report = successData.getSpotRate();
                printCurrentSummaryLine("Spot", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(),
                        report.getEstimatedLinehaulTotal(), report.getLowLinehaulTotal(),
                        report.getHighLinehaulTotal(), report.getConfidenceLevel(), report.getRatedLane()
                                .getOriginGeography(), report.getRatedLane().getDestinationGeography(),
                        report.getAverageFuelSurchargeRate(), report.getContributors(), report.getMoves(),
                        report.getDaysBack());
            }
            
            
            if (successData.isSetSpotRate()) {
                final CurrentSpotRateReport report = successData.getSpotRate();
                A1 = report.getEstimatedLinehaulRate();
                A2 = report.getLowLinehaulRate();
                A3 = report.getHighLinehaulRate();
                System.out.format("%f, %f, %f", A1, A2, A3);
                
                try{
    			conn = DBconnect.getConnectionStatus();
                
        	String query = "Insert into DAT_Lane_hist ([Spot Avg Linehaul Rate] ,[Spot Low Linehaul Rate],[Spot High Linehaul Rate]) values (?,?,?);";
        	System.out.println(query);	        	
				pstCheckExist = conn.prepareStatement(query);

				pstCheckExist.setFloat(1, A1);

				pstCheckExist.setFloat(2, A2);

				pstCheckExist.setFloat(3, A3);

				pstCheckExist.executeUpdate();
					conn.close();
					  
                  }
                
                catch(Exception e)
                {
                	e.printStackTrace();
   	         
                }
                }
      /*     
            String origin2 = "Chicago, IL";
            String destination2 = "Detroit, MI";	
        	 
             LookupRateRequestDocument lookupRateRequestDoc2 = LookupRateRequestDocument.Factory
                     .newInstance();
              LookupRateRequest request2 = lookupRateRequestDoc2.addNewLookupRateRequest();


              LookupRateOperation operation2 = request2.addNewLookupRateOperations();

              operation2.setOrigin(place(origin2));
              operation2.setDestination(place(destination2));
              
              
              TfmiFreightMatchingServiceStub stub2 = new TfmiFreightMatchingServiceStub(endpointUrl);

              LookupRateResponseDocument  responseDoc2 = stub2.lookupRate(lookupRateRequestDoc2, null, null,
                     sessionHeaderDocument(sessionToken));
               LookupRateResponse response2 = responseDoc2.getLookupRateResponse();
               LookupRateSuccessData successData2 = response2.getLookupRateResultsArray(i)
                       .getLookupRateSuccessData();
              
              
              
            if (successData2.isSetSpotRate()) {
                final CurrentSpotRateReport report2 = successData2.getSpotRate();
                printCurrentSummaryLine("Spot", report2.getEstimatedLinehaulRate(),
                        report2.getLowLinehaulRate(), report2.getHighLinehaulRate(),
                        report2.getEstimatedLinehaulTotal(), report2.getLowLinehaulTotal(),
                        report2.getHighLinehaulTotal(), report2.getConfidenceLevel(), report2.getRatedLane()
                                .getOriginGeography(), report2.getRatedLane().getDestinationGeography(),
                        report2.getAverageFuelSurchargeRate(), report2.getContributors(), report2.getMoves(),
                        report2.getDaysBack());
            }
*/
            if (successData.isSetContractRate()) {
                final CurrentContractRateReport report = successData.getContractRate();
                printCurrentSummaryLine("Contract", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(), 0, 0, 0, 0, report
                                .getRatedLane().getOriginGeography(), report.getRatedLane()
                                .getDestinationGeography(), report.getAverageFuelSurchargeRate(),
                        report.getContributors(), report.getMoves(), 0);

            }

            if (successData.isSetYourRate()) {
                final SpotRateReport report = successData.getYourRate();
                printCurrentSummaryLine("Contributor", report.getEstimatedLinehaulRate(),
                        report.getLowLinehaulRate(), report.getHighLinehaulRate(),
                        report.getEstimatedLinehaulTotal(), report.getLowLinehaulTotal(),
                        report.getHighLinehaulTotal(), report.getConfidenceLevel(), null, null, 0, 0, 0, 0);

            }
        }
        origin = "Chicago, IL";
        destination = "Detroit, MI";
        }
    	 }

    public static void main(final String[] args) {
        run(args);
    }

    private void printCurrentSummaryLine(final String type, final float rateEst, final float rateLow,
            final float rateHigh, final float totalEst, final float totalLow, final float totalHigh,
            final int confidence, final String ratedOrigin, final String ratedDestination,
            final float fuelSurcharge, final int contributors, final int moves, final int daysBack) {
        System.out
                .format("%-11s | %7.2f | %7.2f | %8.2f | %8s | %8s | %9s | %4s | %-25s | %-25s | %7.2f | %5s | %5s | %8s\n",
                        type, rateEst, rateLow, rateHigh, asString(totalEst), asString(totalLow),
                        asString(totalHigh), asString(confidence), ratedOrigin, ratedDestination,
                        fuelSurcharge, asString(contributors), asString(moves), asString(daysBack));
    }

    private String asString(final float f) {
        return f == 0 ? "-" : String.format("%.2f", f);
    }

    private String asString(final int i) {
        return i == 0 ? "-" : Integer.toString(i);
    }

    /**
     * Enumeration of all supported lookup types.
     */
    public static enum LookupType {
        Contract("c"), Spot("s"), Contributor("m"), All("a"), Historic_spot("hs"), Historic_contract("hc");

        private LookupType(final String... aliases) {
            EnumAliasMap.singleton().addAliases(this, aliases);
        }
    }

    protected Place place(final String place) {
        final Place p = Place.Factory.newInstance();

        // US Postal code
        if (US_POSTAL_CODE_PATTERN.matcher(place).matches()) {
            p.addNewPostalCode().setCode(place);
            p.getPostalCode().setCountry(com.tcore.tcoreTypes.CountryCode.US);
        }
        // Canadian Postal code
        else if (CA_POSTAL_CODE_PATTERN.matcher(place).matches()) {
            p.addNewPostalCode().setCode(place.toUpperCase());
            p.getPostalCode().setCountry(com.tcore.tcoreTypes.CountryCode.CA);
        }
        // City/state
        else {
            p.setCityAndState(cityAndState(place));
        }
        return p;
    }

    private final static Pattern US_POSTAL_CODE_PATTERN = Pattern.compile("[0-9]{5}(-[0-9]{4})?");

    private final static Pattern CA_POSTAL_CODE_PATTERN = Pattern
            .compile("[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]");

    protected CityAndState cityAndState(final String commaDelimitedCityAndState) {
        final String[] split = commaDelimitedCityAndState.split(" *, *");
        if (split.length < 2 || split.length > 3) {
            throw new IllegalArgumentException(
                    "Expected comma-delimited city and state (e.g. 'Boise,ID', 'Salem,AL,Lee')");
        }
        return cityAndState(split);
    }

    protected CityAndState cityAndState(final String[] cityState) {
        final CityAndState cityAndState = CityAndState.Factory.newInstance();
        cityAndState.setCity(cityState[0]);
        cityAndState.setStateProvince(StateProvince.Enum.forString(cityState[1].toUpperCase()));
        if (cityState.length == 3) {
            cityAndState.setCounty(cityState[2]);
        }
        return cityAndState;
    }

    private String summaryString(final ServiceError serviceError) {
        if (serviceError.getDetailedMessage() == null) {
            return String.format("%s", serviceError.getMessage());
        }

        return String.format("%s : %s", serviceError.getMessage(), serviceError.getDetailedMessage());
    }

}
