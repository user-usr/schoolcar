package com.GOTEL.SchoolCar.Controller;

import com.GOTEL.SchoolCar.Entity.News;
import com.GOTEL.SchoolCar.Result.CommonResult;
import com.GOTEL.SchoolCar.Service.IMPL.NewsServiceIMPL;
import com.GOTEL.SchoolCar.Utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("news")
@RestController
public class NewsController {
    @Autowired
    NewsServiceIMPL newsServiceIMPL;

    private final IDGenerator idGenerator = new IDGenerator();

    @GetMapping("getAll")
    public CommonResult<?> getAllNews(){
        List<News> allNews = newsServiceIMPL.getAllNews();

        if (allNews.isEmpty()) {
            return CommonResult.error("获取新闻失败");
        } else {
            return CommonResult.success(allNews);
        }
    }

    @PostMapping("delete")
    public CommonResult<?> deleteNews(@RequestParam("nid")String nid) {
        if (newsServiceIMPL.deleteNews(nid)) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    @PostMapping("add")
    public void addNews(@RequestBody News news) {
        news.setNID(idGenerator.generateID(newsServiceIMPL.selectAllNid(), "N"));
        newsServiceIMPL.insertNews(news);
    }
}
