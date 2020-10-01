package com.cashcontrol.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cashcontrol.dto.RegistrationRequestDto;
import com.cashcontrol.model.RegistrationRequest;

@Component
public class RegistrationRequestConverter {

  @Autowired
  ModelMapper modelMapper;

  public RegistrationRequestDto convertToDto(final RegistrationRequest request){
    final RegistrationRequestDto dto = modelMapper.map(request, RegistrationRequestDto.class);
    return dto;
  }

}
