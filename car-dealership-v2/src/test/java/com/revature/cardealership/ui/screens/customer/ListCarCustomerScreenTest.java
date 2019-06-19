package com.revature.cardealership.ui.screens.customer;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.revature.cardealership.exceptions.NotFoundRecordException;
import com.revature.cardealership.model.Car;
import com.revature.cardealership.services.CarService;
import com.revature.cardealership.services.OfferService;
import com.revature.cardealership.ui.screens.Screen;
import com.revature.cardealership.utils.InputUtilities;
import com.revature.cardealership.utils.ServiceUtilities;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ InputUtilities.class, ServiceUtilities.class })
public class ListCarCustomerScreenTest {

	@Mock
	private CarService carService;
	@Mock OfferService offerService;
	@Mock
	private Screen previousScreen;

	@InjectMocks
	private ListCarCustomerScreen listCarCustomerScreen;

	private static Set<Car> cars;
	private static String username = "username";
	private static String vin = "1111111";

	@Before
	public void setUp() throws Exception {
		listCarCustomerScreen = new ListCarCustomerScreen(previousScreen, username);
		listCarCustomerScreen.setCarService(carService);
		
		cars = new HashSet<Car>();

		cars = new HashSet<>();
		cars.add(new Car("1111111", "Toyota", "Corolla", 17000, false, true));
		cars.add(new Car("22222222222", "Ford", "Focus", 23000, false, true));
		cars.add(new Car("3333333", "Dodge", "Charger", 35000, false, true));
		cars.add(new Car("4444444", "Ford", "Focus", 23000, false, true));
	}

	@Test
	public void testRenderScreen() throws NotFoundRecordException {
		when(carService.getCarsInInvertory()).thenReturn(cars);
		when(offerService.makeAnOffer(username, vin, 23000)).thenReturn(true);
		
		PowerMockito.mockStatic(InputUtilities.class);
		PowerMockito.mockStatic(ServiceUtilities.class);
		when(InputUtilities.getNumber(1, 2)).thenReturn(1).thenReturn(2).thenReturn(2);
		when(InputUtilities.getString()).thenReturn("1111111");
		when(InputUtilities.getDouble()).thenReturn(23000d);
		
		when(ServiceUtilities.getOfferService()).thenReturn(offerService);
		
		listCarCustomerScreen.display();
		
		verify(carService, atLeast(1)).getCarsInInvertory();
		verify(offerService).makeAnOffer(username, vin, 23000);
	}

}
