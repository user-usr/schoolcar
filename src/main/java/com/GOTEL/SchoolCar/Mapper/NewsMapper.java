package com.GOTEL.SchoolCar.Mapper;

import com.GOTEL.SchoolCar.Entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface NewsMapper {

    List<News> getAll();
    News getByNID(@Param("NID") String NID);
    int deleteNews(@Param("nid")String nid);

    List<String> selectAllNid();
    int insertNews(News news);
}
