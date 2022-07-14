import com.cloud.util.SignUtil;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Time: 2022/7/11
 * Author: Dankejun
 * Description:
 */
public class SignTest {
    public static void main(String[] args) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        String uri = "/v1/open/device/lua/control";
        String json = "{\"control\":{\"bucket\":\"db\",\"db_location\":2,\"db_power\":\"off\"}}";
        params.add("clientId", "6d60d3637f6960e4f20e07b458a87baa");
        params.add("reqId", "fe8234bf-e94c-4cdf-8ea9-c3112962ab01");
        params.add("stamp", "202207111800000");
        params.add("applianceCode", "197912094955114");
        params.add("command", json);
        params.add("sign", SignUtil.requestSign(uri, params, "fd9fd3d9ce2f279f9409fdbba9e973b7"));
        System.out.println("sign ----- >" + params.get("sign"));
    }
}
