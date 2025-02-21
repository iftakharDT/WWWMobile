package com.arcis.vgil.trackapp.data;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class User implements KvmSerializable {

 	private String UserID;
    private String UserName;
    private String UserType;
    private String Password;
    private String MachineIP;
    private String ProductVersion;
    private boolean Authenticated;
    private String RejectionReason;

    private static User _instance=null;

    public static User getUserInstance(){
		if (_instance == null) {
			_instance = new User();
			return _instance;
		} else {
			return _instance;
		}
    	
    }
    
	public  Object getProperty(int arg0) {
	
		switch (arg0) {
		case 0 : return  UserID;
        case 1 : return  UserName;
        case 2 : return  UserType;
        case 3 : return  Password;
        case 4 : return  MachineIP;
        case 5 : return  ProductVersion;
        case 6 : return  Authenticated;
        case 7 : return  RejectionReason;
 
		}

		return null;
	}

	
	public int getPropertyCount() {
		return 7;
	}

	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		switch (index) {
		
		case 0 :
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserID";
			break;  
			
        case 1 :
        	info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserName";
			break;
			
        case 2 :
        	info.type = PropertyInfo.STRING_CLASS;
			info.name = "UserType";
			break;
			
        case 3 :
        	info.type = PropertyInfo.STRING_CLASS;
			info.name = "Password";
			break;
			
        case 4 :
        	info.type = PropertyInfo.STRING_CLASS;
			info.name = "MachineIP";
			break;
			
        case 5 :
        	info.type = PropertyInfo.STRING_CLASS;
			info.name = "ProductVersion";
			break; 
			
        case 6 :
        	info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "Authenticated";
			break; 
			
        case 7 :info.type = PropertyInfo.STRING_CLASS;
			info.name = "RejectionReason";
			break;
			
   
		default:
			break;
		}

	}

	public void setProperty(int index, Object value) {
		switch (index) {

		case 0 :
			UserID= value.toString();
			break;
			
        case 1 : 
        	UserName= value.toString();
			break;
			
        case 2 : 
        	UserType= value.toString();
			break;
        
        case 3 : 
        	Password= value.toString();
			break;
        
        case 4 : 
        	MachineIP= value.toString();
			break;
        
        case 5 : 
        	ProductVersion= value.toString();
			break;
        
        case 6 :
        	Authenticated= value.toString() != null;
			break;
			
        case 7 :
        	RejectionReason= value.toString();
			break;
        
      

        default:
			break;
		}
	}

	@Override
	public String toString() {
		
		return ((Boolean)Authenticated).toString();
		
	}
}