package api.listners;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup {

	@EventListener
	public void OnApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("Application Ready Event ...");
	}
}
