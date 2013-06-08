package com.allrun.jdtecmanagesystem.dao;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.utils.Base64;
import com.allrun.jdtecmanagesystem.utils.EasyLogger;

public class DataAccessSoap {

	public static enum PropertyType {
		TYPE_STRING(0), TYPE_INTEGER(1), TYPE_LONG(2), TYPE_BOOLEAN(3), TYPE_OBJECT(
				4);

		public int value;

		PropertyType(int aValue) {
			value = aValue;
		}
	}

	private String mUrl;

	private String mMethodName;

	private SoapObject mRequest;
	
	private String mSoapAction;

	public DataAccessSoap() {

	}

	public DataAccessSoap(String nameSpace, String url, String method) {
		mUrl = url;
		mMethodName = method;
		mRequest = new SoapObject(nameSpace, method);
		mSoapAction = nameSpace + method;
	}

	public String request() throws IOException, XmlPullParserException,
			RuntimeException, SocketTimeoutException {

		HttpTransportSE androidHttpTransport = new HttpTransportSE(mUrl, App.HTTPTIMEOUT);
		

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.bodyOut = mRequest;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(mRequest);

		new MarshalBase64().register(envelope); // 传递byte[]时需要序列化

		SoapObject response = null;

		androidHttpTransport.call(mSoapAction, envelope);

		if (envelope.bodyIn instanceof SoapFault) {
			EasyLogger.e( "DataAccessSoap:request()",
					envelope.bodyIn.toString());
		}

		response = (SoapObject) (envelope.bodyIn);

		return response.getProperty(mMethodName + "Result").toString();
	}

	public byte[] requestBytes() throws IOException, XmlPullParserException,
			RuntimeException, SocketTimeoutException {

		HttpTransportSE androidHttpTransport = new HttpTransportSE(mUrl, App.HTTPTIMEOUT);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.bodyOut = mRequest;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(mRequest);

		SoapObject response = null;

		androidHttpTransport.call(mSoapAction, envelope);

		if (envelope.bodyIn instanceof SoapFault) {
			EasyLogger.e( "DataAccessSoap:requestBytes()",
					envelope.bodyIn.toString());
		}

		response = (SoapObject) (envelope.bodyIn);

		return Base64.decode(response.getProperty(mMethodName + "Result")
				.toString());
	}

	public void setProperty(String propertyName, Object propertyValue,
			PropertyType type) {
		PropertyInfo pi = new PropertyInfo();
		Object typeValue = null;
		switch (type) {
		case TYPE_STRING:
			typeValue = PropertyInfo.STRING_CLASS;
			break;
		case TYPE_INTEGER:
			typeValue = PropertyInfo.INTEGER_CLASS;
			break;
		case TYPE_LONG:
			typeValue = PropertyInfo.LONG_CLASS;
			break;
		case TYPE_BOOLEAN:
			typeValue = PropertyInfo.BOOLEAN_CLASS;

			break;
		case TYPE_OBJECT:
			typeValue = MarshalBase64.BYTE_ARRAY_CLASS;

			break;
		default:
			typeValue = PropertyInfo.STRING_CLASS;
			break;
		}

		pi.setType(typeValue);
		pi.setName(propertyName);
		pi.setValue(propertyValue);

		mRequest.addProperty(pi);
	}

}
