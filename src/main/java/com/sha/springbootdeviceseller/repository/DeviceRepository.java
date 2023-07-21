package com.sha.springbootdeviceseller.repository;

import com.sha.springbootdeviceseller.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
