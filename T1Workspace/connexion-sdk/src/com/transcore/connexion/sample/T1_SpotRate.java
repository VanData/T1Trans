package com.transcore.connexion.sample;

import java.rmi.RemoteException;
import java.util.regex.Pattern;

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

public class T1_SpotRate extends BaseSampleClient {

	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
