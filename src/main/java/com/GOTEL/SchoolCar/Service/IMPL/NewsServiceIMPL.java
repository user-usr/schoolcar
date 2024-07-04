package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Entity.News;
import com.GOTEL.SchoolCar.Mapper.NewsMapper;
import com.GOTEL.SchoolCar.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceIMPL implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getAllNews() {
        return newsMapper.getAll();
    }

    @Override
    public News getNewsByNID(String NID) {
        return newsMapper.getByNID(NID);
    }

    @Override
    public boolean deleteNews(String nid) {
        return newsMapper.deleteNews(nid) > 0;
    }

    @Override
    public List<String> selectAllNid() {
        return newsMapper.selectAllNid();
    }

    @Override
    public boolean insertNews(News news) {
        return newsMapper.insertNews(news) > 0;
    }


}
