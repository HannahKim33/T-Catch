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

    public Qna findById(int qna_no){
        Optional<Qna> optionalQna=dao.findById(qna_no);

        if(optionalQna.isPresent()) {
            return optionalQna.get();
        }else{
            try {
                throw new Exception("qna_no is wrong or qna was deleted");
            }catch (Exception e){
                Qna qna=new Qna();
                qna.setQna_no(-1);
                return qna;
            }
        }
    }



    public void delete(int qna_no) {
        Qna q=findById(qna_no);
        dao.delete(q);
    }

//    public void save(Qna q){dao.save(q);}
}
