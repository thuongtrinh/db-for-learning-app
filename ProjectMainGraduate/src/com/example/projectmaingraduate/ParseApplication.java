package com.example.projectmaingraduate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Application;
import android.util.Log;

import com.example.object.AccountAdmin;
import com.example.object.AccountUser;
import com.example.object.Delivery;
import com.example.object.DetailPhone;
import com.example.object.DetailTablet;
import com.example.object.DetailAccessory;
import com.example.object.Gift;
import com.example.object.Income;
import com.example.object.PhoneNumber;
import com.example.object.TabletNumber;
import com.example.object.TypeAccessory;
import com.example.object.TypePhone;
import com.example.object.TypeTablet;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ParseApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize Crash Reporting.
		ParseCrashReporting.enable(this);

		// Enable Local Datastore.
		// Parse.enableLocalDatastore(this);

		// Add your initialization code here
		Parse.initialize(this, "KZ7Hk3ib7uUj0WbfWlauZQAKHLPmtzyBo7EDXJGn",
				"Bt2hc0YUQ8HnTvgER6Gzf21LjMgS2qXseKq2IU4v");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);
	}
	
	
	public static ArrayList<TypePhone> getTypePhone() {
		ArrayList<TypePhone> listTypePhone = new ArrayList<TypePhone>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PhoneType");
		try {
			List<ParseObject> objects = query.find();
			if (objects.size() > 0) {
				for (ParseObject parseObject : objects) {
					TypePhone typePhone = new TypePhone();
					typePhone.setName(parseObject.get("Form").toString());
					typePhone.setAvatar(parseObject.getParseFile("Avatar"));
					typePhone.setId(parseObject.getObjectId());
					listTypePhone.add(typePhone);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTypePhone;
	}

	public static ArrayList<TypeTablet> getTypeTablet() {
		ArrayList<TypeTablet> listTypetablet = new ArrayList<TypeTablet>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TabletType");
		try {
			List<ParseObject> objects = query.find();
			if (objects.size() > 0) {
				for (ParseObject parseObject : objects) {
					TypeTablet typeTablet = new TypeTablet();
					typeTablet.setName(parseObject.get("Form").toString());
					typeTablet.setImage(parseObject.getParseFile("image"));
					typeTablet.setRating(parseObject.getString("Rating"));
					typeTablet.setRatingNumber(parseObject.getInt("RatingNumber"));
					typeTablet.setId(parseObject.getObjectId());
					listTypetablet.add(typeTablet);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTypetablet;
	}

	public static ArrayList<TypeAccessory> getTypeAccessory() {
		ArrayList<TypeAccessory> listTypeAccessory = new ArrayList<TypeAccessory>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccessoriesType");
		try {
			List<ParseObject> objects = query.find();
			int size = objects.size(), i = 0;
			if (size > 0) {
				TypeAccessory typeAccessory = new TypeAccessory();
				for (ParseObject parseObject : objects) {
					if (i % 2 == 0) {
						typeAccessory.setNameLeft(parseObject.get("Form").toString());
						typeAccessory.setImageLeft(parseObject.getParseFile("Image"));
						typeAccessory.setRatingLeft(parseObject.getString("Rating"));
						typeAccessory.setRatingNumberLeft(parseObject.getInt("RatingNumber"));
						typeAccessory.setIdLeft(parseObject.getObjectId());
						if (i == size - 1) {
							listTypeAccessory.add(typeAccessory);
						}
					} else {
						typeAccessory.setNameRight(parseObject.get("Form").toString());
						typeAccessory.setImageRight(parseObject.getParseFile("Image"));
						typeAccessory.setRatingRight(parseObject.getString("Rating"));
						typeAccessory.setRatingNumberRight(parseObject.getInt("RatingNumber"));
						typeAccessory.setIdRight(parseObject.getObjectId());
						listTypeAccessory.add(typeAccessory);
						typeAccessory = new TypeAccessory();
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTypeAccessory;
	}
	
	
	public static ArrayList<TypeAccessory> getTypeAccessoryAdmin() {
		ArrayList<TypeAccessory> listTypeAccessory = new ArrayList<TypeAccessory>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccessoriesType");
		try {
			List<ParseObject> list = query.find();
			if (list.size() > 0) {
				for (ParseObject parseObject : list) {
					TypeAccessory typeAccessory = new TypeAccessory();
					typeAccessory.setIdLeft(parseObject.getObjectId());
					typeAccessory.setNameLeft(parseObject.get("Form").toString());
					typeAccessory.setImageLeft(parseObject.getParseFile("Image"));
//					typeAccessory.setRatingLeft(parseObject.getString("Rating"));
//					typeAccessory.setRatingNumberLeft(parseObject.getInt("RatingNumber"));
					listTypeAccessory.add(typeAccessory);
				}  
					 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTypeAccessory;
	}
	
	
	public static ArrayList<Gift> getGiftInventory(){
		ArrayList<Gift> listGift = new ArrayList<Gift>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Gift");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0){
				for(ParseObject parseObject : list) {
					Gift gift = new Gift();
					gift.setLabel(parseObject.getString("Label"));
					gift.setImage(parseObject.getParseFile("Image"));
					gift.setId(parseObject.getObjectId());
					listGift.add(gift);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listGift;
	}
	
	public static Gift getInformationOfGift(String id){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Gift");
		query.whereEqualTo("objectId", id);
		Gift gift = null;
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0){
				for(ParseObject parseObject : list) {
					gift = new Gift();
					gift.setId(parseObject.getObjectId());
					gift.setLabel(parseObject.getString("Label"));
					gift.setImage(parseObject.getParseFile("Image"));
					gift.setContent(parseObject.getString("Content"));
//					gift.setArrayIdPromotions((ArrayList<String>) parseObject.get("AccessoriesId"));
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return gift;
	}
	
	public static ArrayList<String> getArrayIdPromotionsOfGift2(String columnName, String id){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Gift");
		query.whereEqualTo("objectId", id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0){
				for(ParseObject parseObject : list) {
					return (ArrayList<String>) parseObject.get(columnName);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ArrayList<DetailPhone> getRepresentPhone(String columnName, String id) {
		ArrayList<DetailPhone> listRepresentPhone = new ArrayList<DetailPhone>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
//		query.include("PhoneType");
		query.whereEqualTo(columnName, id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					DetailPhone representPhone = new DetailPhone();
					representPhone.setAvatar(parseObject.getParseFile("Avatar"));
					representPhone.setName(parseObject.getString("Label"));
					representPhone.setSaleOff(parseObject.getBoolean("SaleOff"));
					representPhone.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					representPhone.setId(parseObject.getObjectId());
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						representPhone.setCondition("Còn hàng");
						representPhone.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						representPhone.setCondition("Sắp có hàng");
						representPhone.setPrice("0");
					} else {
						representPhone.setCondition("Hết hàng");
						representPhone.setPrice("0");
					}
					listRepresentPhone.add(representPhone);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listRepresentPhone;
	}
	
	
	public static ArrayList<DetailTablet> getRepresentTablet(String columnName, String id) {
		ArrayList<DetailTablet> listRepresentTablet = new ArrayList<DetailTablet>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
//		query.include("TabletType");
		query.whereEqualTo(columnName, id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					DetailTablet representTablet = new DetailTablet();
					representTablet.setAvatar(parseObject.getParseFile("Avatar"));
					representTablet.setName(parseObject.getString("Label"));
					representTablet.setSaleOff(parseObject.getBoolean("SaleOff"));
					representTablet.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					representTablet.setId(parseObject.getObjectId());
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						representTablet.setCondition("Còn hàng");
						representTablet.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						representTablet.setCondition("Sắp có hàng");
						representTablet.setPrice("0");
					} else {
						representTablet.setCondition("Hết hàng");
						representTablet.setPrice("0");
					}
					listRepresentTablet.add(representTablet);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listRepresentTablet;
	}
	
	
	public static ArrayList<DetailAccessory> getRepresentAccessory(String columnName, String id) {
		ArrayList<DetailAccessory> listRepresentAccessory = new ArrayList<DetailAccessory>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
//		query.include("AccessoriesType");
		query.whereEqualTo(columnName, id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				Log.d("list.size() > 0", "okokoko");
				for(ParseObject parseObject : list) {
					DetailAccessory representAccessory = new DetailAccessory();
					representAccessory.setAvatar(parseObject.getParseFile("Avatar"));
					representAccessory.setName(parseObject.getString("Label"));
					representAccessory.setSaleOff(parseObject.getBoolean("SaleOff"));
					representAccessory.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					representAccessory.setId(parseObject.getObjectId());
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						representAccessory.setCondition("Còn hàng");
						representAccessory.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						representAccessory.setCondition("Sắp có hàng");
						representAccessory.setPrice("0");
					} else {
						representAccessory.setCondition("Hết hàng");
						representAccessory.setPrice("0");
					}
					listRepresentAccessory.add(representAccessory);
				}
			} else {
				Log.d("list.size() > 0", "kkokokoko");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listRepresentAccessory;
	}

	
//	 This way don't use because over time
//	
//	public static ArrayList<DetailAccessory> getRepresentAccessory(String id) {
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
//		query.include("AccessoriesType");
//		try {
//			List<ParseObject> list = query.find();
//			if(list.size() > 0) {
//				Log.d("list.size() > 0", "okokoko" + list.size());
//				listRepresentAccessory = new ArrayList<DetailAccessory>();
//				for(ParseObject parseObject : list) {
//					ParseObject object = parseObject.getParseObject("AccessoriesType");
//					if(object.getObjectId().toString().equalsIgnoreCase(id)) {
//						DetailAccessory representAccessory = new DetailAccessory();
//						representAccessory.setAvatar(parseObject.getParseFile("Avatar"));
//						representAccessory.setName(parseObject.getString("Label"));
//						representAccessory.setSaleOff(parseObject.getBoolean("SaleOff"));
//						representAccessory.setPercentPromotion(parseObject.getInt("PercentPromotional"));
//						representAccessory.setId(parseObject.getObjectId());
//						String state = parseObject.getString("condition").trim();
//						if (state.equalsIgnoreCase("2")) { // còn hàng
//							representAccessory.setCondition("Còn hàng");
//							representAccessory.setPrice(parseObject.getString("Price"));
//						} else if (state.equalsIgnoreCase("1")) {
//							representAccessory.setCondition("Sắp có hàng");
//							representAccessory.setPrice("0");
//						} else {
//							representAccessory.setCondition("Hết hàng");
//							representAccessory.setPrice("0");
//						}
//						listRepresentAccessory.add(representAccessory);
//					}
//				}
//			} else {
//				Log.d("list.size() > 0", "kokokoko");
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return listRepresentAccessory;
//	}
	
	
	
	
	public static DetailPhone getInforDetailPhone(String idDetail) {
		DetailPhone detailPhone = new DetailPhone();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
		query.include("PhoneDetail");
		query.whereEqualTo("objectId", idDetail);
		try {
			List<ParseObject> list = query.find();
			if(list.size() >0) {
				for(ParseObject parseObject : list) {
					detailPhone.setName(parseObject.getString("Label"));
					detailPhone.setAvatar(parseObject.getParseFile("Avatar"));
					detailPhone.setId(parseObject.getObjectId());
					detailPhone.setSaleOff(parseObject.getBoolean("SaleOff"));
					detailPhone.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					//
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						detailPhone.setCondition("Còn hàng");
						detailPhone.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						detailPhone.setCondition("Sắp có hàng");
						detailPhone.setPrice("0");
					} else {
						detailPhone.setCondition("Hết hàng");
						detailPhone.setPrice("0");
					}
					
					detailPhone.setGuarantee(parseObject.getString("Guarantee"));
					detailPhone.setState(parseObject.getString("State"));
					//get Data phone number of a Phone object
					ArrayList<PhoneNumber> arrayPhoneNumber = new ArrayList<PhoneNumber>();
					ParseRelation<ParseObject> relation = parseObject.getRelation("PhoneNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							PhoneNumber phoneNumber = new PhoneNumber();
							phoneNumber.setImage(object.getParseFile("ImagePhone"));
							phoneNumber.setNumber(object.getInt("Number"));
							phoneNumber.setColor(object.getString("Color"));
							phoneNumber.setIdDetails(object.getObjectId());
							arrayPhoneNumber.add(phoneNumber);
						}
					}
					detailPhone.setPhoneNumbers(arrayPhoneNumber);
					
					// get detail information of Phone object
					ParseObject object = parseObject.getParseObject("PhoneDetail");
					if(object != null) {
						detailPhone.setInfor3G(object.getString("Infor_3G"));
						detailPhone.setInfor4G(object.getString("Infor_4G"));
						detailPhone.setSim(object.getString("SIM"));
						detailPhone.setSize(object.getString("Size"));
						detailPhone.setWeight(object.getString("Weight"));
						detailPhone.setTypeMonitor(object.getString("TypeMonitor"));
						detailPhone.setMonitorSize(object.getString("Monitor_size"));
						detailPhone.setMemory(object.getString("Memory"));
						detailPhone.setMemoryCardSlot(object.getString("Memory_card_slot"));
						detailPhone.setWlan(object.getString("WLAN"));
						detailPhone.setBluetooth(object.getString("Bluetooth"));
						detailPhone.setUsb(object.getString("USB"));
						detailPhone.setNfc(object.getString("NFC"));
						detailPhone.setGps(object.getString("GPS"));
						detailPhone.setOperatorSystem(object.getString("Operator_system"));
						detailPhone.setChipset(object.getString("Chipset"));
						detailPhone.setCpu(object.getString("CPU"));
						detailPhone.setGpu(object.getString("GPU"));
						detailPhone.setSensors(object.getString("Sensors"));
						detailPhone.setMainCamera(object.getString("Main_camera"));
						detailPhone.setSubCamera(object.getString("Sub_camera"));
						detailPhone.setVideo(object.getString("Video"));
						detailPhone.setPin(object.getString("Pin"));
						detailPhone.setConversationTime(object.getString("ConversationTime"));
						detailPhone.setWaitTime(object.getString("WaitTime"));
						detailPhone.setPlayMusicTime(object.getString("Time_play_music"));
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return detailPhone;
	}
	
	

	public static DetailTablet getInforDetailTablet(String idDetail) {
		DetailTablet detailTablet = new DetailTablet();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
		query.include("TabletDetail");
		query.whereEqualTo("objectId", idDetail);
		try {
			List<ParseObject> list = query.find();
			if(list.size() >0) {
				for(ParseObject parseObject : list) {
					detailTablet.setName(parseObject.getString("Label"));
					detailTablet.setAvatar(parseObject.getParseFile("Avatar"));
					detailTablet.setSaleOff(parseObject.getBoolean("SaleOff"));
					detailTablet.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					detailTablet.setId(parseObject.getObjectId());
					//
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						detailTablet.setCondition("Còn hàng");
						detailTablet.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						detailTablet.setCondition("Sắp có hàng");
						detailTablet.setPrice("0");
					} else {
						detailTablet.setCondition("Hết hàng");
						detailTablet.setPrice("0");
					}
					
					detailTablet.setGuarantee(parseObject.getString("Guarantee"));
					detailTablet.setState(parseObject.getString("State"));
					
					//get Data phone number of a Phone object
					ArrayList<TabletNumber> arrayTabletNumber = new ArrayList<TabletNumber>();
					ParseRelation<ParseObject> relation = parseObject.getRelation("TabletNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							TabletNumber tabletNumber = new TabletNumber();
							tabletNumber.setImage(object.getParseFile("ImageTablet"));
							tabletNumber.setNumber(object.getInt("Number"));
							tabletNumber.setColor(object.getString("Color"));
							tabletNumber.setIdDetails(object.getObjectId());
							arrayTabletNumber.add(tabletNumber);
						}
					}
					detailTablet.setTabletNumbers(arrayTabletNumber);
					
					ParseObject object = parseObject.getParseObject("TabletDetail");
					if(object != null) {
						detailTablet.setInfor3G(object.getString("Infor_3G"));
						detailTablet.setInfor4G(object.getString("Infor_4G"));
						detailTablet.setSim(object.getString("SIM"));
						detailTablet.setSize(object.getString("Size"));
						detailTablet.setWeight(object.getString("Weight"));
						detailTablet.setTypeMonitor(object.getString("TypeMonitor"));
						detailTablet.setMonitorSize(object.getString("Monitor_size"));
						detailTablet.setMemory(object.getString("Memory"));
						detailTablet.setMemoryCardSlot(object.getString("Memory_card_slot"));
						detailTablet.setWlan(object.getString("WLAN"));
						detailTablet.setBluetooth(object.getString("Bluetooth"));
						detailTablet.setUsb(object.getString("USB"));
						detailTablet.setNfc(object.getString("NFC"));
						detailTablet.setGps(object.getString("GPS"));
						detailTablet.setOperatorSystem(object.getString("Operator_system"));
						detailTablet.setChipset(object.getString("Chipset"));
						detailTablet.setCpu(object.getString("CPU"));
						detailTablet.setGpu(object.getString("GPU"));
						detailTablet.setSensors(object.getString("Sensors"));
						detailTablet.setMainCamera(object.getString("Main_camera"));
						detailTablet.setSubCamera(object.getString("Sub_camera"));
						detailTablet.setVideo(object.getString("Video"));
						detailTablet.setPin(object.getString("Pin"));
						detailTablet.setConversationTime(object.getString("ConversationTime"));
						detailTablet.setWaitTime(object.getString("WaitTime"));
						detailTablet.setPlayMusicTime(object.getString("Time_play_music"));
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return detailTablet;
	}

	public static DetailAccessory getInforDetailAccessory(String idDetail) {
		DetailAccessory detailAccessory = new DetailAccessory();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
		query.whereEqualTo("objectId", idDetail);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					detailAccessory.setId(idDetail);
					detailAccessory.setName(parseObject.getString("Label"));
					detailAccessory.setState(parseObject.getString("State"));
					detailAccessory.setSaleOff(parseObject.getBoolean("SaleOff"));
					detailAccessory.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					detailAccessory.setInforDetail(parseObject.getString("Infor_Detail"));
					detailAccessory.setSumAccessory(parseObject.getInt("Number"));
					detailAccessory.setSumAccessorySold(parseObject.getInt("NumberSold"));
					detailAccessory.setGuarantee(parseObject.getString("Guarantee"));
					detailAccessory.setImage1(parseObject.getParseFile("Image1"));
					detailAccessory.setImage2(parseObject.getParseFile("Image2"));
					detailAccessory.setImage3(parseObject.getParseFile("Image3"));
					detailAccessory.setImage4(parseObject.getParseFile("Image4"));
					detailAccessory.setImage5(parseObject.getParseFile("Image5"));
					detailAccessory.setAvatar(parseObject.getParseFile("Avatar"));
					detailAccessory.setImageRepresent(parseObject.getParseFile("Avatar"));
					String state = parseObject.getString("condition").trim();
					if (state.equalsIgnoreCase("2")) { // còn hàng
						detailAccessory.setCondition("Còn hàng");
						detailAccessory.setPrice(parseObject.getString("Price"));
					} else if (state.equalsIgnoreCase("1")) {
						detailAccessory.setCondition("Sắp có hàng");
						detailAccessory.setPrice("0");
					} else {
						detailAccessory.setCondition("Hết hàng");
						detailAccessory.setPrice("0");
					}
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return detailAccessory;
	}


	// get Name products tablet or phone to display on head of shop
	public static String getNameTypeProduct(String tableName, String id) {
		String title = "";
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() >0) {
				for(ParseObject parseObject : list) {
					title = parseObject.getString("Form");
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return title;
	}


	public static void UpdateRating(String tableName, String idUpdate, float rate, int ratingNumber) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", idUpdate);
		try {
			List<ParseObject> list = query.find();
			if(list.size() >0) {
				for(ParseObject parseObject : list) {
					parseObject.put("Rating", Float.toString(rate));
					parseObject.put("RatingNumber", ratingNumber);
					parseObject.saveInBackground();
					break;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


	public static void initValueIdColumnPromotions(String idDetail, String tableName, String nameColumnId) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Gift");
		ParseObject objectGift = null;
		query.whereEqualTo("objectId", idDetail);
		try {
			List<ParseObject> list = query.find();
			if(list.size() >0) {
				for(ParseObject parseObject : list) {
					objectGift = parseObject;
					break;
				}
			}  
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if (objectGift != null) {
			ArrayList<String> listId = new ArrayList<String>();
			ParseQuery<ParseObject> query1 = ParseQuery.getQuery(tableName);
			try {
				List<ParseObject> list1 = query1.find();
				if (list1.size() > 0) {
					for (ParseObject parseObject : list1) {
						if (parseObject.getBoolean("SaleOff")) {
							listId.add(parseObject.getObjectId());
						}
					}
					objectGift.put(nameColumnId, listId); ////////////////
					objectGift.saveInBackground();
				} else {
					Log.d("list1", "list1 = null");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			Log.d("objectGift", "objectGift = null");
		}

	}


	public static ArrayList<DetailAccessory> getInforRepresentAccesoryPromotions(String idGift) {
		
		ArrayList<String> listIdAccessory = getArrayIdPromotionsOfGift2("AccessoriesId", idGift);
		
		ArrayList<DetailAccessory> listRepresentAccessoriesPromotions = new ArrayList<DetailAccessory>();
		if(listIdAccessory != null) {
			for(String Id : listIdAccessory) {
				DetailAccessory accessory = getRepresentAccessory("objectId",Id).get(0);
				if(accessory != null){
					listRepresentAccessoriesPromotions.add(accessory);
				}
			}
		}
		
		return listRepresentAccessoriesPromotions;
	}

	
	public static ArrayList<DetailTablet> getInforRepresentTabletPromotions(String idGift) {
		
		ArrayList<String> listIdTablet = getArrayIdPromotionsOfGift2("TabletsId", idGift);
		
		ArrayList<DetailTablet> listRepresentTabletPromotions = new ArrayList<DetailTablet>();
		if(listIdTablet != null) {
			for(String Id : listIdTablet) {
				DetailTablet tablet = getRepresentTablet("objectId", Id).get(0);
				if(tablet != null){
					listRepresentTabletPromotions.add(tablet);
				}
			}
		}
		
		return listRepresentTabletPromotions;
	}
	

	public static ArrayList<DetailPhone> getInforRepresentPhonePromotions(String idGift) {
		
		ArrayList<String> listIdPhone = getArrayIdPromotionsOfGift2("PhonesId", idGift);
		
		ArrayList<DetailPhone> listRepresentPhonePromotions = new ArrayList<DetailPhone>();
		if(listIdPhone != null) {
			for(String Id : listIdPhone) {
				DetailPhone phone = getRepresentPhone("objectId", Id).get(0);
				if(phone != null){
					listRepresentPhonePromotions.add(phone);
				}
			}
		}
		return listRepresentPhonePromotions;
	}
	
	
	public static ArrayList<AccountAdmin> getInforAccountAdmin() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AdminAccount");
		ArrayList<AccountAdmin> listAccount = new ArrayList<AccountAdmin>();
		try {
			 List<ParseObject> list = query.find();
			 if (list.size() > 0) {
				for(ParseObject object : list) {
					AccountAdmin account = new AccountAdmin();
					account.setUser(object.getString("AccountAdmin"));
					account.setPass(object.getString("Password"));
					listAccount.add(account);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listAccount;
	}
	
	public static ArrayList<AccountUser> getInforAccountUser() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		ArrayList<AccountUser> listAccount = new ArrayList<AccountUser>();
		try {
			 List<ParseObject> list = query.find();
			 if (list.size() > 0) {
				for(ParseObject object : list) {
					AccountUser account = new AccountUser();
					account.setAccount(object.getString("Account"));
					account.setPassword(object.getString("Password"));
					listAccount.add(account);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listAccount;
	}
	
	public static Boolean UpdatePasswordAdmin(String account, String password) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AdminAccount");
		query.whereEqualTo("AccountAdmin", account);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject object : list) {
					object.put("Password", password);
					object.saveInBackground();
				}
				return true;
			} else {
				Log.d("UPDATE PASSWORD ADMIN", "KO TON TAI TAI KHOAN NAY");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("UPDATE PASSWORD ADMIN", "LOI NGOAI LE DA XAY RA");
			return false;
		}
		
		return false;
	}


	public static ArrayList<DetailPhone> getDetailPhoneManager(String idType) {
		// TODO Auto-generated method stub
		ArrayList<DetailPhone> arrayList = new ArrayList<DetailPhone>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
		query.whereEqualTo("IdTypePhone", idType);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					DetailPhone phone = new DetailPhone();
					phone.setId(parseObject.getObjectId());
					phone.setAvatar(parseObject.getParseFile("Avatar"));
					phone.setName(parseObject.getString("Label"));
					ParseRelation<ParseObject> relation = parseObject.getRelation("PhoneNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					int Sum = 0, Sumsold = 0;
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							Sum += object.getInt("Number");
							Sumsold += object.getInt("NumberSold");
						}
					}
					phone.setSumPhone(Sum);
					phone.setSumPhoneSold(Sumsold);
					arrayList.add(phone);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}

	public static ArrayList<DetailTablet> getDetailTabletManager(String idType) {
		// TODO Auto-generated method stub
		ArrayList<DetailTablet> arrayList = new ArrayList<DetailTablet>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
		query.whereEqualTo("IdTypeTablet", idType);
		try {
			List<ParseObject> list = query.find();
			if (list.size() > 0) {
				for (ParseObject parseObject : list) {
					DetailTablet tablet = new DetailTablet();
					tablet.setId(parseObject.getObjectId());
					tablet.setAvatar(parseObject.getParseFile("Avatar"));
					tablet.setName(parseObject.getString("Label"));
					ParseRelation<ParseObject> relation = parseObject.getRelation("TabletNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					int Sum = 0, Sumsold = 0;
					if (listQuery.size() > 0) {
						for (ParseObject object : listQuery) {
							Sum += object.getInt("Number");
							Sumsold += object.getInt("NumberSold");
						}
					}
					tablet.setSumTablet(Sum);
					tablet.setSumTabletSold(Sumsold);
					arrayList.add(tablet);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	
	public static ArrayList<DetailAccessory> getDetailAccessoryManager(String idType) {
		// TODO Auto-generated method stub
		ArrayList<DetailAccessory> arrayList = new ArrayList<DetailAccessory>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
		query.whereEqualTo("IdTypeAccessories", idType);
		try {
			List<ParseObject> list = query.find();
			if (list.size() > 0) {
				for (ParseObject parseObject : list) {
					DetailAccessory accessory = new DetailAccessory();
					accessory.setId(parseObject.getObjectId());
					accessory.setAvatar(parseObject.getParseFile("Avatar"));
					accessory.setName(parseObject.getString("Label"));
					
					accessory.setSumAccessory(parseObject.getInt("Number"));
					accessory.setSumAccessorySold(parseObject.getInt("NumberSold"));
					
					arrayList.add(accessory);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	
	
	public static ArrayList<PhoneNumber> getDetailColorPhoneManager(String idColorType) {
		ArrayList<PhoneNumber> arrayList = new ArrayList<PhoneNumber>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
		query.whereEqualTo("objectId", idColorType);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					ParseRelation<ParseObject> relation = parseObject.getRelation("PhoneNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							PhoneNumber phoneNumber = new PhoneNumber();
							phoneNumber.setImage(object.getParseFile("ImagePhone"));
							phoneNumber.setColor(object.getString("Color"));
							phoneNumber.setNumber(object.getInt("Number"));
							phoneNumber.setNumberSold(object.getInt("NumberSold"));
							phoneNumber.setIdDetails(object.getObjectId());
							arrayList.add(phoneNumber);
						}
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayList;
	}
	
	public static ArrayList<TabletNumber> getDetailColorTabletManager(String idColorType) {
		ArrayList<TabletNumber> arrayList = new ArrayList<TabletNumber>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
		query.whereEqualTo("objectId", idColorType);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					ParseRelation<ParseObject> relation = parseObject.getRelation("TabletNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							TabletNumber tabletNumber = new TabletNumber();
							tabletNumber.setImage(object.getParseFile("ImageTablet"));
							tabletNumber.setColor(object.getString("Color"));
							tabletNumber.setNumber(object.getInt("Number"));
							tabletNumber.setNumberSold(object.getInt("NumberSold"));
							tabletNumber.setIdDetails(object.getObjectId());
							arrayList.add(tabletNumber);
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return arrayList;
	}


	public static boolean RemoveObjectProduct(String tableName, String tableNumberName, String id) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", id);
		 try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				ParseQuery<ParseObject> parseQuery = list.get(0).getRelation(tableNumberName).getQuery();
				List<ParseObject> list2 = parseQuery.find();
				if(list2.size() > 0) {
					for(ParseObject parseObject : list2) {
						parseObject.deleteInBackground();
					}
				}
				list.get(0).deleteInBackground();
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		 return false;
	}
	
	
	public static boolean RemoveObjectProductAccessory(String id) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
		query.whereEqualTo("objectId", id);
		 try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).deleteInBackground();
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		 return false;
	}
	
	
	public static boolean RemoveObjectSubProduct(String tableName, String idDetails) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", idDetails);
		 try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).deleteInBackground();
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		 return false;
	}
	

	public static DetailPhone getInforUpdateProductsPhone(String id) {
		DetailPhone phone = new DetailPhone();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
		query.whereEqualTo("objectId", id);
		
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					phone.setName(parseObject.getString("Label"));
					phone.setPrice(parseObject.getString("Price"));
					phone.setGuarantee(parseObject.getString("Guarantee"));
					phone.setState(parseObject.getString("State"));
					phone.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					phone.setSaleOff(parseObject.getBoolean("SaleOff"));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return phone;
	}
	
	
	public static DetailTablet getInforUpdateProductsTablet(String id) {
		DetailTablet tablet = new DetailTablet();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
		query.whereEqualTo("objectId", id);
		
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					tablet.setName(parseObject.getString("Label"));
					tablet.setPrice(parseObject.getString("Price"));
					tablet.setGuarantee(parseObject.getString("Guarantee"));
					tablet.setState(parseObject.getString("State"));
					tablet.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					tablet.setSaleOff(parseObject.getBoolean("SaleOff"));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tablet;
	}
	
	
	public static DetailAccessory getInforUpdateProductsAccessory(String id) {
		DetailAccessory accessory = new DetailAccessory();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
		query.whereEqualTo("objectId", id);
		
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					accessory.setName(parseObject.getString("Label"));
					accessory.setPrice(parseObject.getString("Price"));
					accessory.setGuarantee(parseObject.getString("Guarantee"));
					accessory.setState(parseObject.getString("State"));
					accessory.setPercentPromotion(parseObject.getInt("PercentPromotional"));
					accessory.setSaleOff(parseObject.getBoolean("SaleOff"));
					accessory.setSumAccessory(parseObject.getInt("Number"));
					accessory.setSumAccessorySold(parseObject.getInt("NumberSold"));
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessory;
	}


	public static boolean VerifyDataUpdateProduct(String tableName, String id, String label, String price,
			int percent, boolean saleOff, String guarantee, String state) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject object : list) {
					object.put("Label", label);
					object.put("Price", price);
					object.put("PercentPromotional", percent);
					object.put("Guarantee", guarantee);
					object.put("State", state);
					object.put("SaleOff", saleOff);
					object.saveInBackground();
					return true;
				}
			} else {
				Log.d("VerifyDataUpdateProduct", "KHONG TIM RA DIA CHI ID = " + id + " of table " + tableName);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean VerifyDataUpdateProductAccessory(String idUpdate, String label, String price, 
			int percent, boolean saleOff, String guarantee, String state, String numberProduct, String numberSold) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Accessories");
		query.whereEqualTo("objectId", idUpdate);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject object : list) {
					object.put("Label", label);
					object.put("Price", price);
					object.put("PercentPromotional", percent);
					object.put("Guarantee", guarantee);
					object.put("State", state);
					object.put("SaleOff", saleOff);
					object.put("Number", numberProduct);
					object.put("NumberSold", numberSold);
					object.saveInBackground();
					return true;
				}
			} else {
				Log.d("VerifyDataUpdateProduct", "KHONG TIM RA DIA CHI ID = " + idUpdate + " of table Accessories");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}


	public static boolean UpdateNumberColorProduct(String tableName, String idDetail,
			int numberProduct, int numberSold) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
		query.whereEqualTo("objectId", idDetail);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).put("Number", numberProduct);
				list.get(0).put("NumberSold", numberSold);
				list.get(0).saveInBackground();
				return true;
			} else {
				Log.d("UPDATE Number product", "KO TON TAI TAI KHOAN NAY");
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("UPDATE Number product", "LOI NGOAI LE DA XAY RA");
			return false;
		}
	}


	public static boolean SigninAccountUser(String account, String password,
			String address, String phone, String fullName, String email) {
		try {
			ParseObject object = new ParseObject("AccountUser");
			object.put("Account", account);
			object.put("Password", password);
			object.put("Phone", phone);
			object.put("Address", address);
			object.put("FullName", fullName);
			object.put("Email", email);
			//
			object.put("DataMessage", (new ArrayList<String>()));
			object.put("countMessage", 0);
			object.put("DataMessageReceived", (new ArrayList<String>()));
			object.put("countMessageAdminSent", 0);
			object.saveInBackground();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean UpdateAccountUser(String oldAccount, String account, String pass,
			String address, String phone, String fullname, String email) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		query.whereEqualTo("Account", oldAccount);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).put("Account", account);
				list.get(0).put("Password", pass);
				list.get(0).put("Email", email);
				list.get(0).put("Address", address);
				list.get(0).put("FullName", fullname);
				list.get(0).put("Phone", phone);
				list.get(0).saveInBackground();
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	public static boolean sendMessage(String nameColumn, String nameColumnCount, String account, String text) {
		Log.d("name column", nameColumn);
		Log.d("name column count", nameColumnCount);
		Log.d("account", account);
		Log.d("text", text);
		
		ArrayList<String> arrayText = new ArrayList<String>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		query.whereEqualTo("Account", account);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				Log.d("TAI KHOAN sendMessage  > 0", "sendMessage  > 0");
				for(ParseObject parseObject : list) {
					arrayText = (ArrayList<String>) parseObject.get(nameColumn);
					try {
						arrayText.add(text);
						Log.d("", "kooko nga le " + arrayText.size());
					} catch(NullPointerException e) {
						Log.d("", "cocococ nga le");
						e.printStackTrace();
						parseObject.put(nameColumn, new ArrayList<String>());
						parseObject.saveInBackground();
						arrayText = (ArrayList<String>) parseObject.get(nameColumn);
						arrayText.add(text);
					}
					parseObject.put(nameColumn, arrayText);
					int count = parseObject.getInt(nameColumnCount); 
					if(count == -1) {
						count+=2;
						Log.d("", "count+=2;count+=2;count+=2;");
					}
					else {
						count+=1;
						Log.d("", "count+=1;count+=1;count+=1;");
					}
					parseObject.put(nameColumnCount, count);
					if(parseObject!=null) {
						Log.d("", "parseObject!= null parseObject != null");
					}
					parseObject.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(ParseException e) {
							if(e != null) {
								Log.d("", "save ko thanh cong");
								e.printStackTrace();
							} else {
								Log.d("", "okko thanh cong thanh cong");
							}
						}
					});
				}
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static ArrayList<String> getAllMessageOfAccount(String nameColumn, String account) {
		ArrayList<String> arrayMessage = new ArrayList<String>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		query.whereEqualTo("Account", account);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				try {
					arrayMessage = (ArrayList<String>) list.get(0).get(nameColumn);
					arrayMessage.size();// dong nay bat ngoai le neu xay ra
				} catch (NullPointerException e) {
					e.printStackTrace();
					arrayMessage = new ArrayList<String>();
					list.get(0).put(nameColumn, arrayMessage);
				}
			}
		} catch (ParseException e) { 
			e.printStackTrace();
			return (new ArrayList<String>());
		}
		return arrayMessage;
	}


	public static ArrayList<AccountUser> getAllInforUser() {
		ArrayList<AccountUser> listArrayList = new ArrayList<AccountUser>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					AccountUser user = new AccountUser();
					user.setId(parseObject.getObjectId());
					user.setAccount(parseObject.getString("Account"));
					user.setEmail(parseObject.getString("Email"));
					user.setFullName(parseObject.getString("FullName"));
					user.setPhone(parseObject.getString("Phone"));
					user.setCountMessage(parseObject.getInt("countMessage"));
					user.setAddress(parseObject.getString("Address"));
					user.setPassword(parseObject.getString("Password"));
					listArrayList.add(user);
				}
			}
		} catch (ParseException e) { 
			e.printStackTrace();
		}

		return listArrayList;
	}
	
	
	public static AccountUser getAllInforOfAUser(String account) {
		AccountUser user = new AccountUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		query.whereEqualTo("Account", account);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					user.setId(parseObject.getObjectId());
					user.setAccount(parseObject.getString("Account"));
					user.setEmail(parseObject.getString("Email"));
					user.setFullName(parseObject.getString("FullName"));
					user.setPhone(parseObject.getString("Phone"));
					user.setCountMessage(parseObject.getInt("countMessage"));
					user.setCountMessageAdmin(parseObject.getInt("countMessageAdminSent"));
					user.setAddress(parseObject.getString("Address"));
					user.setPassword(parseObject.getString("Password"));
				}
			}
		} catch (ParseException e) { 
			e.printStackTrace();
		}
		
		return user;
	}


	public static void NoticeReadMessage(String nameColumn, String account) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		query.whereEqualTo("Account", account);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).put(nameColumn, 0);
				list.get(0).saveInBackground();
			}
		} catch (ParseException e) { 
			e.printStackTrace();
		}
	}
	
	
	public static boolean CheckMessageAdmin() {
		int count;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					count = parseObject.getInt("countMessage");
					if(count >0) return true;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static PhoneNumber getInforPhoneNumberOfColor(String idPhone, String color) {
		PhoneNumber phoneNumber = new PhoneNumber();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Phone");
		query.whereEqualTo("objectId", idPhone);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					ParseRelation<ParseObject> relation = parseObject.getRelation("PhoneNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					parseQuery.whereEqualTo("Color", color);
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							phoneNumber.setImage(object.getParseFile("ImagePhone"));
							phoneNumber.setColor(object.getString("Color"));
							phoneNumber.setNumber(object.getInt("Number"));
							phoneNumber.setNumberSold(object.getInt("NumberSold"));
							phoneNumber.setIdDetails(object.getObjectId());
						}
					} else {
						phoneNumber = null;
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return phoneNumber;
	}


	public static TabletNumber getInforTabletNumberOfColor(String idProduct, String color) {
		TabletNumber tabletNumber = new TabletNumber();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tablet");
		query.whereEqualTo("objectId", idProduct);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					ParseRelation<ParseObject> relation = parseObject.getRelation("TabletNumber");
					ParseQuery<ParseObject> parseQuery = relation.getQuery();
					parseQuery.whereEqualTo("Color", color);
					List<ParseObject> listQuery = parseQuery.find();
					if(listQuery.size() > 0){
						for(ParseObject object : listQuery) {
							tabletNumber.setImage(object.getParseFile("ImageTablet"));
							tabletNumber.setColor(object.getString("Color"));
							tabletNumber.setNumber(object.getInt("Number"));
							tabletNumber.setNumberSold(object.getInt("NumberSold"));
							tabletNumber.setIdDetails(object.getObjectId());
						}
					} else {
						tabletNumber = null;
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tabletNumber;
	}


	public static ArrayList<Delivery> getInforDelivery() {
		ArrayList<Delivery> list = new ArrayList<Delivery>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableDelivery");
		query.include("AccountUser");
		query.include("Phone");
		query.include("Tablet");
		query.include("Accessories");
		
		try {
			List<ParseObject> objects = query.find();
			for(ParseObject parseObject : objects) {
				Delivery delivery = new Delivery();
				delivery.setId(parseObject.getObjectId());
				delivery.setSoluong(parseObject.getInt("Soluong"));
				delivery.setTime(parseObject.getDate("Time"));
				String type = parseObject.getString("Type").trim();
				delivery.setType(type);
				delivery.setStateDelivery(parseObject.getBoolean("StateDelivery"));
				delivery.setAddressDelivery(parseObject.getString("Address_Delivery"));
				
				ParseObject objectUser = parseObject.getParseObject("AccountUser");
				if(objectUser != null) {
					AccountUser user = new AccountUser();
					user.setAccount(objectUser.getString("Account"));
					user.setAddress(objectUser.getString("Address"));
					user.setEmail(objectUser.getString("Email"));
					user.setFullName(objectUser.getString("FullName"));
					user.setPassword(objectUser.getString("Password"));
					user.setPhone(objectUser.getString("Phone"));
					delivery.setUser(user);
				}
				
				switch (type) {
				case "phone":
					ParseObject objectPhone = parseObject.getParseObject("Phone");
					if(objectPhone != null) {
						DetailPhone phone = new DetailPhone();
						phone.setName(objectPhone.getString("Label"));
						phone.setPrice(objectPhone.getString("Price"));
						phone.setId(objectPhone.getObjectId());
						phone.setPercentPromotion(objectPhone.getInt("PercentPromotional"));
						phone.setSaleOff(objectPhone.getBoolean("SaleOff"));
						delivery.setPhone(phone);
					}

					break;
				case "tablet":
					ParseObject objectTablet = parseObject.getParseObject("Tablet");
					if(objectTablet != null) {
						DetailTablet tablet = new DetailTablet();
						tablet.setName(objectTablet.getString("Label"));
						tablet.setPrice(objectTablet.getString("Price"));
						tablet.setId(objectTablet.getObjectId());
						tablet.setPercentPromotion(objectTablet.getInt("PercentPromotional"));
						tablet.setSaleOff(objectTablet.getBoolean("SaleOff"));
						delivery.setTablet(tablet);
					}
					break;
				case "accessory":
					ParseObject objectAccessory = parseObject.getParseObject("Accessories");
					if(objectAccessory != null) {
						DetailAccessory accessory = new DetailAccessory();
						accessory.setName(objectAccessory.getString("Label"));
						accessory.setPrice(objectAccessory.getString("Price"));
						accessory.setId(objectAccessory.getObjectId());
						accessory.setPercentPromotion(objectAccessory.getInt("PercentPromotional"));
						accessory.setSaleOff(objectAccessory.getBoolean("SaleOff"));
						delivery.setAccessory(accessory);
					}
					break;

				default:
					break;
				}
				
				list.add(delivery);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	
	public static Delivery getInforDetaiProductDelivery(String id, String type) {
		Delivery delivery = new Delivery();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableDelivery");
		query.whereEqualTo("objectId", id);
		query.include("AccountUser");
		query.include("Phone");
		query.include("Tablet");
		query.include("Accessories");
		
		try {
			List<ParseObject> objects = query.find();
			for(ParseObject parseObject : objects) {
				delivery.setId(parseObject.getObjectId());
				String color = parseObject.getString("Color");
				delivery.setColor(color);
				
				switch (type) {
				case "phone":
					ParseObject objectPhone = parseObject.getParseObject("Phone");
					if(objectPhone != null) {
						DetailPhone phone = new DetailPhone();
						phone.setName(objectPhone.getString("Label"));
						phone.setState(objectPhone.getString("State"));
						phone.setGuarantee(objectPhone.getString("Guarantee"));
						phone.setPercentPromotion(objectPhone.getInt("PercentPromotional"));
						phone.setSaleOff(objectPhone.getBoolean("SaleOff"));
						ParseRelation<ParseObject> phoneNumber = objectPhone.getRelation("PhoneNumber");
						ParseQuery<ParseObject> parseQuery = phoneNumber.getQuery();
						parseQuery.whereEqualTo("Color", color);
						List<ParseObject> list1 = parseQuery.find();
						if(list1.size() > 0) {
							ArrayList<PhoneNumber> arrayPN = new ArrayList<PhoneNumber>();
							PhoneNumber number = new PhoneNumber();
							number.setImage(list1.get(0).getParseFile("ImagePhone"));
							arrayPN.add(number);
							phone.setPhoneNumbers(arrayPN);
						} else {
							phone.setPhoneNumbers(new ArrayList<PhoneNumber>());
						}
						delivery.setPhone(phone);
					}
					break;
				case "tablet":
					ParseObject objectTablet = parseObject.getParseObject("Tablet");
					if(objectTablet != null) {
						DetailTablet tablet = new DetailTablet();
						tablet.setName(objectTablet.getString("Label"));
						tablet.setState(objectTablet.getString("State"));
						tablet.setGuarantee(objectTablet.getString("Guarantee"));
						tablet.setPercentPromotion(objectTablet.getInt("PercentPromotional"));
						tablet.setSaleOff(objectTablet.getBoolean("SaleOff"));
						ParseRelation<ParseObject> tabletNumber = objectTablet.getRelation("TabletNumber");
						ParseQuery<ParseObject> parseQuery1 = tabletNumber.getQuery();
						parseQuery1.whereEqualTo("Color", color);
						List<ParseObject> list2 = parseQuery1.find();
						if(list2.size() > 0) {
							ArrayList<TabletNumber> arrayTN = new ArrayList<TabletNumber>();
							TabletNumber number = new TabletNumber();
							number.setImage(list2.get(0).getParseFile("ImageTablet"));
							arrayTN.add(number);
							tablet.setTabletNumbers(arrayTN);
						} else {
							tablet.setTabletNumbers(new ArrayList<TabletNumber>());
						}
						delivery.setTablet(tablet);
					}
					break;
				case "accessory":
					ParseObject objectAccessory = parseObject.getParseObject("Accessories");
					if(objectAccessory != null) {
						DetailAccessory accessory = new DetailAccessory();
						accessory.setName(objectAccessory.getString("Label"));
						accessory.setState(objectAccessory.getString("State"));
						accessory.setGuarantee(objectAccessory.getString("Guarantee"));
						accessory.setPercentPromotion(objectAccessory.getInt("PercentPromotional"));
						accessory.setSaleOff(objectAccessory.getBoolean("SaleOff"));
						accessory.setAvatar(objectAccessory.getParseFile("Avatar"));
						delivery.setAccessory(accessory);
					}
					break;

				default:
					break;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return delivery;
	}


	public static boolean RemoveDataDelivery(String id) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableDelivery");
		query.whereEqualTo("objectId", id);
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				list.get(0).deleteInBackground();
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void AddDataDelivery(int type, String objectIdAccount, String objectIdProduct, 
				int numberSelected, String colorSelected, String addressDelivery, 
				String labelIncome, String priceIncome) {
		// Add a object Delivery into table TableDelivery
		ParseObject parseObject = new ParseObject("TableDelivery");
		parseObject.put("AccountUser", ParseObject.createWithoutData("AccountUser", objectIdAccount));
		parseObject.put("Color", colorSelected);
		parseObject.put("Address_Delivery", addressDelivery);
		parseObject.put("Soluong", numberSelected);
		parseObject.put("Time", new java.util.Date());
		parseObject.put("StateDelivery", false);
		switch (type) {
			case 0:
				parseObject.put("Type", "phone");
				parseObject.put("Phone", ParseObject.createWithoutData("Phone", objectIdProduct));
				break;
			case 1:
				parseObject.put("Type", "tablet");
				parseObject.put("Tablet", ParseObject.createWithoutData("Tablet", objectIdProduct));
				break;
			case 2:
				parseObject.put("Type", "accessory");
				parseObject.put("Accessories", ParseObject.createWithoutData("Accessories", objectIdProduct));
				break;
			default:
				break;
		}
		parseObject.saveInBackground();
		
		// Add a object Income into table TabletIncome
		ParseObject parseObject2 = new ParseObject("TableIncome");
		parseObject2.put("ProductName", labelIncome);
		parseObject2.put("Price", priceIncome);
		parseObject2.put("Soluong", numberSelected);
		parseObject2.put("Time", new java.util.Date());
		switch (type) {
			case 0:
				parseObject2.put("Type", "phone");
				break;
			case 1:
				parseObject2.put("Type", "tablet");
				break;
			case 2:
				parseObject2.put("Type", "accessory");
				break;
			default:
				break;
		}
		parseObject2.saveInBackground();
		
		// subtract number product warehouse 
		switch (type) {
		case 0:    // product = Phone
			ParseQuery<ParseObject> query_phone = ParseQuery.getQuery("Phone");
			query_phone.whereEqualTo("objectId", objectIdProduct);
			try {
				List<ParseObject> list_phone = query_phone.find();
				if(list_phone.size() > 0) {
					ParseRelation<ParseObject> relation = list_phone.get(0).getRelation("PhoneNumber");
					ParseQuery<ParseObject> query2_phone = relation.getQuery();
					query2_phone.whereEqualTo("Color", colorSelected);
					List<ParseObject> list2_phone = query2_phone.find();
					if(list2_phone.size() > 0) {
						int num = list2_phone.get(0).getInt("Number");
						int num_sold = list2_phone.get(0).getInt("NumberSold");
						list2_phone.get(0).put("Number", num - numberSelected);
						list2_phone.get(0).put("NumberSold", num_sold + numberSelected);
						list2_phone.get(0).saveInBackground();
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 1:    // product = Tablet
			ParseQuery<ParseObject> query_tablet = ParseQuery.getQuery("Tablet");
			query_tablet.whereEqualTo("objectId", objectIdProduct);
			try {
				List<ParseObject> list_tablet = query_tablet.find();
				if(list_tablet.size() > 0) {
					ParseRelation<ParseObject> relation = list_tablet.get(0).getRelation("TabletNumber");
					ParseQuery<ParseObject> query2_tablet = relation.getQuery();
					query2_tablet.whereEqualTo("Color", colorSelected);
					List<ParseObject> list2_tablet = query2_tablet.find();
					if(list2_tablet.size() > 0) {
						int num = list2_tablet.get(0).getInt("Number");
						int num_sold = list2_tablet.get(0).getInt("NumberSold");
						list2_tablet.get(0).put("Number", num - numberSelected);
						list2_tablet.get(0).put("NumberSold", num_sold + numberSelected);
						list2_tablet.get(0).saveInBackground();
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 2:    // product = Accessory
			ParseQuery<ParseObject> query_accessory = ParseQuery.getQuery("Accessories");
			query_accessory.whereEqualTo("objectId", objectIdProduct);
			try {
				List<ParseObject> list_accessory = query_accessory.find();
				if(list_accessory.size() > 0) {
					int num = list_accessory.get(0).getInt("Number");
					int num_sold = list_accessory.get(0).getInt("NumberSold");
					list_accessory.get(0).put("Number", num - numberSelected);
					list_accessory.get(0).put("NumberSold", num_sold + numberSelected);
					list_accessory.get(0).saveInBackground();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
	}
	}

	
	public static ArrayList<Integer> checkYearNumberIncome() {
		ArrayList<Integer> year = new ArrayList<Integer>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableIncome");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				for(ParseObject parseObject : list) {
					Date date = (Date) parseObject.get("Time");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
					String time = sdf.format(date.getTime());
					int y = Integer.parseInt(time);
					if(year.size() == 0) {
						year.add(y);
					} else {
						int n =year.size();
						for(int i = 0; i < n; i++) {
							if(y != year.get(i)) {
								if(i==n-1) year.add(y);
							} else {
								break;
							}
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return year;
		
	}
	

	public static ArrayList<Income> getInforIncomeOfStore(int year, int month) {
		ArrayList<Income> listIncomes = new ArrayList<Income>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableIncome");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				if(month == 0) {
					for(ParseObject parseObject : list) {
						Date date = parseObject.getDate("Time");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
						String time = sdf.format(date);
						int y = Integer.parseInt(time);
						if(y == year) {
							Income income = new Income();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
							String strTime = format.format(date);
							income.setTime(strTime);
							income.setId(parseObject.getObjectId());
							income.setProductLabel(parseObject.getString("ProductName"));
							income.setType(parseObject.getString("Type").trim());
							int so = parseObject.getInt("Soluong");
							income.setSoluong(so); 
							String price = parseObject.getString("Price");
							income.setPrice(price);
							String money = stylePrice(price, so);
							income.setSumMoney(money);
							listIncomes.add(income);
						}
					}
				} else {
					for(ParseObject parseObject : list) {
						Date date = parseObject.getDate("Time");
						SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy", Locale.getDefault());
						SimpleDateFormat sdfMonth = new SimpleDateFormat("MM", Locale.getDefault());
						String timeYear = sdfYear.format(date);
						String timeMonth = sdfMonth.format(date);
						int y = Integer.parseInt(timeYear);
						int m = Integer.parseInt(timeMonth);
						if(y == year && m == month) {
							Income income = new Income();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
							String strTime = format.format(date);
							income.setTime(strTime);
							income.setId(parseObject.getObjectId());
							income.setProductLabel(parseObject.getString("ProductName"));
							income.setType(parseObject.getString("Type").trim());
							int so = parseObject.getInt("Soluong");
							income.setSoluong(so); 
							String price = parseObject.getString("Price");
							income.setPrice(price);
							String money = stylePrice(price, so);
							income.setSumMoney(money);
							listIncomes.add(income);
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return (new ArrayList<Income>());
		}
		return listIncomes;
	}
	
	
	private static String stylePrice(String price, int so) {
		String money = "";
		String cost = price.replace(".", "");
		long gia = Long.parseLong(cost), newPrice;
		newPrice = gia*so;
		// establish dot "."
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		// decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormatSymbols.setGroupingSeparator('.');
		DecimalFormat decimalFormat = new DecimalFormat("#,##0", decimalFormatSymbols);
		money = decimalFormat.format(newPrice);
		return money;
	}
	

	public static ArrayList<Income> getInforIncomeOfStoreSortLabel(int year, int index) {
		ArrayList<Income> listIncomes = new ArrayList<Income>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TableIncome");
		try {
			List<ParseObject> list = query.find();
			if(list.size() > 0) {
				if(index == 0) {
					for(ParseObject parseObject : list) {
						Date date = parseObject.getDate("Time");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
						String time = sdf.format(date);
						int y = Integer.parseInt(time);
						if(y == year) {
							Income income = new Income();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
							String strTime = format.format(date);
							income.setTime(strTime);
							income.setId(parseObject.getObjectId());
							income.setProductLabel(parseObject.getString("ProductName"));
							income.setType(parseObject.getString("Type").trim());
							int so = parseObject.getInt("Soluong");
							income.setSoluong(so); 
							String price = parseObject.getString("Price");
							income.setPrice(price);
							String money = stylePrice(price, so);
							income.setSumMoney(money);
							listIncomes.add(income);
						}
					}
				} else {
					String kind = "";
					switch (index) {
					case 1: // duyệt những sản phẩm là điện thoại
						kind = "phone";
						break;
					case 2: // duyệt những sản phẩm là máy tính bảng
						kind = "tablet";
						break;
					case 3: // duyệt những sản phẩm là phụ kiện
						kind = "accessory";
						break;
					default:
						break;
					}
					for(ParseObject parseObject : list) {
						Date date = parseObject.getDate("Time");
						SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy", Locale.getDefault());
						String timeYear = sdfYear.format(date);
						int y = Integer.parseInt(timeYear);
						String type = (parseObject.getString("Type")).trim();
						if(y == year && type.equalsIgnoreCase(kind)) {
							Income income = new Income();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
							String strTime = format.format(date);
							income.setTime(strTime);
							income.setType(type);
							income.setId(parseObject.getObjectId());
							income.setProductLabel(parseObject.getString("ProductName"));
							int so = parseObject.getInt("Soluong");
							income.setSoluong(so); 
							String price = parseObject.getString("Price");
							income.setPrice(price);
							String money = stylePrice(price, so);
							income.setSumMoney(money);
							listIncomes.add(income);
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return (new ArrayList<Income>());
		}
		return listIncomes;
	}

	
//	public static boolean DeleteAccountOfCustomer(String account) {
//		final boolean ok;
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("AccountUser");
//		query.whereEqualTo("Account", account);
//		try {
//			List<ParseObject> list = query.find();
//			if(list.size() > 0) {
//				list.get(0).deleteInBackground();
//				//
//				ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("TableDelivery");
//				List<ParseObject> listDelivery = parseQuery.find();
//				if(listDelivery.size() > 0) {
//					for(ParseObject object : listDelivery) {
//						if(object.getParseObject("AccountUser") == null) {
//							object.deleteInBackground();
//							Log.d("nnnununu", "da null null");
//						} 
//					}
//				}
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return true;
//	}
	
	
	
}



















