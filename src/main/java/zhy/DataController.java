package zhy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangocean
 * @Date: Created in 14:57 2018/3/11
 * Describe:
 */
@RestController
public class DataController {

    @Autowired
    PersonDao personDao;

    /**
     * 访问 http://localhost:8080/set，查看 RedisClient可发现字符存储在 Redis 中
     */
    @RequestMapping("/set")
    public void set(){
        Person person = new Person("1","zhy",20);
        personDao.save(person);
        personDao.stringRedisTemplateDemo();
    }

    /**
     * 访问 http://localhost:8080/getStr
     */
    @RequestMapping("/getStr")
    public String getStr(){
        return personDao.getString();
    }

    /**
     * 访问 http://localhost:8080/getPerson
     */
    @RequestMapping("/getPerson")
    public Person getPerson(){
        return personDao.getPerson();
    }

}
