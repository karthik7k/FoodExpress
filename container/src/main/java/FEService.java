import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FEService {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        DeliveryPathFinder deliveryPathFinder = (DeliveryPathFinder) context.getBean("deliveryPathFinder");
        deliveryPathFinder.run();
    }
}
