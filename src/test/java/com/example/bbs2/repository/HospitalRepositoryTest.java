package com.example.bbs2.repository;

import com.example.bbs2.domain.Hospital;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HospitalRepositoryTest {
    @Autowired
    HospitalRepository hospitalRepository;



    @Test
    @DisplayName("BusinessTypeName이 보건소, 보건지소, 보건진료소인 데이터 추출")
    void findByBusinessTypeNameIn() {
        List<String> includes = new ArrayList<>();
        includes.add("보건소");
        includes.add("보건지소");
        includes.add("보건진료소");

        List<Hospital> hospitals = hospitalRepository.findByBusinessTypeNameIn(includes);
        for (var hospital: hospitals) {
            System.out.println(hospital.getHospitalName());
        }
    }

    @Test
    void containing() {
        List<Hospital> hospitals = hospitalRepository.findByRoadNameAddressContaining("송파구");
        printHospitalNamdAndAddress(hospitals);
    }

    @Test
    void startsWith() {
        List<Hospital> hospitals = hospitalRepository.findByHospitalNameStartsWith("경희");
        printHospitalNamdAndAddress(hospitals);
    }

    @Test
    void endsWith() {
        List<Hospital> hospitals = hospitalRepository.findByHospitalNameEndsWith("병원");
        printHospitalNamdAndAddress(hospitals);
    }

    @Test
    void between() {
        List<Hospital> hospitals = hospitalRepository.findByTotalNumberOfBedsBetween(10, 20);
        for (var hospital : hospitals) {
            System.out.printf("%s | %s %d \n", hospital.getHospitalName(),
                    hospital.getRoadNameAddress(), hospital.getTotalNumberOfBeds());
        }
    }

    // 출력 테스트 부분 분리
    void printHospitalNamdAndAddress(List<Hospital> hospitals) {
        for (var hospital : hospitals) {
            System.out.printf("%s | %s %f \n", hospital.getHospitalName(),
                    hospital.getRoadNameAddress(), hospital.getTotalAreaSize());
        }
        System.out.println(hospitals.size());
    }
}