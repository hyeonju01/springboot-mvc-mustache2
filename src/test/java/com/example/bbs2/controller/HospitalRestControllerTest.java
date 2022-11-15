package com.example.bbs2.controller;

import com.example.bbs2.domain.dto.HospitalResponse;
import com.example.bbs2.service.HospitalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(HospitalRestController.class)
class HospitalRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean // HospitalService는 테스트를 위해 가짜 객체를 사용하겠다는 뜻이다.
    HospitalService hospitalService; //장점 - DB와 상관 없이 테스트할 수 있음

    @Test
    @DisplayName("1개의 Json 형태로 Response가 잘 오는지") //비즈니스 로직을 여기서 검증하는 것은 의미없다. 컨트롤러만 검증할 수 있다.
    void jsonResponse() throws Exception{
        HospitalResponse hospitalResponse = HospitalResponse.builder()
                                            .id(2321)
                                            .roadNameAddress("서울특별시 서초구 서초중앙로 230, 202호 (반포동, 동화반포프라자빌딩)")
                                            .hospitalName("노소아청소년과의원")
                                            .patientRoomCount(0)
                                            .totalNumberOfBeds(0)
                                            .businessTypeName("의원")
                                            .totalAreaSize(0.0f)
                                            .businessStatusName("영업중").build();

        given(hospitalService.getHospital(2321)).willReturn(hospitalResponse);


        int hospitalId = 2321;
        String url = String.format("/api/v1/hospitals/%d", hospitalId);
        //앞에서 Qutowired한 MockMVC
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hospitalName").exists())
                .andExpect(jsonPath("$.hospitalName").value("노소아청소년과의원"))
                .andDo(print()); //http request, response 내역을 출력하라..

        verify(hospitalService).getHospital(hospitalId);
    }

}