package cn.bugkit.toy.app.service;

import cn.bugkit.toy.autoconfigure.Toy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author bugkit
 * @since 2022.2.24
 */
@Service
public class ToyService {

    @Autowired
    private Toy toy;

    @PostConstruct
    public void init() {
        toy.showInfo();
    }

}
