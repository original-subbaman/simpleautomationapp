package com.mact.simpleautomationapp.Models;

import java.util.Objects;

public class IoTDevice {
    int value;
    String deviceName;

    public IoTDevice(int value, String deviceName) {
        this.value = value;
        this.deviceName = deviceName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IoTDevice ioTDevice = (IoTDevice) o;
        return deviceName.equals(ioTDevice.deviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, deviceName);
    }
}
