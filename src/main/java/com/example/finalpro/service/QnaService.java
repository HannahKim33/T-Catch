package com.example.finalpro.service;

import com.example.finalpro.dao.QnaDAO;
import com.example.finalpro.entity.Qna;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Setter
public class QnaService {

    @Autowired
    private QnaDAO dao;

    public List<Qna> findAll(){return dao.findAll();}

    public Qna findById(int qna_no) {
        Optional<Qna> optionalQna=dao.findById(qna_no);
        if(optionalQna.isPresent()) {
            return optionalQna.get();
        }else{
            throw new RuntimeException("qna_no is wrong or qna was deleted");
        }
    }



    public void delete(int qna_no) {
        Qna q=findById(qna_no);
        dao.delete(q);
    }

//    public void save(Qna q){dao.save(q);}
}
