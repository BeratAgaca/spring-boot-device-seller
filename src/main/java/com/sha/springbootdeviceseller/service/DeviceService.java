package com.sha.springbootdeviceseller.service;

import com.sha.springbootdeviceseller.model.Device;

import java.util.List;

public interface DeviceService {
    Device saveDevice(Device device);

    void deleteDevice(Long id);

    List<Device> findAllDevices();
}
