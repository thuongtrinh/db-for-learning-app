package com.example.object;

import java.io.Serializable;
import java.util.ArrayList;

import com.parse.ParseFile;

public class DetailPhone implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ParseFile avatar;
	private String condition, name, id, price;
	private String guarantee, state;
	private ArrayList<PhoneNumber> phoneNumbers;
	private boolean saleOff;
	private int percentPromotion, SumPhoneSold, SumPhone;
	
	
	public int getSumPhoneSold() {
		return SumPhoneSold;
	}

	public void setSumPhoneSold(int sumPhoneSold) {
		SumPhoneSold = sumPhoneSold;
	}

	public int getSumPhone() {
		return SumPhone;
	}

	public void setSumPhone(int sumPhone) {
		SumPhone = sumPhone;
	}

	public boolean isSaleOff() {
		return saleOff;
	}

	public void setSaleOff(boolean saleOff) {
		this.saleOff = saleOff;
	}

	
	public int getPercentPromotion() {
		return percentPromotion;
	}

	public void setPercentPromotion(int percentPromotion) {
		this.percentPromotion = percentPromotion;
	}

	
	public ParseFile getAvatar() {
		return avatar;
	}

	public void setAvatar(ParseFile avatar) {
		this.avatar = avatar;
	}

	
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	


	public String getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInfor3G() {
		return infor3G;
	}

	public void setInfor3G(String infor3g) {
		infor3G = infor3g;
	}

	public String getInfor4G() {
		return infor4G;
	}

	public void setInfor4G(String infor4g) {
		infor4G = infor4g;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getTypeMonitor() {
		return typeMonitor;
	}

	public void setTypeMonitor(String typeMonitor) {
		this.typeMonitor = typeMonitor;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getMemoryCardSlot() {
		return memoryCardSlot;
	}

	public void setMemoryCardSlot(String memoryCardSlot) {
		this.memoryCardSlot = memoryCardSlot;
	}

	public String getWlan() {
		return wlan;
	}

	public void setWlan(String wlan) {
		this.wlan = wlan;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getUsb() {
		return usb;
	}

	public void setUsb(String usb) {
		this.usb = usb;
	}

	public String getNfc() {
		return nfc;
	}

	public void setNfc(String nfc) {
		this.nfc = nfc;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getOperatorSystem() {
		return operatorSystem;
	}

	public void setOperatorSystem(String operatorSystem) {
		this.operatorSystem = operatorSystem;
	}

	public String getChipset() {
		return chipset;
	}

	public void setChipset(String chipset) {
		this.chipset = chipset;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getGpu() {
		return gpu;
	}

	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	public String getSensors() {
		return sensors;
	}

	public void setSensors(String sensors) {
		this.sensors = sensors;
	}

	public String getMainCamera() {
		return mainCamera;
	}

	public void setMainCamera(String mainCamera) {
		this.mainCamera = mainCamera;
	}

	public String getSubCamera() {
		return subCamera;
	}

	public void setSubCamera(String subCamera) {
		this.subCamera = subCamera;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getConversationTime() {
		return conversationTime;
	}

	public void setConversationTime(String conversationTime) {
		this.conversationTime = conversationTime;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public String getPlayMusicTime() {
		return playMusicTime;
	}

	public void setPlayMusicTime(String playMusicTime) {
		this.playMusicTime = playMusicTime;
	}

	public String getMonitorSize() {
		return monitorSize;
	}

	public void setMonitorSize(String monitorSize) {
		this.monitorSize = monitorSize;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public ArrayList<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}




	private String infor3G, infor4G, sim, size, weight, typeMonitor,
			monitorSize, memory, memoryCardSlot, wlan, bluetooth, usb, nfc,
			gps, operatorSystem, chipset, cpu, gpu, sensors, mainCamera,
			subCamera, video, pin, conversationTime, waitTime, playMusicTime;

}
