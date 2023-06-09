package com.macro.hjstore.service;

import com.macro.hjstore.core.annotation.MyLog;
import com.macro.hjstore.core.exception.Exception404;
import com.macro.hjstore.dto.shop.SoftwareRequestDTO;
import com.macro.hjstore.dto.shop.SoftwareResponseDTO;
import com.macro.hjstore.model.softwareProduct.Software;
import com.macro.hjstore.model.softwareProduct.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SoftwareService {

    private final SoftwareRepository softwareRepository;

    @MyLog
    public List<SoftwareResponseDTO> 게시글목록보기(){
        List<Software> softwareList = softwareRepository.findAll();
        List<SoftwareResponseDTO>newList = softwareList.stream()
                .map(software -> new SoftwareResponseDTO(software)).collect(Collectors.toList());
        return newList;
    }

    @MyLog
    public SoftwareResponseDTO.Detail 게시글상세보기(Long id){
        Software softwarePS = softwareRepository.findById(id)
                .orElseThrow(()-> new Exception404("게시글을 찾을 수 없습니다."));
        SoftwareResponseDTO.Detail detailPS = new SoftwareResponseDTO.Detail(softwarePS);
        return detailPS;
    }

    @MyLog
    public void 게시글저장하기(SoftwareRequestDTO.Save saveDTO){
        Software savePS = Software.toEntity(saveDTO);
        String link = "/software/"+savePS.getId();
        savePS.setLink(link);
        softwareRepository.save(savePS);
    }
}
