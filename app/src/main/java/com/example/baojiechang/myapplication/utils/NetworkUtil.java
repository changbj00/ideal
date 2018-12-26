package com.example.baojiechang.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


/**
 * @ClassName NetworkUtil
 * @Description 网络连接工具类
 *
 */
public class NetworkUtil {

	// private static final String TAG = "NetworkUtil";

	private static final String TYPE_NAME = "mobile";
	private static final String CARRIEROPERATOR_NULL = "NULL";//
	private static final String CARRIEROPERATOR_CMCC = "CMCC";//移动
	private static final String CARRIEROPERATOR_CUCC = "CUCC";//联通
	private static final String CARRIEROPERATOR_CTCC = "CTCC";//电信


	/**
	 * @Description 得到当前网络连接类型
	 * @param context
	 * @return String 当前网络连接类型名称
	 */
	public static String getCurrentNetworkTypeName(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo == null) {
			return null;
		} else {// 当前存在网络接入方式，获取具体的网络接入类型值
			String typeName = networkInfo.getTypeName();
			if (typeName.contains(TYPE_NAME)) {
				String extraInfo = networkInfo.getExtraInfo();
				if (extraInfo == null) {
					extraInfo = "ChinaTelecom";
				}
				return extraInfo;
			}
			return typeName;
		}
	}

	/**
	 * @Description 得到当前网络连接类型
	 * @param context
	 * @return String 当前网络连接类型名称
	 */
	public static String getCurrentNetworkType(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo == null) {
			return null;
		} else {// 当前存在网络接入方式，获取具体的网络接入类型值
			String strNetworkType = "";
			String typeName = networkInfo.getTypeName();
			if (typeName.toLowerCase().contains(TYPE_NAME)) {
				String strSubTypeName = networkInfo.getSubtypeName();
				// TD-SCDMA   networkType is 17
				int networkType = networkInfo.getSubtype();
				switch (networkType){
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
						strNetworkType = "2G";
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
					case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
					case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
						strNetworkType = "3G";
						break;
					case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
						strNetworkType = "4G";
						break;
					default:
						// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
						if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000"))
						{
							strNetworkType = "3G";
						}
						else
						{
							strNetworkType = strSubTypeName;
						}

						break;
				}
				return strNetworkType;
			}
			return typeName;
		}
	}

	/**
	 * @Description 获取sim卡运营商
	 * @return String CARRIEROPERATOR_NULL，CARRIEROPERATOR_CMCC,CARRIEROPERATOR_CUCC,CARRIEROPERATOR_CTCC
	 */
	public static String getCarrieroperator() {
		TelephonyManager tm = (TelephonyManager) BRApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
		@SuppressLint("MissingPermission") String imsi=tm.getSubscriberId();
		if(!StringUtil.checkStr(imsi)){
			return CARRIEROPERATOR_NULL;
		}
		if(imsi.startsWith("46000")||imsi.startsWith("46002")){
			return CARRIEROPERATOR_CMCC;
		}
		if(imsi.startsWith("46001")){
			return CARRIEROPERATOR_CUCC;
		}
		if(imsi.startsWith("46003")){
			return CARRIEROPERATOR_CTCC;
		}
		return CARRIEROPERATOR_NULL;
	}

	/**
	 * @Description 判断网络连接是否存在并可用
	 * @param context
	 * @return boolean true可用，false不可用
	 */
	public static boolean isConnected(Context context) {

		if (context == null)
			return false;

		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			if (networkInfo == null) {
				return false;
			} else {
				if (networkInfo.isConnected()) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

	}
}
