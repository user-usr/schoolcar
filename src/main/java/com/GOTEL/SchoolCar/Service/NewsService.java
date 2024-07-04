package com.GOTEL.SchoolCar.Service;

import com.GOTEL.SchoolCar.Entity.News;

import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    News getNewsByNID(String NID);
    boolean deleteNews(String nid);
    List<String> selectAllNid();
    boolean insertNews(News news);
}
