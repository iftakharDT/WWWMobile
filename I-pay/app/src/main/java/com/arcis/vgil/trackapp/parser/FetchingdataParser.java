package com.arcis.vgil.trackapp.parser;



import com.arcis.vgil.trackapp.data.Constants;
import com.arcis.vgil.trackapp.data.DeptAndDesignationDetails;
import com.arcis.vgil.trackapp.model.AmCallPlanerModel;
import com.arcis.vgil.trackapp.model.DashboardModel;
import com.arcis.vgil.trackapp.model.Questions;
import com.arcis.vgil.trackapp.model.SubSegmentType;
import com.arcis.vgil.trackapp.model.TerettoryListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class FetchingdataParser {

	public ArrayList<HashMap<String, Object>> getcontactdetails(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.ID, jsonObject.get("ID"));
				dataMap.put(Constants.CONTACTTYPEID_1, jsonObject.get("ContactTypeID"));
				dataMap.put(Constants.CONTACTNAME_1, jsonObject.get("Name"));
				dataMap.put(Constants.dealerid, jsonObject.get("DealerID"));
				dataMap.put(Constants.MOBILE_NUMBER, jsonObject.get("MobileNo"));
				dataMap.put(Constants.emailid, jsonObject.get("Email"));
				dataMap.put(Constants.alternatemobile, jsonObject.get("AlternateMob"));
				dataMap.put(Constants.dob, jsonObject.get("DateofBirth"));
				dataMap.put(Constants.streetaddress, jsonObject.get("Address"));
				dataMap.put(Constants.country, jsonObject.get("Country"));
				dataMap.put(Constants.countryID, jsonObject.get("CountryID"));
				dataMap.put(Constants.zone, jsonObject.get("Zone"));
				dataMap.put(Constants.zoneID, jsonObject.get("ZoneID"));
				dataMap.put(Constants.state, jsonObject.get("State"));
				dataMap.put(Constants.stateID, jsonObject.get("StateID"));
				//				dataMap.put(Constants.district, jsonObject.get("District"));
				//				dataMap.put(Constants.districtID, jsonObject.get("DistrictID"));
				dataMap.put(Constants.city, jsonObject.get("City"));
				dataMap.put(Constants.cityID, jsonObject.get("CityID"));
				dataMap.put(Constants.area, jsonObject.get("Area"));
				dataMap.put(Constants.areaID, jsonObject.get("AreaID"));
				dataMap.put(Constants.pincode, jsonObject.get("Pincode"));
				dataMap.put(Constants.IsPrimary, jsonObject.get("IsPrimary"));

				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public  static ArrayList<HashMap<String, Object>> getarealistparser(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.GeoID, jsonObject.get("ID"));
				dataMap.put(Constants.GeoName, jsonObject.get("VALUE"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	public  static ArrayList<HashMap<String, String>> getarealistparserCform(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String, String> dataMap = new HashMap<String, String>();
				JSONObject jsonObject = array.getJSONObject(i);
				dataMap.put(Constants.GeoID, jsonObject.getString("ID"));
				dataMap.put(Constants.GeoName, jsonObject.getString("VALUE"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	
	public  static ArrayList<HashMap<String, Object>> getarealistparserDealerCode(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.GeoID, jsonObject.get("ID"));
				dataMap.put(Constants.GeoName, jsonObject.get("VALUE"));
				dataMap.put(Constants.DealerCode, jsonObject.get("DealerCode"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	public ArrayList<HashMap<String, Object>> getdealerparser(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.contacttype, jsonObject.get("Type"));
				dataMap.put(Constants.CONTACTTYPEID_1, jsonObject.get("ID"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> getdealernameparser(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.dealerid, jsonObject.get("ID"));
				dataMap.put(Constants.dealer, jsonObject.get("DealerName"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> getretailernameparser(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.dealerid, jsonObject.get("ID"));
				dataMap.put(Constants.dealer, jsonObject.get("Name"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public ArrayList<HashMap<String, Object>> getretailercontactdetails(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.ID, jsonObject.get("ID"));
				dataMap.put(Constants.CONTACTTYPEID_1, jsonObject.get("ContactTypeID"));
				dataMap.put(Constants.contacttype, jsonObject.get("ContactType"));
				dataMap.put(Constants.CONTACTNAME_1, jsonObject.get("Name"));
				dataMap.put(Constants.PhoneNo, jsonObject.get("PhoneNo"));
				dataMap.put(Constants.streetaddress, jsonObject.get("Address"));
				dataMap.put(Constants.country, jsonObject.get("Country"));
				dataMap.put(Constants.countryID, jsonObject.get("CountryID"));
				dataMap.put(Constants.zone, jsonObject.get("Zone"));
				dataMap.put(Constants.zoneID, jsonObject.get("ZOneID"));
				dataMap.put(Constants.state, jsonObject.get("State"));
				dataMap.put(Constants.stateID, jsonObject.get("StateID"));
				dataMap.put(Constants.city, jsonObject.get("City"));
				dataMap.put(Constants.cityID, jsonObject.get("CityID"));
				dataMap.put(Constants.area, jsonObject.get("Area"));
				dataMap.put(Constants.areaID, jsonObject.get("AreaID"));
				dataMap.put(Constants.pincode, jsonObject.get("PinCode"));
				dataMap.put(Constants.AccountHolderName, jsonObject.get("AccountHolderName"));
				dataMap.put(Constants.AccountNumber, jsonObject.get("AccountNumber"));
				dataMap.put(Constants.BankName, jsonObject.get("BankName"));
				dataMap.put(Constants.BankNameID, jsonObject.get("BankID"));
				dataMap.put(Constants.BranchName, jsonObject.get("BranchName"));
				dataMap.put(Constants.BankBranchID, jsonObject.get("BankBranchID"));
				dataMap.put(Constants.IFSCCode, jsonObject.get("IFSCCode"));
				dataMap.put(Constants.AccountType, jsonObject.get("AccountType"));
				dataMap.put(Constants.AccountTypeID, jsonObject.get("AccountTypeID"));
				dataMap.put(Constants.authorized, jsonObject.get("bAuthorized"));
				dataMap.put(Constants.BankCity, jsonObject.get("BankCity"));
				dataMap.put(Constants.GarageName, jsonObject.get("GargageName"));
				dataMap.put(Constants.VerificationID, jsonObject.get("IdentityTypeId"));
				dataMap.put(Constants.VerificationIDDOC, jsonObject.get("VerificationDocType"));
				dataMap.put(Constants.VerificationName, jsonObject.get("IdentityNo"));
				dataMap.put(Constants.ISIndividula, jsonObject.get("IndiviualAC"));
				dataMap.put(Constants.districtID, jsonObject.get("DistrictID"));
				dataMap.put(Constants.district, jsonObject.get("District"));

				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> getbankdetails(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.BankNameID, jsonObject.get("BankID"));
				dataMap.put(Constants.BankName, jsonObject.get("BankName"));
				dataMap.put(Constants.BranchName, jsonObject.get("BranchName"));
				dataMap.put(Constants.BankBranchID, jsonObject.get("BranchID"));
				dataMap.put(Constants.IFSCCode, jsonObject.get("IFSCCode"));

				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	public ArrayList<HashMap<String, Object>> getaccounttype(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.AccountTypeID, jsonObject.get("ID"));
				dataMap.put(Constants.AccountType, jsonObject.get("AccountType"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> getverificationtype(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("Verification");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.VerificationID, jsonObject.get("ID"));
				dataMap.put(Constants.VerificationName, jsonObject.get("VerificationDocType"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> isMechanicExist(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.ID, jsonObject.get("ID"));
				dataMap.put(Constants.CONTACTTYPEID_1, jsonObject.get("ContactType"));
				dataMap.put(Constants.CONTACTNAME_1, jsonObject.get("Name"));
				dataMap.put(Constants.streetaddress, jsonObject.get("Address"));
				dataMap.put(Constants.area, jsonObject.get("Area"));
				dataMap.put(Constants.state, jsonObject.get("State"));
				dataMap.put(Constants.city, jsonObject.get("City"));
				dataMap.put(Constants.AccountNumber, jsonObject.get("AccountNumber"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * { 'Root': { 'data': [{'ID': '1255','CouponCode': 'MP136104931','CouponTypeID': '2','ProductID': '792','MasterCode': 'I1403075284','MechanicCouponPoints': '25',
 * 'PackagingRefNo': '','PackagingDate': '','PackagingLocationCode': '','MerchandisingRG1Date': '1/1/1970','MerchandisingRefNo': '1PK002755','MerchandisingLocationCode': 'MER',
 * 'DispatchDate': '','DispatchRefNo': '','DispatchLocationCode': '','MGReceiptDate': '','MGReceiptRefNo': '','MGLocationCode': '','DispatchfromMER_MGDate': '',
 * 'DispatchfromMER_MGRefNo': '','MER_MGLocationCode': '','CFAReceiptDate': '','CFAReceiptRefNo': '','CFALocationCode': '','InvoicingDate': '11/3/2014',
 * 'InvoicingRefNo': 'SAP10110143','InvoicingLocationCode': 'DAP','CouponStatusID': '8','RedeemerTypeID': '1','RedeemBy': '27','RedeemTo': '6','RedeemToTypeID': '2',
 * 'RedeemToContactID': '37','RedeemDate': '6/4/2014 4:43:00 PM','ApprovalID': '201406041','Sync': '','ERPPullDate': '','ERPPUSHDate': '','Code': '1PC 10225CCASTD',
 * 'ProductName': '1PC 10225CCASTD','RedeemToID': '37','RedeemToName': 'Mech-4','RedeemToMobile': '9654737363','City': 'New Delhi','Area': 'Jamia Nagar'}] }}
	 * @param data
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> couponvalidation(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();

				dataMap.put(Constants.CouponID, jsonObject.get("ID"));
				dataMap.put(Constants.CouponCode, jsonObject.get("CouponCode"));
				dataMap.put(Constants.CouponTypeID, jsonObject.get("CouponTypeID"));
				dataMap.put(Constants.ProductID, jsonObject.get("ProductID"));
				dataMap.put(Constants.InvoicingRefNo, jsonObject.get("InvoicingRefNo"));
				dataMap.put(Constants.CouponStatusID, jsonObject.get("CouponStatusID"));
				dataMap.put(Constants.RedeemDate, jsonObject.get("RedeemDate"));
				dataMap.put(Constants.RedeemedToID, jsonObject.get("RedeemToID"));
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				dataMap.put(Constants.area, jsonObject.getString(Constants.area));
				dataMap.put(Constants.NAME, jsonObject.getString("RedeemToName"));
				dataMap.put(Constants.APPROVALID, jsonObject.getString(Constants.APPROVALID));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}


	public HashMap<String, Object> isUserExist(String data) {
		JSONObject object;

		/*
		 * { 'Root': { 'UserDetails': [{'Id': '3','UserId': '1','UserCode': '9899278453',
		 * 'UserType': 'External','Password': 'FVO0i0aL558=','UserName': 'Maruti Con 00',
		 * 'ContactName': 'Maruti Con 001','DeptId': '13','DeptName': 'Admin',
		 * 'DesignationID': '9','Designation': 'Admin','MobileNo': '9899278453','ContactTypeId': '14',
		 * 'ContactTypeName': 'Dealer','MasterId': '27',PasswordChangedStatus}] }}
		 */
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("UserDetails");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				dataMap.put(Constants.ID, jsonObject.get("Id"));
				dataMap.put(Constants.USERID_1, jsonObject.get("UserId"));
				dataMap.put(Constants.USERCODE , jsonObject.get("UserCode"));
				dataMap.put(Constants.TYPE, jsonObject.get("UserType"));
				dataMap.put(Constants.PASSWORD_1, jsonObject.get("Password"));
				dataMap.put(Constants.CONTACTNAME_1, jsonObject.get("ContactName"));
				dataMap.put(Constants.CONTACTTYPEID_1, jsonObject.get("ContactTypeId"));
				dataMap.put(Constants.CONTACTTYPENAME_1, jsonObject.get("ContactTypeName"));
				dataMap.put(Constants.DEALERID_1, jsonObject.get("MasterId"));
				dataMap.put(Constants.MOBILE_NUMBER, jsonObject.get("MobileNo"));
				dataMap.put(Constants.isLocked, jsonObject.get(Constants.isLocked));
				dataMap.put(Constants.PasswordStatus, jsonObject.get(Constants.PasswordStatus));



			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	public ArrayList<HashMap<String, Object>> getbankdata(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.BankNameID, jsonObject.get("BankID"));
				dataMap.put(Constants.BankName, jsonObject.get("BankName"));
				dataMap.put(Constants.BranchName, jsonObject.get("BranchName"));
				dataMap.put(Constants.BankBranchID, jsonObject.get("BankBranchID"));
				dataMap.put(Constants.IFSCCode, jsonObject.get("IFSCCode"));

				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public ArrayList<HashMap<String, Object>> getbankname(String data) {
		JSONObject object;
		ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put(Constants.BankNameID, jsonObject.get("BankID"));
				dataMap.put(Constants.BankName, jsonObject.get("BankName"));
				dataList.add(dataMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * Get department and Designation list.
	 * 
	 * @ returns Arraylist of hashmap conataining data. 
	 */
	public ArrayList<DeptAndDesignationDetails> getDeptandDesgData(String  data){

		JSONObject object;
		ArrayList<DeptAndDesignationDetails> dataList = new ArrayList<DeptAndDesignationDetails>();
		try{

			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("State");
			for(int  i=0;i<array.length();i++){
				DeptAndDesignationDetails details = new DeptAndDesignationDetails();
				JSONObject jsondata = array.getJSONObject(i);
				details.setDeptId(Constants.getIntValue(jsondata.getString("DeptID")));
				details.setDeptName(jsondata.getString("DeptName"));
				details.setContacttypeId(Constants.getIntValue(jsondata.getString("ContactTypeID")));
				details.setDesignationId(Constants.getIntValue(jsondata.getString("DesignationID")));
				details.setDesigName(jsondata.getString("Desg"));
				dataList.add(details);

			}
			return dataList;


		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get Product List
	 * @param jsonResponce
	 * @return
	 */
	 
	public static  ArrayList<HashMap<String , String>> getDealerProductList(String jsonResponce){
// part no , pending oreder, git,invenrty, quantity
		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
				productMap.put(Constants.CODE,data.getString("Code" ));
				productMap.put(Constants.ID,data.getString("ID") );
				productMap.put(Constants.PRODUCTNAME, data.getString("ProductName"));
				productMap.put(Constants.UNITPRICE, data.getString(Constants.UNITPRICE));
				productMap.put(Constants.DISCOUNTPERCENTAGE, data.getString(Constants.DISCOUNTPERCENTAGE));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get Product List
	 * @param jsonResponce
	 * @return
	 */
	//{ 'Root': { 'data': [{'PRODUCTID': '1','TARGETSTOCK': '150','STOCKQTY': '-25','DealerID': '27',
		//'UNITPRICE': '5526.00','DealerSKU': '3','Code': '1PC 10225CSASTD',
	//'QuarantinedQty': '0','PendingQty': '1216','AlolocatedNotInvoiced': '0','GITQty': '0','OrderQty': ''},
	public static  ArrayList<HashMap<String , String>> getMannualDealerProductList(String jsonResponce){

		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
				productMap.put(Constants.CODE,data.getString(Constants.CODE));
				productMap.put(Constants.PENDING_ORDER,data.getString(Constants.PENDING_ORDER) );
				productMap.put(Constants.GITQAUNTITY, data.getString(Constants.GITQAUNTITY));
				productMap.put(Constants.INVENTORY, data.getString(Constants.INVENTORY));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	
//	{ 'Root': { 'table': [{'DealerID': '275','ChequeNo': '102','ChequeID': '1341','BankName': 'ICICI BANK LTD','BranchName': 'BELIAGHATA, KOLKATA','City': 'Kolkata','AccountNo': '110001002356','Status': '2'},
	public static  ArrayList<HashMap<String , String>> getdirectDealerBlank_Un_Used_Check(String jsonResponce){

		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("table");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
				productMap.put(Constants.ChequeID,data.getString(Constants.ChequeID));
				productMap.put(Constants.ChequeNo,data.getString(Constants.ChequeNo));
				productMap.put(Constants.BankName,data.getString(Constants.BankName) );
				productMap.put(Constants.BranchName, data.getString(Constants.BranchName));
				productMap.put(Constants.city, data.getString(Constants.city));
				productMap.put(Constants.AccountNo, data.getString(Constants.AccountNo));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
//	{ 'Root': { 'table': [{'DealerID': '275','ChequeID': '1340','ChequeNo': '101','BankName': 'ICICI BANK LTD','BranchName': 'BELIAGHATA, KOLKATA','City': 'Kolkata','AccountNo': '110001002356','AllocatedAmount': '974.00','AllocationDate': '31 Jul 2015','Status': '3'},
	//{'DealerID': '275','ChequeID': '1342','ChequeNo': '103','BankName': 'ICICI BANK LTD','BranchName': 'BELIAGHATA, KOLKATA','City': 'Kolkata','AccountNo': '110001002356','AllocatedAmount': '2995.00','AllocationDate': '31 Jul 2015','Status': '3'}] }}
	
	
	public static  ArrayList<HashMap<String , String>> getdirectDealerBlank_Used_Check(String jsonResponce){

		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("table");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
				productMap.put(Constants.ChequeID,data.getString(Constants.ChequeID));
				productMap.put(Constants.ChequeNo,data.getString(Constants.ChequeNo));
				productMap.put(Constants.BankName,data.getString(Constants.BankName) );
				productMap.put(Constants.BranchName, data.getString(Constants.BranchName));
				productMap.put(Constants.city, data.getString(Constants.city));
				productMap.put(Constants.AccountNo, data.getString(Constants.AccountNo));
				productMap.put(Constants.AllocatedAmount, data.getString(Constants.AllocatedAmount));
				productMap.put(Constants.AllocationDate, data.getString(Constants.AllocationDate));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	//{ 'Root': { 'table': [{'DealerID': '275','ChequeID': '1340','ChequeNo': '101','InvoiceNumber': 'SWB15160139','AllocatedAmount': '974.00','AllocationDate': '31 Jul 2015'}] }}
	
	public static  ArrayList<HashMap<String , String>> getdirectDealerBlank_dialogue_Used_Check(String jsonResponce){

		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("table");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
			
				productMap.put(Constants.ChequeNo,data.getString(Constants.ChequeNo));
				productMap.put(Constants.InvoiceNumber,data.getString(Constants.InvoiceNumber) );
				productMap.put(Constants.AllocatedAmount, data.getString(Constants.AllocatedAmount));
				productMap.put(Constants.AllocationDate, data.getString(Constants.AllocationDate));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
/**
	 * Get Product List
	 * @param jsonResponce
	 * @return
	 */
	public static  ArrayList<HashMap<String , String>> getDealerGoodsProductsList(String jsonResponce){

		JSONObject object;
		ArrayList<HashMap<String , String>> productlist = new ArrayList<HashMap<String ,String>>();

		HashMap<String , String> datamap = new HashMap<String, String>();
		datamap.put(Constants.CODE, "Please Select");
		productlist.add(datamap);
		HashMap<String ,String> productMap = null;



		try{
			object = new JSONObject(jsonResponce);
			JSONArray dataarray = object.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<dataarray.length();i++){
				JSONObject data = dataarray.getJSONObject(i);
				productMap = new HashMap<String, String>();
				productMap.put(Constants.CODE,data.getString("Code" ));
				productMap.put(Constants.ID,data.getString("ID") );
				productMap.put(Constants.PRODUCTNAME, data.getString("ProductName"));
				productMap.put(Constants.UNITPRICE, data.getString(Constants.UNITPRICE));
				productMap.put(Constants.DISCOUNTPERCENTAGE, data.getString(Constants.DISCOUNTPERCENTAGE));
				productlist.add(productMap);
			}

			return productlist;
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	
	/**
	 * 'FinalSubmission':'True',
            'SaleId':'10',
            'SaleNo':'2013112827000011',
            'PartyName':'test',
            'SaleDate':'28/11/2013',
            'SaleQty':'5',
            'SaleAmount':'2250',
            'ReturnQty':'0',
            'ReturnAmount':'0',
            'TAmount':'2250',
            'TotalAmount':'2250',
            'TotalQty':'5',
            'NoOfItems':'1',
            'EntryDate':'28/11/2013'
	 */
private static int ParseInt(String value){
		int parsedValue;
		try{
			parsedValue = Integer.parseInt(value);
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;

		}
		return parsedValue;
	}

	private static boolean ParseBoolean(String value){

		boolean flag = false;
		try{
			flag = 	Boolean.parseBoolean(value);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}

	private static double ParseDouble(String doublevalue){

		double value  = 0.0;
		try {

			value = Double.parseDouble(doublevalue);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}
	/**
	 * { "Root": { "DealerList": [{"ID": "0","DealerName": "--Please Select--"},
	 * {"ID": "27","DealerName": "Maruti Exports"},{"ID": "28","DealerName": "Honda Exports"}] }}

	 */
	public static ArrayList<HashMap<String, String>> getDealerNamesList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("DealerList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				dataMap.put(Constants.ID, jsonObject.getString("ID"));
				dataMap.put(Constants.DEALERNAME, jsonObject.getString("DealerName"));
				dataList.add(dataMap);

			}
			return dataList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*{ "Root": { "dtVisitType": [{"ID": "0","TYPE": "-- Please Select --"},
	 * {"ID": "11","TYPE": "CFA"},{"ID": "14","TYPE": "Dealer"},
	 * {"ID": "15","TYPE": "Retailer"},{"ID": "16","TYPE": "Mechanic"}] }}*/


	public static ArrayList<HashMap<String, String>> getPartTypeList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtVisitType");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				dataMap.put(Constants.ID, jsonObject.getString("ID"));
				dataMap.put(Constants.TYPE, jsonObject.getString("TYPE"));
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * { "Root": { "dtPartyList": [{"ID": "0","Code": "-- Please Select --",
	 * "Name": "-- Please Select --","NameWithCode": "-- Please Select --"}] }}

	 */

	public static ArrayList<HashMap<String, String>> getPartyList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtPartyList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				dataMap.put(Constants.ID, jsonObject.getString("ID"));
				dataMap.put(Constants.CODE, jsonObject.getString("Code"));
				dataMap.put(Constants.PREF_NAME, jsonObject.getString("Name"));
				dataMap.put(Constants.NAMEWITHCODE, jsonObject.getString("NameWithCode"));
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * { "Root": { "dtQuestionList": [
	 * {"SNO": "1","QuestionId": "22","Question": "What is your title?","ContactType": "14","Active": "True"},]

	 */

	public static ArrayList<Questions> getQuestionsList(String data) {
		JSONObject object;
		ArrayList<Questions> dataList = new ArrayList<Questions>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtQuestionList");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Questions q = new Questions();
				q.setId(jsonObject.getString("QuestionId"));
				q.setQuestion(jsonObject.getString("Question"));
				q.setContacttypeid(jsonObject.getString("ContactType"));
				q.setActive(jsonObject.getString("Active"));
				dataList.add(q);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}


	

	public static ArrayList<Questions> getAMVisitLogQuestions(String data) {
		JSONObject object;
		ArrayList<Questions> dataList = new ArrayList<Questions>();
		try {
			object = new JSONObject(data);
			JSONObject object2=object.getJSONObject("Root");
			JSONArray jsonArray=object2.getJSONArray("AMVisitLogQuestions");
			
		//	JSONArray array = object.getJSONObject("Root").getJSONArray("AMVisitLogQuestions");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Questions q = new Questions();
				q.setId(jsonObject.getString("QuestionID"));
				q.setQuestion(jsonObject.getString("Questions"));
				q.setContacttypeid(jsonObject.getString("ContactTYpe"));
				q.setAnswer(jsonObject.getString("Answer"));
				q.setAnswerId(jsonObject.getString("AnswerID"));
				q.setQuestionSequenceID(jsonObject.getString("QuestionSequenceID"));
				dataList.add(q);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}


	
	
	//{ 'Root': { 'AMVisitLog': [{'VisitLogID': '130'}] }}
	
	public static ArrayList<Questions> getAMLogResponce(String data) {

		JSONObject object;
		ArrayList<Questions> dataList = new ArrayList<Questions>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMVisitLog");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				Questions q = new Questions();
				q.setSetvisitLogID(jsonObject.getString("VisitLogID"));
				
				dataList.add(q);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	
		
	}


	public static HashMap<String, Object> getInvoiceTotalDetails(String data){


		HashMap< String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtGrossInvoice");
			for(int i=0;i<array.length();i++){
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.noofinvoice, value.get(Constants.noofinvoice));
				dataMap.put(Constants.noofproducts, value.get(Constants.noofproducts));
				dataMap.put(Constants.quantity, value.get(Constants.quantity));
				dataMap.put(Constants.totalvolume, value.get(Constants.totalvolume));
				dataMap.put(Constants.pretaxamount, value.get(Constants.pretaxamount));

			}

			return dataMap;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * {"Root":{
      "dtInternalContactList":[
       		{
            "ID":"74",
            "ContactName":"Area Manager -1",
            "CONTACTTYPEID":"1"
         },]
	 */
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> getNameOfPerson(String data){

		ArrayList<HashMap<String , String>> datalist = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtInternalContactList");
			for(int i=0;i<array.length();i++){ 
				JSONObject value = array.getJSONObject(i);
				HashMap< String, String> dataMap = new HashMap<String, String>();

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.CONTACTNAME, value.getString(Constants.CONTACTNAME));
				dataMap.put(Constants.CONTACTTYPEID, value.getString(Constants.CONTACTTYPEID));
				datalist.add(dataMap);			  
			}

			return datalist;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * { "Root": { "dtPendingCFormAtFA": [{"SNO": "1","CFAId": "7","CFAName": "The CFA","DealerID": "27",
	 * "DealerName": "Maruti Exports","CFormId": "2","CFormNo": "1",
	 * "CFormDate": "01/09/2013","CFormValue": "1","ModeOfDispath": "Hand",
	 * "PersonId": "75"}] }}
	 */

	public static ArrayList<HashMap<String, String>> getCformPendingList(String data){

		ArrayList<HashMap<String , String>> datalist = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("dtPendingCFormAtFA");
			for(int i=0;i<array.length();i++){ 
				JSONObject value = array.getJSONObject(i);
				HashMap< String, String> dataMap = new HashMap<String, String>();

				dataMap.put(Constants.cfaId, value.getString(Constants.cfaId));
				dataMap.put(Constants.cfaName, value.getString(Constants.cfaName));
				dataMap.put(Constants.DEALERNAME, value.getString(Constants.DEALERNAME));
				dataMap.put(Constants.cformId, value.getString(Constants.cformId));
				dataMap.put(Constants.cformno, value.getString(Constants.cformno));
				dataMap.put(Constants.cformvalue, value.getString(Constants.cformvalue));
				dataMap.put(Constants.modeofdispatch, value.getString(Constants.modeofdispatch));
				datalist.add(dataMap);			  
			}

			return datalist;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * { "Root": { "Inv": [{"ID": "0","InvoiceNo": "--Please Select"},{"ID": "1","InvoiceNo": "INV-0A0-001"}] }}
	 */

	public static ArrayList<HashMap<String, String>> getGoodsInvoices(String data){

		ArrayList<HashMap<String , String>> datalist = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("Inv");
			for(int i=0;i<array.length();i++){ 
				JSONObject value = array.getJSONObject(i);
				HashMap< String, String> dataMap = new HashMap<String, String>();

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.InvoiceNo, value.getString(Constants.InvoiceNo));
				datalist.add(dataMap);			  
			}

			return datalist;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	
	/**
	 * { 'Root': { 'data': [{'CustomerID': '40','CustomerName': 'Dealer- ABC','AMID': '128',
	 * 'AMName': 'Area Manager - 1','MobileNo': '+915555523698','Email': 'am1@pcindia.com'}] }}
	 */

	public static HashMap<String, String> getAreaManagerInfo(String data){

		HashMap<String , String> dataMap = new HashMap<String,String>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<array.length();i++){ 

				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.AM_ID, value.getString(Constants.AM_ID));
				dataMap.put(Constants.AM_NAME, value.getString(Constants.AM_NAME));
				dataMap.put(Constants.AM_MobNo, value.getString(Constants.AM_MobNo));
				dataMap.put(Constants.AM_EMAIL, value.getString(Constants.AM_EMAIL));
				dataMap.put(Constants.AM_CITY, value.getString(Constants.AM_CITY));
				dataMap.put(Constants.area, value.getString(Constants.area));

			}

			return dataMap;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 *{ 'Root': { 'Data': [{'MobileNo': '9881375553','City': 'New Delhi','Area': 'Jamia Nagar'}] }}

	 */
	
	public static HashMap<String, String> getDealerInfo(String data){

		HashMap<String , String> dataMap = new HashMap<String,String>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("Data");
			for(int i=0;i<array.length();i++){ 

				JSONObject value = array.getJSONObject(i);

				
				dataMap.put(Constants.area, value.getString(Constants.area));
				dataMap.put(Constants.city, value.getString(Constants.city));
				dataMap.put(Constants.MOBILE_NUMBER, value.getString("MobileNo"));
			}

			return dataMap;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * { 'Root': { 'data': [{'ID': '18','Name': 'Retailer - 3','PhoneNo': '3333333333','ContactType': 'Retailer','Address': 'Address-3',
	 * 'Pincode': '110025','Area': 'Jamia Nagar','City': 'New Delhi','State': 'Delhi','IdentityNo': '3333333333','AccountNumber': '3333333333333333333',
	 * 'BranchName': '','BankName': ''}
	 */


	public static ArrayList<HashMap<String , String>> getUnverifiedCutomerList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.NAME, value.getString(Constants.NAME));
				dataMap.put(Constants.contacttype, value.getString(Constants.contacttype));
				dataMap.put(Constants.address, value.getString(Constants.address));

				dataMap.put(Constants.pincode, value.getString(Constants.pincode));
				dataMap.put(Constants.area, value.getString(Constants.area));
				dataMap.put(Constants.city, value.getString(Constants.city));
				dataMap.put(Constants.state, value.getString(Constants.state));
				dataMap.put(Constants.AccountNumber, value.getString(Constants.AccountNumber));
				dataMap.put(Constants.BranchName, value.getString(Constants.BranchName));
				dataMap.put(Constants.BankName, value.getString(Constants.BankName));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * { 'Root': { 'dtProductSeg': [{'Id': '0','SegmentName': '--PLEASE SELECT--'},{'Id': '1','SegmentName': 'C V'},
	 * {"Root":{"data":[{"Name":"C V","ID":"1"},{"Name":"PV","ID":"4"}
	 */
	
	public static ArrayList<HashMap<String, String>> getSegment(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString("ID"));
				dataMap.put(Constants.SEGMENTNAME, value.getString(Constants.SEGMENTNAME));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	public static ArrayList<HashMap<String, String>> getAgenda(String data){

	//	{ 'Root': { 'data': [{'ID': '0','Description': '--Please Select--'},{'ID': '1','Description': 'Meeting with Dealer'},{'ID': '2','Description': 'Meeting with CFA'}] }}
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString("ID"));
				dataMap.put(Constants.Description, value.getString(Constants.Description));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static ArrayList<HashMap<String, String>> getAmFiscalDetails(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.Display, value.getString(Constants.Display));
				dataMap.put(Constants.From, value.getString(Constants.From));
				dataMap.put(Constants.To, value.getString(Constants.To));
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	//{ 'Root': { 'data': [{'AMID': '133','CYSaleQuantity': '0','CYSaleValue': '0.00',
		//'PYSaleQuantity': '0','PYSaleValue': '0.00','CYSecondarySaleQuantity': '122',
		//'CYSecondarySaleValue': '2439509','PYSecondarySaleQuantity': '185',
		//'PYSecondarySaleValue': '307127','CYFitmentQuantity': '0','CYFitmentValue': '0.00',
		//'PYFitmentQuantity': '0','PYFitmentValue': '0.00','CYPendingQuantity': '-16',
		//'CYPendingValue': '-77788.76','PYPendingQuantity': '7252','PYPendingValue': '6342790.60'}] }}
	
	
	
	public static ArrayList<HashMap<String, String>> getAmSaleRportDetails(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.PYSaleQuantity, value.getString(Constants.PYSaleQuantity));
				dataMap.put(Constants.PYSaleValue, value.getString(Constants.PYSaleValue));
				dataMap.put(Constants.CYSaleQuantity, value.getString(Constants.CYSaleQuantity));
				dataMap.put(Constants.CYSaleValue, value.getString(Constants.CYSaleValue));
				
				
				dataMap.put(Constants.PYSecondarySaleQuantity, value.getString(Constants.PYSecondarySaleQuantity));
				dataMap.put(Constants.PYSecondarySaleValue, value.getString(Constants.PYSecondarySaleValue));
				dataMap.put(Constants.CYSecondarySaleQuantity, value.getString(Constants.CYSecondarySaleQuantity));
				dataMap.put(Constants.CYSecondarySaleValue, value.getString(Constants.CYSecondarySaleValue));
				

				dataMap.put(Constants.PYPendingQuantity, value.getString(Constants.PYPendingQuantity));
				dataMap.put(Constants.PYPendingValue, value.getString(Constants.PYPendingValue));
				dataMap.put(Constants.CYPendingQuantity, value.getString(Constants.CYPendingQuantity));
				dataMap.put(Constants.CYPendingValue, value.getString(Constants.CYPendingValue));
				
				dataMap.put(Constants.PYFitmentQuantity, value.getString(Constants.PYFitmentQuantity));
				dataMap.put(Constants.PYFitmentValue, value.getString(Constants.PYFitmentValue));
				dataMap.put(Constants.CYFitmentQuantity, value.getString(Constants.CYFitmentQuantity));
				dataMap.put(Constants.CYFitmentValue, value.getString(Constants.CYFitmentValue));
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	/*
	{ 'Root': { 'data': [{'ItemCode': '1PCP136P STD','CYQuantity': '220',
		'CYPendingOrderValue': '13756.60','PYQuantity': '','PYPendingOrderValue': ''},
	*/
	public static ArrayList<HashMap<String, String>> getAmSalePendingRportDetails(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.ItemCode, value.getString(Constants.ItemCode));
				dataMap.put(Constants.CYQuantity, value.getString(Constants.CYQuantity));
				dataMap.put(Constants.CYPendingOrderValue, value.getString(Constants.CYPendingOrderValue));
				dataMap.put(Constants.PYQuantity, value.getString(Constants.PYQuantity));
				dataMap.put(Constants.PYPendingOrderValue, value.getString(Constants.PYPendingOrderValue));
				
			
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
	
	
//	{ 'Root': { 'data': [{'ProductCode': '1PC 120KMPG STD','Quantity': '1','CouponValue': '75','AvgValue': '0','SaleValue': '0'},
	
	
	public static ArrayList<HashMap<String, String>> getAmCustomerMaster_FitmentDetails(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.PRODUCTCODE, value.getString(Constants.PRODUCTCODE));
				dataMap.put(Constants.DIRECTDEALERQUANTITY, value.getString(Constants.DIRECTDEALERQUANTITY));
				dataMap.put(Constants.CouponValue, value.getString(Constants.CouponValue));
				dataMap.put(Constants.AvgValue, value.getString(Constants.AvgValue));
				dataMap.put(Constants.SaleValue, value.getString(Constants.SaleValue));
				
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
//{ 'Root': { 'data': [{'ID': '133','ContactName': 'Prem AM'}] }}
	public static ArrayList<HashMap<String, String>> getAM(String data) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject object= new JSONObject(data);
			JSONArray array=object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String,String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
                dataMap.put(Constants.ID, value.getString("ID"));
				dataMap.put(Constants.CONTACTTYPENAME_2, value.getString(Constants.CONTACTTYPENAME_2));
				dataList.add(dataMap);
			}
	      return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<HashMap<String,String>> getOE(String data){
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		try {
			JSONObject object= new JSONObject(data);
			//{ 'Root': { 'data': [{'ID': '0','Name': '--- Please Select ---'}
			JSONArray array  =object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String,String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.NAME, value.getString(Constants.NAME));
				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	
	//{ 'Root': { 'data': [{'CityID': '0','City': '--- Please Select ---'},{'CityID': '46','City': 'Agra'},
	public static ArrayList<HashMap<String,String>> getDirect_dealer_Search(String data){
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		try {
			JSONObject object= new JSONObject(data);
			
			JSONArray array  =object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String,String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.city, value.getString(Constants.city));
				dataMap.put(Constants.cityID, value.getString(Constants.cityID));
				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	
	
	
	
	
	
	//{ 'Root': { 'data': [{'SaleTargetID': '0','GroupName': '--- Please Select ---'}] }}
	
	public static ArrayList<HashMap<String,String>> getDealerTargerProductGroups(String data){
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		try {
			JSONObject object= new JSONObject(data);
			
			JSONArray array  =object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String,String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.SaleTargetID, value.getString(Constants.SaleTargetID));
				dataMap.put(Constants.GroupName, value.getString(Constants.GroupName));
				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
	//{ 'Root': { 'data': [{'SaleTargetID': '0','SchemeName': '--- Please Select ---'}
	
	
	public static ArrayList<HashMap<String,String>> getDealerSchemes(String data){
		ArrayList<HashMap<String,String>> dataList = new ArrayList<HashMap<String,String>>();
		try {
			JSONObject object= new JSONObject(data);
			
			JSONArray array  =object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				HashMap<String,String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.SaleTargetID, value.getString(Constants.SaleTargetID));
				dataMap.put(Constants.SchemeName, value.getString(Constants.SchemeName));
				dataList.add(dataMap);
			}
			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 *{ 'Root': { 'data': [{'ID': '0','Name': '--- Please Select ---'}
	 */

	public static ArrayList<HashMap<String, String>> getApplications(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.NAME,value.getString(Constants.NAME));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	

	
	public static ArrayList<HashMap<String, String>> getTrendList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.DIRECTDEALER, value.getString(Constants.DIRECTDEALER));
				dataMap.put(Constants.DIRECTDEALERNAME, value.getString(Constants.DIRECTDEALERNAME));
				dataMap.put(Constants.DIRECTDEALERCITY, value.getString(Constants.DIRECTDEALERCITY));
				dataMap.put(Constants.DIRECTDEALERQUANTITY, value.getString(Constants.DIRECTDEALERQUANTITY));
				dataMap.put(Constants.DIRECTDEALERVALUE, value.getString(Constants.DIRECTDEALERVALUE));
				
				dataMap.put(Constants.RETAILERID, value.getString(Constants.RETAILERID));
				dataMap.put(Constants.RETAILERNAME, value.getString(Constants.RETAILERNAME));
				dataMap.put(Constants.RETAILERCITY, value.getString(Constants.RETAILERCITY));
				dataMap.put(Constants.RETAILERQUANTITY, value.getString(Constants.RETAILERQUANTITY));
				dataMap.put(Constants.RETAILERVALUE, value.getString(Constants.RETAILERVALUE));
				
				dataMap.put(Constants.MECHID, value.getString(Constants.MECHID));
				dataMap.put(Constants.MECHNAME, value.getString(Constants.MECHNAME));
				dataMap.put(Constants.MECHCITY, value.getString(Constants.MECHCITY));
				dataMap.put(Constants.MECHANICQUANTITYT, value.getString(Constants.MECHANICQUANTITYT));
				dataMap.put(Constants.MECHVALUE, value.getString(Constants.MECHVALUE));
				
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'Month': 'APR','Quantity': '20','DirectDealerValue': '36000.00','DealerQuantity': '1','DealerValue': '1800.00','MechQuantity': '1','MechValue': ''} }
	 */
	
	
	public static ArrayList<HashMap<String, String>> getMonthelyTrendList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.MONTH, value.getString(Constants.MONTH));
				dataMap.put(Constants.quantity, value.getString(Constants.quantity));
				dataMap.put(Constants.VALUE, value.getString(Constants.VALUE));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'ID': '27','DealerName': 'Dealer-1'}
	 */
	
	public static ArrayList<HashMap<String, String>> getDealerList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		HashMap<String , String> dataMapdefault = new HashMap<String,String>();
		dataMapdefault.put(Constants.ID, "-1");
		dataMapdefault.put(Constants.DEALERNAME, "-- Please Select--");
		dataList.add(dataMapdefault);
		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.DEALERNAME, value.getString(Constants.DEALERNAME));
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'ID': '27','Name': 'mech1'}
	 */
	public static ArrayList<HashMap<String, String>> getMechanicList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		HashMap<String , String> dataMapdefault = new HashMap<String,String>();
		dataMapdefault.put(Constants.ID, "-1");
		dataMapdefault.put(Constants.NAME, "-- Please Select--");
		dataList.add(dataMapdefault);
		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.NAME, value.getString(Constants.NAME));
				
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'ProductID': '790','PartNo': '1PC 101CSA STD','Quantity': '1','Value': '400.00'}
	 */
	
	public static ArrayList<HashMap<String, String>> getProductTrendList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ProductID, value.getString(Constants.ProductID));
				dataMap.put(Constants.PARTNO, value.getString(Constants.PARTNO));
				dataMap.put(Constants.quantity, value.getString(Constants.quantity));
				dataMap.put(Constants.VALUE, value.getString(Constants.VALUE));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'Month': 'ARP','Quantity': '4','Value': '7200.00'}] }}
	 */
	
	public static ArrayList<HashMap<String, String>> getProductMonthelyTrendList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.MONTH, value.getString(Constants.MONTH));
				dataMap.put(Constants.quantity, value.getString(Constants.quantity));
				dataMap.put(Constants.VALUE, value.getString(Constants.VALUE));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * { 'Root': { 'data': [{'DirectDealerQuantity': '20','DirectDealerValue': '36000.00','DealerQuantity': '4','DealerValue': '7200.00000000000','MechanicQuantity': '4','MechanicValue': '7200.00000000000'}] }}
	 */
	
	public static ArrayList<HashMap<String, String>> getCustomerTrendValue(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.DIRECTDEALERQUANTITY, value.getString("DirectDealerQuantity"));
				dataMap.put(Constants.DIRECTDEALERVALUE, value.getString(Constants.DIRECTDEALERVALUE));
				
				dataMap.put(Constants.RETAILERQUANTITY, value.getString(Constants.RETAILERQUANTITY));
				dataMap.put(Constants.RETAILERVALUE, value.getString(Constants.RETAILERVALUE));
				
				
				dataMap.put(Constants.MECHANICQUANTITYT, value.getString(Constants.MECHANICQUANTITYT));
				dataMap.put(Constants.MECHVALUE, value.getString(Constants.MECHVALUE));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
	
/*
{ 'Root': { 'AMVisitLogAnswers': [{'AnswerID': '3','Answer': 'Three Types of products I sells'},
{'AnswerID': '4','Answer': 'Six Types of products I sells'}] }}*/
	
	public static ArrayList<HashMap<String, String>> getAmQuestionAnswer(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMVisitLogAnswers");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				dataMap.put(Constants.answerID, jsonObject.getString(Constants.answerID));
				dataMap.put(Constants.answer, jsonObject.getString(Constants.answer));
				
				/*dataMap.put(Constants.cityID, jsonObject.getString(Constants.cityID));
				
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				
				dataMap.put(Constants.stateID, jsonObject.getString(Constants.stateID));
				dataMap.put(Constants.state, jsonObject.getString(Constants.state));*/
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	
	
	
	
	/**{
   'Root':{
      'AMTerettory':[{
            'AMID':'128',
            'AreaID':'14273',
            'Area':'Jamia Nagar',
            'CityID':'2143',
            'City':'New Delhi',
            'StateID':'10',
            'State':'Delhi'
         },
	 * @param data
	 * @return
	 */
	public static ArrayList<HashMap<String, String>> getAmTerettoryList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMTerettory");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				dataMap.put(Constants.areaID, jsonObject.getString(Constants.areaID));
				dataMap.put(Constants.area, jsonObject.getString(Constants.area));
				
				dataMap.put(Constants.cityID, jsonObject.getString(Constants.cityID));
				
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				
				dataMap.put(Constants.stateID, jsonObject.getString(Constants.stateID));
				dataMap.put(Constants.state, jsonObject.getString(Constants.state));
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	/**
	 * { 'Root': { 'AMTerettoryCustomers': [{'ID': '40','Name': 'Dealer- ABC','ContactType': '14','AreaID': '14273','Area': 'Jamia Nagar','CityID': '2143','City': 'New Delhi','StateID': '10','State': 'Delhi','MobileNo': '9881375553'}] }}
	 */
//	{ 'Root': { 'AMTerettoryCustomers': [{'ID': '4583','Name': 'AJAY KR AGARWAL','FirmName': '',
	//'ContactType': '14','AreaID': '6193','Area': 'Calcutta G P O','CityID': '1580',
	//'City': 'Kolkata','StateID': '35','State': 'West Bengal','MobileNo': '9831025636','Planned': 'Planned'},
	

	public static ArrayList<HashMap<String, String>> getAmTerettoryCustomersList(String data) {
		JSONObject object;
		
		
//		{ 'Root': { 'SITerettoryCustomers': [{'ID': '4396','Name': 'Sampat','FirmName': '',
		//'ContactType': '14','AreaID': '9036','Area': 'Dhank Wadi','CityID': '2403','City': 'Pune','StateID': '21','State': 
		//'Maharashtra','MobileNo': '9860196321','Planned': ''},
		
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMTerettoryCustomers");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				
				dataMap.put(Constants.ID, jsonObject.getString(Constants.ID));
				dataMap.put(Constants.NAME, jsonObject.getString(Constants.NAME));
				dataMap.put(Constants.FirmName, jsonObject.getString(Constants.FirmName));
				
				
				dataMap.put(Constants.stateID, jsonObject.getString(Constants.stateID));
				dataMap.put(Constants.state, jsonObject.getString(Constants.state));
				
				dataMap.put(Constants.cityID, jsonObject.getString(Constants.cityID));
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				
				dataMap.put(Constants.areaID, jsonObject.getString(Constants.areaID));
				dataMap.put(Constants.area, jsonObject.getString(Constants.area));
				
				dataMap.put(Constants.MOBILE_NUMBER, jsonObject.getString(Constants.MOBILE_NUMBER));
				dataMap.put(Constants.Planned, jsonObject.getString(Constants.Planned));
				
				
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}



	public static ArrayList<TerettoryListModel> getTerettoryCustomersList(String data) {
		JSONObject object;


//	{
//					'ID': '11543',
//					'Name': 'SHOIB',
//					'FirmName': 'A K SELECT AUTO PARTS',
//					'ContactType': '15',
//					'AreaID': '5490',
//					'Area': 'Chithaya Road',
//					'CityID': '161',
//					'City': 'Bangalore',
//					'StateID': '25',
//					'State': 'Karnataka',
//					'MobileNo': '8880038853',
//					'IsCalled': '0',
//					'Planned': ''
//				}

		ArrayList<TerettoryListModel> dataList = new ArrayList<TerettoryListModel>();
		try {
			object = new JSONObject(data);

			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				TerettoryListModel amTerettoryListModel = new TerettoryListModel();

				amTerettoryListModel.setID(jsonObject.getString(Constants.ID));
				amTerettoryListModel.setName(jsonObject.getString(Constants.NAME));
				amTerettoryListModel.setContactType(jsonObject.getString(Constants.CONTACT_TYPE));
				amTerettoryListModel.setFirmName(jsonObject.getString(Constants.FirmName));
				amTerettoryListModel.setStateID(jsonObject.getString(Constants.stateID));
				amTerettoryListModel.setState(jsonObject.getString(Constants.state));
				amTerettoryListModel.setCityID(jsonObject.getString(Constants.cityID));
				amTerettoryListModel.setCity(jsonObject.getString(Constants.city));
				amTerettoryListModel.setAreaID(jsonObject.getString(Constants.areaID));
				amTerettoryListModel.setArea(jsonObject.getString(Constants.area));
				amTerettoryListModel.setMobileNo(jsonObject.getString(Constants.MOBILE_NUMBER));
				amTerettoryListModel.setPlanned(jsonObject.getString(Constants.Planned));
				amTerettoryListModel.setAddress(jsonObject.getString(Constants.Address));
				amTerettoryListModel.setIsCalled(jsonObject.getString(Constants.IsCalled));


				dataList.add(amTerettoryListModel);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	
	
	public static ArrayList<HashMap<String, String>> getSITerettoryCustomers(String data) {
		JSONObject object;
		
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			
			JSONArray array = object.getJSONObject("Root").getJSONArray("SITerettoryCustomers");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				
				dataMap.put(Constants.ID, jsonObject.getString(Constants.ID));
				dataMap.put(Constants.NAME, jsonObject.getString(Constants.NAME));
				dataMap.put(Constants.FirmName, jsonObject.getString(Constants.FirmName));
				
				
				dataMap.put(Constants.stateID, jsonObject.getString(Constants.stateID));
				dataMap.put(Constants.state, jsonObject.getString(Constants.state));
				
				dataMap.put(Constants.cityID, jsonObject.getString(Constants.cityID));
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				
				dataMap.put(Constants.areaID, jsonObject.getString(Constants.areaID));
				dataMap.put(Constants.area, jsonObject.getString(Constants.area));
				
				dataMap.put(Constants.MOBILE_NUMBER, jsonObject.getString(Constants.MOBILE_NUMBER));
				dataMap.put(Constants.Planned, jsonObject.getString(Constants.Planned));
				
				
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	/*{ 'Root': { 'data': [{'ID': '494','Code': 'UP1PM1AGR','DealerName': ' PRADEEP MOTOR COMPANY               ',
		'ContactName': 'NISCHCY DHINGRA','Phone': '9627721182','Address': 'SHOP NO. - 48, SEC.-2, T P NAGAR,',
		'City': 'Agra','Area': 'Udyognagar','State': 'Uttar Pradesh','PinCode': '282007'},
	*/
	
	
	
	public static ArrayList<HashMap<String, String>> getdirect_Direct_Dealer_searchList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();

				
				dataMap.put(Constants.DEALERNAME, jsonObject.getString(Constants.DEALERNAME));
				dataMap.put(Constants.CONTACTTYPENAME_2, jsonObject.getString(Constants.CONTACTTYPENAME_2));
				dataMap.put(Constants.Phone, jsonObject.getString(Constants.Phone));
				dataMap.put(Constants.streetaddress, jsonObject.getString(Constants.streetaddress));
				dataMap.put(Constants.state, jsonObject.getString(Constants.state));
				dataMap.put(Constants.city, jsonObject.getString(Constants.city));
				dataMap.put(Constants.area, jsonObject.getString(Constants.area));
				dataMap.put(Constants.PinCode, jsonObject.getString(Constants.PinCode));
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	
	
	//{ 'Root': { 'table': [{'SN': 'TGT1','SaleTargetID': '1','BaseValue': '0','TargetValue': '50','Actual': '50','Incentive': '0.5','Increment': '1','StartDate': '26 May 2013','EndDate': '30 Aug 2015','IsValueTarget': '1','IsPercentageIncentive': '1'},
	//{'SN': 'TGT2','SaleTargetID': '1','BaseValue': '51','TargetValue': '200','Actual': '48048','Incentive': '480.48','Increment': '1','StartDate': '26 May 2013','EndDate': '30 Aug 2015','IsValueTarget': '1','IsPercentageIncentive': '1'}] }}
	

	public static ArrayList<HashMap<String, String>> getDealerTargetVsSales(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("table");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				dataMap.put(Constants.StartDate, jsonObject.getString(Constants.StartDate));
				dataMap.put(Constants.EndDate, jsonObject.getString(Constants.EndDate));
				dataMap.put(Constants.SN, jsonObject.getString(Constants.SN));
				dataMap.put(Constants.Actual, jsonObject.getString(Constants.Actual));
				dataMap.put(Constants.TargetValue, jsonObject.getString(Constants.TargetValue));
				dataMap.put(Constants.Increment, jsonObject.getString(Constants.Increment));
				
				dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	

	public static ArrayList<HashMap<String, String>> getAmPendingCallList(String data) {
		JSONObject object;
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMVisitLog");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				HashMap<String, String> dataMap = new HashMap<String, String>();
				
				
				dataMap.put(Constants.visitLogIDPending, jsonObject.getString(Constants.visitLogIDPending));
				dataMap.put(Constants.customerName, jsonObject.getString(Constants.customerName));
			    dataMap.put(Constants.endDateTime, jsonObject.getString(Constants.endDateTime));
				dataMap.put(Constants.startDateTime, jsonObject.getString(Constants.startDateTime));
				dataMap.put(Constants.contactType, jsonObject.getString(Constants.contactType));
				dataMap.put(Constants.purposeOfVisit, jsonObject.getString(Constants.purposeOfVisit));
				dataMap.put(Constants.meetingNotes, jsonObject.getString(Constants.meetingNotes));
				
				
				
                dataList.add(dataMap);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	
	/*
	 * { 'Root': { 'data': [{'ID': '0','StateIncharge': '--Please Select--'},{'ID': '129','StateIncharge': 'State Incharge - 1'},
	 * {'ID': '137','StateIncharge': 'SI Delhi'}] }}
	 */
	
	public static ArrayList<HashMap<String, String>> getStateInchargeList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.STATE_INCHARGE, value.getString(Constants.STATE_INCHARGE));
				dataList.add(dataMap);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dataList;
	}
	
	/*
	 * 
	 * { 'Root': { 'data': [{'ID': '0','AreaManager': '--Please Select--'},{'ID': '128','AreaManager': 'Area Manager - 1'},
	 * {'ID': '133','AreaManager': 'Prem AM'},
	 * {'ID': '136','AreaManager': 'AM-123'}] }}
	 */
	
	public static ArrayList<HashMap<String, String>> getAreaManagerList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.AREAMANAGER, value.getString(Constants.AREAMANAGER));
				dataList.add(dataMap);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dataList;
	}
	
	/**
	 * { 'Root': { 'data': [{'ID': '0','Name': '-- Please Select --'},
	 * {'ID': '4082','Name': 'Dealer 12'},{'ID': '4084','Name': 'Dealer-1'},
	 * {'ID': '3278','Name': 'Erp Test Dealer'}
	 */
	
	public static ArrayList<HashMap<String, String>> getExternalContactListList(String data){


		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.NAME, value.getString(Constants.NAME));
				dataList.add(dataMap);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dataList;
	}
	
	public static ArrayList<HashMap<String, String>> getAMMappedCity(String data){
		
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("AMCity");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.ID, value.getString(Constants.ID));
				dataMap.put(Constants.city, value.getString(Constants.city));
				dataList.add(dataMap);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dataList;
		
	}

/*	public static ArrayList<HashMap<String, String>> getMechanicalObject(
			String data) {

		//{ 'Root': { 'cfa': [{'ID': '0','Code': '---Please Select---'}
				ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


				try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("data");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);

						dataMap.put(Constants.CODE, value.getString(Constants.CODE));
						dataMap.put(Constants.PARTNOID, value.getString(Constants.PARTNOID));
						dataList.add(dataMap);

					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;}
*/

	public static ArrayList<HashMap<String, String>> getDealerObject(
			String data) {

		//{ 'Root': { 'data': [{'ID': '0','DealerName': '-- Please Select --'}
				ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


				try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("data");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);

						dataMap.put(Constants.ID, value.getString(Constants.ID));
						dataMap.put(Constants.DEALERNAME, value.getString(Constants.DEALERNAME));
						dataList.add(dataMap);

					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;}

	public static ArrayList<HashMap<String, String>> getCFAObject(String data) {

		//{ 'Root': { 'cfa': [{'ID': '0','Code': '---Please Select---'},
		//{ 'Root': { 'table': [{'ID': '0','Code': '---Please Select---','CFACode': ''},{'ID': '15','Code': 'DWB [DWB] ','CFACode': 'DWB'}] }}
				ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


				try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("cfa");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);

						dataMap.put(Constants.ID, value.getString(Constants.ID));
						dataMap.put(Constants.CODE, value.getString(Constants.CODE));
						dataList.add(dataMap);

					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;}

	public static ArrayList<HashMap<String, String>> getItemQuantityObject(String data) {
		
		//'CurrentStock': '7187','CurrentStock_Value': '4403150',

		//{ 'Root': { 'data': [{'ProductCode': '1PC 1023CSA 012','CurrentStock': '0','CurrentStock_Value': '0.00',
		//'GIT': '0','GIT_Value': '0.00','RGap': '17','RGap_Value': '24466.91','YGap': '17','YGap_Value': '24466.91','GGap': '17','GGap_Value': '24466.91'}] }}
				ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


				try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("data");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);
						dataMap.put(Constants.CurrentStock, value.getString(Constants.CurrentStock));
						dataMap.put(Constants.CurrentStock_Value, value.getString(Constants.CurrentStock_Value));
						dataMap.put(Constants.GIT, value.getString(Constants.GIT));
						dataMap.put(Constants.GIT_Value, value.getString(Constants.GIT_Value));
						dataMap.put(Constants.RGap, value.getString(Constants.RGap));
						dataMap.put(Constants.RGap_Value, value.getString(Constants.RGap_Value));
						dataMap.put(Constants.YGap, value.getString(Constants.YGap));
						dataMap.put(Constants.YGap_Value, value.getString(Constants.YGap_Value));
						dataMap.put(Constants.GGap, value.getString(Constants.GGap));
						dataMap.put(Constants.GGap_Value, value.getString(Constants.GGap_Value));
		                dataList.add(dataMap);  
						
					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;}

	public static ArrayList<HashMap<String, String>> getDealerItemQuantityRYG(
			String data) {
	//	{ 'Root': { 'data': [{'CurrentStock': '770','CurrentStock_Value': '1124360','GIT': '124','GIT_Value': '250597',
		//'RGap': '80','RGap_Value': '115138','YGap': '150','YGap_Value': '218192','GGap': '300','GGap_Value': '440999'}] }}
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
			/*	dataMap.put(Constants.Product_Code, value.getString(Constants.Product_Code));*/
				dataMap.put(Constants.CurrentStock, value.getString(Constants.CurrentStock));
				dataMap.put(Constants.RGap, value.getString(Constants.RGap));
			    dataMap.put(Constants.YGap, value.getString(Constants.YGap));
				dataMap.put(Constants.GGap, value.getString(Constants.GGap));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
//	{ 'Root': { 'data': [{'AMName': 'Rajendra Sharma','AMMobileNo': '9352325381',
	//'AMmail': 'rajender.sharma@anandgroupindia.com','SIName': 'Not Available',
	//'SIMobileNo': 'Not Available','SImail': 'Not Available','RMName': 'Ratan Kumar Singh',
	//'RMMobileNo': '9305834958','RMmail': 'ratan.singh@anandindia.com',
	//'MMName': 'Not Available','MMMobileNo': 'Not Available','MMmail': 'Not Available'}] }}
	public static ArrayList<HashMap<String, String>> getCntactUS(
			String data) {
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.AM_NAME, value.getString(Constants.AM_NAME));
				dataMap.put(Constants.Am_Cotnact, value.getString(Constants.Am_Cotnact));
				dataMap.put(Constants.Am_EMAIL, value.getString(Constants.Am_EMAIL));
				
				dataMap.put(Constants.SI_NAME, value.getString(Constants.SI_NAME));
				dataMap.put(Constants.SI_CONTACT, value.getString(Constants.SI_CONTACT));
				dataMap.put(Constants.SI_EMAIL, value.getString(Constants.SI_EMAIL));
				
				dataMap.put(Constants.RM_NAME, value.getString(Constants.RM_NAME));
				dataMap.put(Constants.RM_CONTACT, value.getString(Constants.RM_CONTACT));
				dataMap.put(Constants.RM_EMAIL, value.getString(Constants.RM_EMAIL));
				
				dataMap.put(Constants.MM_NAME, value.getString(Constants.MM_NAME));
				dataMap.put(Constants.MM_CONTACT, value.getString(Constants.MM_CONTACT));
				dataMap.put(Constants.MM_EMAIL, value.getString(Constants.MM_EMAIL));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//{ 'Root': { 'data': [{'TransDate': '','Credit': '0','Debit': '500','RefNo': '','SortDate': '','Days': ''},,
	
	public static ArrayList<HashMap<String, String>> getCouponRedeemptionAccount(
			String data) {
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.TransDate, value.getString(Constants.TransDate));
				dataMap.put(Constants.RefNo, value.getString(Constants.RefNo));
				dataMap.put(Constants.Credit, value.getString(Constants.Credit));
			    dataMap.put(Constants.Debit, value.getString(Constants.Debit));
			    dataMap.put(Constants.Days, value.getString(Constants.Days));
				
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	//{ 'Root': { 'data': [{'CouponCode': 'MP133016184','Amount': '530'}] }}
	public static ArrayList<HashMap<String, String>> getCouponPendingPaymentDetails(
			String data) {
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.CouponCode, value.getString(Constants.CouponCode));
				dataMap.put(Constants.Amount, value.getString(Constants.Amount));
				
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static ArrayList<HashMap<String, String>> getCouponPendingAmount(
			String data) {
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.Amount, value.getString(Constants.Amount));
				
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

	public static ArrayList<HashMap<String, String>> getDealeMannualrSaleDiscount(
			String data) {
		// TODO Auto-generated method stub
		//{ 'Root': { 'dtDealerDiscPer': [{'MOrderDiscount': 'True','MOrderDiscountPer': '20.52','NestDiscount': 'True'}] }}
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("dtDealerDiscPer");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.MORDERDISCOUNT, value.getString(Constants.MORDERDISCOUNT));
				dataMap.put(Constants.MORDERDISCOUNTPER, value.getString(Constants.MORDERDISCOUNTPER));
				dataMap.put(Constants.NETDISCOUNT, value.getString(Constants.MORDERDISCOUNT));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}            
	


	public static ArrayList<HashMap<String, String>> getAmOutstandingCF_Result(
			String data) {
		// TODO Auto-generated method stub
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("dtDealerDiscPer");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.MORDERDISCOUNT, value.getString(Constants.MORDERDISCOUNT));
				dataMap.put(Constants.MORDERDISCOUNTPER, value.getString(Constants.MORDERDISCOUNTPER));
				dataMap.put(Constants.NETDISCOUNT, value.getString(Constants.MORDERDISCOUNT));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;}
	
	//{ 'Root': { 'data': [{'DealerID': '38','DealerCode': 'AP1PA3VIZ','CreditLimit': '0.00','CreditPeriods': '45',
	//	'OutStanding': '1111139.00','OverDue': '1168087.00','OD_30_Days': '1168087.00','Pending_CForms': '0'}] }}
	
	public static ArrayList<HashMap<String, String>> getDealerOutstandingSummary(
			String data) {
		// TODO Auto-generated method stub
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.OutStanding, value.getString(Constants.OutStanding));
				dataMap.put(Constants.OverDue, value.getString(Constants.OverDue));
				dataMap.put(Constants.OD_30_Days, value.getString(Constants.OD_30_Days));
				dataMap.put(Constants.Pending_CForms, value.getString(Constants.Pending_CForms));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;}

	
	
	
	//{ 'Root': { 'data': [{'DealerID': '27','DealerCode': 'AP1BI0VIJ',
	//'CreditLimit': '50000.00','CreditPeriods': '180','OutStanding': '2486109.00',
	//'OverDue': '2679519.00','OD_30_Days': '0.00','Pending_CForms': '0','LockReason': ''}] }}
	
	
	public static ArrayList<HashMap<String, String>> getDealerOutstanding_Dealer_Summary(
			String data) {
		// TODO Auto-generated method stub
		
		ArrayList<HashMap<String, String> > dataList= new ArrayList<HashMap<String,String>>();
		try {
			JSONObject jsonObject= new JSONObject(data);
			JSONArray jsonArray= jsonObject.getJSONObject("Root").getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = jsonArray.getJSONObject(i);
				dataMap.put(Constants.LockReason, value.getString(Constants.LockReason));
				dataMap.put(Constants.CreditLimit, value.getString(Constants.CreditLimit));
				dataMap.put(Constants.OutStanding, value.getString(Constants.OutStanding));
				dataMap.put(Constants.OverDue, value.getString(Constants.OverDue));
				dataMap.put(Constants.OD_30_Days, value.getString(Constants.OD_30_Days));
				dataMap.put(Constants.Pending_CForms, value.getString(Constants.Pending_CForms));
				dataList.add(dataMap);  
				
			}

			return dataList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;}
/*{ 'Root': { 'InvoiceDetail': [{'InvoiceID': '1','DispatchID': '1','InvoiceNo': 'SAM10110526',
	'ProductID': '1','Code': '1PC 10225CSASTD','DispatchedQty': '12','DamagedQty': '0',
	'NotAccepted': '0','ShortQty': '0','ExcessQty': '0','ReceivedQty': '12','Noofcase': '4','LoosePiece': '0'}

	*/
	public static ArrayList<HashMap<String, String>> getDealeGood_GIt_Recieved_Details(
			String data) {
	   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


				try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("InvoiceDetail");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);
						dataMap.put(Constants.InvoiceNo, value.getString(Constants.InvoiceNo));
						dataMap.put(Constants.code, value.getString(Constants.code));
						dataMap.put(Constants.ReceivedQty, value.getString(Constants.ReceivedQty));
						dataMap.put(Constants.DispatchedQty, value.getString(Constants.DispatchedQty));
						dataMap.put(Constants.ProductID, value.getString(Constants.ProductID));
						
						
		                dataList.add(dataMap);  
						
					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;}
//{ 'Root': { 'data': [{'BillNumber': 'SAP10110135','BillDate': '7/23/2013 12:00:00 AM','Amount': '8652.00'},
	public static ArrayList<HashMap<String, String>> getDealerOutstandingDetail(
			String data) {
		   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


					try {
						JSONObject object = new JSONObject(data);
						JSONArray array = object.getJSONObject("Root").getJSONArray("data");

						for(int i=0;i<array.length();i++){ 

							HashMap<String , String> dataMap = new HashMap<String,String>();
							JSONObject value = array.getJSONObject(i);
							dataMap.put(Constants.BILLNO, value.getString(Constants.BILLNO));
							dataMap.put(Constants.DATE, value.getString(Constants.DATE));
							dataMap.put(Constants.AMMOUNT, value.getString(Constants.AMMOUNT));
							dataList.add(dataMap);  
							
						}

						return dataList;

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					return null;}
	//{ 'Root': { 'data': [{'ProductID': '333','Quantity': '176','Value': '216044.88','PendingOrder': '0'}] }}
	public static ArrayList<HashMap<String, String>> getpuchageItemQuantityValueList(
			String data) {
		   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();


					try {
						JSONObject object = new JSONObject(data);
						JSONArray array = object.getJSONObject("Root").getJSONArray("data");

						for(int i=0;i<array.length();i++){ 

							HashMap<String , String> dataMap = new HashMap<String,String>();
							JSONObject value = array.getJSONObject(i);
							dataMap.put(Constants.DIRECTDEALERQUANTITY, value.getString(Constants.DIRECTDEALERQUANTITY));
							dataMap.put(Constants.VALUE, value.getString(Constants.VALUE));
							dataMap.put(Constants.PendingOrder, value.getString(Constants.PendingOrder));
							dataList.add(dataMap);  
							
						}

						return dataList;

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					return null;
					}

	public static ArrayList<HashMap<String, String>> getExternalContactTypeList(
			String data) {
		   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		 //  { 'Root': { 'data': [{'ID': '0','Type': '-- Please Select --'},{'ID': '14','Type': 'Dealer'}

					try {
						JSONObject object = new JSONObject(data);
						JSONArray array = object.getJSONObject("Root").getJSONArray("data");

						for(int i=0;i<array.length();i++){ 

							HashMap<String , String> dataMap = new HashMap<String,String>();
							JSONObject value = array.getJSONObject(i);
							dataMap.put(Constants.ID, value.getString(Constants.ID));
							dataMap.put(Constants.TYPE, value.getString(Constants.TYPE));
						
							dataList.add(dataMap);  
							
						}

						return dataList;

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					return null;
					}
	//{ 'Root': { 'data': [{'ID': '0','Name': '--Please Select--'}] }}
	public static ArrayList<HashMap<String, String>> getAreaManagerContacts(
			String data) {
		   ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		 
					try {
						JSONObject object = new JSONObject(data);
						JSONArray array = object.getJSONObject("Root").getJSONArray("data");

						for(int i=0;i<array.length();i++){ 

							HashMap<String , String> dataMap = new HashMap<String,String>();
							JSONObject value = array.getJSONObject(i);
							dataMap.put(Constants.ID, value.getString(Constants.ID));
							dataMap.put(Constants.NAME, value.getString(Constants.NAME));
						
							dataList.add(dataMap);  
							
						}

						return dataList;

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					return null;
					}

	public static ArrayList<HashMap<String, String>> getExternalContactMAster_MIS(
			String data) {
				ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
				//{ 'Root': { 'data': [{'ID': '5817','DealerID': '5664','City': '17','Area': '5886',
				//	'RedeemToTypeID': '4','ContactType': '15','Name': 'SUBRATA NASKAR',
				//	'MobileNo': '9903800313','Address': 'MAHESHTALA','Email': '',
				//	'DateofBirth': '','Type': 'A','AVG_Calls_Year': '0',
				//	'LastCallDate': '','CustomerValueAvgPM': '4557','CustomerValueTM': '13673'}] }}
try {
					JSONObject object = new JSONObject(data);
					JSONArray array = object.getJSONObject("Root").getJSONArray("data");

					for(int i=0;i<array.length();i++){ 

						HashMap<String , String> dataMap = new HashMap<String,String>();
						JSONObject value = array.getJSONObject(i);
						
						dataMap.put(Constants.MOBILE_NUMBER, value.getString(Constants.MOBILE_NUMBER));
						dataMap.put(Constants.streetaddress, value.getString(Constants.streetaddress));
						dataMap.put(Constants.emailid, value.getString(Constants.emailid));
						dataMap.put(Constants.dob, value.getString(Constants.dob));
						dataMap.put(Constants.TYPE, value.getString(Constants.TYPE));
						dataMap.put(Constants.AVG_Calls_Year, value.getString(Constants.AVG_Calls_Year));
						dataMap.put(Constants.LastCallDate, value.getString(Constants.LastCallDate));
						dataMap.put(Constants.CustomerValueAvgPM, value.getString(Constants.CustomerValueAvgPM));
						dataMap.put(Constants.CustomerValueTM, value.getString(Constants.CustomerValueTM));
		                dataList.add(dataMap);  
						
					}

					return dataList;

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

				return null;
				
	}
	
	//{ 'Root': { 'data': [{'AMID': '26','Target': '25.00','Sale': '245.48','Fitment_D': '12.41','Fitment_M': '9.99','Pending': '8.60',
	//'Penging_FTM': '2.24','Lock': '0','R': '519','Y': '519','DCalls': '54','MCalls': '3','Dealer_V': '142','Dealer_U': '24','Mech_V': '20','Mech_U': '21'}] }}
	
	public static ArrayList<HashMap<String, String>> getAMSummary_MIS(
			String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		
try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.SaleTarget, value.getString("Target"));
				dataMap.put(Constants.SaleActual, value.getString("Sale"));
				
				dataMap.put(Constants.FitmentDealer, value.getString("Fitment_D"));
				dataMap.put(Constants.FitmentMechanical, value.getString("Fitment_M"));
				
				dataMap.put(Constants.OrderPending, value.getString("Pending"));
				dataMap.put(Constants.OrderFTM, value.getString("Penging_FTM"));
				
				dataMap.put(Constants.OutOD, value.getString("OD"));
				dataMap.put(Constants.OUTLock, value.getString("Lock"));
				
				dataMap.put(Constants.R, value.getString("R"));
				dataMap.put(Constants.Y, value.getString("Y"));
		
				
				dataMap.put(Constants.CallDealer, value.getString("DCalls"));
				dataMap.put(Constants.CallMeachanic, value.getString("MCalls"));
				
				dataMap.put(Constants.RDLRT, value.getString("Dealer_V"));
				dataMap.put(Constants.RDLRP, value.getString("Dealer_U"));
				
				dataMap.put(Constants.RMET, value.getString("Mech_V"));
				dataMap.put(Constants.RMEP, value.getString("Mech_U"));
				
				
				
                dataList.add(dataMap);  
				
			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
		}

	
	
	
	public static ArrayList<HashMap<String, String>> getDealerSummary_MIS(
			String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		
try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.TargetFTY, value.getString(Constants.TargetFTY));
				dataMap.put(Constants.ActualFTY, value.getString(Constants.ActualFTY));
				dataMap.put(Constants.TargetFTM, value.getString(Constants.TargetFTM));
				dataMap.put(Constants.ActualFTM, value.getString(Constants.ActualFTM));
				dataMap.put(Constants.SaleToday, value.getString(Constants.SaleToday));
				dataMap.put(Constants.SaleFTM, value.getString(Constants.SaleFTM));
				dataMap.put(Constants.PendingOrder, value.getString(Constants.PendingOrder));
				dataMap.put(Constants.PendingOrderFTM, value.getString(Constants.PendingOrderFTM));
				dataMap.put(Constants.RGap, value.getString(Constants.RGap));
				dataMap.put(Constants.YGap, value.getString(Constants.YGap));
				dataMap.put(Constants.GGap, value.getString(Constants.GGap));
				dataMap.put(Constants.OutStanding, value.getString(Constants.OutStanding));
				dataMap.put(Constants.OverDueToday, value.getString(Constants.OverDueToday));
				dataMap.put(Constants.OverDue, value.getString(Constants.OverDue));
				dataMap.put(Constants.CurrentCreditLimit, value.getString(Constants.CurrentCreditLimit));
				dataMap.put(Constants.AvailableCheques, value.getString(Constants.AvailableCheques));
				
				
				
                dataList.add(dataMap);  
				
			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
		}

	
	
	
	
	
	//	{ 'Root': { 'data': [{'Date': '07/15/2015','Time': '19:30:43','DateToday': '7/15/2015 7:30:43 PM'}] }}
	public static ArrayList<HashMap<String, String>> gettingSystemTime(
			String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		
try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.Date, value.getString(Constants.Date));
				dataMap.put(Constants.TIME, value.getString(Constants.TIME));
				dataMap.put(Constants.combinedDateAndTime, value.getString(Constants.combinedDateAndTime));
				dataMap.put(Constants.visitLogID, value.getString(Constants.visitLogID));
				dataList.add(dataMap);  
				
			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
		}

	//{ 'Root': { 'Data': [{'Code': 'WB1CM0KOL','DealerName': 'CHANDHOK MOTORS','City': 'Kolkata'}] }}
	public static ArrayList<HashMap<String, String>> getAm_Cus_City_Name(
			String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		
try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("Data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.code, value.getString(Constants.code));
				dataMap.put(Constants.DEALERNAME, value.getString(Constants.DEALERNAME));
				dataMap.put(Constants.city, value.getString(Constants.city));
				dataList.add(dataMap);  
				
			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
		}
//{ 'Root': { 'data': [{'CouponCode': 'DP131185991','Amount': '100','Trans': 'CR'},
	//{'CouponCode': 'DP144265000','Amount': '100','Trans': 'CR'},
	
	public static ArrayList<HashMap<String, String>> getDebit_Credit_List(String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();
		
try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){ 

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				
				dataMap.put(Constants.CouponCode, value.getString(Constants.CouponCode));
				dataMap.put(Constants.Amount, value.getString(Constants.Amount));
				dataMap.put(Constants.Trans, value.getString(Constants.Trans));
				dataList.add(dataMap);  
				
			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
		}

	public static ArrayList<AmCallPlanerModel> getAmCallPlannerCustomersList(
			String data) {

		ArrayList<AmCallPlanerModel> saleList = new ArrayList<AmCallPlanerModel>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
        
			for(int i=0;i<array.length();i++){ 

				AmCallPlanerModel amCallPlanerModel  = new AmCallPlanerModel();
				JSONObject value = array.getJSONObject(i);
				//'OrderQty': ''

				amCallPlanerModel.setID(value.getString(Constants.ID));
				amCallPlanerModel.setContactType(value.getString(Constants.contacttype));
				amCallPlanerModel.setPlanned(value.getString(Constants.Planned));
				
				amCallPlanerModel.setFirmName(value.getString("FirmName"));
				amCallPlanerModel.setName(value.getString(Constants.NAME)); //----
				amCallPlanerModel.setState(value.getString("State"));
				amCallPlanerModel.setCity(value.getString("City")); //---
				amCallPlanerModel.setArea(value.getString("Area")); //--
				amCallPlanerModel.setMobileNo(value.getString("MobileNo"));
				amCallPlanerModel.setLastVisited(value.getString("LastVisited")); //--
				amCallPlanerModel.setNoOfCalls_Last30Days(value.getString("NoOfCalls_Last30Days"));
			    amCallPlanerModel.setBusinessTrend(value.getString("BusinessTrend"));
			    saleList.add(amCallPlanerModel);
              
			}

			return saleList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			
		}

		return null;
	}
	
	
	
	
	
	

	public static ArrayList<DashboardModel> getDashboardList(
			String data) {

	//	{ 'Root': { 'table': [{'AMName': 'Sanjoy Roy','AMMobileNo': '9333025114',
		//'AMEmail': 'sanjoy.roy@anandgroupindia.com','LoginTime': ' 11:21AM','Calls': '3','Color': 'Y'}] }}
		ArrayList<DashboardModel> saleList = new ArrayList<DashboardModel>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("table");
        
			for(int i=0;i<array.length();i++){ 

				DashboardModel dashboardModel  = new DashboardModel();
				JSONObject value = array.getJSONObject(i);
				
				dashboardModel.setAMName(value.getString("AMName"));
				dashboardModel.setAMMobileNo(value.getString("AMMobileNo"));
				dashboardModel.setAMEmail(value.getString("AMEmail"));
				dashboardModel.setLoginTime(value.getString("LoginTime")); 
				dashboardModel.setCalls(value.getString("Calls")); 
				dashboardModel.setColor(value.getString("Color"));
				
			    saleList.add(dashboardModel);
              
			}

			return saleList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			
		}

		return null;
	}


	public HashMap<String, Object> getOTP(String data) {
		JSONObject object;
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		try {
			object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				dataMap.put(Constants.OTP, jsonObject.get("OTP"));

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dataMap;
	}



	//	{ 'Root': { 'data': [{'Date': '07/15/2015','Time': '19:30:43','DateToday': '7/15/2015 7:30:43 PM'}] }}
	public static ArrayList<HashMap<String, String>> getUnclosedVisitsList(
			String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);

				dataMap.put(Constants.CONTACT_TYPE, value.getString(Constants.CONTACT_TYPE));
				dataMap.put(Constants.OTHER_ACTIVITY, value.getString(Constants.OTHER_ACTIVITY));
				dataMap.put(Constants.START_DATE_TIME, value.getString(Constants.START_DATE_TIME));
				dataMap.put(Constants.MOBILE, value.getString(Constants.MOBILE));
				dataMap.put(Constants.NAME, value.getString(Constants.NAME));
				dataMap.put(Constants.GARAGE_NAME, value.getString(Constants.GARAGE_NAME));
				dataMap.put(Constants.VISIT_LOG_ID, value.getString(Constants.VISIT_LOG_ID));
				dataMap.put(Constants.CITY, value.getString(Constants.CITY));
				dataMap.put(Constants.STATE, value.getString(Constants.STATE));

				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}



	//{ 'Root': { 'table': [{'ID': '30','SubSegmentName': '3WD'}] }}
	public static ArrayList<SubSegmentType> getSubSegment(String data) {
		ArrayList<SubSegmentType> subSegmentList = new ArrayList();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("data");

			for(int i=0;i<array.length();i++){
				SubSegmentType dataList  = new SubSegmentType();
				JSONObject value = array.getJSONObject(i);
				dataList.setSubSegmentID(value.getInt("ID"));
				dataList.setSubSegmentName(value.getString("SubSegmentName"));
				subSegmentList.add(dataList);
			}

			return subSegmentList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}


	//{ 'Root': { 'table': [{'LastCallDate': 'Apr 30 2020 12:17 PM','LastNote': 'No Note Prepared'}] }}
	public static ArrayList<HashMap<String, String>> getPreviousCallDetails(String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("table");

			for(int i=0;i<array.length();i++){

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.LAST_CALL_DATE, value.getString(Constants.LAST_CALL_DATE));
				dataMap.put(Constants.LAST_NOTE, value.getString(Constants.LAST_NOTE));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	//{ 'Root': { 'table': [{'LastCallDate': 'Apr 30 2020 12:17 PM','LastNote': 'No Note Prepared'}] }}
	public static ArrayList<HashMap<String, String>> getReceivableDetails(String data) {
		ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String,String>>();

		try {
			JSONObject object = new JSONObject(data);
			JSONArray array = object.getJSONObject("Root").getJSONArray("table");

			for(int i=0;i<array.length();i++){

				HashMap<String , String> dataMap = new HashMap<String,String>();
				JSONObject value = array.getJSONObject(i);
				dataMap.put(Constants.BILL_NUMBER, value.getString(Constants.BILL_NUMBER));
				dataMap.put(Constants.BILL_DATE, value.getString(Constants.BILL_DATE));
				dataMap.put(Constants.AMOUNT, value.getString(Constants.AMOUNT));
				dataList.add(dataMap);

			}

			return dataList;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}



}