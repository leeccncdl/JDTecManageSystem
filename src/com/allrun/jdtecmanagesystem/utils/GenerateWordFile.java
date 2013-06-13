package com.allrun.jdtecmanagesystem.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.allrun.jdtecmanagesystem.model.MissionInfo;

import android.content.Context;
import android.util.Log;

public class GenerateWordFile {
	
	public static void word(Context context, File file, MissionInfo info) {
		try {
			//模板文件
			InputStream tempInputStream = null;
			switch(Integer.parseInt(info.getMISSIONTYPECODE())){
				case 0:
					tempInputStream = context.getAssets().open("template_page2.dot");
					break;
				case 1:
					tempInputStream = context.getAssets().open("template_page2.dot");
					break;
				case 2:
					tempInputStream = context.getAssets().open("template_page1.dot");
					break;
			}
		    POIFSFileSystem fs = new POIFSFileSystem(tempInputStream);
			HWPFDocument doc = new HWPFDocument(fs);
			Range range = doc.getRange();

			switch(Integer.parseInt(info.getMISSIONTYPECODE())){
				case 0:
				case 1:
					replaceText(range,info);
					break;
				case 2:
					replaceTextCharge(range,info);
					break;
			}
			
			OutputStream fileOutputStream = new FileOutputStream(file);
		    doc.write(fileOutputStream);
		    fileOutputStream.flush();
		    fileOutputStream.close();
		} catch (Exception e) {
		    Log.i("BluetoothPrintDemo",e.getMessage());
		}
	}
	
	private static Range replaceText(Range range, MissionInfo info) {
		if (null == range ) {
			return range;
		}
		range.replaceText("##A##", info.getREGION());
		range.replaceText("##B##", info.getAFFILIATION());
		range.replaceText("##C##", info.getDIRVER());
		range.replaceText("##D##", info.getDIRVERPHONE());
		range.replaceText("##E##", info.getPLATENO());
		range.replaceText("##F##", info.getMISSIONTYPE());
		range.replaceText("##G##", info.getDEVICEMANUFACTURE());
		range.replaceText("##H##", info.getDEVICETYPE());
		range.replaceText("##I##", info.getCOMMUNICTIONCARD());
		range.replaceText("##J##", info.getDEVICENUMBER());
		range.replaceText("##K##", info.getPHONE());
		range.replaceText("##L##", Utils.GetNowDateZh());
		return range;
	}

	private static Range replaceTextCharge(Range range, MissionInfo info) {
		if (null == range ) {
			return range;
		}
		range.replaceText("##A##", info.getAFFILIATION());
		range.replaceText("##B##", info.getPLATENO());
		System.out.println("replaceTextCharge:"+info.getDEVICENUMBER());
		range.replaceText("##C##", info.getVEHICLEDEVICENUMBER());
		range.replaceText("##D##", Utils.GetDateZh(info.getEXPRATIONDATE()));
		range.replaceText("##E##", Utils.GetDateZh(info.getEXPRATIONDATE_NEW()));
		range.replaceText("##F##", Utils.GetNowDateZh());
		range.replaceText("##G##", info.getPHONE());
		range.replaceText("##H##", info.getFAX());
		return range;
	}
}
