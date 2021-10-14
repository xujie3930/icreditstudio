import org.apache.dolphinscheduler.api.request.SchedulerHomepageRequest;
import org.apache.dolphinscheduler.api.service.HomePageService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xujie
 * @description testHomepage
 * @create 2021-10-13 16:23
 **/
public class HomepageTest {
    @Autowired
    private HomePageService homePageService;
    @Test
    public void testRough() {
        SchedulerHomepageRequest request = new SchedulerHomepageRequest();
        request.setWorkspaceId("0");
        homePageService.rough(request);
    }
}
