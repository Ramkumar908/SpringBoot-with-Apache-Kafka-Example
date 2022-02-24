package com.ram.web.controller;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ram.web.entity.DeliveryAddress;
import com.ram.web.entity.OrderDetail;
import com.ram.web.entity.Orders;
import com.ram.web.entity.TotalAmount;
import com.ram.web.repository.OrderDetailRepository;
import com.ram.web.repository.OrdersRepository;

@RestController
public class WebController {
	
	
	
	
	@Autowired
	private OrderDetailRepository repo2;
	
	
	@Autowired
	private OrdersRepository orderRepo;
	
	@Autowired
	KafkaTemplate<String,OrderDetail> kafkaTemplate;
	
	@Value("${inputfilePath}")
	String InputPath;
	
	private DeliveryAddress address=new DeliveryAddress();
	private Orders  orders =new Orders();
	private OrderDetail details =new OrderDetail();
	private TotalAmount total=new TotalAmount();
	private float itemTotalAmount = 0;
	private float shippingCharge = 0;
	private String OrderDate;
	List<Orders> orderList = new ArrayList<>();
	
	private static final String TOPIC ="OrderDetail";

	@RequestMapping(value = "/cc", method = { RequestMethod.GET, RequestMethod.POST })
	public String readDataFromTxtInput() {
		
		int line = 1;
		Scanner sc = null;
		try {
			sc = new Scanner(new File(InputPath));
			while (sc.hasNextLine()) {
				String str = sc.nextLine();
				if (line == 1) {
					parseData(str);
				}
				if (line > 1 && line <= 3) {
					parseOrderedData(str);
				}
				if (line > 3) {
					parseCustomerAddress(str);

				}
				line++;
			}

			
			if(itemTotalAmount <1000)
			{
				shippingCharge=100;
			}
			total.setOrderTotal(itemTotalAmount);
			total.setShippingCharge(shippingCharge);
			total.setTotalAmount(itemTotalAmount+shippingCharge);
			details.setTotalAmount(total);
			details.setAddress(address);
			repo2.save(details);
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		
		return "Data Saved Successfully";
	}
	
	private void parseCustomerAddress(String str) {
		
		String deliveryAddress, city, state, pinCode, mobileNo;
		Scanner lineScanner = new Scanner(str);
		lineScanner.useDelimiter("\\|");
		while (lineScanner.hasNext()) {
			deliveryAddress = lineScanner.next();
			city = lineScanner.next();
			state = lineScanner.next();
			pinCode = lineScanner.next();
			mobileNo = lineScanner.next();
			address.setDeliveryAddress(deliveryAddress);
			address.setCity(city);
			address.setState(state);
			address.setMobileNo(mobileNo);
			address.setPincode(pinCode);

		}

		lineScanner.close();
	}

	private void parseOrderedData(String str) {
		String itemId, itemType, itemName, itemQuantity;
		float itemPrice, itemDiscount, itemTotal;
		Scanner lineScanner = new Scanner(str);
		lineScanner.useDelimiter("\\|");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(OrderDate + " " + "00:00:00", formatter);
		DayOfWeek day = DayOfWeek.of(dateTime.get(ChronoField.DAY_OF_WEEK));
		while (lineScanner.hasNext()) {
			itemId = lineScanner.next();
			itemType = lineScanner.next();
			itemName = lineScanner.next();
			itemQuantity = lineScanner.next();
			itemPrice = lineScanner.nextFloat();
			switch (day) {
			case SATURDAY:
				System.out.println("Weekend -" + day);
				itemDiscount = (float) (itemPrice * (5.0f / 100.0f));
				break;
			case SUNDAY:
				System.out.println("Weekend - " + day);
				itemDiscount = (float) (itemPrice * (5.0f / 100.0f));

				break;
			default:
				System.out.println("Not a Weekend" + day);
				itemDiscount = (float) (itemPrice * (2.0f / 100.0f));

			}

			orders.setItemId(itemId);
			orders.setItemType(itemType);
			orders.setItemName(itemName);
			orders.setItemQuantity(Integer.parseInt(itemQuantity));
			orders.setItemPrice(itemPrice);
			float ItemTypeDiscount = 0;
			if (itemType.equalsIgnoreCase("Grocery")) {
				ItemTypeDiscount = (float) (itemPrice * (1.0f / 100.0f));
			}
			if (itemType.equalsIgnoreCase("Book")) {
				ItemTypeDiscount = (float) (itemPrice * (5.0f / 100.0f));
			}

			float totalDiscount = itemDiscount + ItemTypeDiscount;
			itemTotal = itemPrice - totalDiscount;
			itemTotalAmount = itemTotalAmount + itemTotal;
			orders.setItemDiscount(totalDiscount);
			orders.setItemTotalAmount(itemTotal);
			System.out.println("Beore Save "+orders.toString());
			orderRepo.save(orders);
			orderList.add(orders);
			System.out.println("Save Successfully");

		}
		lineScanner.close();
		
	}

	private void parseData(String str) {
		
		String recordId, acctId, orderedDate, accType;
		Scanner lineScanner = new Scanner(str);
		lineScanner.useDelimiter("\\|");
		while (lineScanner.hasNext()) {
			recordId = lineScanner.next();
			acctId = lineScanner.next();
			address.setAccountId(acctId);
			OrderDate = lineScanner.next();
			accType = lineScanner.next();
			}
		lineScanner.close();

		
	}

	

	
	@RequestMapping(value="/orderDetail")
	public ResponseEntity<OrderDetail> getOrderDetail()
	{
		OrderDetail orderDetail=new OrderDetail();
		orderDetail=repo2.findByAddressAccountId("TM0003");
		List<Orders> orderList=orderRepo.findAll();
		orderDetail.setOrders(orderList);
		kafkaTemplate.send(TOPIC,orderDetail);
		return new ResponseEntity<OrderDetail>(orderDetail,HttpStatus.OK);
	}
}
