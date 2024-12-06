package com.example.datatier.service.property_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.datatier.dto.PropertyDTO;
import com.example.datatier.dto.PropertyRentDTO;
import com.example.datatier.dto.PropertySellDTO;
import com.example.datatier.service.property_service.property_rent_service.PropertyRentService;
import com.example.datatier.service.property_service.property_sell_service.PropertySellService;

@Component
public class PropertyServiceFactory {

    private final PropertyRentService propertyRentService;
    private final PropertySellService propertySellService;

    @Autowired
    public PropertyServiceFactory(PropertyRentService propertyRentService,
                                  PropertySellService propertySellService) {
        this.propertyRentService = propertyRentService;
        this.propertySellService = propertySellService;
    }

    public PropertyService<? extends PropertyDTO> getService(PropertyDTO propertyDTO) {
        if (propertyDTO instanceof PropertyRentDTO) {
            return propertyRentService;
        } else if (propertyDTO instanceof PropertySellDTO) {
            return propertySellService;
        } else {
            throw new IllegalArgumentException("Unsupported property type");
        }
    }
}