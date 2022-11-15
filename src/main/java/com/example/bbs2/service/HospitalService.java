package com.example.bbs2.service;

import com.example.bbs2.domain.Hospital;
import com.example.bbs2.domain.dto.HospitalResponse;
import com.example.bbs2.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    //id를 받아서 HospitalResponse를 리턴한다.
    public HospitalResponse getHospital(Integer id) {
        Optional<Hospital> optHospital = hospitalRepository.findById(id); // Entity
        Hospital hospital = optHospital.get();
        HospitalResponse hospitalResponse = Hospital.of(hospital);
        //switch-case 문 사용하여 리팩토링 가능함.
        /*
        switch (hospital.getBusinessStatusCode()) {
            case 13: hospitalResponse.setBusinessStatusName("영업중"); break;
            case 3: hospitalResponse.setBusinessStatusName("폐업"); break;
            case 4: hospitalResponse.setBusinessStatusName(); break;
            case 24: hospitalResponse.setBusinessStatusName(); break;
        }
         */
        if(hospital.getBusinessStatusCode() == 13) {
            hospitalResponse.setBusinessStatusName("영업중"); //pt, data영역이 아니라 서비스 영역이므로 이곳에 넣어줘야 함
        } else if (hospital.getBusinessStatusCode() == 3) {
            hospitalResponse.setBusinessStatusName("폐업");
        } else {
            hospitalResponse.setBusinessStatusName(String.valueOf(hospital.getBusinessStatusCode()));
        }
        return hospitalResponse;
    }
}
