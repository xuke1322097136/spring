package cn.edu.ustc.xk.service;

import cn.edu.ustc.xk.dao.BookDAO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by xuke
 * Description: 详细的解释详见IOCTest_Autowired这个类中
 * Date: 2019-01-18
 * Time: 15:26
 */
@Service
public class BookService {

//    @Qualifier("bookDAO2")
//    @Autowired(required = false)
//    @Resource(name = "bookDAO2")
    @Inject
    private BookDAO bookDAO;

    @Override
    public String toString() {
        return "BookService{" +
                "bookDAO=" + bookDAO +
                '}';
    }
}
