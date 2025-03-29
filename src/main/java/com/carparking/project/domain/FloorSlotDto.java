package com.carparking.project.domain;

import java.util.List;
import java.util.Map;

public class FloorSlotDto {
    private List<String> slotNumber;
    private String floor;
    private List<String> vehicleType; // Added vehicleType
    private Map<String,String> sheetMap;

    public FloorSlotDto() {}

    public FloorSlotDto(List<String> slotNumber, String floor, List<String> vehicleType, Map<String, String> sheetMap) {
        this.slotNumber = slotNumber;
        this.floor = floor;
        this.vehicleType = vehicleType;
        this.sheetMap = sheetMap;
    }

    public List<String> getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(List<String> slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Map<String, String> getSheetMap() {
        return sheetMap;
    }

    public void setSheetMap(Map<String, String> sheetMap) {
        this.sheetMap = sheetMap;
    }

    public List<String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(List<String> vehicleType) {
        this.vehicleType = vehicleType;
    }
}
