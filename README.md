Shopping Checkout System : Solution Approach, Technical Choices and Design
===============================

I have attempted the problem statement for DIUS Shopping Checkout System at : https://github.com/DiUS/coding-tests/blob/master/dius_shopping.md

I have Used Java 8 for programming the solution with object oriented approach and Junit 4.12 for Unit Testing. It contains no CLI or GUI. I only added Junit jars for testing purpose which can be added via maven's pom.xml as well. I had simply added junit 4.12 jar for the development purpose, for the sake of simplicity. Same can be done for the project to run test cases, while evaluating.
    
No sophisticated error handling, just exceptions.

- Domain Classes are used for different business entities. Catalogue is for items available in the store. Item is a POJO representing an item sold at the store. ItemData gives numerical 
  data about an item/product. SKU is the Enum of SKUs of products offered by the store.

- Model Classes are used to define various Pricing offer rules. A BasePricingRule Interface is created representing 
  a pricing rule algorithm that can be applied on to a shopping cart of items. I have used Strategy design pattern for this to keep it flexible 
  so that new offers can be added with respective implementation of the interface BasePricingRule. The 3 implementations pertaining to 
  the existing offers are BulkDiscountedRule (discounted price per item for items purchased in bulk), FreeOfChargeRule (offer of one item free with other item)
  and ThreeForTwoRule (3 for 2 offer implementation)
  
- CheckoutService contains the service methods for the application representing a Sales checkout counter.
  
- LoaderSingletonClass builds a catalog and pricing rules for items/products sold by using Singleton Design Pattern. CommonUtils helps in finding item in cart.
  
- The app can be run using Junit Tests provided under following classes:
    - CheckoutServiceUnitTest : Runs the example scenarios listed in the problem along with some other use cases.
    - OfferRulesUnitTest : Tests flows of the 3 Pricing Rules mentioned in the problem  
