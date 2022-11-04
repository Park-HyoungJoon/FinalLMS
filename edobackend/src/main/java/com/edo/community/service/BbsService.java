package com.edo.community.service;

import com.edo.community.entity.Bbs;
import com.edo.community.repository.BbsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BbsService {

    private final BbsRepository bbsRepository;

    public List<Bbs> getList() {
       List<Bbs> bbsList = bbsRepository.findAll();
       return bbsList;
    }
}
