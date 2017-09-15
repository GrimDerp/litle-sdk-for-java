package com.litle.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;

import com.litle.sdk.generate.ActionTypeEnum;
import com.litle.sdk.generate.Activate;
import com.litle.sdk.generate.ActivateResponse;
import com.litle.sdk.generate.ActivateReversal;
import com.litle.sdk.generate.ActivateReversalResponse;
import com.litle.sdk.generate.AdvancedFraudChecksType;
import com.litle.sdk.generate.AdvancedFraudResultsType;
import com.litle.sdk.generate.ApplepayHeaderType;
import com.litle.sdk.generate.ApplepayType;
import com.litle.sdk.generate.AuthInformation;
import com.litle.sdk.generate.AuthReversal;
import com.litle.sdk.generate.AuthReversalResponse;
import com.litle.sdk.generate.Authentication;
import com.litle.sdk.generate.Authorization;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.BalanceInquiry;
import com.litle.sdk.generate.BalanceInquiryResponse;
import com.litle.sdk.generate.CancelSubscription;
import com.litle.sdk.generate.CancelSubscriptionResponse;
import com.litle.sdk.generate.Capture;
import com.litle.sdk.generate.CaptureGivenAuth;
import com.litle.sdk.generate.CaptureGivenAuthResponse;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.CreateAddOnType;
import com.litle.sdk.generate.CreatePlan;
import com.litle.sdk.generate.CreatePlanResponse;
import com.litle.sdk.generate.Credit;
import com.litle.sdk.generate.CreditResponse;
import com.litle.sdk.generate.CustomerInfo;
import com.litle.sdk.generate.Deactivate;
import com.litle.sdk.generate.DeactivateResponse;
import com.litle.sdk.generate.DeactivateReversal;
import com.litle.sdk.generate.DeactivateReversalResponse;
import com.litle.sdk.generate.DepositReversal;
import com.litle.sdk.generate.DepositReversalResponse;
import com.litle.sdk.generate.EcheckAccountTypeEnum;
import com.litle.sdk.generate.EcheckCredit;
import com.litle.sdk.generate.EcheckCreditResponse;
import com.litle.sdk.generate.EcheckRedeposit;
import com.litle.sdk.generate.EcheckRedepositResponse;
import com.litle.sdk.generate.EcheckSale;
import com.litle.sdk.generate.EcheckSalesResponse;
import com.litle.sdk.generate.EcheckType;
import com.litle.sdk.generate.EcheckVerification;
import com.litle.sdk.generate.EcheckVerificationResponse;
import com.litle.sdk.generate.EcheckVoid;
import com.litle.sdk.generate.EcheckVoidResponse;
import com.litle.sdk.generate.ForceCapture;
import com.litle.sdk.generate.ForceCaptureResponse;
import com.litle.sdk.generate.FraudCheck;
import com.litle.sdk.generate.FraudCheckResponse;
import com.litle.sdk.generate.GiftCardAuthReversal;
import com.litle.sdk.generate.GiftCardAuthReversalResponse;
import com.litle.sdk.generate.GiftCardCapture;
import com.litle.sdk.generate.GiftCardCaptureResponse;
import com.litle.sdk.generate.GiftCardCardType;
import com.litle.sdk.generate.GiftCardCredit;
import com.litle.sdk.generate.GiftCardCreditResponse;
import com.litle.sdk.generate.LitleOnlineRequest;
import com.litle.sdk.generate.Load;
import com.litle.sdk.generate.LoadResponse;
import com.litle.sdk.generate.LoadReversal;
import com.litle.sdk.generate.LoadReversalResponse;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;
import com.litle.sdk.generate.OrderSourceType;
import com.litle.sdk.generate.QueryTransaction;
import com.litle.sdk.generate.QueryTransactionResponse;
import com.litle.sdk.generate.QueryTransactionUnavailableResponse;
import com.litle.sdk.generate.RecurringRequestType;
import com.litle.sdk.generate.RecurringSubscriptionType;
import com.litle.sdk.generate.RefundReversal;
import com.litle.sdk.generate.RefundReversalResponse;
import com.litle.sdk.generate.RegisterTokenRequestType;
import com.litle.sdk.generate.RegisterTokenResponse;
import com.litle.sdk.generate.Sale;
import com.litle.sdk.generate.SaleResponse;
import com.litle.sdk.generate.SepaDirectDebitType;
import com.litle.sdk.generate.TransactionTypeWithReportGroup;
import com.litle.sdk.generate.Unload;
import com.litle.sdk.generate.UnloadResponse;
import com.litle.sdk.generate.UnloadReversal;
import com.litle.sdk.generate.UnloadReversalResponse;
import com.litle.sdk.generate.UpdatePlan;
import com.litle.sdk.generate.UpdatePlanResponse;
import com.litle.sdk.generate.UpdateSubscription;
import com.litle.sdk.generate.UpdateSubscriptionResponse;
import com.litle.sdk.generate.Wallet;
import com.litle.sdk.generate.WalletSourceType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class TestLitleOnline {

	private LitleOnline litle;

	@Before
	public void before() throws Exception {
		litle = new LitleOnline();
	}

	@Test
	public void testAuth() throws Exception {

		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authorization.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse><litleTxnId>123</litleTxnId></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		AuthorizationResponse authorize = litle.authorize(authorization);
		assertEquals(123L, authorize.getLitleTxnId());
	}
	
	@Test
    public void testAuthWithApplepayAndSecondaryAmountAndWallet() throws Exception {

        Authorization authorization = new Authorization();
        authorization.setReportGroup("Planets");
        authorization.setOrderId("12344");
        authorization.setAmount(106L);
        authorization.setSecondaryAmount(10L);
        authorization.setOrderSource(OrderSourceType.ECOMMERCE);
        ApplepayType applepayType = new ApplepayType();
        ApplepayHeaderType applepayHeaderType = new ApplepayHeaderType();
        applepayHeaderType.setApplicationData("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setEphemeralPublicKey("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setPublicKeyHash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setTransactionId("1234");
        applepayType.setHeader(applepayHeaderType);
        applepayType.setData("user");
        applepayType.setSignature("sign");
        applepayType.setVersion("1");
        authorization.setApplepay(applepayType);
        
        Wallet wallet = new Wallet();
        wallet.setWalletSourceType(WalletSourceType.MASTER_PASS);
        wallet.setWalletSourceTypeId("123");
        authorization.setWallet(wallet);
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                .requestToServer(
                        matches(".*?<litleOnlineRequest.*?<authorization.*?<secondaryAmount>10</secondaryAmount>.*?<applepay>.*?<data>user</data>.*?</applepay>.*?<wallet>.*?<walletSourceTypeId>123</walletSourceTypeId>.*?</wallet>.*?</authorization>.*?"),
                        any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse><litleTxnId>123</litleTxnId><applepayResponse><applicationPrimaryAccountNumber>123455</applicationPrimaryAccountNumber><transactionAmount>106</transactionAmount></applepayResponse></authorizationResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        AuthorizationResponse authorize = litle.authorize(authorization);
        assertEquals(123L, authorize.getLitleTxnId());
        assertEquals("123455", authorize.getApplepayResponse().getApplicationPrimaryAccountNumber());
        assertEquals(new Long(106), authorize.getApplepayResponse().getTransactionAmount());
    }

	@Test
	public void testAuthWithOverrides() throws Exception {

		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?merchantId=\"9001\".*?<authorization.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse><litleTxnId>123</litleTxnId></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		LitleOnlineRequest overrides = new LitleOnlineRequest();
		overrides.setMerchantId("9001");
		AuthorizationResponse authorize = litle.authorize(authorization, overrides);
		assertEquals(123L, authorize.getLitleTxnId());
	}

	@Test
	public void testAuthReversal() throws Exception {

		AuthReversal reversal = new AuthReversal();
		reversal.setLitleTxnId(12345678000L);
		reversal.setAmount(106L);
		reversal.setPayPalNotes("Notes");


		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authReversal.*?<litleTxnId>12345678000</litleTxnId>.*?</authReversal>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authReversalResponse><litleTxnId>123</litleTxnId></authReversalResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		AuthReversalResponse authreversal = litle.authReversal(reversal);
		assertEquals(123L, authreversal.getLitleTxnId());
	}

	@Test
	public void testAuthReversalWithOverrides() throws Exception {

		AuthReversal reversal = new AuthReversal();
		reversal.setLitleTxnId(12345678000L);
		reversal.setAmount(106L);
		reversal.setPayPalNotes("Notes");


		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?merchantId=\"54321\".*?<authReversal.*?<litleTxnId>12345678000</litleTxnId>.*?</authReversal>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.11' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authReversalResponse><litleTxnId>123</litleTxnId></authReversalResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		LitleOnlineRequest overrides = new LitleOnlineRequest();
		overrides.setMerchantId("54321");
		AuthReversalResponse authreversal = litle.authReversal(reversal, overrides);
		assertEquals(123L, authreversal.getLitleTxnId());
	}


	@Test
	public void testCapture() throws Exception {

		Capture capture = new Capture();
		capture.setLitleTxnId(123456000L);
		capture.setAmount(106L);
		capture.setPayPalNotes("Notes");

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<capture.*?<litleTxnId>123456000</litleTxnId>.*?</capture>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><captureResponse><litleTxnId>123</litleTxnId></captureResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		CaptureResponse captureresponse = litle.capture(capture);
		assertEquals(123L, captureresponse.getLitleTxnId());
	}

	@Test
	public void testCaptureWithOverrides() throws Exception {

		Capture capture = new Capture();
		capture.setLitleTxnId(123456000L);
		capture.setAmount(106L);
		capture.setPayPalNotes("Notes");
		
		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<password>supersecret</password>.*?<capture.*?<litleTxnId>123456000</litleTxnId>.*?</capture>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><captureResponse><litleTxnId>123</litleTxnId></captureResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		LitleOnlineRequest overrides = new LitleOnlineRequest();
		overrides.setAuthentication(new Authentication());
		overrides.getAuthentication().setPassword("supersecret");
		CaptureResponse captureresponse = litle.capture(capture, overrides);
		assertEquals(123L, captureresponse.getLitleTxnId());
	}


	@Test
	public void testCaptureGivenAuth() throws Exception {

		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(106L);
		capturegivenauth.setSecondaryAmount(10L);
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(12345L);
		capturegivenauth.setAuthInformation(authInfo);
		capturegivenauth.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		capturegivenauth.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
							matches(".*?<litleOnlineRequest.*?<captureGivenAuth.*?<secondaryAmount>10</secondaryAmount>.*?<card>.*?<number>4100000000000001</number>.*?</card>.*?</captureGivenAuth>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><captureGivenAuthResponse><litleTxnId>123</litleTxnId></captureGivenAuthResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		CaptureGivenAuthResponse capturegivenauthresponse = litle.captureGivenAuth(capturegivenauth);
		assertEquals(123L, capturegivenauthresponse.getLitleTxnId());
	}

	@Test
	public void testCaptureGivenAuthWithOverrides() throws Exception {

		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(106L);
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(12345L);
		capturegivenauth.setAuthInformation(authInfo);
		capturegivenauth.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		capturegivenauth.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
							matches(".*?<litleOnlineRequest.*?<user>neweruser</user>.*?<captureGivenAuth.*?<card>.*?<number>4100000000000001</number>.*?</card>.*?</captureGivenAuth>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><captureGivenAuthResponse><litleTxnId>123</litleTxnId></captureGivenAuthResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		LitleOnlineRequest overrides = new LitleOnlineRequest();
		overrides.setAuthentication(new Authentication());
		overrides.getAuthentication().setUser("neweruser");
		CaptureGivenAuthResponse capturegivenauthresponse = litle.captureGivenAuth(capturegivenauth, overrides);
		assertEquals(123L, capturegivenauthresponse.getLitleTxnId());
	}


	@Test
	public void testCredit() throws Exception {

		Credit credit = new Credit();
		credit.setAmount(106L);
        credit.setSecondaryAmount(10L);
		credit.setOrderId("12344");
		credit.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		credit.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<credit.*?<secondaryAmount>10</secondaryAmount>.*?<card>.*?<number>4100000000000001</number>.*?</card>.*?</credit>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><creditResponse><litleTxnId>123</litleTxnId></creditResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		CreditResponse creditresponse = litle.credit(credit);
		assertEquals(123L, creditresponse.getLitleTxnId());
	}

	@Test
	public void testEcheckCredit() throws Exception {
		EcheckCredit echeckcredit = new EcheckCredit();
		echeckcredit.setAmount(12L);
        echeckcredit.setSecondaryAmount(10L);
		echeckcredit.setLitleTxnId(123456789101112L);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<echeckCredit.*?<litleTxnId>123456789101112</litleTxnId>.*?<secondaryAmount>10</secondaryAmount>.*?</echeckCredit>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><echeckCreditResponse><litleTxnId>123</litleTxnId></echeckCreditResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		EcheckCreditResponse echeckcreditresponse = litle.echeckCredit(echeckcredit);
		assertEquals(123L, echeckcreditresponse.getLitleTxnId());
	}

	@Test
	public void testEcheckRedeposit() throws Exception {
		EcheckRedeposit echeckredeposit = new EcheckRedeposit();
		echeckredeposit.setLitleTxnId(123456L);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<echeckRedeposit.*?<litleTxnId>123456</litleTxnId>.*?</echeckRedeposit>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><echeckRedepositResponse><litleTxnId>123</litleTxnId></echeckRedepositResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		EcheckRedepositResponse echeckredepositresponse = litle.echeckRedeposit(echeckredeposit);
		assertEquals(123L, echeckredepositresponse.getLitleTxnId());
	}

	@Test
	public void testEcheckSale() throws Exception {
		EcheckSale echecksale = new EcheckSale();
		echecksale.setAmount(123456L);
        echecksale.setSecondaryAmount(10L);
		echecksale.setOrderId("12345");
		echecksale.setOrderSource(OrderSourceType.ECOMMERCE);
		EcheckType echeck = new EcheckType();
		echeck.setAccType(EcheckAccountTypeEnum.CHECKING);
		echeck.setAccNum("12345657890");
		echeck.setRoutingNum("123456789");
		echeck.setCheckNum("123455");
		echeck.setCcdPaymentInformation("1234567890");
		echecksale.setEcheck(echeck);
		Contact contact = new Contact();
		contact.setName("Bob");
		contact.setCity("lowell");
		contact.setState("MA");
		contact.setEmail("litle.com");
		echecksale.setBillToAddress(contact);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<echeckSale.*?<secondaryAmount>10</secondaryAmount>.*?<echeck>.*?<accNum>12345657890</accNum>.*?</echeck>.*?</echeckSale>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><echeckSalesResponse><litleTxnId>123</litleTxnId></echeckSalesResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		EcheckSalesResponse echecksaleresponse = litle.echeckSale(echecksale);
		assertEquals(123L, echecksaleresponse.getLitleTxnId());
	}

	@Test
	public void testEcheckVerification() throws Exception {
		EcheckVerification echeckverification = new EcheckVerification();
		echeckverification.setAmount(123456L);
		echeckverification.setOrderId("12345");
		echeckverification.setOrderSource(OrderSourceType.ECOMMERCE);
		EcheckType echeck = new EcheckType();
		echeck.setAccType(EcheckAccountTypeEnum.CHECKING);
		echeck.setAccNum("12345657890");
		echeck.setRoutingNum("123456789");
		echeck.setCheckNum("123455");
		echeckverification.setEcheck(echeck);
		Contact contact = new Contact();
		contact.setName("Bob");
		contact.setCity("lowell");
		contact.setState("MA");
		contact.setEmail("litle.com");
		echeckverification.setBillToAddress(contact);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<echeckVerification.*?<echeck>.*?<accNum>12345657890</accNum>.*?</echeck>.*?</echeckVerification>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><echeckVerificationResponse><litleTxnId>123</litleTxnId></echeckVerificationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		EcheckVerificationResponse echeckverificationresponse = litle.echeckVerification(echeckverification);
		assertEquals(123L, echeckverificationresponse.getLitleTxnId());
	}

	@Test
	public void testForceCapture() throws Exception {
		ForceCapture forcecapture = new ForceCapture();
		forcecapture.setAmount(106L);
		forcecapture.setSecondaryAmount(10L);
		forcecapture.setOrderId("12344");
		forcecapture.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		forcecapture.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<forceCapture.*?<secondaryAmount>10</secondaryAmount>.*?<card>.*?<number>4100000000000001</number>.*?</card>.*?</forceCapture>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><forceCaptureResponse><litleTxnId>123</litleTxnId></forceCaptureResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		ForceCaptureResponse forcecaptureresponse = litle.forceCapture(forcecapture);
		assertEquals(123L, forcecaptureresponse.getLitleTxnId());
	}

	@Test
	public void testSale() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setLitleTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		sale.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<sale.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</sale>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><saleResponse><litleTxnId>123</litleTxnId></saleResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		SaleResponse saleresponse = litle.sale(sale);
		assertEquals(123L, saleresponse.getLitleTxnId());
	}
	
	@Test
	public void testSale_withAndroidpay() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setLitleTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ANDROIDPAY);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		sale.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<sale reportGroup=\"Default Report Group\">"
										+ "<litleTxnId>123456</litleTxnId>"
										+ "<orderId>12344</orderId>"
										+ "<amount>106</amount>"
										+ "<orderSource>androidpay</orderSource>"
										+ "<card>"
										+ "<type>VI</type>"
										+ "<number>4100000000000002</number>"
										+ "<expDate>1210</expDate>"
										+ "</card>"
										+ "</sale>"
										+ "</litleOnlineRequest>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><saleResponse><litleTxnId>123</litleTxnId></saleResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		SaleResponse saleresponse = litle.sale(sale);
		assertEquals(123L, saleresponse.getLitleTxnId());
	}
	
	@Test
	public void testSale_withSepa() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setLitleTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		SepaDirectDebitType sepa = new SepaDirectDebitType();
		sepa.setMandateProvider("Vantiv");
		sepa.setSequenceType("OneTime");
		sepa.setMandateReference("1234567890");
		sepa.setMandateUrl("http://mandate.url");
		sepa.setMandateSignatureDate(new GregorianCalendar());
		sepa.setIban("Iban");
		sepa.setPreferredLanguage(CountryTypeEnum.CH);
		sale.setSepaDirectDebit(sepa);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<sale reportGroup=\"Default Report Group\">"
										+ "<litleTxnId>123456</litleTxnId>"
										+ "<orderId>12344</orderId>"
										+ "<amount>106</amount>"
										+ "<orderSource>ecommerce</orderSource>"
										+ "<sepaDirectDebit>"
										+ "<mandateProvider>Vantiv</mandateProvider>"
										+ "<sequenceType>OneTime</sequenceType>"
										+ "<mandateReference>1234567890</mandateReference>"
										+ "<mandateUrl>http://mandate.url</mandateUrl>"
										+ "<mandateSignatureDate>.*?</mandateSignatureDate>"
										+ "<iban>Iban</iban>"
										+ "<preferredLanguage>CH</preferredLanguage>"
										+ "</sepaDirectDebit>"
										+ "</sale>"
										+ "</litleOnlineRequest>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><saleResponse><litleTxnId>123</litleTxnId></saleResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		SaleResponse saleresponse = litle.sale(sale);
		assertEquals(123L, saleresponse.getLitleTxnId());
	}
	
	@Test
    public void testSaleWithApplepayAndSecondaryAmountAndWallet() throws Exception {
        Sale sale = new Sale();
        sale.setAmount(106L);
        sale.setSecondaryAmount(10L);
        sale.setLitleTxnId(123456L);
        sale.setOrderId("12344");
        sale.setOrderSource(OrderSourceType.ECOMMERCE);
        ApplepayType applepayType = new ApplepayType();
        ApplepayHeaderType applepayHeaderType = new ApplepayHeaderType();
        applepayHeaderType.setApplicationData("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setEphemeralPublicKey("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setPublicKeyHash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setTransactionId("1234");
        applepayType.setHeader(applepayHeaderType);
        applepayType.setData("user");
        applepayType.setSignature("sign");
        applepayType.setVersion("1");
        sale.setApplepay(applepayType);

        Wallet wallet = new Wallet();
        wallet.setWalletSourceType(WalletSourceType.MASTER_PASS);
        wallet.setWalletSourceTypeId("123");
        sale.setWallet(wallet);
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<sale.*?<secondaryAmount>10</secondaryAmount>.*?<applepay>.*?<data>user</data>.*?</applepay>.*?<wallet>.*?<walletSourceTypeId>123</walletSourceTypeId>.*?</wallet>.*?</sale>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><saleResponse><litleTxnId>123</litleTxnId><applepayResponse><applicationPrimaryAccountNumber>123455</applicationPrimaryAccountNumber></applepayResponse></saleResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        SaleResponse saleresponse = litle.sale(sale);
        assertEquals(123L, saleresponse.getLitleTxnId());
        assertEquals("123455", saleresponse.getApplepayResponse().getApplicationPrimaryAccountNumber());
    }

	@Test
	public void testToken() throws Exception {
		RegisterTokenRequestType token = new RegisterTokenRequestType();
		token.setOrderId("12344");
		token.setAccountNumber("1233456789103801");

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<registerTokenRequest.*?<accountNumber>1233456789103801</accountNumber>.*?</registerTokenRequest>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><registerTokenResponse><litleTxnId>123</litleTxnId></registerTokenResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		RegisterTokenResponse registertokenresponse = litle.registerToken(token);
		assertEquals(123L, registertokenresponse.getLitleTxnId());
	}
	
	@Test
    public void testTokenWithApplepay() throws Exception {
        RegisterTokenRequestType token = new RegisterTokenRequestType();
        token.setOrderId("12344");
        ApplepayType applepayType = new ApplepayType();
        ApplepayHeaderType applepayHeaderType = new ApplepayHeaderType();
        applepayHeaderType.setApplicationData("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setEphemeralPublicKey("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setPublicKeyHash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setTransactionId("1234");
        applepayType.setHeader(applepayHeaderType);
        applepayType.setData("user");
        applepayType.setSignature("sign");
        applepayType.setVersion("1");
        token.setApplepay(applepayType);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<registerTokenRequest.*?<applepay>.*?<data>user</data>.*?</applepay>.*?</registerTokenRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><registerTokenResponse><litleTxnId>123</litleTxnId><applepayResponse><applicationPrimaryAccountNumber>123455</applicationPrimaryAccountNumber></applepayResponse></registerTokenResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        RegisterTokenResponse registertokenresponse = litle.registerToken(token);
        assertEquals(123L, registertokenresponse.getLitleTxnId());
        assertEquals("123455", registertokenresponse.getApplepayResponse().getApplicationPrimaryAccountNumber());
    }

	@Test
	public void testLitleOnlineException() throws Exception {

		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authorization.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='1' message='Error validating xml data against the schema' xmlns='http://www.litle.com/schema'><authorizationResponse><litleTxnId>123</litleTxnId></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		try{
		litle.authorize(authorization);
		fail("Expected Exception");
		} catch(LitleOnlineException e){
			assertEquals("Error validating xml data against the schema", e.getMessage());
		}
	}

	@Test
	public void testJAXBException() throws Exception {

		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authorization.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"no xml");
		litle.setCommunication(mockedCommunication);
		try{
		litle.authorize(authorization);
		fail("Expected Exception");
		} catch(LitleOnlineException e){
			assertEquals("Error validating xml data against the schema", e.getMessage());
		}
	}

	@Test
	public void testDefaultReportGroup() throws Exception {


		Authorization authorization = new Authorization();
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authorization.*? reportGroup=\"Default Report Group\">.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse reportGroup='Default Report Group'></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		AuthorizationResponse authorize = litle.authorize(authorization);
		assertEquals("Default Report Group", authorize.getReportGroup());
	}

	@Test
	public void testOverrideLoggedInUser() throws Exception {
		Properties config = new Properties();
		config.setProperty("loggedInUser", "avig");
		litle = new LitleOnline(config);
		Authorization authorization = new Authorization();
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?loggedInUser=\"avig\".*?<authorization.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse reportGroup='Default Report Group'></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		AuthorizationResponse authorize = litle.authorize(authorization);
		assertEquals("Default Report Group", authorize.getReportGroup());
	}


	@Test
	public void testEcheckVoid() throws Exception {
		EcheckVoid echeckvoid = new EcheckVoid();
		echeckvoid.setLitleTxnId(12345L);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<echeckVoid.*?<litleTxnId>12345</litleTxnId>.*?</echeckVoid>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><echeckVoidResponse><litleTxnId>123</litleTxnId></echeckVoidResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		EcheckVoidResponse echeckvoidresponse = litle.echeckVoid(echeckvoid);
		assertEquals(123L, echeckvoidresponse.getLitleTxnId());
	}

	@Test
	public void test_CustomerInfo_dob() throws Exception {
		Authorization authorization = new Authorization();
		authorization.setReportGroup("Planets");
		authorization.setOrderId("12344");
		authorization.setAmount(106L);
		authorization.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000002");
		card.setExpDate("1210");
		authorization.setCard(card);
		CustomerInfo customerInfo = new CustomerInfo();
		Calendar c = Calendar.getInstance();
		c.set(1980, Calendar.APRIL, 14);
		customerInfo.setDob(c);
		authorization.setCustomerInfo(customerInfo);

		Communication mockedCommunication = mock(Communication.class);
		when(
				mockedCommunication
						.requestToServer(
								matches(".*?<litleOnlineRequest.*?<authorization.*?<dob>1980-04-14</dob>.*?</authorization>.*?"),
								any(Properties.class)))
				.thenReturn(
						"<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><authorizationResponse><litleTxnId>123</litleTxnId></authorizationResponse></litleOnlineResponse>");
		litle.setCommunication(mockedCommunication);
		AuthorizationResponse authorize = litle.authorize(authorization);
		assertEquals(123L, authorize.getLitleTxnId());
	}

	@Test(expected=LitleOnlineException.class)
	public void testSendToLitleNamespaceHotswap() {
	    Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                any(String.class),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version=\"1.0\" xmlns=\"http://www.litle.com/schema/online\" response=\"1\" message=\"System Error - Call Litle &amp; Co.\"></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);


        CardType card = new CardType();
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        card.setType(MethodOfPaymentTypeEnum.VI);

        Sale sale = new Sale();
        sale.setReportGroup("Planets");
        sale.setOrderId("12344");
        sale.setAmount(6000L);
        sale.setOrderSource(OrderSourceType.ECOMMERCE);
        sale.setCard(card);

        litle.sale(sale);
	}

    @Test
    public void testCancelSubscription() throws Exception {
        CancelSubscription cancel = new CancelSubscription();
        cancel.setSubscriptionId(12345L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<cancelSubscription><subscriptionId>12345</subscriptionId></cancelSubscription></litleOnlineRequest>*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><cancelSubscriptionResponse><subscriptionId>12345</subscriptionId></cancelSubscriptionResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        CancelSubscriptionResponse cancelResponse = litle.cancelSubscription(cancel);
        assertEquals(12345L, cancelResponse.getSubscriptionId());
    }

    @Test
    public void testCancelSubscriptionWithOverrides() throws Exception {

        CancelSubscription cancel = new CancelSubscription();
        cancel.setSubscriptionId(12345L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"9001\".*?<cancelSubscription><subscriptionId>12345</subscriptionId></cancelSubscription></litleOnlineRequest>*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><cancelSubscriptionResponse><subscriptionId>12345</subscriptionId></cancelSubscriptionResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("9001");
        CancelSubscriptionResponse cancelResponse = litle.cancelSubscription(cancel, overrides);
        assertEquals(12345L, cancelResponse.getSubscriptionId());
    }

    @Test
    public void testUpdateSubscription() throws Exception {
        UpdateSubscription update = new UpdateSubscription();
        Calendar c = Calendar.getInstance();
        c.set(2013, Calendar.AUGUST, 7);
        update.setBillingDate(c);
        Contact billToAddress = new Contact();
        billToAddress.setName("Greg Dake");
        billToAddress.setCity("Lowell");
        billToAddress.setState("MA");
        billToAddress.setEmail("sdksupport@litle.com");
        update.setBillToAddress(billToAddress);
        CardType card = new CardType();
        card.setNumber("4100000000000001");
        card.setExpDate("1215");
        card.setType(MethodOfPaymentTypeEnum.VI);
        update.setCard(card);
        update.setPlanCode("abcdefg");
        update.setSubscriptionId(12345L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<updateSubscription><subscriptionId>12345</subscriptionId><planCode>abcdefg</planCode><billToAddress><name>Greg Dake</name><city>Lowell</city><state>MA</state><email>sdksupport@litle.com</email></billToAddress><card><type>VI</type><number>4100000000000001</number><expDate>1215</expDate></card><billingDate>2013-08-07</billingDate></updateSubscription></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><updateSubscriptionResponse><subscriptionId>12345</subscriptionId></updateSubscriptionResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        UpdateSubscriptionResponse updateResponse = litle.updateSubscription(update);
        assertEquals(12345L, updateResponse.getSubscriptionId());
    }

    @Test
    public void testUpdateSubscriptionWithOverrides() throws Exception {
        UpdateSubscription update = new UpdateSubscription();
        Calendar c = Calendar.getInstance();
        c.set(2013, Calendar.AUGUST, 7);
        update.setBillingDate(c);
        Contact billToAddress = new Contact();
        billToAddress.setName("Greg Dake");
        billToAddress.setCity("Lowell");
        billToAddress.setState("MA");
        billToAddress.setEmail("sdksupport@litle.com");
        update.setBillToAddress(billToAddress);
        CardType card = new CardType();
        card.setNumber("4100000000000001");
        card.setExpDate("1215");
        card.setType(MethodOfPaymentTypeEnum.VI);
        update.setCard(card);
        update.setPlanCode("abcdefg");
        update.setSubscriptionId(12345L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<updateSubscription><subscriptionId>12345</subscriptionId><planCode>abcdefg</planCode><billToAddress><name>Greg Dake</name><city>Lowell</city><state>MA</state><email>sdksupport@litle.com</email></billToAddress><card><type>VI</type><number>4100000000000001</number><expDate>1215</expDate></card><billingDate>2013-08-07</billingDate></updateSubscription></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><updateSubscriptionResponse><subscriptionId>12345</subscriptionId></updateSubscriptionResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        UpdateSubscriptionResponse updateResponse = litle.updateSubscription(update, overrides);
        assertEquals(12345L, updateResponse.getSubscriptionId());
    }

    @Test
    public void testUpdatePlan() throws Exception {
        UpdatePlan update = new UpdatePlan();
        update.setActive(true);
        update.setPlanCode("abc");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<updatePlan><planCode>abc</planCode><active>true</active></updatePlan></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><updatePlanResponse><planCode>abc</planCode></updatePlanResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        UpdatePlanResponse updateResponse = litle.updatePlan(update);
        assertEquals("abc", updateResponse.getPlanCode());
    }

    @Test
    public void testUpdatePlanWithOverrides() throws Exception {
        UpdatePlan update = new UpdatePlan();
        update.setActive(true);
        update.setPlanCode("abc");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<updatePlan><planCode>abc</planCode><active>true</active></updatePlan></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.20' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><updatePlanResponse><planCode>abc</planCode></updatePlanResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        UpdatePlanResponse updateResponse = litle.updatePlan(update, overrides);
        assertEquals("abc", updateResponse.getPlanCode());
    }

    @Test
    public void testCreatePlan() throws Exception {
        CreatePlan create = new CreatePlan();
        create.setPlanCode("abc");
        create.setActive(true);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<createPlan><planCode>abc</planCode><active>true</active></createPlan></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><createPlanResponse><planCode>abc</planCode></createPlanResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        CreatePlanResponse createResponse = litle.createPlan(create);
        assertEquals("abc", createResponse.getPlanCode());
    }

    @Test
    public void testCreatePlanWithOverrides() throws Exception {
        CreatePlan create = new CreatePlan();
        create.setPlanCode("abc");
        create.setActive(true);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<createPlan><planCode>abc</planCode><active>true</active></createPlan></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><createPlanResponse><planCode>abc</planCode></createPlanResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        CreatePlanResponse createResponse = litle.createPlan(create, overrides);
        assertEquals("abc", createResponse.getPlanCode());
    }

    @Test
    public void testActivate() throws Exception {
        Activate activate = new Activate();
        activate.setAmount(100L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<activate><amount>100</amount></activate></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><activateResponse><litleTxnId>123456</litleTxnId></activateResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        ActivateResponse response = litle.activate(activate);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testActivateWithOverrides() throws Exception {
        Activate activate = new Activate();
        activate.setAmount(100L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<activate><amount>100</amount></activate></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><activateResponse><litleTxnId>123456</litleTxnId></activateResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        ActivateResponse response = litle.activate(activate, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDectivate() throws Exception {
        Deactivate deactivate = new Deactivate();
        deactivate.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<deactivate><orderId>123</orderId></deactivate></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><deactivateResponse><litleTxnId>123456</litleTxnId></deactivateResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        DeactivateResponse response = litle.deactivate(deactivate);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDeactivateWithOverrides() throws Exception {
        Deactivate deactivate = new Deactivate();
        deactivate.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<deactivate><orderId>123</orderId></deactivate></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><deactivateResponse><litleTxnId>123456</litleTxnId></deactivateResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        DeactivateResponse response = litle.deactivate(deactivate, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testLoad() throws Exception {
        Load load = new Load();
        load.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<load><orderId>123</orderId></load></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><loadResponse><litleTxnId>123456</litleTxnId></loadResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LoadResponse response = litle.load(load);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testLoadWithOverrides() throws Exception {
        Load load = new Load();
        load.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<load><orderId>123</orderId></load></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><loadResponse><litleTxnId>123456</litleTxnId></loadResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        LoadResponse response = litle.load(load, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testUnload() throws Exception {
        Unload unload = new Unload();
        unload.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<unload><orderId>123</orderId></unload></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><unloadResponse><litleTxnId>123456</litleTxnId></unloadResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        UnloadResponse response = litle.unload(unload);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testUnloadWithOverrides() throws Exception {
        Unload unload = new Unload();
        unload.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<unload><orderId>123</orderId></unload></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><unloadResponse><litleTxnId>123456</litleTxnId></unloadResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        UnloadResponse response = litle.unload(unload, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testBalanceInquiry() throws Exception {
        BalanceInquiry balanceInquiry = new BalanceInquiry();
        balanceInquiry.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<balanceInquiry><orderId>123</orderId></balanceInquiry></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><balanceInquiryResponse><litleTxnId>123456</litleTxnId></balanceInquiryResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        BalanceInquiryResponse response = litle.balanceInquiry(balanceInquiry);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testBalanceInquiryWithOverrides() throws Exception {
        BalanceInquiry balanceInquiry = new BalanceInquiry();
        balanceInquiry.setOrderId("123");

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?<balanceInquiry><orderId>123</orderId></balanceInquiry></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><balanceInquiryResponse><litleTxnId>123456</litleTxnId></balanceInquiryResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        BalanceInquiryResponse response = litle.balanceInquiry(balanceInquiry, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testActivateReversal() throws Exception {
        ActivateReversal activateReversal = new ActivateReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0502");
        giftCard.setNumber("5400000000000000");
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setPin("1234");
        activateReversal.setLitleTxnId(123L);
        activateReversal.setCard(giftCard);
        activateReversal.setId("id");
        activateReversal.setOriginalRefCode("3");
        activateReversal.setOriginalSequenceNumber("999999");
        activateReversal.setOriginalSystemTraceId(5);
        activateReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<activateReversal id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>5400000000000000</number>"
                                		+ "<expDate>0502</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>1234</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>3</originalRefCode>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>5</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>999999</originalSequenceNumber>"
                                		+ "</activateReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><activateReversalResponse><litleTxnId>123456</litleTxnId></activateReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        ActivateReversalResponse response = litle.activateReversal(activateReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testActivateReversalWithOverrides() throws Exception {
        ActivateReversal activateReversal = new ActivateReversal();
        activateReversal.setLitleTxnId(123L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<activateReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</activateReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><activateReversalResponse><litleTxnId>123456</litleTxnId></activateReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        ActivateReversalResponse response = litle.activateReversal(activateReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDeactivateReversal() throws Exception {
        DeactivateReversal deactivateReversal = new DeactivateReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0502");
        giftCard.setNumber("5400000000000000");
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setPin("1234");
        deactivateReversal.setLitleTxnId(123L);
        deactivateReversal.setCard(giftCard);
        deactivateReversal.setId("id");
        deactivateReversal.setOriginalRefCode("3");
        deactivateReversal.setOriginalSequenceNumber("999999");
        deactivateReversal.setOriginalSystemTraceId(5);
        deactivateReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<deactivateReversal id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>5400000000000000</number>"
                                		+ "<expDate>0502</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>1234</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>3</originalRefCode>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>5</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>999999</originalSequenceNumber>"
                                		+ "</deactivateReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><deactivateReversalResponse><litleTxnId>123456</litleTxnId></deactivateReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        DeactivateReversalResponse response = litle.deactivateReversal(deactivateReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDeActivateReversalWithOverrides() throws Exception {
        DeactivateReversal deactivateReversal = new DeactivateReversal();
        deactivateReversal.setLitleTxnId(123l);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest merchantId=\"905\" merchantSdk=.*version=\"11.2\" xmlns=\"http://www.litle.com/schema\">.*?"
                                		+ "<deactivateReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</deactivateReversal"
                                		+ "></litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><deactivateReversalResponse><litleTxnId>123456</litleTxnId></deactivateReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        DeactivateReversalResponse response = litle.deactivateReversal(deactivateReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testLoadReversal() throws Exception {
        LoadReversal loadReversal = new LoadReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0502");
        giftCard.setNumber("5400000000000000");
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setPin("1234");
        loadReversal.setLitleTxnId(123L);
        loadReversal.setCard(giftCard);
        loadReversal.setId("id");
        loadReversal.setOriginalAmount(45l);
        loadReversal.setOriginalRefCode("3");
        loadReversal.setOriginalSequenceNumber("999999");
        loadReversal.setOriginalSystemTraceId(5);
        loadReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<loadReversal id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>5400000000000000</number>"
                                		+ "<expDate>0502</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>1234</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>3</originalRefCode>"
                                		+ "<originalAmount>45</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>5</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>999999</originalSequenceNumber>"
                                		+ "</loadReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><loadReversalResponse><litleTxnId>123456</litleTxnId></loadReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LoadReversalResponse response = litle.loadReversal(loadReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testLoadReversalWithOverrides() throws Exception {
        LoadReversal loadReversal = new LoadReversal();
        loadReversal.setLitleTxnId(123L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<loadReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</loadReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><loadReversalResponse><litleTxnId>123456</litleTxnId></loadReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        LoadReversalResponse response = litle.loadReversal(loadReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testUnLoadReversal() throws Exception {
        UnloadReversal unloadReversal = new UnloadReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0502");
        giftCard.setNumber("5400000000000000");
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setPin("1234");
        unloadReversal.setLitleTxnId(123L);
        unloadReversal.setCard(giftCard);
        unloadReversal.setId("id");
        unloadReversal.setOriginalRefCode("3");
        unloadReversal.setOriginalSequenceNumber("999999");
        unloadReversal.setOriginalSystemTraceId(5);
        unloadReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<unloadReversal id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>5400000000000000</number>"
                                		+ "<expDate>0502</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>1234</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>3</originalRefCode>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>5</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>999999</originalSequenceNumber>"
                                		+ "</unloadReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><unloadReversalResponse><litleTxnId>123456</litleTxnId></unloadReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        UnloadReversalResponse response = litle.unloadReversal(unloadReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testUnLoadReversalWithOverrides() throws Exception {
        UnloadReversal unloadReversal = new UnloadReversal();
        unloadReversal.setLitleTxnId(123L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<unloadReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</unloadReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><unloadReversalResponse><litleTxnId>123456</litleTxnId></unloadReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        UnloadReversalResponse response = litle.unloadReversal(unloadReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testRefundReversal() throws Exception {
        RefundReversal refundReversal = new RefundReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        giftCard.setTrack("track data goes here");
        
        refundReversal.setLitleTxnId(123L);
        refundReversal.setId("id");
        refundReversal.setReportGroup("rptGrp");
        refundReversal.setCard(giftCard);
        refundReversal.setOriginalRefCode("ref");
        refundReversal.setOriginalAmount(44455l);
        refundReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        refundReversal.setOriginalSystemTraceId(3);
        refundReversal.setOriginalSequenceNumber("222222");
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<refundReversal reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<track>track data goes here</track>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card><originalRefCode>ref</originalRefCode>"
                                		+ "<originalAmount>44455</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>3</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>222222</originalSequenceNumber>"
                                		+ "</refundReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><refundReversalResponse><litleTxnId>123456</litleTxnId></refundReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        RefundReversalResponse response = litle.refundReversal(refundReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }
    
    @Test
    public void testRefundReversalWithOverrides() throws Exception {
        RefundReversal refundReversal = new RefundReversal();
        refundReversal.setLitleTxnId(123L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<refundReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</refundReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><refundReversalResponse><litleTxnId>123456</litleTxnId></refundReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        RefundReversalResponse response = litle.refundReversal(refundReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDepositReversal() throws Exception {
        DepositReversal depositReversal = new DepositReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setNumber("400000000000001");
        giftCard.setExpDate("0150");
        giftCard.setCardValidationNum("411");
        giftCard.setPin("1234");
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        
        depositReversal.setLitleTxnId(123L);
        depositReversal.setId("id");
        depositReversal.setReportGroup("Planets");
        depositReversal.setCard(giftCard);
        depositReversal.setOriginalRefCode("101");
        depositReversal.setOriginalAmount(3456l);
        depositReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        depositReversal.setOriginalSystemTraceId(33);
        depositReversal.setOriginalSequenceNumber("111111");
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<depositReversal reportGroup=\"Planets\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>400000000000001</number>"
                                		+ "<expDate>0150</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>1234</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>101</originalRefCode>"
                                		+ "<originalAmount>3456</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>33</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>111111</originalSequenceNumber>"
                                		+ "</depositReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><depositReversalResponse><litleTxnId>123456</litleTxnId></depositReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        DepositReversalResponse response = litle.depositReversal(depositReversal);
        assertEquals(123456L, response.getLitleTxnId());
    }

    @Test
    public void testDepositReversalWithOverrides() throws Exception {
        DepositReversal depositReversal = new DepositReversal();
        depositReversal.setLitleTxnId(123L);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<depositReversal>"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<originalSystemTraceId>0</originalSystemTraceId>"
                                		+ "</depositReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.21' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><depositReversalResponse><litleTxnId>123456</litleTxnId></depositReversalResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        DepositReversalResponse response = litle.depositReversal(depositReversal, overrides);
        assertEquals(123456L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardAuthReversal() throws Exception {
        GiftCardAuthReversal gcAuthReversal = new GiftCardAuthReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcAuthReversal.setLitleTxnId(123L);
        gcAuthReversal.setId("id");
        gcAuthReversal.setReportGroup("rptGrp");
        gcAuthReversal.setCard(giftCard);
        gcAuthReversal.setOriginalRefCode("ref");
        gcAuthReversal.setOriginalAmount(44455l);
        gcAuthReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        gcAuthReversal.setOriginalSystemTraceId(3);
        gcAuthReversal.setOriginalSequenceNumber("222222");
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<giftCardAuthReversal reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>ref</originalRefCode>"
                                		+ "<originalAmount>44455</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>3</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>222222</originalSequenceNumber>"
                                		+ "</giftCardAuthReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                        + "<giftCardAuthReversalResponse id=\"id\" reportGroup=\"rptGrp\">"
                        + "<litleTxnId>21825673457518565</litleTxnId>"
                        + "<response>330</response>"
                        + "<responseTime>2016-11-21T18:08:48</responseTime>"
                        + "<postDate>2016-11-22</postDate>"
                        + "<message>Invalid Payment Type</message>"
                        + "<giftCardResponse>"
                        + "<txnTime>2016-11-21T13:08:48</txnTime>"
                        + "<systemTraceId>0</systemTraceId>"
                        + "</giftCardResponse>"
                        + "</giftCardAuthReversalResponse>"
                        + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        GiftCardAuthReversalResponse response = litle.giftCardAuthReversal(gcAuthReversal);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardAuthReversal_withOverrides() throws Exception {
        GiftCardAuthReversal gcAuthReversal = new GiftCardAuthReversal();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcAuthReversal.setLitleTxnId(123L);
        gcAuthReversal.setId("id");
        gcAuthReversal.setReportGroup("rptGrp");
        gcAuthReversal.setCard(giftCard);
        gcAuthReversal.setOriginalRefCode("ref");
        gcAuthReversal.setOriginalAmount(44455l);
        gcAuthReversal.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        gcAuthReversal.setOriginalSystemTraceId(3);
        gcAuthReversal.setOriginalSequenceNumber("222222");
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                                		+ "<giftCardAuthReversal reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>ref</originalRefCode>"
                                		+ "<originalAmount>44455</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "<originalSystemTraceId>3</originalSystemTraceId>"
                                		+ "<originalSequenceNumber>222222</originalSequenceNumber>"
                                		+ "</giftCardAuthReversal>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                        + "<giftCardAuthReversalResponse id=\"id\" reportGroup=\"rptGrp\">"
                        + "<litleTxnId>21825673457518565</litleTxnId>"
                        + "<response>330</response>"
                        + "<responseTime>2016-11-21T18:08:48</responseTime>"
                        + "<postDate>2016-11-22</postDate>"
                        + "<message>Invalid Payment Type</message>"
                        + "<giftCardResponse>"
                        + "<txnTime>2016-11-21T13:08:48</txnTime>"
                        + "<systemTraceId>0</systemTraceId>"
                        + "</giftCardResponse>"
                        + "</giftCardAuthReversalResponse>"
                        + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        GiftCardAuthReversalResponse response = litle.giftCardAuthReversal(gcAuthReversal, overrides);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardCapture() throws Exception {
        GiftCardCapture gcCapture = new GiftCardCapture();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcCapture.setLitleTxnId(123L);
        gcCapture.setId("id");
        gcCapture.setReportGroup("rptGrp");
        gcCapture.setCaptureAmount(2434l);
        gcCapture.setCard(giftCard);
        gcCapture.setOriginalRefCode("ref");
        gcCapture.setOriginalAmount(44455l);
        gcCapture.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<giftCardCapture reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<captureAmount>2434</captureAmount>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>ref</originalRefCode>"
                                		+ "<originalAmount>44455</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "</giftCardCapture>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                        + "<giftCardCaptureResponse id=\"id\" reportGroup=\"rptGrp\">"
                        + "<litleTxnId>21825673457518565</litleTxnId>"
                        + "<response>330</response>"
                        + "<responseTime>2016-11-21T18:35:55</responseTime>"
                        + "<postDate>2016-11-22</postDate>"
                        + "<message>Invalid Payment Type</message>"
                        + "<giftCardResponse>"
                        + "<txnTime>2016-11-21T13:35:55</txnTime>"
                        + "<systemTraceId>0</systemTraceId>"
                        + "</giftCardResponse>"
                        + "</giftCardCaptureResponse>"
                        + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        GiftCardCaptureResponse response = litle.giftCardCapture(gcCapture);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardCapture_withOverrides() throws Exception {
    	GiftCardCapture gcCapture = new GiftCardCapture();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcCapture.setLitleTxnId(123L);
        gcCapture.setId("id");
        gcCapture.setReportGroup("rptGrp");
        gcCapture.setCaptureAmount(2434l);
        gcCapture.setCard(giftCard);
        gcCapture.setOriginalRefCode("ref");
        gcCapture.setOriginalAmount(44455l);
        gcCapture.setOriginalTxnTime(new XMLGregorianCalendarImpl());
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                        		matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                        				+ "<giftCardCapture reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<captureAmount>2434</captureAmount>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "<originalRefCode>ref</originalRefCode>"
                                		+ "<originalAmount>44455</originalAmount>"
                                		+ "<originalTxnTime></originalTxnTime>"
                                		+ "</giftCardCapture>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                		"<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                                + "<giftCardCaptureResponse id=\"id\" reportGroup=\"rptGrp\">"
                                + "<litleTxnId>21825673457518565</litleTxnId>"
                                + "<response>330</response>"
                                + "<responseTime>2016-11-21T18:35:55</responseTime>"
                                + "<postDate>2016-11-22</postDate>"
                                + "<message>Invalid Payment Type</message>"
                                + "<giftCardResponse>"
                                + "<txnTime>2016-11-21T13:35:55</txnTime>"
                                + "<systemTraceId>0</systemTraceId>"
                                + "</giftCardResponse>"
                                + "</giftCardCaptureResponse>"
                                + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        GiftCardCaptureResponse response = litle.giftCardCapture(gcCapture, overrides);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardCredit() throws Exception {
        GiftCardCredit gcCredit = new GiftCardCredit();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcCredit.setLitleTxnId(123L);
        gcCredit.setId("id");
        gcCredit.setReportGroup("rptGrp");
        gcCredit.setCreditAmount(3399l);
        gcCredit.setCard(giftCard);
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<giftCardCredit reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<creditAmount>3399</creditAmount>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "</giftCardCredit>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                        + "<giftCardCreditResponse id=\"id\" reportGroup=\"rptGrp\">"
                        + "<litleTxnId>21825673457518565</litleTxnId>"
                        + "<response>330</response>"
                        + "<responseTime>2016-11-21T18:54:41</responseTime>"
                        + "<postDate>2016-11-22</postDate>"
                        + "<message>Invalid Payment Type</message>"
                        + "<giftCardResponse>"
                        + "<txnTime>2016-11-21T13:54:41</txnTime>"
                        + "<systemTraceId>0</systemTraceId>"
                        + "</giftCardResponse>"
                        + "</giftCardCreditResponse>"
                        + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        GiftCardCreditResponse response = litle.giftCardCredit(gcCredit);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testGiftCardCredit_withOverrides() throws Exception {
    	GiftCardCredit gcCredit = new GiftCardCredit();
        GiftCardCardType giftCard = new GiftCardCardType();
        giftCard.setType(MethodOfPaymentTypeEnum.GC);
        giftCard.setCardValidationNum("411");
        giftCard.setExpDate("0655");
        giftCard.setNumber("4100000000000001");
        giftCard.setPin("9999");
        
        gcCredit.setLitleTxnId(123L);
        gcCredit.setId("id");
        gcCredit.setReportGroup("rptGrp");
        gcCredit.setCreditAmount(3399l);
        gcCredit.setCard(giftCard);
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                        		matches(".*?<litleOnlineRequest.*?merchantId=\"905\".*?"
                        				+ "<giftCardCredit reportGroup=\"rptGrp\" id=\"id\">"
                                		+ "<litleTxnId>123</litleTxnId>"
                                		+ "<creditAmount>3399</creditAmount>"
                                		+ "<card>"
                                		+ "<type>GC</type>"
                                		+ "<number>4100000000000001</number>"
                                		+ "<expDate>0655</expDate>"
                                		+ "<cardValidationNum>411</cardValidationNum>"
                                		+ "<pin>9999</pin>"
                                		+ "</card>"
                                		+ "</giftCardCredit>"
                                		+ "</litleOnlineRequest>.*?"),
                                any(Properties.class)))
                .thenReturn(
                		"<litleOnlineResponse version=\"11.2\" xmlns=\"http://www.litle.com/schema\" response=\"0\" message=\"Valid Format\">"
                                + "<giftCardCreditResponse id=\"id\" reportGroup=\"rptGrp\">"
                                + "<litleTxnId>21825673457518565</litleTxnId>"
                                + "<response>330</response>"
                                + "<responseTime>2016-11-21T18:54:41</responseTime>"
                                + "<postDate>2016-11-22</postDate>"
                                + "<message>Invalid Payment Type</message>"
                                + "<giftCardResponse>"
                                + "<txnTime>2016-11-21T13:54:41</txnTime>"
                                + "<systemTraceId>0</systemTraceId>"
                                + "</giftCardResponse>"
                                + "</giftCardCreditResponse>"
                                + "</litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        LitleOnlineRequest overrides = new LitleOnlineRequest();
        overrides.setMerchantId("905");
        GiftCardCreditResponse response = litle.giftCardCredit(gcCredit, overrides);
        assertEquals(21825673457518565L, response.getLitleTxnId());
    }
    
    @Test
    public void testRecurring() throws Exception{
        Sale sale = new Sale();
        sale.setAmount(106L);
        sale.setLitleTxnId(123456L);
        sale.setOrderId("12344");
        sale.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000002");
        card.setExpDate("1210");
        sale.setCard(card);
        RecurringRequestType recuring = new RecurringRequestType();
        RecurringSubscriptionType sub = new RecurringSubscriptionType();
        sub.setPlanCode("12345");
        sub.setNumberOfPayments(12);
        sub.setStartDate(Calendar.getInstance());
        sub.setAmount(1000L);
        CreateAddOnType cat = new CreateAddOnType();
        cat.setAddOnCode("1234");
        cat.setAmount(500L);
        cat.setEndDate(Calendar.getInstance());
        cat.setName("name");
        cat.setEndDate(Calendar.getInstance());
        sub.getCreateAddOns().add(cat);
        recuring.setSubscription(sub);
        sale.setRecurringRequest(recuring);

        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<sale.*?<card>.*?<number>4100000000000002</number>.*?</card>.*?<createAddOn>.*?</createAddOn>.*?</sale>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='8.10' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><saleResponse><litleTxnId>123</litleTxnId></saleResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);
        SaleResponse saleresponse = litle.sale(sale);
        assertEquals(123L, saleresponse.getLitleTxnId());
        
    }
    
    @Test
    public void testQueryTransactionResponse_notFound() {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("1234");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("org1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        
        Communication mockedComm = mock (Communication.class);
        when(mockedComm.requestToServer(matches(".*?<litleOnlineRequest.*?<queryTransaction.*id=\"1234\".*?customerId=\"customerId\".*?<origId>org1</origId>.*?<origActionType>A</origActionType>.*?"),
                any(Properties.class))).thenReturn("<litleOnlineResponse version='11.2' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><queryTransactionResponse id='1234' customerId='customerId'> <response>150</response> <responseTime>2015-04-14T12:37:26</responseTime> <message>Original transaction not found</message><matchCount>0</matchCount></queryTransactionResponse></litleOnlineResponse>");
        
        litle.setCommunication(mockedComm);
        TransactionTypeWithReportGroup response = litle.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("1234", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        
    }
    
    @Test
    public void testQueryTransactionResponse_transactionFound() {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("findId");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("org1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        
        
        
        Communication mockedComm = mock (Communication.class); 
        when(mockedComm.requestToServer(matches(".*?<litleOnlineRequest.*?<queryTransaction.*id=\"findId\".*?customerId=\"customerId\".*?<origId>org1</origId>.*?<origActionType>A</origActionType>.*?"),
                any(Properties.class))).thenReturn("<litleOnlineResponse version='11.2' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><queryTransactionResponse id='findId' customerId='customerId'> <response>150</response> <responseTime>2015-04-14T12:37:26</responseTime> " +
                		"<message>Original transaction found</message><matchCount>1</matchCount>" +
                		"<results_max10> <authorizationResponse id=\"findId\" > <litleTxnId>1111111</litleTxnId> <orderId>150306_auth</orderId> <response>000</response><responseTime>2015-04-14T12:37:23</responseTime><postDate>2015-04-14</postDate><message>Approved</message></authorizationResponse></results_max10></queryTransactionResponse></litleOnlineResponse>");
        
        litle.setCommunication(mockedComm);
        TransactionTypeWithReportGroup response = litle.queryTransaction(queryTransaction);
        QueryTransactionResponse queryTransactionResponse = (QueryTransactionResponse)response;
        assertEquals("findId", queryTransactionResponse.getId());
        assertEquals("customerId", queryTransactionResponse.getCustomerId());
        assertEquals("150", queryTransactionResponse.getResponse());
        assertEquals(1, queryTransactionResponse.getResultsMax10().getTransactionResponses().size());
        JAXBElement<?> authorization = queryTransactionResponse.getResultsMax10().getTransactionResponses().get(0);
        AuthorizationResponse authResponse = (AuthorizationResponse)authorization.getValue();
        assertEquals(1111111L,authResponse.getLitleTxnId());
    }
    
    @Test
    public void testQueryTransactionResponse_unavailable() {
        QueryTransaction queryTransaction = new QueryTransaction();
        queryTransaction.setId("1234");
        queryTransaction.setCustomerId("customerId");
        queryTransaction.setOrigId("org1");
        queryTransaction.setOrigActionType(ActionTypeEnum.A);
        
        Communication mockedComm = mock (Communication.class);
        when(mockedComm.requestToServer(matches(".*?<litleOnlineRequest.*?<queryTransaction.*id=\"1234\".*?customerId=\"customerId\".*?<origId>org1</origId>.*?<origActionType>A</origActionType>.*?"),
                any(Properties.class))).thenReturn("<litleOnlineResponse version='11.2' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><queryTransactionUnavailableResponse id='1234' customerId='customerId'><litleTxnId>123456</litleTxnId> <response>123</response> <message>Sample message</message></queryTransactionUnavailableResponse></litleOnlineResponse>");
        
        litle.setCommunication(mockedComm);
        TransactionTypeWithReportGroup response =litle.queryTransaction(queryTransaction);
        QueryTransactionUnavailableResponse unavailableResponse = (QueryTransactionUnavailableResponse)response;
        assertEquals("1234", unavailableResponse.getId());
        assertEquals(123456L,unavailableResponse.getLitleTxnId());
        assertEquals("Sample message", unavailableResponse.getMessage());
    }
    

    public void testFraudCheck() throws Exception{
        FraudCheck fraudCheck = new FraudCheck();
        AdvancedFraudChecksType advancedFraudChecks = new AdvancedFraudChecksType();
        advancedFraudChecks.setThreatMetrixSessionId("123");
        advancedFraudChecks.setCustomAttribute1("pass");
        advancedFraudChecks.setCustomAttribute2("42");
        advancedFraudChecks.setCustomAttribute3("5");
        fraudCheck.setAdvancedFraudChecks(advancedFraudChecks);
        
        
        Communication mockedCommunication = mock(Communication.class);
        when(
                mockedCommunication
                        .requestToServer(
                                matches(".*?<litleOnlineRequest.*?<fraudCheck.*?<advancedFraudChecks>.*?</advancedFraudChecks>.*?</fraudCheck>.*?"),
                                any(Properties.class)))
                .thenReturn(
                        "<litleOnlineResponse version='11.2' response='0' message='Valid Format' xmlns='http://www.litle.com/schema'><fraudCheckResponse id='' reportGroup='Default Report Group' customerId=''><litleTxnId>602413782865196123</litleTxnId><response>123</response><responseTime>2016-07-11T15:12:34</responseTime><message>Call Discover</message><advancedFraudResults><deviceReviewStatus>pass</deviceReviewStatus><deviceReputationScore>42</deviceReputationScore><triggeredRule>triggered_rule_1</triggeredRule><triggeredRule>triggered_rule_2</triggeredRule><triggeredRule>triggered_rule_3</triggeredRule><triggeredRule>triggered_rule_4</triggeredRule><triggeredRule>triggered_rule_5</triggeredRule></advancedFraudResults></fraudCheckResponse></litleOnlineResponse>");
        litle.setCommunication(mockedCommunication);        
        
        FraudCheckResponse fraudCheckResponse = litle.fraudCheck(fraudCheck);
        AdvancedFraudResultsType advancedFraudResultsType = fraudCheckResponse.getAdvancedFraudResults();
        assertEquals("pass", advancedFraudResultsType.getDeviceReviewStatus());
        assertEquals(new Integer(42), advancedFraudResultsType.getDeviceReputationScore());
        assertEquals(5, advancedFraudResultsType.getTriggeredRules().size());
    }

}
