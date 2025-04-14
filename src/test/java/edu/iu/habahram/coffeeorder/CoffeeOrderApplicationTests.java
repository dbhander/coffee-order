package edu.iu.habahram.coffeeorder;

import edu.iu.habahram.coffeeorder.model.OrderData;
import edu.iu.habahram.coffeeorder.model.Receipt;
import edu.iu.habahram.coffeeorder.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoffeeOrderApplicationTests {

	private OrderRepository repository;

	@BeforeEach
	public void setup() {
		repository = new OrderRepository();
	}

	// No condiments
	@Test public void testDarkRoast() throws Exception {
		testOrder("Dark Roast", List.of(), "Dark roast", 1.99F);
	}

	@Test public void testEspresso() throws Exception {
		testOrder("Espresso", List.of(), "Espresso", 1.34F);
	}

	@Test public void testDecaf() throws Exception {
		testOrder("Decaf", List.of(), "Decaf", 1.28F);
	}

	@Test public void testHouseBlend() throws Exception {
		testOrder("House Blend", List.of(), "House Blend", 1.65F);
	}

	// Single condiment
	@Test public void testDarkRoastWithMilk() throws Exception {
		testOrder("Dark Roast", List.of("Milk"), "Dark roast, Milk", 2.39F);
	}

	@Test public void testEspressoWithMocha() throws Exception {
		testOrder("Espresso", List.of("Mocha"), "Espresso, Mocha", 1.64F);
	}

	@Test public void testDecafWithSoy() throws Exception {
		testOrder("Decaf", List.of("Soy"), "Decaf, Soy", 1.55F);
	}

	@Test public void testHouseBlendWithWhip() throws Exception {
		testOrder("House Blend", List.of("Whip"), "House Blend, Whip", 1.90F);
	}

	// Double condiments
	@Test public void testDarkRoastWithMilkAndMocha() throws Exception {
		testOrder("Dark Roast", List.of("Milk", "Mocha"), "Dark roast, Milk, Mocha", 2.69F);
	}

	@Test public void testEspressoWithMochaAndSoy() throws Exception {
		testOrder("Espresso", List.of("Mocha", "Soy"), "Espresso, Mocha, Soy", 1.91F);
	}

	@Test public void testDecafWithMilkAndWhip() throws Exception {
		testOrder("Decaf", List.of("Milk", "Whip"), "Decaf, Milk, Whip", 1.93F);
	}

	@Test public void testHouseBlendWithMilkAndSoy() throws Exception {
		testOrder("House Blend", List.of("Milk", "Soy"), "House Blend, Milk, Soy", 2.32F);
	}

	// Triple condiments
	@Test public void testDarkRoastWithAllCondiments() throws Exception {
		testOrder("Dark Roast", List.of("Milk", "Mocha", "Soy", "Whip"), "Dark roast, Milk, Mocha, Soy, Whip", 3.21F);
	}

	@Test public void testEspressoWithAllCondiments() throws Exception {
		testOrder("Espresso", List.of("Milk", "Mocha", "Soy", "Whip"), "Espresso, Milk, Mocha, Soy, Whip", 2.56F);
	}

	@Test public void testDecafWithAllCondiments() throws Exception {
		testOrder("Decaf", List.of("Milk", "Mocha", "Soy", "Whip"), "Decaf, Milk, Mocha, Soy, Whip", 2.50F);
	}

	@Test public void testHouseBlendWithAllCondiments() throws Exception {
		testOrder("House Blend", List.of("Milk", "Mocha", "Soy", "Whip"), "House Blend, Milk, Mocha, Soy, Whip", 2.87F);
	}

	private void testOrder(String beverage, List<String> condiments, String expectedDescription, float expectedCost) throws Exception {
		OrderData order = new OrderData(beverage, condiments);
		Receipt receipt = repository.add(order);
		assertEquals(expectedDescription, receipt.description());
		assertEquals(expectedCost, receipt.cost(), 0.01);
		assertNotNull(receipt.id());
	}
}
