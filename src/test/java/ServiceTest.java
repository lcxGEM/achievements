import com.zzu.staff.achievement.BaseApplication;
import com.zzu.staff.achievement.entity.GradePatent;
import com.zzu.staff.achievement.entity.GradePrize;
import com.zzu.staff.achievement.entity.GradeResult;
import com.zzu.staff.achievement.service.IGradePatentService;
import com.zzu.staff.achievement.service.IGradePrizeService;
import com.zzu.staff.achievement.service.IGradeResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public class ServiceTest {

    @Autowired
    @Resource
    IGradePrizeService gradePrizeServiceImpl;

    @Autowired
    IGradePatentService patentService;

    @Autowired
    IGradeResultService resultService;

    @Test
    public void prizeTest(){
        gradePrizeServiceImpl.insert(new GradePrize(1l,1l,"",8,10,"",
                2,7,"",null));
    }

    @Test
    public void patentTest(){
        patentService.insert(new GradePatent(1l,1l,"ss",2,"",
                true,"",null));
    }

    @Test
    public void resultTest(){
        resultService.insert(new GradeResult(1l,1l,"qwewq",333.4f,null,null));
    }

}
